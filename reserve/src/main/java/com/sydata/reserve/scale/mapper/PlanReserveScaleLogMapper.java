package com.sydata.reserve.scale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.reserve.scale.domain.PlanReserveScaleLog;

/**
 * 储备计划管理-储备规模调整记录Mapper接口
 *
 * @author fuql
 * @date 2023-05-17
 */
@DataPermissionExclude
public interface PlanReserveScaleLogMapper extends BaseMapper<PlanReserveScaleLog> {


}