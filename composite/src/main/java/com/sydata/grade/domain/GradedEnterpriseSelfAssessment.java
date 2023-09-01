/**
 * @filename:GradedEnterpriseSelfAssessment 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

/**
 * @Description:TODO(等级粮库评定管理-企业申报自评表实体类)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseSelfAssessment对象", description = "等级粮库评定管理-企业申报自评表")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("graded_enterprise_self_assessment")
public class GradedEnterpriseSelfAssessment implements Serializable {

    private static final long serialVersionUID = 1684746677614L;

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;

    @ApiModelProperty(name = "czbz", value = "操作标志")
    private String czbz;

    @ApiModelProperty(name = "qyid", value = "企业ID")
    private String qyid;

    @ApiModelProperty(name = "scoringMethodId", value = "模板树叶子节点ID")
    private String scoringMethodId;

    @ApiModelProperty(name = "citySdpf", value = "市实地评分")
    private BigDecimal citySddf;

    @ApiModelProperty(name = "cityKfyy", value = "市实地扣分原因")
    private String cityKfyy;

    @ApiModelProperty(name = "provinceSdpf", value = "省实地评分")
    private BigDecimal provinceSddf;

    @ApiModelProperty(name = "provinceKfyy", value = "省实扣分原因")
    private String provinceKfyy;

    @ApiModelProperty(name = "sjzpf", value = "市级自评分")
    private BigDecimal sjzpf;

    @ApiModelProperty(name = "templateId", value = "等级粮库评定模板ID")
    private String templateId;

    @ApiModelProperty(name = "zpf", value = "自评分")
    private BigDecimal zpf;

    @ApiModelProperty(name = "createBy", value = "创建者")
    private String createBy;

    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;

    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;
}
