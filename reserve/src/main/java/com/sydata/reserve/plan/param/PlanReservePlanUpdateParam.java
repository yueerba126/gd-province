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
 * 储备计划管理-储备计划修改参数
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划修改参数")
public class PlanReservePlanUpdateParam implements Serializable {

    @ApiModelProperty(value = "储备计划主表")
    @Valid
    private PlanReservePlan mainBill;

    @ApiModelProperty(value = "需要删除的储备计划明细ID列表")
    private List<Long> deleteDetailIds;

    @ApiModelProperty(value = "需要新增的储备计划明细Param列表")
    @Valid
    private List<PlanReservePlanDtl> addDetailParams;

    @ApiModelProperty(value = "需要修改的储备计划明细Param列表")
    @Valid
    private List<PlanReservePlanDtl> updateDetailParams;

    
}
