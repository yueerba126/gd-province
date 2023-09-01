/**
 * @filename:ApparitorSecureSystemBeanService 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.storage.param.ApparitorSecureSystemPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureSystemSaveParam;
import com.sydata.dostrict.storage.vo.ApparitorSecureSystemVo;

import java.util.List;

/**
 * @Description:TODO(安全仓储-安全生产-生产制度服务层)
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface IApparitorSecureSystemService {
    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorSecureSystemVo> pages(ApparitorSecureSystemPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return VO
     */
    ApparitorSecureSystemVo detail(String id);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorSecureSystemSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorSecureSystemSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

    /**
     * 发布
     *
     * @param id id
     * @return
     */
    boolean pushDataById(String id);


    /**
     * 下载附件
     *
     * @param fileId fileId
     */
    void download(String fileId);
}