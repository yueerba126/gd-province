package com.sydata.reserve.layout.param;

import com.sydata.reserve.layout.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 储备布局地理信息-油罐信息备案
 * </p>
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "储备布局地理信息-油罐信息备案新增参数")
public class TankRecordSaveParam extends BaseEntity implements Serializable {


    @ApiModelProperty("油罐代码")
    private String ygdm;

    @ApiModelProperty("油罐名称")
    private String ygmc;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("罐容")
    private BigDecimal gr;

    @ApiModelProperty("建造时间")
    private LocalDate jzsj;

    @ApiModelProperty("油罐及附属设施是否完好")
    private String ygjfssssfwh;

    @ApiModelProperty("有无加热装置")
    private String ywjrzz;

    @ApiModelProperty("油罐类型")
    private String yglx;

    @ApiModelProperty("罐内直径")
    private BigDecimal gnzj;

    @ApiModelProperty("罐内高度")
    private BigDecimal gngd;

    @ApiModelProperty("检定方式")
    private String jdfs;

    @ApiModelProperty("焊接方式")
    private String hjfs;

    @ApiModelProperty("油罐状态")
    private String ygzt;

    @ApiModelProperty("设计单位")
    private String sjdw;

    @ApiModelProperty("建设单位")
    private String jsdw;

    @ApiModelProperty("监理单位")
    private String jldw;

    @ApiModelProperty("状态 0：保存，1：提交，2：审核")
    private String status;

    @ApiModelProperty("单位代码")
    private String dwdm;


}
