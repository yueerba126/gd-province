/**
 * @filename:GradedGrainDepotStandardService 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service;

import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.grade.domain.GradedGrainDepotStandard;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.grade.dto.XmDto;
import com.sydata.grade.param.FileStorageUploadParam;
import com.sydata.grade.param.GradedGrainDepotStandardSaveParam;
import com.sydata.grade.param.GradedGrainDepotStandardPageParam;
import com.sydata.grade.vo.GradedEnterpriseSelfAssessmentVo;
import com.sydata.grade.vo.GradedGrainDepotStandardExcelVo;
import com.sydata.grade.vo.GradedGrainDepotStandardTreeVo;
import com.sydata.grade.vo.GradedGrainDepotStandardVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定标准服务层)
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IGradedGrainDepotStandardService extends IService<GradedGrainDepotStandard> {
    /**
     * 等级粮库评定标准Excel文件上传
     *
     * @param file 文件
     * @return 解析内容
     */
    List<XmDto> uploadUse(FileStorageUploadParam file);

    /**
     * 组装模板数据
     *
     * @param id
     * @return 主键ID
     */
    List<XmDto> getGradedGrainDepotStandardDto (String id);

    /**
     * 组装模板数据（如果使用的是旧模板且已经申报的用它）
     *
     * @param id
     * @return 主键ID
     */
    List<XmDto> getGradedGrainDepotStandardDtoWithHistory (String id);

    /**
     * 组装模板数据
     *
     * @param id
     * @return 主键ID
     */
    List<XmDto> getGradedGrainDepotStandardDto (String id,List<GradedEnterpriseSelfAssessmentVo> gradedEnterpriseSelfAssessmentVoList);

    /**
     * 组装模板数据（如果使用的是旧模板且已经申报的用它）
     *
     * @param id
     * @return 主键ID
     */
    List<XmDto> getGradedGrainDepotStandardDtoWithHistory (String id,List<GradedEnterpriseSelfAssessmentVo> gradedEnterpriseSelfAssessmentVoList);

    /**
     * 组装模板数据
     *
     * @param id
     * @return 主键ID
     */
    List<GradedGrainDepotStandardExcelVo> getGradedGrainDepotStandardExcelVos(String id);

    /**
     * 组装模板数据（如果使用的是旧模板且已经申报的用它）
     *
     * @param id
     * @return 主键ID
     */
    List<GradedGrainDepotStandardExcelVo> getGradedGrainDepotStandardExcelVosWithHistory(String id);

    /**
     * 等级粮库评定标准Excel解析
     *
     * @return 解析内容
     */
    void excelImportParsing(InputStream inputStream, String templateId, String fileName);

    /**
     * 等级粮库评定标准树形列表
     *
     * @param templateId 模板id
     * @return 等级粮库评定标准树形列表
     */
    List<GradedGrainDepotStandardTreeVo> treeByTemplateId(String templateId);

    /**
     * 等级粮库评定标准列表
     *
     * @param templateId 模板id
     * @return 等级粮库评定标准列表
     */
    List<GradedGrainDepotStandardVo> listByTemplateId(String templateId);

    /**
     * (如果使用的是旧模板且已经申报的用它)
     *
     * @param templateId 模板id
     * @return 等级粮库评定标准列表
     */
    List<GradedGrainDepotStandardVo> listByTemplateIdWithHistory(String templateId);

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedGrainDepotStandardVo> pages(GradedGrainDepotStandardPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GradedGrainDepotStandardVo detail(String id);

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
    String saveData(GradedGrainDepotStandardSaveParam param);

    /**
     * 提交
     *
     * @param param 参数
     * @return 主键ID
     */
    Boolean submitData(GradedGrainDepotStandardSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(GradedGrainDepotStandardSaveParam param);

    /**
     * 批量新增或更新
     *
     * @param params 参数
     * @return 主键ID
     */
    Boolean saveOrUpdateBatchData(List<GradedGrainDepotStandardSaveParam> params,String templateId);

    /**
     * 删除
     *
     * @param id 参数
     * @return 主键ID
     */
    Boolean removeData(String id);

    /**
     * 删除
     *
     * @param templateIds 参数
     * @return 主键ID
     */
    Boolean removeDataByTemplateId(List<String> templateIds);

    /**
     * 用父类id查询
     *
     * @param parentId 参数
     * @return 主键ID
     */
    List<GradedGrainDepotStandard> listByParentId(String parentId);
}