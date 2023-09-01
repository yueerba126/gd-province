package com.sydata.organize.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindMenuSystem;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.organize.domain.Organize;
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
 * @describe 组织架构-组织VO
 * @date 2022-06-30 16:17
 */
@ApiModel(description = "组织架构-组织VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrganizeVo extends Organize implements Serializable {

    @DataBindDict(sourceFieldCombination = "organize_bus_type", sourceField = "#busType")
    @ApiModelProperty(value = "组织业务类型名称")
    private String busTypeName;

    @DataBindMenuSystem
    @ApiModelProperty(value = "菜单系统名称")
    private String menuSystemName;

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
}
