package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.RoleMenu;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

import java.util.List;

/**
 * 组织架构-角色菜单Mapper接口
 *
 * @author lzq
 * @date 2022-06-28
 */
@DataPermissionExclude
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 批量新增
     *
     * @param roleMenus 角色菜单列表
     * @return 操作结果
     */
    boolean insertBatchIgnore(List<RoleMenu> roleMenus);
}