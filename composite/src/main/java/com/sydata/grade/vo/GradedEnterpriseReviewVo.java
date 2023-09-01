/**
 * @filename:GradedEnterpriseReviewVo 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.grade.domain.GradedEnterpriseReview;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核Vo)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseReviewVo对象", description = "等级粮库评定管理-企业申报审核Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GradedEnterpriseReviewVo extends GradedEnterpriseReview implements Serializable {

    private static final long serialVersionUID = 1684750987869L;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(name = "xzqhdmName", value = "所在区域名称")
    private String xzqhdmName;
    @DataBindDict(sourceField = "#sbdj", sourceFieldCombination = "grade_stock")
    @ApiModelProperty(name = "sbdjName", value = "申报等级名称")
    private String sbdjName;
    @DataBindDict(sourceField = "#spdj", sourceFieldCombination = "grade_stock")
    @ApiModelProperty(name = "spdjName", value = "授牌等级名称")
    private String spdjName;
    @DataBindDict(sourceField = "#spzt", sourceFieldCombination = "grade_spzt")
    @ApiModelProperty(name = "spztName", value = "授牌状态名称")
    private String spztName;
    @DataBindDict(sourceField = "#sdzt", sourceFieldCombination = "grade_sdzt")
    @ApiModelProperty(name = "sdztName", value = "实地评分状态名称")
    private String sdztName;
    @DataBindDict(sourceField = "#sbzt", sourceFieldCombination = "grade_sbzt")
    @ApiModelProperty(name = "sbztName", value = "申报状态名称")
    private String sbztName;
    @DataBindDict(sourceField = "#qydj", sourceFieldCombination = "grade_qydj")
    @ApiModelProperty(name = "qydjName", value = "企业等级名称")
    private String qydjName;
    @DataBindDict(sourceField = "#zsSbzt", sourceFieldCombination = "grade_flow_status")
    @ApiModelProperty(name = "zsSbztName", value = "真实申报状态名称")
    private String zsSbztName;
    @DataBindDict(sourceField = "#citySdzt", sourceFieldCombination = "grade_sdzt")
    @ApiModelProperty(name = "citySdztName", value = "市实地评分状态名称")
    private String citySdztName;
    @DataBindDict(sourceField = "#provinceSdzt", sourceFieldCombination = "grade_sdzt")
    @ApiModelProperty(name = "provinceSdztName", value = "省实地评分状态名称")
    private String provinceSdztName;
    @ApiModelProperty(name = "flowCode", value = "流程集合")
    private String flowCode;
    @ApiModelProperty(name = "flowStatus", value = "流程状态集合")
    private String flowStatus;
    @ApiModelProperty(name = "flowXzqhdm", value = "流程状态行政编码集合")
    private String flowXzqhdm;
    @ApiModelProperty(name = "czzt", value = "操作状态")
    private String czzt;
    @DataBindDict(sourceField = "#commonStatus", sourceFieldCombination = "grade_status")
    @ApiModelProperty(name = "commonStatusName", value = "状态名称")
    private String commonStatusName;
}
