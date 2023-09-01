package com.sydata.reserve.plan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.framework.orm.annotation.PageOptimizationExclude;
import com.sydata.reserve.plan.domain.PlanReservePlan;
import com.sydata.reserve.plan.param.PlanReservePlanPageParam;
import com.sydata.reserve.plan.vo.PlanReservePlanVo;
import org.apache.ibatis.annotations.Param;

/**
 * 储备计划管理-储备计划Mapper接口
 *
 * @author fuql
 * @date 2023-05-19
 */
public interface PlanReservePlanMapper extends BaseMapper<PlanReservePlan> {

    /**
     * 分页查询储备计划管理-储备计划
     *
     * @param page page
     * @param param 参数
     * @return 储备计划
     */
    @PageOptimizationExclude
    Page<PlanReservePlanVo> pageData(Page page, @Param("param") PlanReservePlanPageParam param);
}