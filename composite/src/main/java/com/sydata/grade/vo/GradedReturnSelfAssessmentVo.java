/**
 * @filename:GradedEnterpriseSelfAssessmentVo 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description:TODO(等级粮库评定管理-实地检查评分Vo)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseSelfAssessmentVo对象", description = "等级粮库评定管理-实地检查评分Vo")
@Data
@ToString
@Accessors(chain = true)
public class GradedReturnSelfAssessmentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "qyid", value = "企业ID")
    private String qyid;
    @ApiModelProperty(name = "scoringMethodId", value = "指标最小项ID")
    private String scoringMethodId;
    @ApiModelProperty(name = "templateId", value = "模板ID")
    private String templateId;
    @ApiModelProperty(name = "sjzpf", value = "市级自评分")
    private BigDecimal sjzpf;
    @ApiModelProperty(name = "sjzpf", value = "自评分")
    private BigDecimal zpf;
    @ApiModelProperty(name = "sdjcdf", value = "实地检查评分")
    private BigDecimal sdjcdf;
    @ApiModelProperty(name = "kfyy", value = "扣分原因")
    private String kfyy;
    @ApiModelProperty(name = "projectSort", value = "字段顺序")
    private int projectSort;
}
