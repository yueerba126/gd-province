package com.sydata.warn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.warn.domain.AdminGrainThreshold;

/**
 * 粮情预警阈值Mapper接口
 *
 * @author fuql
 * @date 2023-05-09
 */
@DataPermissionExclude
public interface AdminGrainThresholdMapper extends BaseMapper<AdminGrainThreshold> {

}
