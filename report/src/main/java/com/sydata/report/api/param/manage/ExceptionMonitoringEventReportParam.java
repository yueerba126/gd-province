package com.sydata.report.api.param.manage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author lzq
 * @description 视频监控异常事件告警基本信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "视频监控异常事件告警基本信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionMonitoringEventReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "告警时间")
    private LocalDateTime gjsj;

    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "库区地址")
    private String kqdz;

    @ApiModelProperty(value = "视频监控设备id")
    private String spjksbid;

    @ApiModelProperty(value = "安装位置类型")
    private String azwzlx;

    @ApiModelProperty(value = "监视区域说明")
    private String jsqysm;

    @ApiModelProperty(value = "异常告警说明")
    private String ycgjsm;

    @ApiModelProperty("视频文件流")
    private String spwjl;

    @ApiModelProperty(value = "视频文件后缀名")
    private String spwjhzm;
}