package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.organize.domain.MenuSystem;
import com.sydata.organize.param.MenuSystemPageParam;
import com.sydata.organize.param.MenuSystemRemoveParam;
import com.sydata.organize.param.MenuSystemSaveParam;
import com.sydata.organize.param.MenuSystemUpdateParam;

import java.util.Collection;

/**
 * @author lzq
 * @description 组织架构-菜单系统Service接口
 * @date 2023/5/22 15:01
 */
public interface IMenuSystemService extends IService<MenuSystem> {

    /**
     * 分页列表
     *
     * @param pageParam 菜单系统分页参数
     * @return 菜单系统分页列表
     */
    Page<MenuSystem> pages(MenuSystemPageParam pageParam);

    /**
     * 根据系统名称查询
     *
     * @param name 系统名称
     * @return 菜单系统
     */
    MenuSystem getByName(String name);

    /**
     * 根据系统简称查询
     *
     * @param shortName 系统简称
     * @return 菜单系统
     */
    MenuSystem getByShortName(String shortName);

    /**
     * 新增
     *
     * @param saveParam 菜单系统新增参数
     * @return 新增状态
     */
    Boolean save(MenuSystemSaveParam saveParam);

    /**
     * 修改
     *
     * @param updateParam 菜单系统新增参数
     * @return 修改状态
     */
    Boolean update(MenuSystemUpdateParam updateParam);

    /**
     * 删除
     *
     * @param removeParam 菜单系统删除参数
     * @return 删除状态
     */
    Boolean remove(MenuSystemRemoveParam removeParam);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
