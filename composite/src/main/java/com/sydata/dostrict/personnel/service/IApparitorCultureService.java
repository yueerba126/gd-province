package com.sydata.dostrict.personnel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.personnel.domain.ApparitorCulture;
import com.sydata.dostrict.personnel.param.ApparitorCulturePageParam;
import com.sydata.dostrict.personnel.vo.ApparitorCultureVo;

import java.util.List;

/**
 * 行政管理-人才培养计划Service接口
 *
 * @author fuql
 * @date 2023-04-25
 */
public interface IApparitorCultureService extends IService<ApparitorCulture> {
    /**
     * 行政管理-人才培养计划管理新增
     *
     * @param param param
     * @return 人员制度管理id
     */
    String saveData(ApparitorCulture param);

    /**
     * 行政管理-人才培养计划管理page列表
     *
     * @param param param
     * @return 人员制度管理page列表
     */
    Page<ApparitorCultureVo> pageData(ApparitorCulturePageParam param);

    /**
     * 行政管理-人才培养计划管理修改
     *
     * @param param param
     * @return 人员制度管理id
     */
    String updateData(ApparitorCulture param);

    /**
     * 行政管理-人才培养计划管理删除
     *
     * @param ids param
     * @return 人员制度管理id
     */
    Boolean removeData(List<String> ids);

    /**
     * 获取行政管理-人才培养计划管理详情
     *
     * @param id id
     * @return 人员制度管理
     */
    ApparitorCultureVo getDataById(Long id);

    /**
     * 行政管理-人才培养计划管理提交
     *
     * @param param param
     * @return 人员制度管理id
     */
    boolean submitDataByParam(ApparitorCulture param);

    /**
     * 行政管理-人才培养计划管理撤回
     *
     * @param id param
     * @return 人员制度管理id
     */
    boolean revocation(Long id);

    /**
     * 行政管理-人才培养计划管理反审核
     *
     * @param id param
     * @return 人员制度管理id
     */
    boolean unAudit(Long id);

    /**
     * 行政管理-人才培养计划管理审核
     *
     * @param id param
     * @return 人员制度管理id
     */
    boolean audit(Long id);
}
