package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.organize.domain.Role;
import com.sydata.organize.param.RolePageParam;
import com.sydata.organize.param.RoleRemoveParam;

import java.util.Collection;

/**
 * 组织架构-角色Service接口
 *
 * @author lzq
 * @date 2022-06-28
 */
public interface IRoleService extends IService<Role> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<Role> pages(RolePageParam pageParam);

    /**
     * 根据角色名查询用户
     *
     * @param name 角色名
     * @return 角色
     */
    Role getByName(String name);

    /**
     * 删除
     *
     * @param removeParam 角色删除参数
     * @return 删除状态
     */
    Boolean remove(RoleRemoveParam removeParam);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

}