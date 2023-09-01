package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.OrgAssess;
import com.sydata.safe.asess.domain.OrgAssessDept;
import com.sydata.safe.asess.domain.OrgAssessDeptIndex;
import com.sydata.safe.asess.domain.OrgAssessIndex;
import com.sydata.safe.asess.param.OrgAssessDeptAllotParam;
import com.sydata.safe.asess.param.OrgAssessDeptPageParam;
import com.sydata.safe.asess.param.OrgAssessDeptSubmitParam;
import com.sydata.safe.asess.vo.OrgAssessDeptVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 粮食安全考核-部门考核Service接口
 *
 * @author system
 * @date 2023-02-18
 */
public interface IOrgAssessDeptService extends IService<OrgAssessDept> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<OrgAssessDeptVo> page(OrgAssessDeptPageParam pageParam);

    /**
     * 单位考核分配部门考核
     *
     * @param orgAssess            单位考核
     * @param indices              单位考核指标列表
     * @param cooperateDeptEndDate 配合部门填报截止日期
     * @return 部门考核指标列表
     */
    List<OrgAssessDeptIndex> allot(OrgAssess orgAssess, List<OrgAssessIndex> indices, LocalDate cooperateDeptEndDate);

    /**
     * 分配
     *
     * @param allotParam 部门考核分配参数
     * @return 分配状态
     */
    Boolean allot(OrgAssessDeptAllotParam allotParam);

    /**
     * 自评
     *
     * @param submitParam 部门考核自评参数
     * @return 自评状态
     */
    Boolean submit(OrgAssessDeptSubmitParam submitParam);

    /**
     * 操作提交自评部门数
     *
     * @param id    部门考核ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationDeptSubmitCount(String id, int count);

    /**
     * 退回
     *
     * @param id 部门考核ID
     * @return 退回状态
     */
    Boolean reset(String id);

    /**
     * 撤回
     *
     * @param id 部门考核ID
     * @return 撤回状态
     */
    Boolean revoke(String id);

    /**
     * 根据单位考核ID查询部门考核映射
     *
     * @param orgAssessId 单位考核ID
     * @return 部门考核映射《部门ID,《单位考核指标ID,部门考核指标》》
     */
    Map<Long, Map<String, OrgAssessDeptIndex>> indexMap(String orgAssessId);

    /**
     * 考核模板撤回
     *
     * @param templateId 考核模板ID
     */
    void templateRevoke(String templateId);

    /**
     * 单位考核撤回
     *
     * @param orgAssessId 单位考核ID
     */
    void orgAssessRevoke(String orgAssessId);

    /**
     * 操作权限
     *
     * @param id 部门考核ID
     * @return 权限标识
     */
    Boolean operationAuth(String id);
}