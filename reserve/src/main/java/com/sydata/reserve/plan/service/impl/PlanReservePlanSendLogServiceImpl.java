package com.sydata.reserve.plan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.reserve.plan.domain.PlanReservePlanSendLog;
import com.sydata.reserve.plan.mapper.PlanReservePlanSendLogMapper;
import com.sydata.reserve.plan.service.IPlanReservePlanSendLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 储备计划管理-储备计划下发记录Service业务层处理
 *
 * @author fuql
 * @date 2023-05-23
 */
@Service("planReservePlanSendLogService")
public class PlanReservePlanSendLogServiceImpl extends ServiceImpl<PlanReservePlanSendLogMapper, PlanReservePlanSendLog> implements IPlanReservePlanSendLogService {

    @Resource
    private PlanReservePlanSendLogMapper planReservePlanSendLogMapper;

}
