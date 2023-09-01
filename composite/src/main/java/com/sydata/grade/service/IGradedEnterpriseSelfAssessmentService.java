/**
 * @filename:GradedEnterpriseSelfAssessmentService 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service;

import com.sydata.grade.domain.GradedEnterpriseSelfAssessment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.grade.domain.GradedEnterpriseSelfAssessment;
import com.sydata.grade.dto.XmDto;
import com.sydata.grade.param.GradedEnterpriseSelfAssessmentSaveParam;
import com.sydata.grade.param.GradedEnterpriseSelfAssessmentPageParam;
import com.sydata.grade.vo.GradedEnterpriseSelfAssessmentVo;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.grade.vo.GradedReturnSelfAssessmentVo;

import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-企业申报自评表服务层)
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IGradedEnterpriseSelfAssessmentService extends IService<GradedEnterpriseSelfAssessment> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedEnterpriseSelfAssessmentVo> pages(GradedEnterpriseSelfAssessmentPageParam pageParam);

    /**
     * 列表
     *
     * @param qyid 企业id
     * @return 列表
     */
    List<GradedEnterpriseSelfAssessmentVo> list(String qyid);

    /**
     * 列表
     *
     * @param qyid 企业id
     * @return 列表
     */
    List<XmDto> listDto(String qyid);

    /**
     * 列表
     *
     * @param qyid 企业id
     * @return 列表
     */
    List<GradedReturnSelfAssessmentVo> getGradedReturnSelfAssessmentList(String qyid,Boolean sdpf);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GradedEnterpriseSelfAssessmentVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(GradedEnterpriseSelfAssessmentSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(GradedEnterpriseSelfAssessmentSaveParam param);

    /**
     * 批量新增或修改
     *
     * @param params 参数
     */
    Boolean saveOrUpdateBatchData(List<GradedEnterpriseSelfAssessmentSaveParam> params);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

}