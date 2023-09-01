package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.OrgAssessIndex;
import com.sydata.safe.asess.param.CheckPlanIndexCheckParam;
import com.sydata.safe.asess.param.OrgAssessDeptIndexSubmitParam;
import com.sydata.safe.asess.param.OrgAssessIndexDistributionParam;
import com.sydata.safe.asess.param.ReviewIndexAssessParam;
import com.sydata.safe.asess.vo.OrgAssessIndexTreeVo;

import java.util.List;
import java.util.Map;

/**
 * 粮食安全考核-单位考核指标Service接口
 *
 * @author system
 * @date 2023-02-16
 */
public interface IOrgAssessIndexService extends IService<OrgAssessIndex> {

    /**
     * 列表
     *
     * @param orgAssessId 单位考核ID
     * @return 列表
     */
    List<OrgAssessIndex> listByOrgAssessId(String orgAssessId);

    /**
     * 列表
     *
     * @param orgAssessIds 单位考核ID列表
     * @return 列表
     */
    List<OrgAssessIndex> listByOrgAssessIds(List<String> orgAssessIds);

    /**
     * 树形列表
     *
     * @param orgAssessId 单位考核ID
     * @return 树形列表
     */
    List<OrgAssessIndexTreeVo> treeByOrgAssessId(String orgAssessId);

    /**
     * 批量获取树形列表
     *
     * @param orgAssessIds 单位考核ID列表
     * @return 树形列表
     */
    Map<String, List<OrgAssessIndexTreeVo>> treeMapByOrgAssessIds(List<String> orgAssessIds);

    /**
     * map ID映射结构
     *
     * @param orgAssessId 单位考核ID
     * @return ID映射结构
     */
    Map<String, OrgAssessIndex> mapByOrgAssessId(String orgAssessId);

    /**
     * 考核模板指标idMap结构
     *
     * @param orgAssessId 单位考核ID
     * @return ID映射结构
     */
    Map<String, OrgAssessIndex> templateIndexMapByOrgAssessId(String orgAssessId);

    /**
     * 分配
     *
     * @param orgAssessId 单位考核ID
     * @param list        单位考核指标分配参数列表
     * @return 分配状态
     */
    Boolean allot(String orgAssessId, List<OrgAssessIndexDistributionParam> list);

    /**
     * 部门考核提交
     *
     * @param orgAssessId 单位考核ID
     * @param list        部门考核指标提交参数列表
     * @return 操作状态
     */
    Boolean deptAssessSubmit(String orgAssessId, List<OrgAssessDeptIndexSubmitParam> list);

    /**
     * 考核评审考核
     *
     * @param orgAssessId 单位考核ID
     * @param list        考核评审指标考核参数列表
     * @return 操作状态
     */
    Boolean assess(String orgAssessId, List<ReviewIndexAssessParam> list);

    /**
     * 抽查
     *
     * @param orgAssessId 单位考核ID
     * @param list        抽查计划指标抽查参数列表
     * @return 处理状态
     */
    Boolean check(String orgAssessId, List<CheckPlanIndexCheckParam> list);

    /**
     * 根据单位考核ID列表删除
     *
     * @param orgAssessIds 单位考核ID列表
     */
    void removeByOrgAssessIds(List<String> orgAssessIds);
}
