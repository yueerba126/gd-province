package com.sydata.reserve.plan.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.reserve.plan.domain.PlanReservePlanSendLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 储备计划管理-储备计划调整日志返回值
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划调整日志返回值")
public class PlanReservePlanSendLogVo extends PlanReservePlanSendLog implements Serializable {

    @DataBindDict(sourceFieldCombination = "send_status", sourceField = "#sendStatus")
    @ApiModelProperty(value = "下发状态名称")
    private String sendStatusName;
}
