/**
 * @filename:GradedEnterpriseReviewApproveParam 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.param;

import com.sydata.grade.param.GradedEnterpriseProcessSaveParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description:TODO(等级粮库评定管理-企业等级库点-审核参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseStockParam对象", description = "等级粮库评定管理-企业等级库点-审核参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedEnterpriseStockParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "id", value = "id")
    private String id;

    @ApiModelProperty(name = "qyid", value = "企业ID")
    private String qyid;

    @ApiModelProperty(name = "spzt", value = "授牌状态")
    private String spzt;

    @ApiModelProperty(name = "sbzt", value = "申报状态")
    private String sbzt;

    @ApiModelProperty(name = "sdzt", value = "实地评分状态")
    private String sdzt;

    @ApiModelProperty(name = "gradedEnterpriseProcessSaveParam", value = "当前审核信息")
    private GradedEnterpriseProcessSaveParam gradedEnterpriseProcessSaveParam;
}
