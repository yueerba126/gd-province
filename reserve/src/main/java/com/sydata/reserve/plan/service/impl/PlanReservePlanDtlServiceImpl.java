package com.sydata.reserve.plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.reserve.plan.domain.PlanReservePlanDtl;
import com.sydata.reserve.plan.mapper.PlanReservePlanDtlMapper;
import com.sydata.reserve.plan.service.IPlanReservePlanDtlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 储备计划管理-储备计划详情Service业务层处理
 *
 * @author fuql
 * @date 2023-05-19
 */
@Service("planReservePlanDtlService")
public class PlanReservePlanDtlServiceImpl extends ServiceImpl<PlanReservePlanDtlMapper, PlanReservePlanDtl> implements IPlanReservePlanDtlService {

    @Resource
    private PlanReservePlanDtlMapper planReservePlanDtlMapper;
}
