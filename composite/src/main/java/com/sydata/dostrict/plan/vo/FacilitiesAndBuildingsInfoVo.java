package com.sydata.dostrict.plan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: gd-province-platform
 * @description: 设施和建筑数量统计
 * @author: lzq
 * @create: 2023-04-24 16:18
 */
@ApiModel(description = "规划建设-设施和建筑信息统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FacilitiesAndBuildingsInfoVo implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "行政区划代码名称")
    private String xzqhdmName;

    @ApiModelProperty(value = "总仓容")
    private BigDecimal zcr;

    @ApiModelProperty(value = "仓房数量")
    private Integer cfsl;

    @ApiModelProperty(value = "廒间数量")
    private Integer ajsl;

    @ApiModelProperty(value = "货位数量")
    private Integer hwsl;

    @ApiModelProperty(value = "设备数量")
    private Integer sbsl;

}
