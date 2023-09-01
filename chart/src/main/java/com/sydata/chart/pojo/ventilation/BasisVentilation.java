package com.sydata.chart.pojo.ventilation;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 机械通风作业信息记录卡-基础配置信息
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "机械通风作业信息记录卡-基础配置信息")
public class BasisVentilation {

    @ApiModelProperty(value = "仓房代码")
    private String cfdm;

    @DataBindWarehouse
    @ApiModelProperty(value = "仓房名称")
    private String cfmc;

    @ApiModelProperty("仓房类型代码")
    private String cflxdm;

    @ApiModelProperty(value = "仓房类型名称")
    @DataBindDict(sourceField = "#cflxdm", sourceFieldCombination = "cflxdm")
    private String cflxdmName;

    @ApiModelProperty("仓内长")
    private BigDecimal cnc;

    @ApiModelProperty("仓内宽")
    private BigDecimal cnk;

    @ApiModelProperty("筒仓内径")
    private BigDecimal tcnj;

    @ApiModelProperty("设计装粮线高")
    private BigDecimal cnzlxg;

    @ApiModelProperty("品种数量")
    private Integer pzsl;

    @ApiModelProperty(value = "粮堆孔隙度")
    private BigDecimal ldkxd;

    @ApiModelProperty(value = "通风类型")
    private String tflx;

    @ApiModelProperty(value = "通风类型名称")
    @DataBindDict(sourceField = "#tflx", sourceFieldCombination = "tflx")
    private String tflxName;

    @ApiModelProperty(value = "风道型式")
    private String fdxs;

    @ApiModelProperty(value = "风网类型名称")
    @DataBindDict(sourceField = "#fdxs", sourceFieldCombination = "fdxs")
    private String fdxsName;

    @ApiModelProperty(value = "风网设置方式")
    private String fwszfs;

    @ApiModelProperty(value = "风网设置方式名称")
    @DataBindDict(sourceField = "#fwszfs", sourceFieldCombination = "fwszfs")
    private String fwszfsName;

    @ApiModelProperty(value = "主风道截面积")
    private BigDecimal zfdjmj;

    @ApiModelProperty(value = "风网开孔率")
    private BigDecimal fwkkl;

    @ApiModelProperty(value = "空气途径比")
    private BigDecimal kqtjb;

    @ApiModelProperty(value = "通风口设置个数")
    private Integer tfkszgs;
}
