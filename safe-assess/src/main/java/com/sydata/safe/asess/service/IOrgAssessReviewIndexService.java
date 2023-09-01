package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.OrgAssessReviewIndex;
import com.sydata.safe.asess.param.OrgAssessReviewIndexSubmitParam;
import com.sydata.safe.asess.vo.OrgAssessReviewIndexTreeVo;

import java.util.List;

/**
 * 粮食安全考核-部门自评指标Service接口
 *
 * @author system
 * @date 2023-02-20
 */
public interface IOrgAssessReviewIndexService extends IService<OrgAssessReviewIndex> {

    /**
     * 列表
     *
     * @param orgAssessDeptReviewId 部门自评ID
     * @return 列表
     */
    List<OrgAssessReviewIndex> listByAssessDeptReviewId(String orgAssessDeptReviewId);

    /**
     * 树形列表
     *
     * @param orgAssessDeptReviewId 部门自评ID
     * @return 树形列表
     */
    List<OrgAssessReviewIndexTreeVo> treeByAssessDeptReviewId(String orgAssessDeptReviewId);

    /**
     * 自评
     *
     * @param orgAssessDeptReviewId 部门自评ID
     * @param list                  部门自评指标提交参数列表
     * @return 操作状态
     */
    Boolean submit(String orgAssessDeptReviewId, List<OrgAssessReviewIndexSubmitParam> list);

    /**
     * 根据部门自评ID列表删除
     *
     * @param orgAssessDeptReviewIds 部门自评ID列表
     */
    void removeByOrgAssessDeptReviewIds(List<String> orgAssessDeptReviewIds);
}