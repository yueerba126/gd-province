package com.sydata.dashboard.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sydata.framework.core.jackson.format.BigDecimalFormat;
import com.sydata.framework.core.jackson.serialize.BigDecimalSerialize;
import com.sydata.organize.annotation.DataBindRegion;
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
 * @description 储备油报表地市版VO
 * @date 2023/6/20 9:23
 */
@ApiModel(description = "储备油报表地市版VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReserveOilCityVo implements Serializable {

    @ApiModelProperty(value = "地市ID")
    private String cityId;

    @DataBindRegion(sourceField = "#cityId")
    @ApiModelProperty(value = "地市名称")
    @Excel(name = "计价数量(吨)", width = 20, needMerge = true)
    private String cityName;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "计价数量(吨)")
    @Excel(name = "计价数量(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal jjsl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "实际数量(吨)")
    @Excel(name = "实际数量(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal sjsl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "折合原油总计(吨)")
    @Excel(name = "折合原油总计(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal zhyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "省储折合原油总计(吨)")
    @Excel(name = "省储折合原油总计(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal provinceZhyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "市储折合原油总计(吨)")
    @Excel(name = "市储折合原油总计(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal cityZhyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "市储折合原油总计(吨)")
    @Excel(name = "县储折合原油总计(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal areaZhyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "原油总量(吨)")
    @Excel(name = "原油总量(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal yyzl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "大豆原油(吨)")
    @Excel(name = "大豆原油(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal grainddyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "花生原油(吨)")
    @Excel(name = "花生原油(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal grainhsyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "菜籽原油(吨)")
    @Excel(name = "菜籽原油(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal grainczyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "其他原油(吨)")
    @Excel(name = "其他原油(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal grainqtyy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "成品油总量(吨)")
    @Excel(name = "成品油总量(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal cpyzl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "大豆油(吨)")
    @Excel(name = "大豆油(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal grainddy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "花生油(吨)")
    @Excel(name = "花生油(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal grainhsy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "菜籽油(吨)")
    @Excel(name = "菜籽油(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal grainczy;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "其他成品油(吨)")
    @Excel(name = "其他成品油(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal qtcpyzl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "其他类总量(吨)")
    @Excel(name = "其他类总量(吨)", width = 20, needMerge = true, numFormat = "#.##", isStatistics = true)
    private BigDecimal qtlzl;
}
