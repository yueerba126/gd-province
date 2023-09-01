package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.Role;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * 组织架构-角色Mapper接口
 *
 * @author lzq
 * @date 2022-06-28
 */
@DataPermissionExclude
public interface RoleMapper extends BaseMapper<Role> {

}