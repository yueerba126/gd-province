package com.sydata.reserve.manage.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 储备计划管理-储备计划审核参数
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划审核参数")
public class PlanManageAuditParam implements Serializable {

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @ApiModelProperty(value = "状态,字典：plan_manage_status")
    private String billStatus;

}
