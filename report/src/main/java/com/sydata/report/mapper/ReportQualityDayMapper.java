package com.sydata.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.param.QualityDayPageParam;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import com.sydata.report.domain.ReportQualityDay;
import com.sydata.report.vo.ReportQualityVo;
import org.apache.ibatis.annotations.Param;

/**
 * 数据上报-上报数据质量日报Mapper接口
 *
 * @author lzq
 * @date 2022-11-02
 */
@PageOptimizationExclude
public interface ReportQualityDayMapper extends BaseMapper<ReportQualityDay> {

    /**
     * 累加统计
     *
     * @param day 上报数据质量日报
     * @return 统计结果
     */
    boolean statistics(ReportQualityDay day);

    /**
     * 上报数据质量日报-报表查询
     *
     * @param page  分页参数
     * @param param 数据质量统计日报表参数
     * @return 数据上报质量统计报表VO
     */
    Page<ReportQualityVo> report(@Param("page") Page page, @Param("param") QualityDayPageParam param);
}
