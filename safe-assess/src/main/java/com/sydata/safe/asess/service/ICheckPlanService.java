package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.CheckPlan;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.vo.CheckPlanVo;

/**
 * 粮食安全考核-抽查计划Service接口
 *
 * @author system
 * @date 2023-02-14
 */
public interface ICheckPlanService extends IService<CheckPlan> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<CheckPlanVo> pages(CheckPlanPageParam pageParam);

    /**
     * 新增
     *
     * @param saveParam 抽查计划新增参数
     * @return 新增状态
     */
    Boolean save(CheckPlanSaveParam saveParam);

    /**
     * 修改
     *
     * @param updateParam 抽查计划修改参数
     * @return 修改状态
     */
    Boolean update(CheckPlanUpdateParam updateParam);

    /**
     * 发布
     *
     * @param id 抽查计划ID
     * @return 发布状态
     */
    Boolean push(String id);

    /**
     * 查看当前登录人是否有抽查计划的权限
     *
     * @param id 抽查计划ID
     * @return 权限标识
     */
    Boolean checkAuth(String id);

    /**
     * 抽查
     *
     * @param checkParam 抽查计划抽查参数
     * @return 抽查结果
     */
    Boolean check(CheckPlanCheckParam checkParam);

    /**
     * 撤回
     *
     * @param planId 计划ID
     * @param dtlId  计划明细ID
     * @return 撤回状态
     */
    Boolean revoke(String planId, String dtlId);

    /**
     * 上传抽查文件
     *
     * @param uploadFileParam 抽查计划设置附件参数
     * @return 权限标识
     */
    Boolean uploadCheckFiles(CheckPlanUploadFileParam uploadFileParam);

    /**
     * 考核模板撤回
     *
     * @param templateId 考核模板ID
     */
    void templateRevoke(String templateId);

    /**
     * 操作权限
     *
     * @param id 考核模板ID
     * @return 权限标识
     */
    Boolean operationAuth(String id);
}
