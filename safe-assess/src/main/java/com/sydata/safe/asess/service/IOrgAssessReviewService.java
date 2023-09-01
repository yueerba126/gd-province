package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.OrgAssessDept;
import com.sydata.safe.asess.domain.OrgAssessDeptIndex;
import com.sydata.safe.asess.domain.OrgAssessReview;
import com.sydata.safe.asess.param.OrgAssessReviewPageParam;
import com.sydata.safe.asess.param.OrgAssessReviewSubmitParam;
import com.sydata.safe.asess.vo.OrgAssessReviewVo;

import java.util.List;

/**
 * 粮食安全考核-部门自评Service接口
 *
 * @author system
 * @date 2023-02-20
 */
public interface IOrgAssessReviewService extends IService<OrgAssessReview> {

    /**
     * 部门考核分配部门自评
     *
     * @param orgAssessDept 部门考核
     * @param indexList     部门考核指标列表
     */
    void allot(OrgAssessDept orgAssessDept, List<OrgAssessDeptIndex> indexList);

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<OrgAssessReviewVo> page(OrgAssessReviewPageParam pageParam);

    /**
     * 自评
     *
     * @param submitParam 部门自评提交参数
     * @return 操作状态
     */
    Boolean submit(OrgAssessReviewSubmitParam submitParam);

    /**
     * 退回
     *
     * @param id 部门自评ID
     * @return 退回状态
     */
    Boolean reset(String id);

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
     * 部门考核撤回
     *
     * @param orgAssessDeptId 部门考核ID
     */
    void orgAssessDeptRevoke(String orgAssessDeptId);

    /**
     * 操作权限
     *
     * @param id 部门考核ID
     * @return 权限标识
     */
    Boolean operationAuth(String id);
}