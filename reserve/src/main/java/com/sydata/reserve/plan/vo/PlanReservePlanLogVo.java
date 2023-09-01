package com.sydata.reserve.plan.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.reserve.plan.domain.PlanReservePlanLog;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 储备计划管理-储备计划日志返回值
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划日志返回值")
public class PlanReservePlanLogVo extends PlanReservePlanLog implements Serializable {

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @DataBindDict(sourceFieldCombination = "food_plan_variety", sourceField = "#ylpz")
    @ApiModelProperty(value = "粮食品种名称")
    private String ylpzName;

}
