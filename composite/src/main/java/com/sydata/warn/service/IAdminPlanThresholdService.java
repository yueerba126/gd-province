package com.sydata.warn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.warn.domain.AdminPlanThreshold;
import com.sydata.warn.param.AdminPlanThresholdPageParam;
import com.sydata.warn.vo.AdminPlanThresholdVo;

import java.util.List;

/**
 * 储备计划阈值设置Service接口
 *
 * @author fuql
 * @date 2023-05-09
 */
public interface IAdminPlanThresholdService extends IService<AdminPlanThreshold> {

    /**
     * 储备计划阈值设置新增
     *
     * @param param param
     * @return 人员制度管理id
     */
    String saveData(AdminPlanThreshold param);

    /**
     * 储备计划阈值设置page列表
     *
     * @param param param
     * @return 人员制度管理page列表
     */
    Page<AdminPlanThresholdVo> pageData(AdminPlanThresholdPageParam param);

    /**
     * 储备计划阈值设置修改
     *
     * @param param param
     * @return 人员制度管理id
     */
    String updateData(AdminPlanThreshold param);

    /**
     * 储备计划阈值设置删除
     *
     * @param ids param
     * @return 人员制度管理id
     */
    Boolean removeData(List<String> ids);

    /**
     * 获取储备计划阈值设置详情
     *
     * @param id id
     * @return 人员制度管理
     */
    AdminPlanThresholdVo getDataById(Long id);

}
