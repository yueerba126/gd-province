package com.sydata.dostrict.reserve.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: gd-province-platform
 * @description: 粮食储备-粮油购销管理-轮入轮出统计
 * @author: lzq
 * @create: 2023-04-24 16:18
 */
@ApiModel(description = "粮食储备-粮油购销管理-轮入轮出统计")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RealAndPlanningWheelOutAndInVo implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @DataBindCompany
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(value = "行政区划代码名称")
    private String xzqhdmName;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;

    @ApiModelProperty(value = "年度")
    private String nd;

    @ApiModelProperty(value = "省储-实际轮入")
    private BigDecimal provinceInQuantity;

    @ApiModelProperty(value = "省储-计划轮入")
    private BigDecimal provincePlanningInQuantity;

    @ApiModelProperty(value = "市储-实际轮入")
    private BigDecimal cityInQuantity;

    @ApiModelProperty(value = "市储-计划轮入")
    private BigDecimal cityPlanningInQuantity;

    @ApiModelProperty(value = "县储-实际轮入")
    private BigDecimal regionInQuantity;

    @ApiModelProperty(value = "县储-计划轮入")
    private BigDecimal regionPlanningInQuantity;

    @ApiModelProperty(value = "省储-实际轮出")
    private BigDecimal provinceOutQuantity;

    @ApiModelProperty(value = "省储-计划轮出")
    private BigDecimal provincePlanningOutQuantity;

    @ApiModelProperty(value = "市储-实际轮出")
    private BigDecimal cityOutQuantity;

    @ApiModelProperty(value = "市储-计划轮出")
    private BigDecimal cityPlanningOutQuantity;

    @ApiModelProperty(value = "县储-实际轮出")
    private BigDecimal regionOutQuantity;

    @ApiModelProperty(value = "县储-计划轮出")
    private BigDecimal regionPlanningOutQuantity;

    @ApiModelProperty(value = "数量 - 前端不需要")
    private BigDecimal quantity;
}
