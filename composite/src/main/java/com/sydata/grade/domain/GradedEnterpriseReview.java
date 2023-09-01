/**
 * @filename:GradedEnterpriseReview 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sydata.collect.api.enums.GradeEscalateEnum;
import com.sydata.collect.api.param.grade.GradedEnterpriseFlowSaveParam;
import com.sydata.collect.api.param.grade.GradedEnterpriseMaterialSaveParam;
import com.sydata.collect.api.param.grade.GradedEnterpriseProcessSaveParam;
import com.sydata.collect.api.param.grade.GradedEnterpriseSelfAssessmentSaveParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import com.sydata.common.domain.BaseFiledEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核实体类)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseReview对象", description = "等级粮库评定管理-企业申报审核")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("graded_enterprise_review")
public class GradedEnterpriseReview extends BaseFiledEntity implements Serializable {

    private static final long serialVersionUID = 1684750987869L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;

    @ApiModelProperty(name = "czbz", value = "操作标志")
    private String czbz;

    @ApiModelProperty(name = "kqdm", value = "库区代码")
    private String kqdm;

    @ApiModelProperty(name = "kqmc", value = "库区名称")
    private String kqmc;

    @ApiModelProperty(name = "lkcr", value = "粮库仓(罐)容(吨)")
    private BigDecimal lkcr;

    @ApiModelProperty(name = "qydj", value = "企业等级（县、市、省、区）")
    private String qydj;

    @ApiModelProperty(name = "qydm", value = "企业代码")
    private String qydm;

    @ApiModelProperty(name = "qymc", value = "企业名称")
    private String qymc;

    @ApiModelProperty(name = "sbdj", value = "申报等级(1A-5A)")
    private String sbdj;

    @ApiModelProperty(name = "sbnf", value = "申报年份")
    private String sbnf;

    @ApiModelProperty(name = "sbrq", value = "申报或推荐日期")
    private LocalDate sbrq;

    @ApiModelProperty(name = "sbzt", value = "申报状态")
    private String sbzt;

    @ApiModelProperty(name = "citySdpf", value = "市实地评分（总）")
    private BigDecimal citySdpf;

    @ApiModelProperty(name = "citySdzt", value = "市实地评分状态")
    private String citySdzt;

    @ApiModelProperty(name = "provinceSdpf", value = "省实地评分（总）")
    private BigDecimal provinceSdpf;

    @ApiModelProperty(name = "provinceSdzt", value = "省实地评分状态")
    private String provinceSdzt;

    @ApiModelProperty(name = "spdj", value = "授牌等级(1A-5A)")
    private String spdj;

    @ApiModelProperty(name = "spnf", value = "授牌年份")
    private String spnf;

    @ApiModelProperty(name = "spzt", value = "授牌状态")
    private String spzt;

    @ApiModelProperty(name = "xzqhdm", value = "所在区域代码")
    private String xzqhdm;

    @ApiModelProperty(name = "zpf", value = "自评分（总）")
    private BigDecimal zpf;

    @TableField(exist = false)
    @ApiModelProperty(name = "buttonStr", value = "按钮值（用逗号隔开的）")
    private String buttonStr;

    @TableField(exist = false)
    @ApiModelProperty(name = "commonStatus", value = "状态")
    private String commonStatus;

    @TableField(exist = false)
    @ApiModelProperty(name = "zsSbzt", value = "真实申报状态")
    private String zsSbzt;

    @TableField(exist = false)
    @ApiModelProperty(name = "lastZsSbzt", value = "上一个节点的真实申报状态")
    private String lastZsSbzt;

    @TableField(exist = false)
    @ApiModelProperty(name = "gradedEnterpriseSelfAssessmentSaveParams", value = "实地评分保存参数")
    private List<GradedEnterpriseSelfAssessmentSaveParam> gradedEnterpriseSelfAssessmentSaveParams;

    @TableField(exist = false)
    @ApiModelProperty(name = "gradedEnterpriseFlowSaveParams", value = "流程保存参数")
    private List<GradedEnterpriseFlowSaveParam> gradedEnterpriseFlowSaveParams;

    @TableField(exist = false)
    @ApiModelProperty(name = "gradedEnterpriseMaterialSaveParams", value = "证明材料保存参数")
    private List<GradedEnterpriseMaterialSaveParam> gradedEnterpriseMaterialSaveParams;

    @TableField(exist = false)
    @ApiModelProperty(name = "gradedEnterpriseProcessSaveParams", value = "审核详情保存参数")
    private List<GradedEnterpriseProcessSaveParam> gradedEnterpriseProcessSaveParams;
}
