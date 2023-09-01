package com.sydata.dostrict.plan.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.dostrict.plan.domain.ApparitorProject;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program: gd-province-platform
 * @description: 规划建设-项目管理
 * @author: lzq
 * @create: 2023-04-24 16:18
 */
@ApiModel(description = "规划建设-项目管理VO")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorProjectVo extends ApparitorProject implements Serializable {

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

    @DataBindCompany
    @ApiModelProperty(value = "企业名称")
    private String dwmc;

    @DataBindStockHouse
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @DataBindRegion
    @ApiModelProperty(value = "行政区域名称")
    private String regionName;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty("行政区划代码名称")
    private String xzqhdmName;

    @DataBindDict(sourceField = "#jszt", sourceFieldCombination = "jszt")
    @ApiModelProperty(value = "建设状态；1:已立项未开工、2:建设中，3:已验收，4:已取消")
    private String jsztName;

    @ApiModelProperty(value = "项目类型名称")
    @DataBindDict(sourceField = "#xmlx", sourceFieldCombination = "xmlx")
    private String xmlxName;

    @ApiModelProperty(value = "法人证照类型名称")
    @DataBindDict(sourceField = "#fddbrzzlx", sourceFieldCombination = "fddbrzzlx")
    private String fddbrzzlxName;

}
