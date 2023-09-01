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
 * @description: 粮食储备-粮油购销管理-实际和计划库存量统计
 * @author: lzq
 * @create: 2023-04-24 16:18
 */
@ApiModel(description = "粮食储备-粮油购销管理-实际和计划库存量统计")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RealAndPlanningQuantityVo implements Serializable {

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

    @ApiModelProperty(value = "省储-实际库存量")
    private BigDecimal provinceReserveQuantity;

    @ApiModelProperty(value = "省储-储备计划量")
    private BigDecimal provincePlanningQuantity;

    @ApiModelProperty(value = "市储-实际库存量")
    private BigDecimal cityReserveQuantity;

    @ApiModelProperty(value = "市储-储备计划量")
    private BigDecimal cityPlanningQuantity;

    @ApiModelProperty(value = "县储-实际库存量")
    private BigDecimal regionReserveQuantity;

    @ApiModelProperty(value = "县储-储备计划量")
    private BigDecimal regionPlanningQuantity;

    @ApiModelProperty(value = "数量 - 前端不需要")
    private BigDecimal quantity;
}
