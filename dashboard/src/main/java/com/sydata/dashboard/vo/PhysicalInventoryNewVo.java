package com.sydata.dashboard.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
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
 * 储备粮实物库存报表
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "储备粮实物库存报表VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PhysicalInventoryNewVo implements Serializable {

    @ApiModelProperty(value = "库存归属报表id")
    private String id;

    @ApiModelProperty(value = "粮权行政区划代码")
    private String lqxzqhdm;

    @DataBindRegion(sourceField = "#lqxzqhdm")
    @Excel(name = "粮权所属区划", width = 20, needMerge = true)
    @ApiModelProperty(value = "粮权行政区划名称")
    private String lqxzqhdmName;

    @ApiModelProperty(value = "企业Id")
    private String enterpriseId;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    @Excel(name = "单位名称", width = 40, needMerge = true)
    private String enterpriseName;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    @Excel(name = "库区名称", width = 40, needMerge = true)
    private String stockHouseName;

    @ApiModelProperty(value = "库点属地(地市)")
    @DataBindStockHouse(sourceField = "#stockHouseId", dataValue = "#cityId")
    private String stockCity;

    @ApiModelProperty(value = "库点属地(地市)")
    @Excel(name = "库点属地(地市)", width = 20, needMerge = true)
    @DataBindRegion(sourceField = "#stockCity")
    private String stockCityName;

    @ApiModelProperty(value = "库点属地(区县)")
    @DataBindStockHouse(sourceField = "#stockHouseId", dataValue = "#areaId")
    private String stockArea;

    @ApiModelProperty(value = "库点属地(区县)")
    @Excel(name = "库点属地(区县)", width = 20, needMerge = true)
    @DataBindRegion(sourceField = "#stockArea")
    private String stockAreaName;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @Excel(name = "粮食性质", width = 40, needMerge = true)
    @ApiModelProperty(value = "粮食性质")
    private String lsxzdmName;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "省")
    @DataBindRegion(sourceField = "#province")
    private String provinceName;

    @ApiModelProperty(value = "区编码")
    private String area;

    @ApiModelProperty(value = "区名称")
    @DataBindRegion(sourceField = "#area")
    private String areaName;

    @ApiModelProperty(value = "地市编码")
    private String city;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "地市名称")
    @DataBindRegion(sourceField = "#city")
    private String cityName;

    @ApiModelProperty(value = "粮食品种名称")
    private String lspzmc;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "计价数量(吨)")
    @Excel(name = "计价数量(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal zj;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "实际数量(吨)")
    @Excel(name = "实际数量(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal sjslzj;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "折合原粮总计(吨)")
    @Excel(name = "折合原粮总计(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal zhyl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "原粮总量(吨)")
    @Excel(name = "原粮总量(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal ylzj;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "小麦(吨)")
    @Excel(name = "小麦(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainxml;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "稻谷类")
    @Excel(name = "稻谷类(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal graindgl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "玉米类(吨)")
    @Excel(name = "玉米类(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainyml;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "大豆类(吨)")
    @Excel(name = "大豆类(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainddl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "其它原粮(吨)")
    @Excel(name = "其它原粮(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainqtyl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "成品粮总量(吨)")
    @Excel(name = "成品粮总量(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal cplzl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "面粉类(吨)")
    @Excel(name = "面粉类(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainmfl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "玉米面类(吨)")
    @Excel(name = "玉米面类(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainymml;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "大米类(吨)")
    @Excel(name = "大米类(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal graindml;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "其他成品粮(吨)")
    @Excel(name = "其他成品粮(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal qtcpl;

    @BigDecimalFormat
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "其他类总量(吨)")
    @Excel(name = "其他类总量(吨)", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal qtlzl;
}
