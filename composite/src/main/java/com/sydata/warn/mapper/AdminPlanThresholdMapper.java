package com.sydata.warn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.warn.domain.AdminPlanThreshold;

/**
 * 储备计划阈值设置Mapper接口
 *
 * @author fuql
 * @date 2023-05-09
 */
@DataPermissionExclude
public interface AdminPlanThresholdMapper extends BaseMapper<AdminPlanThreshold> {

}
