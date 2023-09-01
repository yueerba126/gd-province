package com.sydata.dashboard.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sydata.framework.core.jackson.format.BigDecimalFormat;
import com.sydata.framework.core.jackson.serialize.BigDecimalSerialize;
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
 * @author lzq
 * @description 储备粮油折合报表ExcelVO
 * @date 2023/6/25 14:43
 */
@ApiModel(description = "储备粮油折合报表ExcelVO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReserveGrainOilEquivalentExcelVo implements Serializable {

    @ApiModelProperty(value = "组织机构名称")
    @Excel(name = "组织机构", width = 20, needMerge = true)
    private String name;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "折合原粮合计(吨)")
    @Excel(name = "折合原粮合计(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal zhyl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "导入折合原粮合计(吨)")
    @Excel(name = "导入折合原粮合计(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal importZhyl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "折合原粮相差百分比")
    @Excel(name = "折合原粮相差百分比", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainDiffer;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "油及料折油合计(吨)")
    @Excel(name = "油及料折油合计(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal zhyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "导入油及料折油合计(吨)")
    @Excel(name = "导入油及料折油合计(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal importZhyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "油及料折油相差百分比")
    @Excel(name = "油及料折油相差百分比", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal oilDiffer;
}
