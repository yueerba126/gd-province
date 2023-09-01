/**
 * @filename:ApparitorSecureRiskIdenBeanService 2023年04月28日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskIdenPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskIdenSaveParam;
import com.sydata.dostrict.storage.vo.ApparitorSecureRiskIdenVo;


import java.util.List;

/**
 * @Description:TODO(安全仓储-风险智能识别服务层)
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface IApparitorSecureRiskIdenService {
    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorSecureRiskIdenVo> pages(ApparitorSecureRiskIdenPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return VO
     */
    ApparitorSecureRiskIdenVo detail(String id);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorSecureRiskIdenSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorSecureRiskIdenSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);
}