/**
 * @filename:ApparitorSecureTypeBeanService 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.storage.param.ApparitorSecureTypePageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureTypeSaveParam;
import com.sydata.dostrict.storage.param.ApparitorSecureTypeSelectParam;
import com.sydata.dostrict.storage.vo.ApparitorSecureTypeVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(安全仓储-安全生产-制度类别服务层)
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface IApparitorSecureTypeService {
    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorSecureTypeVo> pages(ApparitorSecureTypePageParam pageParam);

    /**
     * 查询列表
     *
     * @Param [pageParam]
     * @return java.util.List<com.sydata.dostrict.storage.vo.ApparitorSecureTypeVo>
     **/
    List<ApparitorSecureTypeVo> lists();

    /**
     * 查询树列表
     *
     * @Param [pageParam]
     * @return java.util.List<com.sydata.dostrict.storage.vo.ApparitorSecureTypeVo>
     **/
    List<KuNode> treeList();

    /**
     * 详情
     *
     * @param id 主键ID
     * @return VO
     */
    ApparitorSecureTypeVo detail(String id);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorSecureTypeSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorSecureTypeSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}