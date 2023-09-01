package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.safe.asess.domain.OrgAssessDeptIndex;
import com.sydata.safe.asess.param.OrgAssessDeptIndexDistributionParam;
import com.sydata.safe.asess.param.OrgAssessDeptIndexSubmitParam;
import com.sydata.safe.asess.param.OrgAssessReviewIndexSubmitParam;

import java.util.List;

/**
 * 粮食安全考核-部门考核指标Mapper接口
 *
 * @author system
 * @date 2023-02-18
 */
@DataPermissionExclude
public interface OrgAssessDeptIndexMapper extends BaseMapper<OrgAssessDeptIndex> {

    /**
     * 分配
     *
     * @param list 部门考核指标分配参数
     * @return 修改状态
     */
    Boolean distribution(List<OrgAssessDeptIndexDistributionParam> list);

    /**
     * 部门自评
     *
     * @param list 部门自评指标提交参数列表
     * @return 修改状态
     */
    Boolean reviewSubmit(List<OrgAssessReviewIndexSubmitParam> list);

    /**
     * 部门考核
     *
     * @param list 部门考核指标提交参数列表
     * @return 修改状态
     */
    Boolean submit(List<OrgAssessDeptIndexSubmitParam> list);
}