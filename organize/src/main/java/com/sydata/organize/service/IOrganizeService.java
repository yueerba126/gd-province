package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.organize.domain.Organize;
import com.sydata.organize.param.*;
import com.sydata.organize.vo.OrganizeTreeVo;
import com.sydata.organize.vo.OrganizeVo;

import java.util.Collection;
import java.util.List;

/**
 * 组织架构-组织Service接口
 *
 * @author lzq
 * @date 2022-06-28
 */
public interface IOrganizeService extends IService<Organize> {

    /**
     * 分页列表
     *
     * @param pageParam 组织分页参数
     * @return 组织VO
     */
    Page<OrganizeVo> pages(OrganizePageParam pageParam);

    /**
     * 根据父组织ID集合查询列表
     *
     * @param parentIds 父组织ID集合
     * @return 组织列表
     */
    List<Organize> listByParentIds(String parentIds);

    /**
     * 根据父组织ID集合查询组织树
     *
     * @return 组织树
     */
    List<OrganizeTreeVo> treeByParentIds();

    /**
     * 根据名称获取
     *
     * @param name 名称
     * @return 组织
     */
    Organize getByName(String name);

    /**
     * 新增
     *
     * @param operateParam 行政组织操作参数
     * @return 操作结果
     */
    Boolean save(OrganizeOperateParam operateParam);

    /**
     * 修改
     *
     * @param operateParam 行政组织操作参数
     * @return 操作结果
     */
    Boolean update(OrganizeOperateParam operateParam);

    /**
     * 删除
     *
     * @param removeParam 组织删除参数
     * @return 删除结果
     */
    Boolean remove(OrganizeRemoveParam removeParam);

    /**
     * 设置系统
     *
     * @param setSystemParam 组织设置系统参数
     * @return 操作结果
     */
    Boolean setSystem(OrganizeSetSystemParam setSystemParam);

    /**
     * 设置业务类型
     *
     * @param busTypeParam 组织设置业务类型参数
     * @return 操作结果
     */
    Boolean setBusType(OrganizeSetBusTypeParam busTypeParam);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}