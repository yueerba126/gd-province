package com.sydata.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.param.QualityYearsPageParam;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import com.sydata.report.domain.ReportQualityYears;
import com.sydata.report.vo.ReportQualityVo;
import org.apache.ibatis.annotations.Param;

/**
 * 数据上报-上报数据质量年报Mapper接口
 *
 * @author lzq
 * @date 2022-11-02
 */
@PageOptimizationExclude
public interface ReportQualityYearsMapper extends BaseMapper<ReportQualityYears> {

    /**
     * 累加统计
     *
     * @param years 上报数据质量年报
     * @return 统计结果
     */
    boolean statistics(ReportQualityYears years);

    /**
     * 上报数据质量年报-报表查询
     *
     * @param page  分页参数
     * @param param 数据质量统计年报表参数
     * @return 数据质量统计报表VO
     */
    Page<ReportQualityVo> report(@Param("page") Page page, @Param("param") QualityYearsPageParam param);
}
