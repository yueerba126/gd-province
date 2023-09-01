package com.sydata.organize.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.organize.domain.FoodOwner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 组织架构-粮权关系维护VO
 * @date 2022/11/14 11:24
 */
@ApiModel(description = "组织架构-粮权关系维护VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FoodOwnerVo extends FoodOwner implements Serializable {

    @DataBindRegion
    @ApiModelProperty(value = "行政区域名称")
    private String regionName;

    @DataBindRegion(sourceField = "#countryId")
    @ApiModelProperty(value = "国名称")
    private String countryName;

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

    @DataBindCompany(sourceField = "#companyId")
    @ApiModelProperty(value = "粮权单位名称")
    private String companyName;
}
