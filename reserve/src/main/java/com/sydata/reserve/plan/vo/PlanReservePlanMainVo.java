package com.sydata.reserve.plan.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.reserve.plan.domain.PlanReservePlan;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 储备计划管理-储备计划主表返回值
 *
 * @author fuql
 * @date 2023-05-19 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划管理-储备计划主表返回值")
public class PlanReservePlanMainVo extends PlanReservePlan implements Serializable {

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#ylxz")
    @ApiModelProperty(value = "粮食性质名称")
    private String ylxzName;

    @DataBindCompany(sourceField = "#ccqy")
    @ApiModelProperty(value = "承储企业名称")
    private String ccqyName;

    @DataBindDict(sourceField = "#isChange", sourceFieldCombination = "is_leaf_node")
    @ApiModelProperty(value = "是否发生调整")
    private String isChangeName;

    @DataBindDict(sourceField = "#billStatus", sourceFieldCombination = "reserve_plan_status")
    @ApiModelProperty(value = "状态 字典：reserve_plan_status")
    private String billStatusName;

    @ApiModelProperty(value = "轮换类型：1静态轮换，2.自主轮换 字典：main_rotate_type")
    @DataBindDict(sourceField = "#mainRotateType", sourceFieldCombination = "main_rotate_type")
    private String mainRotateTypeName;

    @DataBindCompany(sourceField = "#jhzddw")
    @ApiModelProperty(value = "制定计划单位")
    private String jhzddwName;
}
