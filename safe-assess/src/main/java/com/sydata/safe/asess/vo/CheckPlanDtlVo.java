package com.sydata.safe.asess.vo;

import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.safe.asess.domain.CheckPlanDtl;
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
 * @description 粮食安全考核-抽查计划明细VO
 * @date 2023/2/27 17:25
 */
@ApiModel(description = "粮食安全考核-抽查计划明细VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CheckPlanDtlVo extends CheckPlanDtl implements Serializable {

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
