/**
 * @filename:GradedEnterpriseSelfAssessmentSaveParam 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.collect.api.param.grade;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description:TODO(等级粮库评定管理-企业申报自评表-保存参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseSelfAssessmentSaveParam对象", description = "等级粮库评定管理-企业申报自评表-保存参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedEnterpriseSelfAssessmentSaveParam implements Serializable {

    private static final long serialVersionUID = 1684746677614L;

    @ApiModelProperty(name = "id", value = "主键ID")
    private String id;

    @ApiModelProperty(name = "qyid", value = "企业ID")
    private String qyid;

    @ApiModelProperty(name = "scoringMethodId", value = "模板树叶子节点ID")
    private String scoringMethodId;

    @ApiModelProperty(name = "sjzpf", value = "市级自评分")
    private BigDecimal sjzpf;

    @ApiModelProperty(name = "templateId", value = "等级粮库评定模板ID")
    private String templateId;

    @ApiModelProperty(name = "zpf", value = "自评分")
    private BigDecimal zpf;

    @ApiModelProperty(name = "citySdpf", value = "市实地评分")
    private BigDecimal citySddf;

    @ApiModelProperty(name = "cityKfyy", value = "市实地扣分原因")
    private String cityKfyy;

    @ApiModelProperty(name = "provinceSdpf", value = "省实地评分")
    private BigDecimal provinceSddf;

    @ApiModelProperty(name = "provinceKfyy", value = "省实扣分原因")
    private String provinceKfyy;

    @ApiModelProperty(name = "createBy", value = "创建者")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @ApiModelProperty(name = "createTime", value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(name = "updateBy", value = "更新者")
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(name = "czbz", value = "操作标志")
    private String czbz;
}
