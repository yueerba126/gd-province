package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.Region;
import com.sydata.safe.asess.domain.*;
import com.sydata.safe.asess.param.*;
import com.sydata.safe.asess.vo.OrgAssessVo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 粮食安全考核-单位考核Service接口
 *
 * @author system
 * @date 2023-02-16
 */
public interface IOrgAssessService extends IService<OrgAssess> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<OrgAssessVo> page(OrgAssessPageParam pageParam);

    /**
     * 考核模板发布
     *
     * @param template       考核模板
     * @param list           考核模板指标
     * @param regions        行政区域列表
     * @param pushDate       发布日期
     * @param lastSubmitDate 最晚提交日期
     * @return 发布状态
     */
    boolean push(Template template, List<TemplateIndex> list, List<Region> regions, LocalDate pushDate, LocalDate lastSubmitDate);

    /**
     * 单位考核VO
     *
     * @param id 主键
     * @return 单位考核VO
     */
    OrgAssessVo details(String id);

    /**
     * 分配
     *
     * @param allotParam 单位考核分配参数
     * @return 分配状态
     */
    Boolean allot(OrgAssessAllotParam allotParam);

    /**
     * 设置附件
     *
     * @param uploadFileParam 单位考核设置附件参数
     * @return 修改状态
     */
    Boolean uploadFiles(OrgAssessUploadFileParam uploadFileParam);

    /**
     * 部门考核提交
     *
     * @param orgAssessDept    部门考核
     * @param leadAlreadyScore 已自评牵头总分
     * @return 操作状态
     */
    Boolean deptAssessSubmit(OrgAssessDept orgAssessDept, BigDecimal leadAlreadyScore);

    /**
     * 部门考核退回
     *
     * @param orgAssessDept 部门考核
     * @return 操作状态
     */
    Boolean deptAssessReset(OrgAssessDept orgAssessDept);

    /**
     * 提交
     *
     * @param id 单位考核ID
     * @return 提交状态
     */
    Boolean submit(String id);

    /**
     * 退回
     *
     * @param id 单位考核ID
     * @return 退回状态
     */
    Boolean reset(String id);

    /**
     * 撤回
     *
     * @param id 单位考核ID
     * @return 撤回状态
     */
    Boolean revoke(String id);

    /**
     * 考核评审考核
     *
     * @param id             单位考核ID
     * @param templateId     考核模板ID
     * @param realLeads      牵头指标列表
     * @param cooperatesSize 配合指标数
     * @return 考核状态
     */
    boolean assess(String id, String templateId, List<ReviewIndexAssessParam> realLeads, int cooperatesSize);

    /**
     * 考核评审退回
     *
     * @param id             单位考核ID
     * @param templateId     考核模板ID
     * @param realLeads      牵头指标列表
     * @param cooperatesSize 配合指标数
     * @return 退回状态
     */
    boolean assessReset(String id, String templateId, List<ReviewIndex> realLeads, int cooperatesSize);

    /**
     * 抽查
     *
     * @param id         单位考核ID
     * @param templateId 考核模板ID
     * @param list       抽查计划指标抽查参数列表
     * @return 处理状态
     */
    Boolean check(String id, String templateId, List<CheckPlanIndexCheckParam> list);

    /**
     * 设置抽查附件ID
     *
     * @param uploadFileParam 抽查计划设置附件参数
     * @return 修改状态
     */
    Boolean uploadCheckFiles(CheckPlanUploadFileParam uploadFileParam);

    /**
     * 考核模板撤回
     *
     * @param templateId 考核模板ID
     */
    void templateRevoke(String templateId);

    /**
     * 导出
     *
     * @param exportParam 单位考核导出参数
     */
    void export(OrgAssessExportParam exportParam);

    /**
     * 操作权限
     *
     * @param id 考核模板ID
     * @return 权限标识
     */
    Boolean operationAuth(String id);
}