package com.sydata.collect.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.collect.domain.RequestLog;
import com.sydata.collect.mapper.RequestLogMapper;
import com.sydata.collect.param.RequestLogPageParam;
import com.sydata.collect.service.IRequestLogService;
import com.sydata.collect.vo.RequestLogVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.job.FrameworkJobHelper;
import com.sydata.framework.job.dto.LimitDto;
import com.sydata.framework.util.BeanUtils;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 数据收集-请求日志Service业务层处理
 *
 * @author lzq
 * @date 2022-10-21
 */
@CacheConfig(cacheNames = RequestLogServiceImpl.CACHE_NAME)
@Service("requestLogService")
public class RequestLogServiceImpl extends ServiceImpl<RequestLogMapper, RequestLog> implements IRequestLogService {

    final static String CACHE_NAME = "collect:requestLog";

    private static final String SAVE_BUFFER = CACHE_NAME + ":saveBuffer";

    @Resource
    private RedisTemplate redisTemplate;


    @Cacheable(key = "'id='+#id")
    @Override
    public RequestLog getById(Serializable id) {
        return super.getById(id);
    }

    @DataBindFieldConvert
    @Override
    public Page<RequestLogVo> pages(RequestLogPageParam pageParam) {
        Page<RequestLog> page = super.lambdaQuery()
                // 列表不查请求参数、响应参数
                .select(RequestLog::getId, RequestLog::getApiCode, RequestLog::getCzbz, RequestLog::getBeginTime,
                        RequestLog::getEndTime, RequestLog::getTimeConsuming, RequestLog::getState,
                        RequestLog::getEnterpriseId, RequestLog::getStockHouseId, RequestLog::getDataId,
                        RequestLog::getHandleState, RequestLog::getRegionId, RequestLog::getCreateBy
                )
                .eq(isNotEmpty(pageParam.getApiCode()), RequestLog::getApiCode, pageParam.getApiCode())
                .ge(isNotEmpty(pageParam.getBeginTimeByBegin()), RequestLog::getBeginTime, pageParam.getBeginTimeByBegin())
                .le(isNotEmpty(pageParam.getBeginTimeByEnd()), RequestLog::getBeginTime, pageParam.getBeginTimeByEnd())
                .eq(isNotEmpty(pageParam.getState()), RequestLog::getState, pageParam.getState())
                .eq(isNotEmpty(pageParam.getCzbz()), RequestLog::getCzbz, pageParam.getCzbz())
                .eq(isNotEmpty(pageParam.getDataId()), RequestLog::getDataId, pageParam.getDataId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), RequestLog::getEnterpriseId, pageParam.getEnterpriseId())
                .orderByDesc(RequestLog::getId)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, RequestLogVo.class);
    }

    @DataBindFieldConvert
    @Override
    public RequestLogVo detail(String id) {
        IRequestLogService requestLogService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(requestLogService.getById(id), RequestLogVo.class);
    }

    @Override
    public void saveByBuffer(RequestLog requestLog) {
        String saveBuffer = SpringUtil.getApplicationName() + COLON + SAVE_BUFFER;
        redisTemplate.opsForList().leftPush(saveBuffer, requestLog);
    }

    @Override
    public List<String> enterpriseIdsByEndTime(LocalDateTime endTime) {
        return super.lambdaQuery()
                .select(RequestLog::getEnterpriseId)
                .lt(RequestLog::getBeginTime, endTime)
                .eq(RequestLog::getHandleState, Boolean.FALSE)
                .groupBy(RequestLog::getEnterpriseId)
                .list()
                .stream()
                .map(RequestLog::getEnterpriseId)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestLog> listByEndTime(LocalDateTime endTime, List<String> enterpriseIds) {
        return super.lambdaQuery()
                .select(RequestLog::getApiCode, RequestLog::getState, RequestLog::getEnterpriseId,
                        RequestLog::getStockHouseId, RequestLog::getCountryId, RequestLog::getProvinceId,
                        RequestLog::getCityId, RequestLog::getAreaId, RequestLog::getBeginTime
                )
                .lt(RequestLog::getBeginTime, endTime)
                .eq(RequestLog::getHandleState, Boolean.FALSE)
                .in(RequestLog::getEnterpriseId, enterpriseIds)
                .list();
    }

    @Override
    public boolean handleByEndTime(LocalDateTime endTime, List<String> enterpriseIds) {
        return super.lambdaUpdate()
                .lt(RequestLog::getBeginTime, endTime)
                .eq(RequestLog::getHandleState, Boolean.FALSE)
                .in(RequestLog::getEnterpriseId, enterpriseIds)
                .set(RequestLog::getHandleState, Boolean.TRUE)
                .update();
    }

    @XxlJob("handleSaveRequestLogBuffer")
    public void handleSaveRequestLogBuffer() {
        String saveBuffer = SpringUtil.getApplicationName() + COLON + SAVE_BUFFER;
        ListOperations listOperations = redisTemplate.opsForList();
        List<LimitDto> limits = FrameworkJobHelper.shardLimitTotalCount(() -> listOperations.size(saveBuffer));
        for (LimitDto limit : limits) {
            // 获取不到分区数据退出本次处理工作
            List<RequestLog> list = listOperations.rightPop(saveBuffer, limit.getSize());
            if (CollectionUtils.isEmpty(list)) {
                break;
            }

            // 批量插入数据库
            super.saveBatch(list);
        }
    }
}