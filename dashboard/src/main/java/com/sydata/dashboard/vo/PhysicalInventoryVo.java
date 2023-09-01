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
public class PhysicalInventoryVo implements Serializable {

    @ApiModelProperty(value = "库存归属报表id")
    private String id;

    @ApiModelProperty(value = "粮食品种")
    private String lspz;

    @ApiModelProperty(value = "粮权行政区划代码")
    private String lqxzqhdm;

    @DataBindRegion(sourceField = "#lqxzqhdm")
    @Excel(name = "粮权所属区划", width = 20, needMerge = true)
    @ApiModelProperty(value = "粮权行政区划名称")
    private String lqxzqhdmName;

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

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    @Excel(name = "库点名称", width = 40, needMerge = true)
    private String stockHouseName;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "组织名称")
    @Excel(name = "企业名称", width = 40, needMerge = true)
    private String enterpriseName;

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

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "企业Id")
    private String enterpriseId;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "总计")
    @Excel(name = "总计", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal zj;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "小麦类")
    @Excel(name = "小麦（吨）", width = 20, needMerge = true)
    private BigDecimal grainxml;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "玉米类")
    @Excel(name = "玉米类（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainyml;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "稻谷类")
    @Excel(name = "稻谷类（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal graindgl;


    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "经济作物类")
    @Excel(name = "经济作物类（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainjjzw;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "成品食用植物油类")
    @Excel(name = "成品食用植物油类（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal graincpsyy;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "原油（毛油）类")
    @Excel(name = "原油（毛油）类（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainyy;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "小麦粉类")
    @Excel(name = "小麦粉类（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainxmfl;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "玉米加工类")
    @Excel(name = "玉米加工类（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainymjgl;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "大米类")
    @Excel(name = "大米类（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal graindml;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "粮食加工副产品类")
    @Excel(name = "粮食加工副产品类（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainlsjg;

    @BigDecimalFormat(scale = 6)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "老国标")
    @Excel(name = "老国标（吨）", width = 20, needMerge = true, numFormat = "#.##")
    private BigDecimal grainlslgb;
}
