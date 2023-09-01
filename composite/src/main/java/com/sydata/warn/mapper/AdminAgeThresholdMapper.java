package com.sydata.warn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.warn.domain.AdminAgeThreshold;

/**
 * 库存年限告警阈值设置Mapper接口
 *
 * @author fuql
 * @date 2023-05-09
 */
@DataPermissionExclude
public interface AdminAgeThresholdMapper extends BaseMapper<AdminAgeThreshold> {

}
