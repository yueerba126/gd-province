package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.UserRole;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

import java.util.List;

/**
 * 组织架构-用户角色Mapper接口
 *
 * @author lzq
 * @date 2022-06-28
 */
@DataPermissionExclude
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 批量新增
     *
     * @param userRoles 用户角色列表
     * @return 操作结果
     */
    boolean insertBatchIgnore(List<UserRole> userRoles);
}