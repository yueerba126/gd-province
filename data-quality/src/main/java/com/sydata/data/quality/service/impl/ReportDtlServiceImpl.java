package com.sydata.data.quality.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.data.quality.domain.ReportDtl;
import com.sydata.data.quality.mapper.ReportDtlMapper;
import com.sydata.data.quality.service.IReportDtlService;
import com.sydata.data.quality.vo.ReportDtlVo;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据质量-报告明细Service业务层处理
 *
 * @author system
 * @date 2023-04-18
 */
@CacheConfig(cacheNames = ReportDtlServiceImpl.CACHE_NAME)
@Service("reportDtlService")
public class ReportDtlServiceImpl extends ServiceImpl<ReportDtlMapper, ReportDtl> implements IReportDtlService {

    final static String CACHE_NAME = "dataQuality:reportDtl";

    @Cacheable(key = "'reportId='+#reportId")
    @DataBindFieldConvert
    @Override
    public List<ReportDtlVo> listByReportId(String reportId) {
        List<ReportDtl> list = super.lambdaQuery().eq(ReportDtl::getReportId, reportId).list();
        return BeanUtils.copyToList(list, ReportDtlVo.class);
    }

    @Override
    public List<ReportDtl> listByReportIds(List<String> reportIds) {
        return CollectionUtils.isEmpty(reportIds) ? Collections.emptyList() : MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, ReportDtl, List<ReportDtl>>composeExecute()
                .fields(ReportDtl::getId)
                .params(reportIds)
                .targets(ids -> super.lambdaQuery().in(ReportDtl::getReportId, ids).list())
                .group(targets -> StreamEx.of(targets).groupingBy(ReportDtl::getReportId))
                .get()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}