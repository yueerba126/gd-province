package com.sydata.report.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.collect.param.QualityDayPageParam;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.framework.orm.FrameworkSqlHelper;
import com.sydata.report.domain.ReportLogs;
import com.sydata.report.domain.ReportQualityDay;
import com.sydata.report.mapper.ReportQualityDayMapper;
import com.sydata.report.service.IReportLogsService;
import com.sydata.report.service.IReportQualityDayService;
import com.sydata.report.vo.ReportQualityVo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.math.BigInteger.ZERO;

/**
 * 数据上报-上报数据质量日报Service业务层处理
 *
 * @author lzq
 * @date 2022-11-02
 */
@Service("reportQualityDayService")
public class ReportQualityDayServiceImpl extends ServiceImpl<ReportQualityDayMapper, ReportQualityDay>
        implements IReportQualityDayService {

    @Resource
    private ReportQualityDayMapper reportQualityDayMapper;

    @Resource
    private IReportLogsService reportLogsService;

    @DataBindFieldConvert
    @Override
    public Page<ReportQualityVo> report(QualityDayPageParam pageParam) {
        return reportQualityDayMapper.report(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()), pageParam);
    }

    @Override
    public List<String> enterpriseIdsByDays(LocalDate days) {
        return super.lambdaQuery()
                .select(ReportQualityDay::getEnterpriseId)
                .lt(ReportQualityDay::getDays, days)
                .eq(ReportQualityDay::getHandleState, FALSE)
                .groupBy(ReportQualityDay::getEnterpriseId)
                .list()
                .stream()
                .map(ReportQualityDay::getEnterpriseId)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportQualityDay> listByDays(LocalDate days, List<String> enterpriseIds) {
        return super.lambdaQuery()
                .lt(ReportQualityDay::getDays, days)
                .eq(ReportQualityDay::getHandleState, FALSE)
                .in(ReportQualityDay::getEnterpriseId, enterpriseIds)
                .list();
    }

    @Override
    public boolean handleByDays(LocalDate days, List<String> enterpriseIds) {
        return super.lambdaUpdate()
                .lt(ReportQualityDay::getDays, days)
                .eq(ReportQualityDay::getHandleState, FALSE)
                .in(ReportQualityDay::getEnterpriseId, enterpriseIds)
                .set(ReportQualityDay::getHandleState, TRUE)
                .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @XxlJob("reportQualityDayHandle")
    public void reportQualityDayHandle() {
        // 获取本小时以前的数据上报日志中的企业列表
        int zero = ZERO.intValue();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), zero);

        List<String> enterpriseIds = reportLogsService.enterpriseIdsByEndTime(endTime);
        XxlJobHelper.log("上报日志企业列表总数:{}", enterpriseIds.size());
        if (CollectionUtils.isEmpty(enterpriseIds)) {
            return;
        }

        // 分片拉取数据
        List<String> shards = FrameworkJobHelper.shardList(enterpriseIds);
        if (CollectionUtils.isEmpty(shards)) {
            return;
        }

        // 数据上报日志转换为上报质量日报数据
        Map<String, ReportQualityDay> idMap = reportLogToDayData(endTime, shards);

        // 查询数据库ID是否已存在-- 查数据库根据ID查ID,而后根据ID移除idMap中的key
        List<ReportQualityDay> updates = super.lambdaQuery()
                .select(ReportQualityDay::getId)
                .in(ReportQualityDay::getId, idMap.keySet())
                .list()
                .stream()
                .map(ReportQualityDay::getId)
                .map(idMap::remove)
                .collect(Collectors.toList());

        // 批量新增
        super.saveBatch(idMap.values());

        // 批量修改
        FrameworkSqlHelper.executeBatchUpdate(this, updates, reportQualityDayMapper::statistics);

        // 根据上报时间和企业列表设置数据为已处理
        reportLogsService.handleByEndTime(endTime, shards);
    }

    /**
     * 数据上报日志转换为上报质量日报数据
     *
     * @param endTime       请求时间
     * @param enterpriseIds 企业列表
     * @return 上报质量日报数据Map
     */
    private Map<String, ReportQualityDay> reportLogToDayData(LocalDateTime endTime,
                                                             List<String> enterpriseIds) {
        List<ReportLogs> reportLogs = reportLogsService.listByEndTime(endTime, enterpriseIds);
        Map<String, ReportQualityDay> idMap = MapUtil.newHashMap();

        // 根据联合主键分组计算总值
        StreamEx.of(reportLogs).groupingBy(t -> {
            LocalDate day = t.getBeginTime().toLocalDate();
            String enterpriseId = t.getEnterpriseId();
            String apiCode = t.getApiCode();
            String stockHouseId = t.getStockHouseId();
            return new StringJoiner(DASH).add(day.toString()).add(enterpriseId).add(stockHouseId).add(apiCode).toString();
        }).forEach((id, list) -> {
            Map<Boolean, List<ReportLogs>> stateMap = StreamEx.of(list).groupingBy(ReportLogs::getState);
            int total = list.size();
            int success = stateMap.getOrDefault(TRUE, Collections.emptyList()).size();
            int fail = stateMap.getOrDefault(FALSE, Collections.emptyList()).size();

            ReportLogs log = list.get(0);
            ReportQualityDay reportQualityDay = new ReportQualityDay()
                    .setId(id)
                    .setDays(log.getBeginTime().toLocalDate())
                    .setEnterpriseId(log.getEnterpriseId())
                    .setStockHouseId(log.getStockHouseId())
                    .setApiCode(log.getApiCode())
                    .setTotal(total)
                    .setSuccess(success)
                    .setFail(fail)
                    .setCountryId(log.getCountryId())
                    .setProvinceId(log.getProvinceId())
                    .setCityId(log.getCityId())
                    .setAreaId(log.getAreaId());
            idMap.put(id, reportQualityDay);
        });
        return idMap;
    }
}
