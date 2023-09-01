package com.sydata.dostrict.casetarget.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.dostrict.casetarget.domain.ApparitorCase;
import com.sydata.dostrict.casetarget.param.ApparitorCasePageParam;
import com.sydata.dostrict.casetarget.param.ApparitorCaseSavaParam;
import com.sydata.dostrict.casetarget.vo.ApparitorCaseVo;

import java.util.List;

/**
 * 行政管理-执法案件Service接口
 *
 * @author fuql
 * @date 2023-04-26
 */
public interface IApparitorCaseService extends IService<ApparitorCase> {

    /**
     * 行政管理-人才培养计划管理新增
     *
     * @param param param
     * @return 人员制度管理id
     */
    String saveData(ApparitorCaseSavaParam param);

    /**
     * 行政管理-人才培养计划管理page列表
     *
     * @param param param
     * @return 人员制度管理page列表
     */
    Page<ApparitorCaseVo> pageData(ApparitorCasePageParam param);

    /**
     * 行政管理-人才培养计划管理修改
     *
     * @param param param
     * @return 人员制度管理id
     */
    String updateData(ApparitorCase param);

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
    ApparitorCaseVo getDataById(Long id);
    
    
}
