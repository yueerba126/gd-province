/**
 * @filename:GradedEnterpriseReviewApproveParam 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核-审核参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseReviewApproveParam对象", description = "等级粮库评定管理-企业申报审核-审核参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedEnterpriseReviewApproveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "qyid", value = "企业ID")
    private String qyid;

    @ApiModelProperty(name = "spzt", value = "授牌状态")
    private String spzt;

    @ApiModelProperty(name = "spCheck", value = "授牌是否校验（1校验,2不校验）")
    private String spCheck;

    @ApiModelProperty(name = "sdzt", value = "实地确认状态")
    private String sdzt;

    @ApiModelProperty(name = "xzqhdm", value = "所在区域代码")
    private String xzqhdm;

    @ApiModelProperty(name = "gradedEnterpriseProcessSaveParam", value = "当前审核信息")
    private GradedEnterpriseProcessSaveParam gradedEnterpriseProcessSaveParam;

    @ApiModelProperty(name = "gradedEnterpriseSelfAssessmentSaveParams", value = "实地评分保存参数")
    List<GradedEnterpriseSelfAssessmentSaveParam> gradedEnterpriseSelfAssessmentSaveParams;
}
