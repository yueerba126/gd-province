package com.sydata.record.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.record.domain.FumigationPeople;
import com.sydata.record.mapper.FumigationPeopleMapper;
import com.sydata.record.service.IFumigationPeopleService;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

/**
 * 备案管理-熏蒸人员Service业务层处理
 *
 * @author system
 * @date 2022-12-10
 */
@CacheConfig(cacheNames = FumigationPeopleServiceImpl.CACHE_NAME)
@Service("fumigationPeopleService")
public class FumigationPeopleServiceImpl extends ServiceImpl<FumigationPeopleMapper, FumigationPeople>
        implements IFumigationPeopleService {

    final static String CACHE_NAME = "record:fumigationPeople";

    @Resource
    private FumigationPeopleMapper fumigationPeopleMapper;

    @Cacheable(key = "'fumigationId='+#fumigationId")
    @Override
    public List<FumigationPeople> listByFumigationId(String fumigationId) {
        return super.lambdaQuery().eq(FumigationPeople::getFumigationId, fumigationId).list();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(key = "'fumigationId='+#fumigationId")
    @Override
    public void build(String fumigationId, List<FumigationPeople> list) {
        // 先删后加
        super.lambdaUpdate().eq(FumigationPeople::getFumigationId, fumigationId).remove();
        StreamEx.of(emptyIfNull(list)).peek(dtl -> dtl.setFumigationId(fumigationId)).toListAndThen(super::saveBatch);
    }
}