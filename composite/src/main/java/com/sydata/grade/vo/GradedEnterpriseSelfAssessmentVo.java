/**
 * @filename:GradedEnterpriseSelfAssessmentVo 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.sydata.grade.annotation.DataBindGradedEnterpriseReview;
import com.sydata.grade.annotation.DataBindGradedGrainDepotStandard;
import com.sydata.grade.annotation.DataBindGradedGrainDepotTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.grade.domain.GradedEnterpriseSelfAssessment;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:TODO(等级粮库评定管理-企业申报自评表Vo)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseSelfAssessmentVo对象", description = "等级粮库评定管理-企业申报自评表Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GradedEnterpriseSelfAssessmentVo extends GradedEnterpriseSelfAssessment implements Serializable {

    private static final long serialVersionUID = 1684746677614L;

    @DataBindGradedEnterpriseReview(sourceField = "#qyid")
    @ApiModelProperty(name = "qymc", value = "企业名称")
    private String qymc;
    @DataBindGradedGrainDepotTemplate(sourceField = "#templateId")
    @ApiModelProperty(name = "templateName", value = "模板名称")
    private String templateName;
    @DataBindGradedGrainDepotStandard(sourceField = "#scoringMethodId")
    @ApiModelProperty(name = "scoringMethodName", value = "模板树叶子节点名称")
    private String scoringMethodName;
    @ApiModelProperty(name = "project", value = "项目")
    private String project;
    @ApiModelProperty(name = "subject", value = "大项题目")
    private String subject;
    @ApiModelProperty(name = "maxScore", value = "大项分值")
    private BigDecimal maxScore;
    @ApiModelProperty(name = "content", value = "评定内容")
    private String content;
    @ApiModelProperty(name = "jfzb", value = "加分指标")
    private String jfzb;
    @ApiModelProperty(name = "jfzbName", value = "加分指标名称")
    @DataBindDict(sourceField = "#jfzb", sourceFieldCombination = "grade_jfzb")
    private String jfzbName;
    @ApiModelProperty(name = "minScore", value = "小项分值")
    private BigDecimal minScore;
    @ApiModelProperty(name = "method", value = "计分方法")
    private String method;
    @ApiModelProperty(name = "projectSort", value = "字段顺序")
    private int projectSort;
}
