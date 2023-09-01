package com.sydata.chart.pojo.ventilation;

import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 机械通风作业信息记录卡-降温通风作业
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "机械通风作业信息记录卡-降温通风作业")
public class CoolingVentilation {

    @ApiModelProperty(value = "通风机型号")
    private String tfjxh;

    @ApiModelProperty(value = "通风机台数")
    private Integer tfjts;

    @ApiModelProperty(value = "单台风机额定全压")
    private BigDecimal dtfjedqy;

    @ApiModelProperty(value = "单台风机额定风量")
    private BigDecimal dtfjedfl;

    @ApiModelProperty(value = "单台风机额定功率")
    private BigDecimal dtfjedgl;

    @ApiModelProperty(value = "送风方式")
    private String sffs;

    @ApiModelProperty(value = "送风方式名称")
    @DataBindDict(sourceField = "#sffs", sourceFieldCombination = "sffs")
    private String sffsName;

    @ApiModelProperty(value = "单台风机实测风量")
    private BigDecimal dtfjscfl;

    @ApiModelProperty(value = "单台风机轴功率")
    private BigDecimal dtfjzgl;

    @ApiModelProperty(value = "总风量")
    private BigDecimal zfl;

    @ApiModelProperty(value = "单位通风量")
    private BigDecimal dwtfl;

    @ApiModelProperty(value = "实测系统阻力")
    private BigDecimal scxtzl;

    @ApiModelProperty(value = "总耗电量")
    private BigDecimal zhdl;

    @ApiModelProperty(value = "作业时气温")
    private BigDecimal zysqw;

    @ApiModelProperty(value = "通风时长")
    private BigDecimal tfsc;

    @ApiModelProperty(value = "作业前平均粮温")
    private BigDecimal zyqpjlw;

    @ApiModelProperty(value = "结束后平均粮温")
    private BigDecimal jshpjlw;

    @ApiModelProperty(value = "降温幅度")
    private BigDecimal jwfd;

    @ApiModelProperty(value = "吨量降温能耗")
    private BigDecimal dljwnh;

    @ApiModelProperty(value = "失水率")
    private BigDecimal ssll;

    @ApiModelProperty(value = "保水效果评价结果")
    private String bsxgpjjg;

    @ApiModelProperty(value = "通风降温均匀性评价整仓")
    private String tfjwjyxpjzc;

    @ApiModelProperty(value = "通风降温均匀性评价上层")
    private String tfjwjyxpjsc;

    @ApiModelProperty(value = "通风降温均匀性评价中间层")
    private String tfjwjyxpjzjc;

    @ApiModelProperty(value = "通风降温均匀性评价_下层")
    private String tfjwjyxpjxc;

}
