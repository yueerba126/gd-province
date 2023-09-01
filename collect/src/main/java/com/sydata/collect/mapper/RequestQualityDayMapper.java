package com.sydata.collect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.domain.RequestQualityDay;
import com.sydata.collect.param.QualityDayPageParam;
import com.sydata.collect.vo.CollectQualityVo;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import org.apache.ibatis.annotations.Param;

/**
 * 数据收集-请求数据质量日报Mapper接口
 *
 * @author lzq
 * @date 2022-10-27
 */
@PageOptimizationExclude
public interface RequestQualityDayMapper extends BaseMapper<RequestQualityDay> {

    /**
     * 累加统计
     *
     * @param day 请求数据质量日报
     * @return 统计结果
     */
    boolean statistics(RequestQualityDay day);

    /**
     * 请求数据质量日报-报表查询
     *
     * @param page  分页参数
     * @param param 数据质量统计日报表参数
     * @return 数据收集质量统计报表VO
     */
    Page<CollectQualityVo> report(@Param("page") Page page, @Param("param") QualityDayPageParam param);
}