package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.Menu;
import com.sydata.organize.domain.RoleMenu;
import com.sydata.organize.param.RoleMenuSetUpParam;
import com.sydata.organize.vo.RoleMenuAuthorizeTreeVo;

import java.util.List;

/**
 * 组织架构-角色菜单Service接口
 *
 * @author lzq
 * @date 2022-06-28
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 设置角色菜单(增量)
     *
     * @param setUpParam 角色菜单设置参数
     * @return 操作结果
     */
    boolean setUp(RoleMenuSetUpParam setUpParam);

    /**
     * 取消角色菜单(增量)
     *
     * @param setUpParam 角色菜单设置参数
     * @return 操作结果
     */
    boolean cancel(RoleMenuSetUpParam setUpParam);

    /**
     * 根据菜单ID删除
     *
     * @param menuId 菜单ID
     * @return 操作状态
     */
    boolean removeByMenuId(Long menuId);

    /**
     * 根据角色ID删除
     *
     * @param roleId 角色ID
     * @return 操作状态
     */
    boolean removeByRoleId(Long roleId);

    /**
     * 根据角色ID查询角色菜单列表
     *
     * @param roleId 角色ID
     * @return 角色菜单列表
     */
    List<RoleMenu> listByRoleId(Long roleId);

    /**
     * 根据角色ID列表查询菜单列表
     *
     * @param roleIds 角色ID列表
     * @return 菜单列表
     */
    List<Menu> menusByRoleIds(List<Long> roleIds);

    /**
     * 查询角色菜单授权树VO列表
     *
     * @param roleId 角色ID
     * @return 角色菜单授权树VO列表
     */
    List<RoleMenuAuthorizeTreeVo> roleMenuAuthorizeTree(Long roleId);
}