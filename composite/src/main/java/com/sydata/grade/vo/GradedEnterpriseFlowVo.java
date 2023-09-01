/**
 * @filename:GradedEnterpriseFlowVo 2023年05月24日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.vo;
import com.sydata.grade.annotation.DataBindGradedEnterpriseReview;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.grade.domain.GradedEnterpriseFlow;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**   
 * @Description:TODO(等级粮库评定管理-企业申报流程表Vo)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedEnterpriseFlowVo对象", description="等级粮库评定管理-企业申报流程表Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GradedEnterpriseFlowVo extends GradedEnterpriseFlow implements Serializable {

	private static final long serialVersionUID = 1684892830417L;

    @DataBindGradedEnterpriseReview(sourceField = "#qyid")
    @ApiModelProperty(name = "qymc" , value = "企业名称")
    private String qymc;
    @DataBindDict(sourceField = "#flowCode", sourceFieldCombination = "grade_province_flow")
    @ApiModelProperty(name = "flowName" , value = "流程名称")
    private String flowName;
    @DataBindDict(sourceField = "#flowStatus", sourceFieldCombination = "grade_flow_status")
    @ApiModelProperty(name = "flowStatusName" , value = "流程状态名称")
    private String flowStatusName;
    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(name = "xzqhdmName", value = "所在区域名称")
    private String xzqhdmName;
}
