package com.sydata.dostrict.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.personnel.domain.ApparitorTitleType;
import com.sydata.dostrict.personnel.param.ApparitorTitleTypePageParam;
import com.sydata.dostrict.personnel.param.ApparitorTitleTypeParam;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 行政管理-称号类别管理Service接口
 *
 * @author fuql
 * @date 2023-04-25
 */
public interface IApparitorTitleTypeService extends IService<ApparitorTitleType> {

    /**
     * 查询行政管理-称号类别管理列表
     *
     * @param param 行政管理-称号类别管理
     * @return 行政管理-称号类别管理集合
     */
    Page<ApparitorTitleType> pageData(ApparitorTitleTypePageParam param);

    /**
     * 行政管理-称号类别树形结构
     *
     * @return 树形节点
     */
    List<KuNode> treeList();

    /**
     * 新增行政管理-称号类别
     *
     * @param titleType 查询参数
     * @return id
     */
    String insertTitleType(ApparitorTitleType titleType);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

    /**
     * 修改行政管理-称号类别
     *
     * @param titleType 查询参数
     * @return id
     */
    String updateTitleType(ApparitorTitleType titleType);

    /**
     * 删除行政管理-称号类别
     *
     * @param ids 查询参数
     * @return id
     */
    Boolean removeData(List<String> ids);


}