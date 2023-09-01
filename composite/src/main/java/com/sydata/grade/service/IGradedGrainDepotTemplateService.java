/**
 * @filename:GradedGrainDepotTemplateService 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service;

import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import com.sydata.framework.util.BeanUtils;
import com.sydata.grade.domain.GradedGrainDepotTemplate;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.grade.domain.GradedGrainDepotTemplate;
import com.sydata.grade.param.GradedGrainDepotTemplateSaveParam;
import com.sydata.grade.param.GradedGrainDepotTemplatePageParam;
import com.sydata.grade.vo.GradedGrainDepotStandardExcelVo;
import com.sydata.grade.vo.GradedGrainDepotTemplateVo;
import com.sydata.framework.databind.domain.DataBindQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定模板服务层)
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IGradedGrainDepotTemplateService extends IService<GradedGrainDepotTemplate> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedGrainDepotTemplateVo> pages(GradedGrainDepotTemplatePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GradedGrainDepotTemplateVo detail(String id);

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
    String saveData(GradedGrainDepotTemplateSaveParam param);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveDataByFile(GradedGrainDepotTemplateSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(GradedGrainDepotTemplateSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateDataByFile(GradedGrainDepotTemplateSaveParam param);

    /**
     * 发布
     *
     * @param id 参数
     * @return 主键ID
     */
    Boolean push(String id);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

    /**
     * 删除
     *
     * @param nf,templateName 参数
     * @return 主键ID
     */
    GradedGrainDepotTemplate getByUnxName(String nf);

    /**
     * 导出模板数据
     *
     * @param id
     * @return 主键ID
     */
    void export(String id);

    /**
     * 获取当前年份的已发布的模板
     *
     * @return GradedGrainDepotTemplate
     */
    GradedGrainDepotTemplate getNowYearGradedGrainDepotTemplate();

    /**
     * 获取当前年份的已发布的模板(如果使用的是旧模板且已经申报的用它)
     *
     * @return GradedGrainDepotTemplate
     */
    GradedGrainDepotTemplate getGradedGrainDepotTemplateById(String templateId);
}