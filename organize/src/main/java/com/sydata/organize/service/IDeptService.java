package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.organize.domain.Dept;
import com.sydata.organize.param.DeptRemoveParam;
import com.sydata.organize.vo.DeptTreeVo;
import com.sydata.organize.vo.DeptVo;

import java.util.Collection;
import java.util.List;

/**
 * 组织架构-部门Service接口
 *
 * @author lzq
 * @date 2022-11-16
 */
public interface IDeptService extends IService<Dept> {

    /**
     * 根据组织ID查询部门树
     *
     * @param organizeId 组织ID
     * @return 部门树
     */
    List<DeptTreeVo> treeByOrganizeId(String organizeId);

    /**
     * 查询当前人所属部门的部门树
     *
     * @return 部门树
     */
    List<DeptTreeVo> treeByParentIds();

    /**
     * 详情
     *
     * @param id 部门ID
     * @return 部门VO
     */
    DeptVo details(Long id);

    /**
     * 根据组织和名称查询部门
     *
     * @param organizeId 组织ID
     * @param name       部门名称
     * @return 部门
     */
    Dept getByName(String organizeId, String name);

    /**
     * 删除
     *
     * @param removeParam 部门删除参数
     * @return 操作状态
     */
    Boolean remove(DeptRemoveParam removeParam);

    /**
     * 根据组织ID列表获取部门列表
     *
     * @param organizeIds 组织ID列表
     * @return 部门列表
     */
    List<Dept> listByOrganizeIds(Collection<String> organizeIds);

    /**
     * 根据父部门IDS查询子部门列表
     *
     * @param parentIds 父部门IDS
     * @return 部门列表
     */
    List<Dept> listByParentIds(String parentIds);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
