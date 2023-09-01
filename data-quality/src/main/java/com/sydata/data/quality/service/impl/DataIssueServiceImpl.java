package com.sydata.data.quality.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sydata.basis.domain.Dict;
import com.sydata.basis.domain.StockHouse;
import com.sydata.basis.service.IDictService;
import com.sydata.basis.service.IStockHouseService;
import com.sydata.collect.service.IDataApiService;
import com.sydata.data.quality.domain.DataIssue;
import com.sydata.data.quality.domain.DataIssueDtl;
import com.sydata.data.quality.job.process.check.IDataIssueCheckClear;
import com.sydata.data.quality.mapper.DataIssueMapper;
import com.sydata.data.quality.param.DataIssueExportParam;
import com.sydata.data.quality.param.DataIssuePageParam;
import com.sydata.data.quality.service.IDataIssueDtlService;
import com.sydata.data.quality.service.IDataIssueService;
import com.sydata.data.quality.vo.DataIssueVo;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.excel.ExcelExportStylerZtImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.sydata.framework.excel.BaseZtExcelExport.EXCEL_SUFFIX_XLS;
import static com.sydata.framework.orm.FrameworkSqlHelper.executeBatchUpdate;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.*;

/**
 * 数据质量-数据问题Service业务层处理
 *
 * @author system
 * @date 2023-04-18
 */
