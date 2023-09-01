package com.sydata.dostrict.plan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.plan.domain.ApparitorPlan;
import com.sydata.dostrict.plan.param.ApparitorPlanPageParam;
import com.sydata.dostrict.plan.param.ApparitorPlanSaveParam;
import com.sydata.dostrict.plan.vo.ApparitorPlanVo;

import java.util.List;

/**
 * @program: gd-province-platform
 * @description: 规划建设-仓储设施建设管理 Service接口
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
public interface IApparitorPlanService extends IService<ApparitorPlan> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ApparitorPlanVo> pages(ApparitorPlanPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return VO
     */
    ApparitorPlanVo detail(String id);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(ApparitorPlanSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(ApparitorPlanSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);
}
