package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.safe.asess.domain.OrgAssessIndex;
import com.sydata.safe.asess.param.CheckPlanIndexCheckParam;
import com.sydata.safe.asess.param.OrgAssessDeptIndexSubmitParam;
import com.sydata.safe.asess.param.OrgAssessIndexDistributionParam;
import com.sydata.safe.asess.param.ReviewIndexAssessParam;

import java.util.List;

/**
 * 粮食安全考核-单位考核指标Mapper接口
 *
 * @author system
 * @date 2023-02-16
 */
@DataPermissionExclude
public interface OrgAssessIndexMapper extends BaseMapper<OrgAssessIndex> {

    /**
     * 分配
     *
     * @param list 单位考核指标分配参数列表
     * @return 分配状态
     */
    Boolean allot(List<OrgAssessIndexDistributionParam> list);

    /**
     * 部门考核提交
     *
     * @param list 部门考核指标提交参数列表
     * @return 操作状态
     */
    Boolean deptAssessSubmit(List<OrgAssessDeptIndexSubmitParam> list);

    /**
     * 考核评审考核
     *
     * @param realLeads 考核评分指标考核参数列表
     * @return 评审状态
     */
    Boolean assess(List<ReviewIndexAssessParam> realLeads);

    /**
     * 抽查
     *
     * @param list 抽查计划指标抽查参数列表
     * @return 处理状态
     */
    Boolean check(List<CheckPlanIndexCheckParam> list);
}