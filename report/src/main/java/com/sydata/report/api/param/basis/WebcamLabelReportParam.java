package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 库区图视频监控设备点位标注上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "库区图视频监控设备点位标注上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WebcamLabelReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "视频监控设备名称")
    private String spjksbmc;

    @ApiModelProperty(value = "视频监控设备id")
    private String spjksbid;

    @ApiModelProperty(value = "视频监控设备相对位置")
    private String spjksbxdwz;

    @ApiModelProperty(value = "视频监控设备位置样式")
    private String spjksbwzys;

    @ApiModelProperty(value = "视频监控类型")
    private String spjklx;

    @ApiModelProperty(value = "备注")
    private String bz;
}