@Slf4j
@CacheConfig(cacheNames = DataIssueServiceImpl.CACHE_NAME)
@Service("dataIssueService")
public class DataIssueServiceImpl extends ServiceImpl<DataIssueMapper, DataIssue>
        implements IDataIssueService, IDataIssueCheckClear {

    final static String CACHE_NAME = "dataQuality:dataIssue";

    @Resource
    private DataIssueMapper dataIssueMapper;

    @Resource
    private IDataIssueDtlService dataIssueDtlService;

    @Resource
    private IDataApiService dataApiService;

    @Resource
    private IDictService dictService;

    @Resource
    private IStockHouseService stockHouseService;

    private final ObjectMapper objectMapper;

    public DataIssueServiceImpl(MappingJackson2HttpMessageConverter messageConverter) {
        this.objectMapper = messageConverter.getObjectMapper();
    }

    @Override
    public void clear() {
        dataIssueMapper.clear();
        MultiCacheBatchHelp.apply(CACHE_NAME).multiCache().clear();
    }

    @DataBindFieldConvert
    @Override
    public Page<DataIssueVo> pageByStockHouse(DataIssuePageParam pageParam) {
        Page<DataIssueVo> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        return dataIssueMapper.pageByStockHouse(page, pageParam);
    }

    @DataBindFieldConvert
    @Override
    public Page<DataIssueVo> pageByStockHouseApi(DataIssuePageParam pageParam) {
        Page<DataIssueVo> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        return dataIssueMapper.pageByStockHouseApi(page, pageParam);
    }

    @DataBindFieldConvert
    @Override
    public Page<DataIssueVo> pages(DataIssuePageParam pageParam) {
        Page<DataIssueVo> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        Page<DataIssueVo> pages = dataIssueMapper.pages(page, pageParam);

        // 组装查询数据问题明细
        List<DataIssueVo> records = pages.getRecords();
        Map<String, List<DataIssueDtl>> issueDataIdMap = StreamEx.of(records)
                .map(DataIssue::getId)
                .toListAndThen(ids -> {
                    List<DataIssueDtl> dataIssueDtls = dataIssueDtlService.listByIssueDataIds(ids);
                    return StreamEx.of(dataIssueDtls).groupingBy(DataIssueDtl::getIssueDataId);
                });

        records.forEach(record -> {
            List<DataIssueDtl> dataIssueDtls = issueDataIdMap.getOrDefault(record.getId(), emptyList());
            Map<String, List<String>> dtlFieldNameMap = StreamEx.of(dataIssueDtls)
                    .groupingBy(DataIssueDtl::getFieldName, mapping(DataIssueDtl::getIssueRemark, toList()));
            record.setDtlFieldNameMap(dtlFieldNameMap);
        });

        return pages;
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void export(DataIssueExportParam exportParam) {
        // 根据库区查询数据问题列表
        Map<String, List<DataIssue>> dataIssueMap = super.lambdaQuery()
                .in(DataIssue::getStockHouseId, exportParam.getStockHouseIds())
                .list()
                .stream()
                .collect(groupingBy(DataIssue::getApiCode));

        // 根据数据问题查询明细,转换为数据问题和数据问题明细的映射map
        Map<String, List<DataIssueDtl>> issueDataIdDtlMap = StreamEx.of(dataIssueMap.values())
                .flatMap(Collection::stream)
                .map(DataIssue::getId)
                .toListAndThen(dataIssueDtlService::listByIssueDataIds)
                .stream()
                .collect(groupingBy(DataIssueDtl::getIssueDataId));

        // 接口映射
        Map<String, String> apiCodeMap = StreamEx.of(dictService.listByDictType("apiCode"))
                .toMap(Dict::getDictKey, Dict::getDictValue);

        // 库区映射
        Map<String, String> stockHouseMap = StreamEx.of(stockHouseService.listByIds(exportParam.getStockHouseIds()))
                .toMap(StockHouse::getId, StockHouse::getKqmc);

        // 设置响应信息
        HttpServletResponse response = SpringMVCUtil.getResponse();
        String fileName = new String((exportParam.getFileName() + "数据问题.zip").getBytes(), ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream; charset=UTF-8");

        // 组装数据并导出
        ServletOutputStream servletOutputStream = null;
        ZipOutputStream zip = null;
        try {
            servletOutputStream = response.getOutputStream();
            zip = new ZipOutputStream(servletOutputStream);
            exportZipData(dataIssueMap, issueDataIdDtlMap, apiCodeMap, stockHouseMap, zip);
            zip.finish();
        } finally {
            IoUtil.close(zip);
            IoUtil.close(servletOutputStream);
        }
    }

    @Override
    public Set<String> ignoreBatchInsert(List<DataIssue> dataIssues) {
        if (CollectionUtils.isEmpty(dataIssues)) {
            return Collections.emptySet();
        }

        executeBatchUpdate(this, dataIssues, dataIssueMapper::ignoreInsert);
        return super.lambdaQuery()
                .select(DataIssue::getId)
                .in(DataIssue::getId, StreamEx.of(dataIssues).map(DataIssue::getId).toList())
                .list()
                .stream()
                .map(DataIssue::getId)
                .collect(Collectors.toSet());
    }

    /**
     * 组装数据并导出zip数据字节
     *
     * @param dataIssueMap      数据问题
     * @param issueDataIdDtlMap 数据问题明细
     * @param apiCodeMap        接口映射
     * @param stockHouseMap     库区映射
     * @param zip               zip输出流
     */
    @SneakyThrows(Throwable.class)
    private void exportZipData(Map<String, List<DataIssue>> dataIssueMap,
                               Map<String, List<DataIssueDtl>> issueDataIdDtlMap,
                               Map<String, String> apiCodeMap, Map<String, String> stockHouseMap,
                               ZipOutputStream zip) {

        dataIssueMap.forEach((apiCode, dataIssues) -> {
            List<ExcelExportEntity> tables = new ArrayList<>();

            List<ExcelExportEntity> columns = StreamEx.of(dataApiService.columns(apiCode))
                    .map(column -> new ExcelExportEntity(column.getFieldMsg(), column.getFieldName()))
                    .toList();

            tables.add(new ExcelExportEntity("单位代码", "enterpriseId"));
            tables.add(new ExcelExportEntity("库区代码", "stockHouseId"));
            tables.add(new ExcelExportEntity("库区名称", "stockHouseName"));
            tables.add(new ExcelExportEntity("问题数", "issueCount"));
            tables.add(new ExcelExportEntity("数据ID", "dataId"));
            tables.addAll(columns);
            tables.add(new ExcelExportEntity("问题明细", "dtls"));

            // 组装数据内容
            List<Map<String, Object>> dataList = new ArrayList<>();
            dataIssues.forEach(dataIssue -> {
                // 固定列头：单位代码、库区代码、问题数、数据ID
                Map<String, Object> data = MapUtil.newHashMap(tables.size());
                data.put("enterpriseId", dataIssue.getEnterpriseId());
                data.put("stockHouseId", dataIssue.getStockHouseId());
                data.put("stockHouseName", stockHouseMap.get(dataIssue.getStockHouseId()));
                data.put("issueCount", dataIssue.getIssueCount());
                data.put("dataId", dataIssue.getDataId());

                // 动态数据列内容
                try {
                    Map<String, String> map = objectMapper.readValue(dataIssue.getDataJson(), Map.class);
                    columns.forEach(column -> {
                        String key = column.getKey().toString();
                        data.put(key, map.get(key));
                    });
                } catch (JsonProcessingException e) {
                    log.error("导出问题明细序列化异常{}", e, dataIssue.getDataJson());
                }

                // 固定列尾：问题明细
                List<DataIssueDtl> dtls = issueDataIdDtlMap.getOrDefault(dataIssue.getId(), Collections.emptyList());
                Map<String, List<String>> dtlFieldNameMap = StreamEx.of(dtls)
                        .groupingBy(DataIssueDtl::getFieldName, mapping(DataIssueDtl::getIssueRemark, toList()));
                try {
                    data.put("dtls", objectMapper.writeValueAsString(dtlFieldNameMap));
                } catch (JsonProcessingException e) {
                    log.error("导出问题明细序列化异常{}", e, dtlFieldNameMap);
                }

                dataList.add(data);
            });

            // 导出Excel并添加到zip
            ExportParams params = new ExportParams();
            String sheetName = apiCode + apiCodeMap.get(apiCode);
            params.setSheetName(sheetName);
            params.setStyle(ExcelExportStylerZtImpl.class);
            Workbook workbook = ExcelExportUtil.exportExcel(params, tables, dataList);

            try {
                zip.putNextEntry(new ZipEntry(sheetName + EXCEL_SUFFIX_XLS));
                workbook.write(zip);
                zip.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException("导出Excel并添加到zip异常" + sheetName, e);
            } finally {
                IoUtil.close(workbook);
            }
        });
    }
}