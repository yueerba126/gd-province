package com.sydata.reserve.plan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 储备计划管理-储备计划返回值
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划返回值")
public class PlanReservePlanVo extends PlanReservePlanMainVo implements Serializable {

    @ApiModelProperty(value = "储备计划详情表")
    private List<PlanReservePlanDtlVo> detailVos;

    @ApiModelProperty(value = "储备计划调整日志")
    private List<PlanReservePlanLogVo> logVos;

}
