package com.sydata.record.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.record.domain.FumigationDtl;
import com.sydata.record.mapper.FumigationDtlMapper;
import com.sydata.record.service.IFumigationDtlService;
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
 * 备案管理-熏蒸明细Service业务层处理
 *
 * @author system
 * @date 2022-12-10
 */
@CacheConfig(cacheNames = FumigationDtlServiceImpl.CACHE_NAME)
@Service("fumigationDtlService")
public class FumigationDtlServiceImpl extends ServiceImpl<FumigationDtlMapper, FumigationDtl>
        implements IFumigationDtlService {

    final static String CACHE_NAME = "record:fumigationDtl";

    @Resource
    private FumigationDtlMapper fumigationDtlMapper;

    @Cacheable(key = "'fumigationId='+#fumigationId")
    @Override
    public List<FumigationDtl> listByFumigationId(String fumigationId) {
        return super.lambdaQuery().eq(FumigationDtl::getFumigationId, fumigationId).list();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @CacheEvict(key = "'fumigationId='+#fumigationId")
    @Override
    public void build(String fumigationId, List<FumigationDtl> list) {
        // 先删后加
        super.lambdaUpdate().eq(FumigationDtl::getFumigationId, fumigationId).remove();
        StreamEx.of(emptyIfNull(list)).peek(dtl -> dtl.setFumigationId(fumigationId)).toListAndThen(super::saveBatch);
    }
}