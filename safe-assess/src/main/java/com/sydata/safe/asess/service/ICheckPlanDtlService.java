package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.safe.asess.domain.CheckPlanDtl;
import com.sydata.safe.asess.vo.CheckPlanDtlVo;

import java.util.List;


/**
 * 粮食安全考核-抽查计划明细Service接口
 *
 * @author system
 * @date 2023-02-14
 */
public interface ICheckPlanDtlService extends IService<CheckPlanDtl> {

    /**
     * 根据单位考核ID列表查询单位考核ID列表
     *
     * @param orgAssessIds 单位考核ID列表
     * @return 单位考核ID列表
     */
    List<String> orgAssessIds(List<String> orgAssessIds);

    /**
     * 查询抽查计划明细VO列表
     *
     * @param planId 计划ID
     * @return 抽查计划明细VO列表
     */
    List<CheckPlanDtlVo> listByPlanId(String planId);

    /**
     * 设置抽查计划明细
     *
     * @param planId 计划ID
     * @param list   抽查计划明细列表
     * @return 设置状态
     */
    boolean setUp(String planId, List<CheckPlanDtl> list);

    /**
     * 删除抽查计划明细
     *
     * @param planIds 计划ID列表
     * @return 删除状态
     */
    boolean removeByPlanIds(List<String> planIds);

    /**
     * 发布
     *
     * @param planId 计划ID
     * @return 发布状态
     */
    Boolean push(String planId);

    /**
     * 抽查提交
     *
     * @param id     计划明细ID
     * @param planId 计划ID
     */
    void checkSubmit(String id, String planId);

    /**
     * 抽查撤回
     *
     * @param id     计划明细ID
     * @param planId 计划ID
     */
    void checkRevoke(String id, String planId);
}
