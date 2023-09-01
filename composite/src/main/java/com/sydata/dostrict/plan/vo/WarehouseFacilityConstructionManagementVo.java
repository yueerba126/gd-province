package com.sydata.dostrict.plan.vo;

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
 * @description: 仓储设施建设管理
 * @author: lzq
 * @create: 2023-04-24 16:33
 */
@ApiModel(description = "规划建设-仓储设施建设管理信息统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseFacilityConstructionManagementVo implements Serializable {

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "行政区划代码名称")
    private String xzqhdmName;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "年份")
    private String year;

    @ApiModelProperty(value = "项目数量")
    private Integer itemQuantity;

    @ApiModelProperty(value = "计划投资额")
    private BigDecimal plannedInvestmentAmount;

}
