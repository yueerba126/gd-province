package com.sydata.data.quality.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.data.quality.domain.Report;
import com.sydata.data.quality.enums.ReportTargetTypeEnum;
import com.sydata.data.quality.mapper.ReportMapper;
import com.sydata.data.quality.param.ReportPageParam;
import com.sydata.data.quality.service.IReportService;
import com.sydata.data.quality.vo.ReportVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 数据质量-报告Service业务层处理
 *
 * @author system
 * @date 2023-04-18
 */
@CacheConfig(cacheNames = ReportServiceImpl.CACHE_NAME)
@Service("reportService")
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {

    final static String CACHE_NAME = "dataQuality:report";

    @DataBindFieldConvert
    @Override
    public Page<ReportVo> pages(ReportPageParam pageParam) {
        Page<Report> pageParams = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        pageParams.setOrders(pageParam.getOrders());

        Page<Report> page = super.lambdaQuery()
                .in(isNotEmpty(pageParam.getTargetTypes()), Report::getTargetType, pageParam.getTargetTypes())
                .in(isNotEmpty(pageParam.getTargetIds()), Report::getTargetId, pageParam.getTargetIds())
                .ge(isNotEmpty(pageParam.getBeginReportDate()), Report::getReportDate, pageParam.getBeginReportDate())
                .le(isNotEmpty(pageParam.getEndReportDate()), Report::getReportDate, pageParam.getEndReportDate())
                .eq(isNotEmpty(pageParam.getCountryId()), Report::getCountryId, pageParam.getCountryId())
                .eq(isNotEmpty(pageParam.getProvinceId()), Report::getProvinceId, pageParam.getProvinceId())
                .eq(isNotEmpty(pageParam.getCityId()), Report::getCityId, pageParam.getCityId())
                .eq(isNotEmpty(pageParam.getAreaId()), Report::getAreaId, pageParam.getAreaId())
                .page(pageParams);
        return BeanUtils.copyToPage(page, ReportVo.class);
    }

    @Override
    public List<Report> listByTargetIds(ReportTargetTypeEnum targetType, List<String> targetIds, LocalDate reportDate) {
        return CollectionUtils.isEmpty(targetIds) ? Collections.emptyList() : super.lambdaQuery()
                .eq(Report::getTargetType, targetType.getType())
                .in(Report::getTargetId, targetIds)
                .eq(Report::getReportDate, reportDate)
                .list();
    }
}