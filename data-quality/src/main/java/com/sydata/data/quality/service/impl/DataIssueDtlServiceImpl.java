package com.sydata.data.quality.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.data.quality.domain.DataIssueDtl;
import com.sydata.data.quality.job.process.check.IDataIssueCheckClear;
import com.sydata.data.quality.mapper.DataIssueDtlMapper;
import com.sydata.data.quality.service.IDataIssueDtlService;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据质量-数据问题明细Service业务层处理
 *
 * @author system
 * @date 2023-04-18
 */
@CacheConfig(cacheNames = DataIssueDtlServiceImpl.CACHE_NAME)
@Service("dataIssueDtlService")
public class DataIssueDtlServiceImpl extends ServiceImpl<DataIssueDtlMapper, DataIssueDtl>
        implements IDataIssueDtlService, IDataIssueCheckClear {

    final static String CACHE_NAME = "dataQuality:dataIssueDtl";

    @Resource
    private DataIssueDtlMapper dataIssueDtlMapper;

    @Override
    public void clear() {
        dataIssueDtlMapper.clear();
        MultiCacheBatchHelp.apply(CACHE_NAME).multiCache().clear();
    }

    @Cacheable(key = "'issueDataId='+#issueDataId")
    @Override
    public List<DataIssueDtl> listByIssueDataId(String issueDataId) {
        return super.lambdaQuery().eq(DataIssueDtl::getIssueDataId, issueDataId).list();
    }

    @Override
    public List<DataIssueDtl> listByIssueDataIds(List<String> issueDataIds) {
        return CollectionUtils.isEmpty(issueDataIds) ? Collections.emptyList() : MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, DataIssueDtl, List<DataIssueDtl>>composeExecute()
                .fields(DataIssueDtl::getIssueDataId)
                .params(issueDataIds)
                .targets(ids -> super.lambdaQuery().in(DataIssueDtl::getIssueDataId, ids).list())
                .group(targets -> StreamEx.of(targets).groupingBy(DataIssueDtl::getIssueDataId))
                .get()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}