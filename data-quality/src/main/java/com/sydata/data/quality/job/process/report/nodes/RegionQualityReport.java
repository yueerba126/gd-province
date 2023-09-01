package com.sydata.data.quality.job.process.report.nodes;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.data.quality.domain.Report;
import com.sydata.data.quality.domain.ReportDtl;
import com.sydata.data.quality.enums.ReportTargetTypeEnum;
import com.sydata.data.quality.job.process.report.IQualityReportProcess;
import com.sydata.data.quality.job.process.report.enums.QualityReportNodeEnum;
import com.sydata.data.quality.service.IReportDtlService;
import com.sydata.data.quality.service.IReportService;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.organize.domain.FoodOwner;
import com.sydata.organize.domain.Region;
import com.sydata.organize.enums.RegionTypeEnum;
import com.sydata.organize.service.IFoodOwnerService;
import com.sydata.organize.service.IRegionService;
import com.sydata.report.api.config.ReportConfig;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.SPACE;
import static com.sydata.data.quality.enums.ReportTargetTypeEnum.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * @author lzq
 * @description 行政区划质量报告
 * @date 2023/4/24 18:33
 */
@Slf4j
@Component
public class RegionQualityReport implements IQualityReportProcess {


    @Resource
    private IReportService reportService;

    @Resource
    private IReportDtlService reportDtlService;

    @Resource
    private IFoodOwnerService foodOwnerService;

    @Resource
    private IRegionService regionService;

    @Resource
    private ReportConfig reportConfig;

    @Override
    public QualityReportNodeEnum node() {
        return QualityReportNodeEnum.REGION;
    }

    @Override
    public int generate(LocalDate statisticsDate, LocalDate reportDate, List<DataApiEnum> dataApiEnums, StringBuilder logs) {
        // 查询广东省所有行政区划列表
        List<Region> list = regionService.tabulation(reportConfig.getProvinceId());

        int size = list.size();
        List<Region> regions = FrameworkJobHelper.shardList(list);
        logs.append("行政区划总数:").append(size).append(SPACE).append("分配数:").append(regions.size()).append(SPACE);

        List<Report> reports = new ArrayList<>();
        List<ReportDtl> reportDtls = new ArrayList<>();

        // 行政区划与库区粮权关系映射
        Map<String, List<String>> regionFoodOwnerMap = regionFoodOwnerMap();
        int apiTotalCount = dataApiEnums.size();
        regions.forEach(region -> {
            // 获取该行政区划的粮权库区列表
            List<String> stockHouseIds = regionFoodOwnerMap.get(region.getId());

            // 获取库区数据质量报告
            List<Report> stockHouseReports = reportService.listByTargetIds(STOCK_HOUSE, stockHouseIds, reportDate);
            List<ReportDtl> stockHouseReportDtls = StreamEx.of(stockHouseReports)
                    .map(Report::getId)
                    .toListAndThen(reportDtlService::listByReportIds);

            // 构建数据质量报告
            String reportId = IdWorker.getIdStr();
            List<ReportDtl> dtls = buildReportDtls(reportId, dataApiEnums, stockHouseReportDtls);
            Report report = buildReport(reportId, region, stockHouseReports, statisticsDate, reportDate, dtls, apiTotalCount);

            // 加入新增列表
            reportDtls.addAll(dtls);
            reports.add(report);
        });

        // 批量保存行政区划数据质量报告
        reportService.saveBatch(reports);
        reportDtlService.saveBatch(reportDtls);

        return reports.size();
    }

    /**
     * 明细报告
     *
     * @param reportId             报告ID
     * @param dataApiEnums         数据API枚举列表
     * @param stockHouseReportDtls 库区报告明细列表
     * @return 明细报告列表
     */
    private List<ReportDtl> buildReportDtls(String reportId, List<DataApiEnum> dataApiEnums,
                                            List<ReportDtl> stockHouseReportDtls) {
        List<ReportDtl> dtls = new ArrayList<>();

        Map<String, List<ReportDtl>> apiCodeMap = StreamEx.of(stockHouseReportDtls).groupingBy(ReportDtl::getApiCode);
        dataApiEnums.forEach(dataApiEnum -> {
            String apiCode = dataApiEnum.getApiCode();
            List<ReportDtl> list = apiCodeMap.getOrDefault(apiCode, Collections.emptyList());

            int apiRequestTotalCount = dtlsSum(list, ReportDtl::getApiRequestTotalCount);

            int apiRequestSuccessCount = dtlsSum(list, ReportDtl::getApiRequestSuccessCount);
            int dataTotalCount = dtlsSum(list, ReportDtl::getDataTotalCount);
            int dataGoodCount = dtlsSum(list, ReportDtl::getDataGoodCount);
            int dataIssueCount = dtlsSum(list, ReportDtl::getDataIssueCount);
            int issueTotalCount = dtlsSum(list, ReportDtl::getIssueTotalCount);
            int fieldTotalCount = dtlsSum(list, ReportDtl::getFieldTotalCount);
            int fieldValidCount = dtlsSum(list, ReportDtl::getFieldValidCount);

            ReportDtl reportDtl = new ReportDtl()
                    .setReportId(reportId)
                    .setApiCode(apiCode)
                    .setUnicom(apiRequestSuccessCount > 0)
                    .setApiRequestTotalCount(apiRequestTotalCount)
                    .setApiRequestSuccessCount(apiRequestSuccessCount)
                    .setDataTotalCount(dataTotalCount)
                    .setDataGoodCount(dataGoodCount)
                    .setDataIssueCount(dataIssueCount)
                    .setIssueTotalCount(issueTotalCount)
                    .setFieldTotalCount(fieldTotalCount)
                    .setFieldValidCount(fieldValidCount);

            // 报告明细计算：通过率、完整率、合格率
            reportDtlCalculationRate(reportDtl);

            dtls.add(reportDtl);

        });
        return dtls;
    }

