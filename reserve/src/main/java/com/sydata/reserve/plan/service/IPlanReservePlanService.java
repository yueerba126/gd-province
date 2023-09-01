package com.sydata.reserve.plan.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.reserve.plan.domain.PlanReservePlan;
import com.sydata.reserve.plan.param.PlanReservePlanAdjustParam;
import com.sydata.reserve.plan.param.PlanReservePlanPageParam;
import com.sydata.reserve.plan.param.PlanReservePlanSaveParam;
import com.sydata.reserve.plan.param.PlanReservePlanUpdateParam;
import com.sydata.reserve.plan.vo.PlanReservePlanLogVo;
import com.sydata.reserve.plan.vo.PlanReservePlanSendLogVo;
import com.sydata.reserve.plan.vo.PlanReservePlanVo;

import java.util.List;

/**
 * 储备计划管理-储备计划Service接口
 *
 * @author fuql
 * @date 2023-05-19
 */
public interface IPlanReservePlanService extends IService<PlanReservePlan> {

    /**
     * 分页查询储备计划管理-储备计划
     *
     * @param param 参数
     * @return 储备计划
     */
    Page<PlanReservePlanVo> pageData(PlanReservePlanPageParam param);

    /**
     * 新增储备计划管理-储备计划
     *
     * @param param 参数
     * @return 计划ID
     */
    String saveData(PlanReservePlanSaveParam param);

    /**
     * 修改储备计划管理-储备计划
     *
     * @param param 参数
     * @return 计划ID
     */
    String updateData(PlanReservePlanUpdateParam param);

    /**
     * 生成编号
     *
     * @return 编号
     */
    String generateBillMainCode();

    /**
     * 批量删除
     *
     * @param mainIds mainIds
     * @return 操作状态
     */
    Boolean batchAbolish(List<String> mainIds);

    /**
     * 批量提交
     *
     * @param mainIds mainIds
     * @return 操作状态
     */
    Boolean submit(List<String> mainIds);

    /**
     * 批量反提交
     *
     * @param mainIds mainIds
     * @return 操作状态
     */
    Boolean unSubmit(List<String> mainIds);

    /**
     * 下发
     *
     * @param mainId mainId
     * @return 操作状态
     */
    Boolean distribution(String mainId);

    /**
     * 计划调整
     *
     * @param param 计划调整
     * @return id
     */
    String adjust(PlanReservePlanAdjustParam param);

    /**
     * 下发日志
     *
     * @param mainId mainId
     * @return 操作状态
     */
    List<PlanReservePlanSendLogVo> distributionLog(String mainId);

    /**
     * 计划调整日志
     *
     * @param mainId mainId
     * @return 操作状态
     */
    List<PlanReservePlanLogVo> adjustLog(String mainId);
}
