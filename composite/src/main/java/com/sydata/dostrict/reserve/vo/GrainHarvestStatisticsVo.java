package com.sydata.dostrict.reserve.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
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
 * @description: 粮食储备-粮油信息统计-按收获年度统计
 * @author: lzq
 * @create: 2023-04-24 16:18
 */
@ApiModel(description = "粮食储备-粮油信息统计-按收获年度统计")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GrainHarvestStatisticsVo implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @DataBindCompany
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(value = "行政区划代码名称")
    private String xzqhdmName;

    @ApiModelProperty(value = "收货年度")
    private String shnd;

    @ApiModelProperty(value = "库存量")
    private BigDecimal grainQuantity;
}
