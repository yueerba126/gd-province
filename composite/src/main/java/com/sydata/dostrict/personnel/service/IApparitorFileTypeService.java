package com.sydata.dostrict.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.personnel.domain.ApparitorFileType;
import com.sydata.dostrict.personnel.param.ApparitorFileTypePageParam;
import com.sydata.dostrict.personnel.param.ApparitorFileTypeParam;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * 行政管理-文件类别管理Service接口
 *
 * @author fuql
 * @date 2023-04-24
 */
public interface IApparitorFileTypeService extends IService<ApparitorFileType> {

    /**
     * 查询行政管理-文件类别管理列表
     *
     * @param param 行政管理-文件类别管理
     * @return 行政管理-文件类别管理集合
     */
    Page<ApparitorFileType> pageData(ApparitorFileTypePageParam param);

    /**
     * 行政管理-文件类别树形结构
     *
     * @return 树形节点
     */
    List<KuNode> treeList();

    /**
     * 新增行政管理-文件类别
     *
     * @param fileType 查询参数
     * @return id
     */
    String insertFileType(ApparitorFileType fileType);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

    /**
     * 修改行政管理-文件类别
     *
     * @param fileType 查询参数
     * @return id
     */
    String updateFileType(ApparitorFileType fileType);

    /**
     * 删除行政管理-文件类别
     *
     * @param ids 查询参数
     * @return id
     */
    Boolean removeData(List<String> ids);
}
