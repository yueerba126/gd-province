package com.sydata.report.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.api.config.ReportConfig;
import com.sydata.report.domain.DataHandle;
import com.sydata.report.domain.ReportLogs;
import com.sydata.report.mapper.ReportLogsMapper;
import com.sydata.report.param.ReportLogsPageParam;
import com.sydata.report.service.IDataHandleService;
import com.sydata.report.service.IReportLogsService;
import com.sydata.report.vo.ReportLogsVo;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sydata.collect.enums.DataHandleSourceEnum.REPORT_DELIVERY;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 数据上报-上报日志Service业务层处理
 *
 * @author lzq
 * @date 2022-11-02
 */
@CacheConfig(cacheNames = ReportLogsServiceImpl.CACHE_NAME)
@Service("reportLogsService")
public class ReportLogsServiceImpl extends ServiceImpl<ReportLogsMapper, ReportLogs> implements IReportLogsService {

    final static String CACHE_NAME = "report:reportLogs";

    @Resource
    private ReportLogsMapper reportLogsMapper;

    @Resource
    private ReportConfig reportConfig;

    @Resource
    private IDataHandleService dataHandleService;

    @Cacheable(key = "'id='+#id")
    @Override
    public ReportLogs getById(Serializable id) {
        return super.getById(id);
    }

    @DataBindFieldConvert
    @Override
    public Page<ReportLogsVo> pages(ReportLogsPageParam pageParam) {
        Page<ReportLogs> page = super.lambdaQuery()
                // 列表不查请求参数、响应参数
                .select(ReportLogs::getId, ReportLogs::getApiCode, ReportLogs::getCzbz, ReportLogs::getBeginTime,
                        ReportLogs::getEndTime, ReportLogs::getTimeConsuming, ReportLogs::getState,
                        ReportLogs::getHandleState, ReportLogs::getDelivery, ReportLogs::getEnterpriseId,
                        ReportLogs::getStockHouseId, ReportLogs::getDataId
                )
                .eq(isNotEmpty(pageParam.getApiCode()), ReportLogs::getApiCode, pageParam.getApiCode())
                .ge(isNotEmpty(pageParam.getBeginTimeByBegin()), ReportLogs::getBeginTime, pageParam.getBeginTimeByBegin())
                .le(isNotEmpty(pageParam.getBeginTimeByEnd()), ReportLogs::getBeginTime, pageParam.getBeginTimeByEnd())
                .eq(isNotEmpty(pageParam.getState()), ReportLogs::getState, pageParam.getState())
                .eq(isNotEmpty(pageParam.getDelivery()), ReportLogs::getDelivery, pageParam.getDelivery())
                .eq(isNotEmpty(pageParam.getCzbz()), ReportLogs::getCzbz, pageParam.getCzbz())
                .eq(isNotEmpty(pageParam.getDataId()), ReportLogs::getDataId, pageParam.getDataId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), ReportLogs::getEnterpriseId, pageParam.getEnterpriseId())
                .orderByDesc(ReportLogs::getId)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ReportLogsVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ReportLogsVo detail(String id) {
        IReportLogsService reportLogsService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(reportLogsService.getById(id), ReportLogsVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean delivery(List<String> ids) {
        int maxDelivery = reportConfig.getMaxDelivery();
        Assert.state(ids.size() <= maxDelivery, "超过最大重报投递数" + maxDelivery);

        // 查询数据
        List<ReportLogs> reportLogs = MultiCacheBatchHelp
                .apply(CACHE_NAME).<String, ReportLogs, ReportLogs>composeExecute()
                .fields(ReportLogs::getId)
                .params(ids)
                .targets(super::listByIds)
                .group(targets -> StreamEx.of(targets).toMap(ReportLogs::getId, Function.identity()))
                .get()
                .stream()
                .filter(logs -> !logs.getState())
                .filter(logs -> !logs.getDelivery())
                .collect(Collectors.toList());
        Assert.state(ids.size() == reportLogs.size(), "只能投递上报失败并未投递过的数据");

        // 更新数据状态
        Wrapper<ReportLogs> wrapper = super.lambdaUpdate()
                .in(ReportLogs::getId, ids)
                .eq(ReportLogs::getDelivery, FALSE)
                .set(ReportLogs::getDelivery, TRUE)
                .getWrapper();
        int rows = getBaseMapper().update(null, wrapper);
        Assert.state(rows == ids.size(), "存在已投递过的数据,无法进行投递");


        // 清除缓存
        MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, ReportLogs, ReportLogs>composeExecute()
                .fields(ReportLogs::getId)
                .params(ids)
                .remove();

        // 新增数据处理
        return StreamEx.of(reportLogs).map(this::buildDataHandle).toListAndThen(dataHandleService::saveBatch);
    }

    @Override
    public List<String> enterpriseIdsByEndTime(LocalDateTime endTime) {
        return super.lambdaQuery()
                .select(ReportLogs::getEnterpriseId)
                .lt(ReportLogs::getBeginTime, endTime)
                .eq(ReportLogs::getHandleState, Boolean.FALSE)
                .groupBy(ReportLogs::getEnterpriseId)
                .list()
                .stream()
                .map(ReportLogs::getEnterpriseId)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportLogs> listByEndTime(LocalDateTime endTime, List<String> enterpriseIds) {
        return super.lambdaQuery()
                .select(ReportLogs::getApiCode, ReportLogs::getState, ReportLogs::getEnterpriseId,
                        ReportLogs::getStockHouseId, ReportLogs::getCountryId, ReportLogs::getProvinceId,
                        ReportLogs::getCityId, ReportLogs::getAreaId, ReportLogs::getBeginTime
                )
                .in(ReportLogs::getEnterpriseId, enterpriseIds)
                .lt(ReportLogs::getBeginTime, endTime)
                .eq(ReportLogs::getHandleState, Boolean.FALSE)
                .list();
    }

    @Override
    public boolean handleByEndTime(LocalDateTime endTime, List<String> enterpriseIds) {
        return super.lambdaUpdate()
                .lt(ReportLogs::getBeginTime, endTime)
                .eq(ReportLogs::getHandleState, Boolean.FALSE)
                .in(ReportLogs::getEnterpriseId, enterpriseIds)
                .set(ReportLogs::getHandleState, Boolean.TRUE)
                .update();
    }

    /**
     * 构建数据处理
     *
     * @param log 上报日志
     * @return 数据处理实例
     */
    private DataHandle buildDataHandle(ReportLogs log) {
        return new DataHandle()
                .setApiCode(log.getApiCode())
                .setDataId(log.getDataId())
                .setCzbz(log.getCzbz())
                .setParam(log.getParam())
                .setHandleState(FALSE)
                .setSource(REPORT_DELIVERY.getSource())
                .setRegionId(log.getRegionId())
                .setProvinceId(log.getProvinceId())
                .setCityId(log.getCityId())
                .setAreaId(log.getAreaId())
                .setEnterpriseId(log.getEnterpriseId())
                .setStockHouseId(log.getStockHouseId());
    }
}