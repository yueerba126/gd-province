package com.sydata.collect.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
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
 * @description 数据收集质量统计报表VO
 * @date 2022/10/11 17:49
 */
@ApiModel(description = "数据收集质量统计报表VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CollectQualityVo implements Serializable {

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @ApiModelProperty(value = "请求总数")
    private Integer total;

    @ApiModelProperty(value = "请求成功数")
    private Integer success;

    @ApiModelProperty(value = "请求失败数")
    private Integer fail;

    @ApiModelProperty(value = "请求成功率")
    private BigDecimal successRate;

    @ApiModelProperty(value = "请求失败率")
    private BigDecimal failRate;

    @DataBindDict(sourceField = "#apiCode", sourceFieldCombination = "apiCode")
    @ApiModelProperty(value = "接口名称")
    private String apiName;

    @DataBindDict(sourceField = "#apiCode", sourceFieldCombination = "apiCode", dataValue = "#dictFarType")
    @ApiModelProperty(value = "接口类型")
    private String apiType;

    @DataBindRegion(sourceField = "#provinceId")
    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @DataBindRegion(sourceField = "#cityId")
    @ApiModelProperty(value = "市名称")
    private String cityName;

    @DataBindRegion(sourceField = "#areaId")
    @ApiModelProperty(value = "区名称")
    private String areaName;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库区名称")
    private String stockHouseName;
}
