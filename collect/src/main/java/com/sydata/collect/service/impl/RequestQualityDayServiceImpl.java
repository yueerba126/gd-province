package com.sydata.collect.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.collect.domain.RequestLog;
import com.sydata.collect.domain.RequestQualityDay;
import com.sydata.collect.mapper.RequestQualityDayMapper;
import com.sydata.collect.param.QualityDayPageParam;
import com.sydata.collect.service.IRequestLogService;
import com.sydata.collect.service.IRequestQualityDayService;
import com.sydata.collect.vo.CollectQualityVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.framework.orm.FrameworkSqlHelper;
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
 * 数据收集-请求数据质量日报Service业务层处理
 *
 * @author lzq
 * @date 2022-10-27
 */
@Service("requestQualityDayService")
public class RequestQualityDayServiceImpl extends ServiceImpl<RequestQualityDayMapper, RequestQualityDay>
        implements IRequestQualityDayService {

    @Resource
    private RequestQualityDayMapper requestQualityDayMapper;

    @Resource
    private IRequestLogService requestLogService;

    @DataBindFieldConvert
    @Override
    public Page<CollectQualityVo> report(QualityDayPageParam pageParam) {
        return requestQualityDayMapper.report(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()), pageParam);
    }

    @Override
    public List<String> enterpriseIdsByDays(LocalDate days) {
        return super.lambdaQuery()
                .select(RequestQualityDay::getEnterpriseId)
                .lt(RequestQualityDay::getDays, days)
                .eq(RequestQualityDay::getHandleState, FALSE)
                .groupBy(RequestQualityDay::getEnterpriseId)
                .list()
                .stream()
                .map(RequestQualityDay::getEnterpriseId)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestQualityDay> listByDays(LocalDate days, List<String> enterpriseIds) {
        return super.lambdaQuery()
                .lt(RequestQualityDay::getDays, days)
                .eq(RequestQualityDay::getHandleState, FALSE)
                .in(RequestQualityDay::getEnterpriseId, enterpriseIds)
                .list();
    }

    @Override
    public boolean handleByDays(LocalDate days, List<String> enterpriseIds) {
        return super.lambdaUpdate()
                .lt(RequestQualityDay::getDays, days)
                .eq(RequestQualityDay::getHandleState, FALSE)
                .in(RequestQualityDay::getEnterpriseId, enterpriseIds)
                .set(RequestQualityDay::getHandleState, TRUE)
                .update();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @XxlJob("requestQualityDaysHandle")
    public void requestQualityDaysHandle() {
        // 获取本小时以前的数据收集请求日志中的企业列表
        int zero = ZERO.intValue();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), zero);

        List<String> enterpriseIds = requestLogService.enterpriseIdsByEndTime(endTime);
        XxlJobHelper.log("收集请求日志企业列表总数:{}", enterpriseIds.size());
        if (CollectionUtils.isEmpty(enterpriseIds)) {
            return;
        }

        // 分片拉取数据
        List<String> shards = FrameworkJobHelper.shardList(enterpriseIds);
        if (CollectionUtils.isEmpty(shards)) {
            return;
        }

        // 数据收集日志转换为质量日报数据
        Map<String, RequestQualityDay> idMap = requestLogToDayData(endTime, shards);

        // 查询数据库ID是否已存在-- 查数据库根据ID查ID,而后根据ID移除idMap中的key
        List<RequestQualityDay> updates = super.lambdaQuery()
                .select(RequestQualityDay::getId)
                .in(RequestQualityDay::getId, idMap.keySet())
                .list()
                .stream()
                .map(RequestQualityDay::getId)
                .map(idMap::remove)
                .collect(Collectors.toList());

        // 批量新增
        super.saveBatch(idMap.values());

        // 批量修改
        FrameworkSqlHelper.executeBatchUpdate(this, updates, requestQualityDayMapper::statistics);

        // 根据请求时间和企业列表设置数据为已处理
        requestLogService.handleByEndTime(endTime, shards);
    }

    /**
     * 数据收集日志转换为质量日报数据
     *
     * @param endTime       请求时间
     * @param enterpriseIds 企业列表
     * @return 质量日报数据Map
     */
    private Map<String, RequestQualityDay> requestLogToDayData(LocalDateTime endTime,
                                                               List<String> enterpriseIds) {
        List<RequestLog> requestLogs = requestLogService.listByEndTime(endTime, enterpriseIds);
        Map<String, RequestQualityDay> idMap = MapUtil.newHashMap();

        // 根据联合主键分组计算总值
        StreamEx.of(requestLogs).groupingBy(t -> {
            LocalDate day = t.getBeginTime().toLocalDate();
            String enterpriseId = t.getEnterpriseId();
            String stockHouseId = t.getStockHouseId();
            String apiCode = t.getApiCode();
            return new StringJoiner(DASH).add(day.toString()).add(enterpriseId).add(stockHouseId).add(apiCode).toString();
        }).forEach((id, list) -> {
            Map<Boolean, List<RequestLog>> stateMap = StreamEx.of(list).groupingBy(RequestLog::getState);
            int total = list.size();
            int success = stateMap.getOrDefault(TRUE, Collections.emptyList()).size();
            int fail = stateMap.getOrDefault(FALSE, Collections.emptyList()).size();

            RequestLog log = list.get(0);
            RequestQualityDay requestQualityYears = new RequestQualityDay()
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
            idMap.put(id, requestQualityYears);
        });
        return idMap;
    }
}