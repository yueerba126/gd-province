package com.sydata.reserve.scale.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.reserve.scale.domain.PlanReserveScale;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 储备计划管理-储备规模Vo
 *
 * @author fuql
 * @date 2022-08-19
 */
@ApiModel(description = "储备计划管理-储备规模导出dto")
@Data
@ToString
@Accessors(chain = true)
public class PlanReserveScaleDto implements Serializable {

    @Excel(name = "行政区划代码", width = 20, needMerge = true)
    @ApiModelProperty(value = "行政区划代码")
    private String regionCode;

    @Excel(name = "行政区域名称", width = 20, needMerge = true)
    @DataBindRegion(sourceField = "#regionCode")
    @ApiModelProperty(value = "行政区域名称")
    private String regionName;

    @Excel(name = "粮食储备规模数量（万吨）", width = 30, needMerge = true)
    @ApiModelProperty(value = "粮食储备规模数量（万吨）")
    private BigDecimal grainAmount;

    @Excel(name = "油储备规模数量（万吨）", width = 30, needMerge = true)
    @ApiModelProperty(value = "油储备规模数量（万吨）")
    private BigDecimal oilAmount;

    @Excel(name = "上级行政区划代码", width = 20, needMerge = true)
    @ApiModelProperty(value = "上级行政区划代码")
    private String regionParentCode;
}
