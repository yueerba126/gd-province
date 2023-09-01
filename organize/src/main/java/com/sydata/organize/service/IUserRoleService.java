package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.Role;
import com.sydata.organize.domain.UserRole;
import com.sydata.organize.param.RolePageParam;
import com.sydata.organize.param.UserPageParam;
import com.sydata.organize.param.UserRoleSetUpParam;
import com.sydata.organize.vo.UserRoleAuthorizeRoleVo;
import com.sydata.organize.vo.UserRoleAuthorizeUserVo;

import java.util.List;

/**
 * 组织架构-用户角色Service接口
 *
 * @author lzq
 * @date 2022-06-28
 */
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 增量设置用户角色
     *
     * @param setUpParam 用户角色设置参数
     * @return 操作状态
     */
    boolean setUp(UserRoleSetUpParam setUpParam);

    /**
     * 增量取消用户角色
     *
     * @param setUpParam 用户角色设置参数
     * @return 操作状态
     */
    boolean cancel(UserRoleSetUpParam setUpParam);

    /**
     * 根据用户ID删除
     *
     * @param userId 用户ID
     * @return 操作状态
     */
    boolean removeByUserId(String userId);

    /**
     * 根据角色ID删除
     *
     * @param roleId 角色ID
     * @return 操作状态
     */
    boolean removeByRoleId(Long roleId);

    /**
     * 根据用户ID查询用户角色列表
     *
     * @param userId 用户ID
     * @return 用户角色列表
     */
    List<UserRole> listByUserId(String userId);

    /**
     * 根据角色ID查询用户角色列表
     *
     * @param roleId 角色ID
     * @return 用户角色列表
     */
    List<UserRole> listByRoleId(Long roleId);

    /**
     * 查询用户的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> rolesByUserId(String userId);

    /**
     * 查询用户的角色授权列表
     *
     * @param pageParam 角色分页参数
     * @param userId    用户ID
     * @return 用户的角色授权VO列表
     */
    Page<UserRoleAuthorizeRoleVo> authorizeRolePage(RolePageParam pageParam, String userId);

    /**
     * 查询角色的用户授权列表
     *
     * @param pageParam 用户分页参数
     * @param roleId    角色ID
     * @return 角色的用户授权VO列表
     */
    Page<UserRoleAuthorizeUserVo> authorizeUserPage(UserPageParam pageParam, Long roleId);
}