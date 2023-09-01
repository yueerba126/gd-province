package com.sydata.reserve.scale.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.reserve.scale.domain.PlanReserveScaleLog;
import com.sydata.reserve.scale.mapper.PlanReserveScaleLogMapper;
import com.sydata.reserve.scale.service.IPlanReserveScaleLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 储备计划管理-储备规模调整记录Service业务层处理
 *
 * @author fuql
 * @date 2023-05-17
 */
@Service("planReserveScaleLogService")
public class PlanReserveScaleLogServiceImpl extends ServiceImpl<PlanReserveScaleLogMapper, PlanReserveScaleLog> implements IPlanReserveScaleLogService {

    @Resource
    private PlanReserveScaleLogMapper planReserveScaleLogMapper;


}
