package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author lzq
 * @description 设备信息上报参数
 * @date 2022/10/31 16:19
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "设备信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeviceReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("库区名称")
    private String kqmc;

    @ApiModelProperty("设备编号")
    private String sbbh;

    @ApiModelProperty("设备仪器名称")
    private String sbyqmc;

    @ApiModelProperty("设备仪器代码")
    private String sbyqdm;

    @ApiModelProperty("设备规格型号")
    private String sbggxh;

    @ApiModelProperty("生产厂家")
    private String sccj;

    @ApiModelProperty("生产日期")
    private LocalDate scrq;

    @ApiModelProperty("设备描述")
    private String sbms;

    @ApiModelProperty("设备状态")
    private String sbzt;

    @ApiModelProperty("检定时间")
    private LocalDate jdsj;

    @ApiModelProperty("检定单位")
    private String jddw;
}
