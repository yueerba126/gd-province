/**
 * @filename:ApparitorSecureRiskUnitBeanService 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskUnitPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureRiskUnitSaveParam;
import com.sydata.dostrict.storage.vo.ApparitorSecureRiskUnitVo;

import java.util.List;

/**
 * @Description:TODO(安全仓储-安全风险台账服务层)
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface IApparitorSecureRiskUnitService {
    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorSecureRiskUnitVo> pages(ApparitorSecureRiskUnitPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return VO
     */
    ApparitorSecureRiskUnitVo detail(String id);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorSecureRiskUnitSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorSecureRiskUnitSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);
}