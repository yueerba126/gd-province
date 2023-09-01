package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.organize.domain.User;
import com.sydata.organize.param.*;
import com.sydata.organize.vo.UserVo;

import java.util.Collection;
import java.util.List;

/**
 * 组织架构-用户Service接口
 *
 * @author lzq
 * @date 2022-06-28
 */
public interface IUserService extends IService<User> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<UserVo> pages(UserPageParam pageParam);

    /**
     * 详情
     *
     * @param id 用户ID
     * @return 用户VO
     */
    UserVo details(String id);

    /**
     * 根据登录账号查询用户
     *
     * @param account 账号
     * @return 用户
     */
    User getByAccount(String account);

    /**
     * 根据广东省统一身份认证用户ID查询用户
     *
     * @param gdsUnifiedIdentityUserId 广东省统一身份认证用户ID
     * @return 用户
     */
    User getByGdsUnifiedIdentityUserId(String gdsUnifiedIdentityUserId);

    /**
     * 修改用户
     *
     * @param updateParam 用户修改参数
     * @return 操作状态
     */
    Boolean update(UserUpdateParam updateParam);

    /**
     * 删除
     *
     * @param removeParam 用户删除参数
     * @return 删除状态
     */
    Boolean remove(UserRemoveParam removeParam);

    /**
     * 修改密码
     *
     * @param passwordParam 用户修改密码参数
     * @return 修改状态
     */
    Boolean updatePassword(UserUpdatePasswordParam passwordParam);

    /**
     * 用户设置部门
     *
     * @param userSetUpDeptParam 用户设置部门参数
     * @return 操作状态
     */
    Boolean setUpDept(UserSetUpDeptParam userSetUpDeptParam);

    /**
     * 根据组织ID获取用户ID列表
     *
     * @param organizeId 组织ID
     * @return 用户ID列表
     */
    List<String> idsByOrganizeId(String organizeId);

    /**
     * 根据部门ID获取用户ID列表
     *
     * @param deptId 部门ID
     * @return 用户ID列表
     */
    List<String> idsByDeptId(Long deptId);

    /**
     * 绑定广东省统一身份认证用户ID
     *
     * @param userId                   用户ID
     * @param gdsUnifiedIdentityUserId 广东省统一身份认证用户ID
     * @return 绑定结果
     */
    boolean bindGdsUnifiedIdentityUserId(String userId, String gdsUnifiedIdentityUserId);

    /**
     * 解绑广东省统一身份认证用户ID
     *
     * @return 解绑结果
     */
    boolean unBindGdsUnifiedIdentityUserId();

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}