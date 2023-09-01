package com.sydata.reserve.plan.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 储备计划管理-储备计划详情对象 plan_reserve_plan_dtl
 *
 * @author fuql
 * @date 2023-05-19
 */
@ApiModel(description = "储备计划管理-储备计划详情")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("plan_reserve_plan_dtl")
public class PlanReservePlanDtl extends BaseFiledEntity implements Serializable {

    @Excel(name = "主键ID")
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Excel(name = "主表ID")
    @ApiModelProperty(value = "主表ID")
    private String mainId;

    @ApiModelProperty(value = "粮食等级代码 字典：food_grade")
    private String lsdjdm;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Excel(name = "粮食品种 字典：food_plan_variety")
    @ApiModelProperty(value = "粮食品种 ：字典：food_plan_variety")
    private String ylpz;

    @Excel(name = "承储数量（吨）")
    @ApiModelProperty(value = "承储数量（吨）")
    private BigDecimal amount;

    @Excel(name = "承储质量要求")
    @ApiModelProperty(value = "承储质量要求")
    private String remark;

    @Excel(name = "状态")
    @ApiModelProperty(value = "状态")
    private String billStatus;
}