    /**
     * 构建报告
     *
     * @param reportId          上报ID
     * @param region            行政区划
     * @param stockHouseReports 库区报告列表
     * @param dtls              报告明细列表
     * @param apiTotalCount     接口总数
     * @return 数据质量-报告
     */
    private Report buildReport(String reportId, Region region, List<Report> stockHouseReports,
                               LocalDate statisticsDate, LocalDate reportDate,
                               List<ReportDtl> dtls, int apiTotalCount) {
        BigDecimal actualStock = StreamEx.of(stockHouseReports).
                map(Report::getActualStock).reduce(ZERO, BigDecimal::add);

        BigDecimal valuationStock = StreamEx.of(stockHouseReports)
                .map(Report::getValuationStock).reduce(ZERO, BigDecimal::add);

        // 组建行政区划类型和数据质量报告类型的关系映射
        Map<String, ReportTargetTypeEnum> targetTypeMap = MapUtil.newHashMap(RegionTypeEnum.values().length);
        targetTypeMap.put(RegionTypeEnum.PROVINCE.getType(), PROVINCE);
        targetTypeMap.put(RegionTypeEnum.CITY.getType(), CITY);
        targetTypeMap.put(RegionTypeEnum.AREA.getType(), AREA);
        ReportTargetTypeEnum targetType = targetTypeMap.get(region.getType());

        // 构建报告
        String stockHouseIds = isEmpty(stockHouseReports) ? null : StreamEx.of(stockHouseReports)
                .map(Report::getTargetId).joining(COMMA);
        Report report = new Report()
                .setId(reportId)
                .setStatisticsDate(statisticsDate)
                .setReportDate(reportDate)
                .setTargetType(targetType.getType())
                .setTargetId(region.getId())
                .setTargetName(PROVINCE.equals(targetType) ? region.getName() + "储备粮管理集团有限公司" : region.getName())
                .setActualStock(actualStock)
                .setValuationStock(valuationStock)
                .setApiVersion(API_VERSION)
                .setApiTotalCount(apiTotalCount)
                .setApiUnicomCount((int) StreamEx.of(dtls).filter(ReportDtl::getUnicom).count())
                .setApiRequestTotalCount(dtlsSum(dtls, ReportDtl::getApiRequestTotalCount))
                .setApiRequestSuccessCount(dtlsSum(dtls, ReportDtl::getApiRequestSuccessCount))
                .setDataTotalCount(dtlsSum(dtls, ReportDtl::getDataTotalCount))
                .setDataGoodCount(dtlsSum(dtls, ReportDtl::getDataGoodCount))
                .setDataIssueCount(dtlsSum(dtls, ReportDtl::getDataIssueCount))
                .setIssueTotalCount(dtlsSum(dtls, ReportDtl::getIssueTotalCount))
                .setFieldTotalCount(dtlsSum(dtls, ReportDtl::getFieldTotalCount))
                .setFieldValidCount(dtlsSum(dtls, ReportDtl::getFieldValidCount))
                .setRegionId(region.getId())
                .setCountryId(region.getCountryId())
                .setProvinceId(region.getProvinceId())
                .setCityId(region.getCityId())
                .setAreaId(region.getAreaId())
                .setStockHouseIds(stockHouseIds);

        // 报告计算：实际业务相符率、修正业务相符率、开通率、通过率、完整率、合格率、分数
        reportCalculationRate(report);

        return report;
    }


    /**
     * 行政区划与库区粮权关系映射
     *
     * @return 行政区划与库区粮权关系映射map《行政区划,库区ID列表》
     */
    private Map<String, List<String>> regionFoodOwnerMap() {
        // 查询广东省底下的粮权关系列表
        List<FoodOwner> foodOwners = foodOwnerService.regionStockHousesByRegionId(reportConfig.getProvinceId());

        // 区分省储/市区储
        Map<Boolean, List<FoodOwner>> provinceMap = StreamEx.of(foodOwners)
                .groupingBy(foodOwner -> reportConfig.getProvinceId().equals(foodOwner.getRegionId()));
        List<FoodOwner> provinces = provinceMap.getOrDefault(TRUE, Collections.emptyList());
        List<FoodOwner> cityAreas = provinceMap.getOrDefault(FALSE, Collections.emptyList());

        // 将行政区划粮权关系归纳为行政区划与库区粮权关系映射map《省/省储,市/市区,区/区》
        Map<String, List<String>> provinceIdMap = StreamEx.of(provinces)
                .groupingBy(FoodOwner::getProvinceId, Collectors.mapping(FoodOwner::getStockHouseId, toList()));

        Map<String, List<String>> cityIdMap = StreamEx.of(cityAreas)
                .groupingBy(FoodOwner::getCityId, Collectors.mapping(FoodOwner::getStockHouseId, toList()));

        Map<String, List<String>> areaIdMap = StreamEx.of(cityAreas)
                .groupingBy(FoodOwner::getAreaId, Collectors.mapping(FoodOwner::getStockHouseId, toList()));

        Map<String, List<String>> regionFoodOwnerMap = MapUtil.newHashMap();
        regionFoodOwnerMap.putAll(provinceIdMap);
        regionFoodOwnerMap.putAll(cityIdMap);
        regionFoodOwnerMap.putAll(areaIdMap);
        return regionFoodOwnerMap;
    }
}
