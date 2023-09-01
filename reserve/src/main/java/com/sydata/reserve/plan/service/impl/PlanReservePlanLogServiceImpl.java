package com.sydata.reserve.plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.reserve.plan.domain.PlanReservePlanLog;
import com.sydata.reserve.plan.mapper.PlanReservePlanLogMapper;
import com.sydata.reserve.plan.service.IPlanReservePlanLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 储备计划管理-储备计划调整历史Service业务层处理
 *
 * @author fuql
 * @date 2023-05-19
 */
@Service("planReservePlanLogService")
public class PlanReservePlanLogServiceImpl extends ServiceImpl<PlanReservePlanLogMapper, PlanReservePlanLog> implements IPlanReservePlanLogService {

    @Resource
    private PlanReservePlanLogMapper planReservePlanLogMapper;

}
