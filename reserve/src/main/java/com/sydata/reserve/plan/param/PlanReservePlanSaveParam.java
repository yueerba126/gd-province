package com.sydata.reserve.plan.param;

import com.sydata.reserve.plan.domain.PlanReservePlan;
import com.sydata.reserve.plan.domain.PlanReservePlanDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * 储备计划管理-储备计划新增参数
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划新增参数")
public class PlanReservePlanSaveParam implements Serializable {

    @ApiModelProperty(value = "储备计划主表")
    @Valid
    private PlanReservePlan mainBill;

    @ApiModelProperty(value = "储备计划详情表")
    @Valid
    private List<PlanReservePlanDtl> details;

}
