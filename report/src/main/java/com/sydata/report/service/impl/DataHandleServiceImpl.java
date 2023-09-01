package com.sydata.report.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sydata.collect.api.event.DataCollectEvent;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.report.domain.DataHandle;
import com.sydata.report.mapper.DataHandleMapper;
import com.sydata.report.param.DataHandlePageParam;
import com.sydata.report.service.IDataHandleService;
import com.sydata.report.vo.DataHandleVo;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static com.sydata.collect.enums.DataHandleSourceEnum.COLLECT;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 数据收集-数据处理Service业务层处理
 *
 * @author lzq
 * @date 2022-10-21
 */
@CacheConfig(cacheNames = DataHandleServiceImpl.CACHE_NAME)
@Service("dataHandleService")
public class DataHandleServiceImpl extends ServiceImpl<DataHandleMapper, DataHandle> implements IDataHandleService {

    final static String CACHE_NAME = "report:dataHandle";

    @Resource
    private DataHandleMapper dataHandleMapper;

    private ObjectMapper objectMapper;

    public DataHandleServiceImpl(MappingJackson2HttpMessageConverter messageConverter) {
        this.objectMapper = messageConverter.getObjectMapper();
    }

    @Cacheable(key = "'id='+#id")
    @Override
    public DataHandle getById(Serializable id) {
        return super.getById(id);
    }

    @DataBindFieldConvert
    @Override
    public Page<DataHandleVo> pages(DataHandlePageParam pageParam) {
        Page<DataHandle> page = super.lambdaQuery()
                // 列表不查请求参数
                .select(DataHandle::getId, DataHandle::getApiCode, DataHandle::getDataId,
                        DataHandle::getCzbz, DataHandle::getHandleState, DataHandle::getSource,
                        DataHandle::getEnterpriseId, DataHandle::getStockHouseId, DataHandle::getDataId,
                        DataHandle::getCreateBy, DataHandle::getCreateTime
                )
                .eq(isNotEmpty(pageParam.getApiCode()), DataHandle::getApiCode, pageParam.getApiCode())
                .ge(isNotEmpty(pageParam.getCreateTimeByBegin()), DataHandle::getCreateTime, pageParam.getCreateTimeByBegin())
                .le(isNotEmpty(pageParam.getCreateTimeByEnd()), DataHandle::getCreateTime, pageParam.getCreateTimeByEnd())
                .eq(isNotEmpty(pageParam.getHandleState()), DataHandle::getHandleState, pageParam.getHandleState())
                .eq(isNotEmpty(pageParam.getCzbz()), DataHandle::getCzbz, pageParam.getCzbz())
                .eq(isNotEmpty(pageParam.getDataId()), DataHandle::getDataId, pageParam.getDataId())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), DataHandle::getEnterpriseId, pageParam.getEnterpriseId())
                .orderByDesc(DataHandle::getId)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, DataHandleVo.class);
    }

    @DataBindFieldConvert
    @Override
    public DataHandleVo detail(String id) {
        IDataHandleService dataHandleService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(dataHandleService.getById(id), DataHandleVo.class);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void dataCollect(DataCollectEvent dataCollectEvent) {
        BaseApiParam param = dataCollectEvent.getParam();
        DataHandle dataHandle = new DataHandle()
                .setApiCode(dataCollectEvent.getApiCode())
                .setDataId(dataCollectEvent.getDataId())
                .setCzbz(param.getCzbz())
                .setParam(objectMapper.writeValueAsString(param))
                .setHandleState(FALSE)
                .setSource(COLLECT.getSource());
        super.save(dataHandle);
    }

    @Override
    public List<String> notHandleIdsByEndTime(String apiCode, LocalDateTime endTime) {
        return dataHandleMapper.notHandleIdsByEndTime(apiCode, endTime);
    }

    @Override
    public int handleByEndTimeAndDataId(String apiCode, LocalDateTime endTime, List<String> dataIds) {
        AbstractWrapper<DataHandle, SFunction<DataHandle, ?>, LambdaUpdateWrapper<DataHandle>> wrapper = lambdaUpdate()
                .eq(DataHandle::getApiCode, apiCode)
                .lt(DataHandle::getCreateTime, endTime)
                .in(DataHandle::getDataId, dataIds)
                .eq(DataHandle::getHandleState, FALSE)
                .set(DataHandle::getHandleState, TRUE)
                .getWrapper();
        return dataHandleMapper.update(null, wrapper);
    }
}