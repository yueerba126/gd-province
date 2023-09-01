package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.organize.domain.Menu;
import com.sydata.organize.param.MenuRemoveParam;
import com.sydata.organize.vo.MenuTreeVo;

import java.util.Collection;
import java.util.List;

/**
 * 组织架构-菜单Service接口
 *
 * @author lzq
 * @date 2022-06-28
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 树列表
     *
     * @return 树列表
     */
    List<MenuTreeVo> tree();

    /**
     * 根据父ID查询菜单列表
     *
     * @param parentId 父ID
     * @return 菜单列表
     */
    List<Menu> listByParentId(Long parentId);

    /**
     * 删除
     *
     * @param removeParam 菜单删除参数
     * @return 删除状态
     */
    Boolean remove(MenuRemoveParam removeParam);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}