package com.sydata.report.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.collect.param.QualityYearsPageParam;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.framework.orm.FrameworkSqlHelper;
import com.sydata.report.domain.ReportQualityDay;
import com.sydata.report.domain.ReportQualityYears;
import com.sydata.report.mapper.ReportQualityYearsMapper;
import com.sydata.report.service.IReportQualityDayService;
import com.sydata.report.service.IReportQualityYearsService;
import com.sydata.report.vo.ReportQualityVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * 数据上报-上报数据质量年报Service业务层处理
 *
 * @author lzq
 * @date 2022-11-02
 */
@Service("reportQualityYearsService")
public class ReportQualityYearsServiceImpl extends ServiceImpl<ReportQualityYearsMapper, ReportQualityYears>
        implements IReportQualityYearsService {

    @Resource
    private ReportQualityYearsMapper reportQualityYearsMapper;

    @Resource
    private IReportQualityDayService reportQualityDayService;

    @DataBindFieldConvert
    @Override
    public Page<ReportQualityVo> report(QualityYearsPageParam pageParam) {
        return reportQualityYearsMapper.report(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()), pageParam);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @XxlJob("reportQualityYearsHandle")
    public void reportQualityYearsHandle() {
        // 拉取昨天数据上报质量日报的企业列表
        LocalDate days = LocalDate.now();
        List<String> enterpriseIds = reportQualityDayService.enterpriseIdsByDays(days);
        XxlJobHelper.log("数据上报质量日报企业列表总数:{}", enterpriseIds.size());
        if (CollectionUtils.isEmpty(enterpriseIds)) {
            return;
        }

        // 分片拉取数据
        List<String> shards = FrameworkJobHelper.shardList(enterpriseIds);
        if (CollectionUtils.isEmpty(shards)) {
            return;
        }

        // 上报质量日报数据转换为上报质量年报数据
        Map<String, ReportQualityYears> idMap = dayDataToYearData(days, shards);

        // 查询数据库ID是否已存在-- 查数据库根据ID查ID,而后根据ID移除idMap中的key
        List<ReportQualityYears> updates = super.lambdaQuery()
                .select(ReportQualityYears::getId)
                .in(ReportQualityYears::getId, idMap.keySet())
                .list()
                .stream()
                .map(ReportQualityYears::getId)
                .map(idMap::remove)
                .collect(Collectors.toList());

        // 批量新增
        super.saveBatch(idMap.values());


        // 批量修改
        FrameworkSqlHelper.executeBatchUpdate(this, updates, reportQualityYearsMapper::statistics);

        // 根据指定日期和企业设置数据为已处理
        reportQualityDayService.handleByDays(days, shards);
    }

    /**
     * 上报质量日报数据转换为上报质量年报数据
     *
     * @param date          日期
     * @param enterpriseIds 企业集合
     * @return 上报质量年报数据Map
     */
    private Map<String, ReportQualityYears> dayDataToYearData(LocalDate date, List<String> enterpriseIds) {
        List<ReportQualityDay> days = reportQualityDayService.listByDays(date, enterpriseIds);
        Map<String, ReportQualityYears> idMap = MapUtil.newHashMap();

        // 根据联合主键分组计算总值
        StreamEx.of(days).groupingBy(t -> {
            String years = String.valueOf(t.getDays().getYear());
            String enterpriseId = t.getEnterpriseId();
            String apiCode = t.getApiCode();
            String stockHouseId = t.getStockHouseId();
            return new StringJoiner(DASH).add(years).add(enterpriseId).add(stockHouseId).add(apiCode).toString();
        }).forEach((id, list) -> {
            int total = StreamEx.of(list).mapToInt(ReportQualityDay::getTotal).sum();
            int success = StreamEx.of(list).mapToInt(ReportQualityDay::getSuccess).sum();
            int fail = StreamEx.of(list).mapToInt(ReportQualityDay::getFail).sum();

            ReportQualityDay day = list.get(0);

            String years = String.valueOf(day.getDays().getYear());
            ReportQualityYears reportQualityYears = new ReportQualityYears()
                    .setId(id)
                    .setYears(years)
                    .setEnterpriseId(day.getEnterpriseId())
                    .setStockHouseId(day.getStockHouseId())
                    .setApiCode(day.getApiCode())
                    .setTotal(total)
                    .setSuccess(success)
                    .setFail(fail)
                    .setCountryId(day.getCountryId())
                    .setProvinceId(day.getProvinceId())
                    .setCityId(day.getCityId())
                    .setAreaId(day.getAreaId());
            idMap.put(id, reportQualityYears);
        });
        return idMap;
    }
}
