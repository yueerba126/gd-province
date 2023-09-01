package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.OrgAssessDeptIndex;
import com.sydata.safe.asess.param.OrgAssessDeptIndexDistributionParam;
import com.sydata.safe.asess.param.OrgAssessDeptIndexSubmitParam;
import com.sydata.safe.asess.param.OrgAssessReviewIndexSubmitParam;
import com.sydata.safe.asess.vo.OrgAssessDeptIndexTreeVo;

import java.util.List;

/**
 * 粮食安全考核-部门考核指标Service接口
 *
 * @author system
 * @date 2023-02-18
 */
public interface IOrgAssessDeptIndexService extends IService<OrgAssessDeptIndex> {

    /**
     * 列表
     *
     * @param orgAssessDeptId 部门考核ID
     * @return 列表
     */
    List<OrgAssessDeptIndex> listByOrgAssessDeptId(String orgAssessDeptId);

    /**
     * 列表
     *
     * @param orgAssessDeptIds 部门考核ID列表
     * @return 列表
     */
    List<OrgAssessDeptIndex> listByOrgAssessDeptIds(List<String> orgAssessDeptIds);

    /**
     * 树形列表
     *
     * @param orgAssessDeptId 部门考核ID
     * @return 树形列表
     */
    List<OrgAssessDeptIndexTreeVo> treeByAssessDeptId(String orgAssessDeptId);

    /**
     * 分配
     *
     * @param orgAssessDeptId 部门考核ID
     * @param list            部门考核指标分配参数
     * @return 分配状态
     */
    Boolean distribution(String orgAssessDeptId, List<OrgAssessDeptIndexDistributionParam> list);

    /**
     * 部门自评
     *
     * @param orgAssessDeptId 部门考核ID
     * @param list            部门自评指标提交参数列表
     * @return 操作状态
     */
    Boolean reviewSubmit(String orgAssessDeptId, List<OrgAssessReviewIndexSubmitParam> list);

    /**
     * 部门考核
     *
     * @param orgAssessDeptId 部门考核ID
     * @param list            部门考核指标提交参数列表
     * @return 操作状态
     */
    Boolean submit(String orgAssessDeptId, List<OrgAssessDeptIndexSubmitParam> list);

    /**
     * 根据部门考核ID列表删除
     *
     * @param orgAssessDeptIds 部门考核ID列表
     */
    void removeByOrgAssessDeptIds(List<String> orgAssessDeptIds);
}