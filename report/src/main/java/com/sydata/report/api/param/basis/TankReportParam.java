package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author lzq
 * @description 油罐信息上报参数
 * @date 2022/10/31 16:19
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "油罐信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TankReportParam extends BaseReportParam implements Serializable {

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
}
