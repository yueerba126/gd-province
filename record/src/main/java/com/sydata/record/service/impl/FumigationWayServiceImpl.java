package com.sydata.record.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.record.domain.FumigationWay;
import com.sydata.record.mapper.FumigationWayMapper;
import com.sydata.record.service.IFumigationWayService;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

/**
 * @author lzq
 * @description
 * @date 2022/12/10 12:24
 */
@CacheConfig(cacheNames = FumigationWayServiceImpl.CACHE_NAME)
@Service("fumigationWayService")
public class FumigationWayServiceImpl extends ServiceImpl<FumigationWayMapper, FumigationWay>
        implements IFumigationWayService {

    final static String CACHE_NAME = "record:fumigationWay";

    @Cacheable(key = "'fumigationId='+#fumigationId")
    @Override
    public List<FumigationWay> listByFumigationId(String fumigationId) {
        return super.lambdaQuery().eq(FumigationWay::getFumigationId, fumigationId).list();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(key = "'fumigationId='+#fumigationId")
    @Override
    public void build(String fumigationId, List<FumigationWay> list) {
        // 先删后加
        super.lambdaUpdate().eq(FumigationWay::getFumigationId, fumigationId).remove();
        StreamEx.of(emptyIfNull(list)).peek(dtl -> dtl.setFumigationId(fumigationId)).toListAndThen(super::saveBatch);
    }
}
