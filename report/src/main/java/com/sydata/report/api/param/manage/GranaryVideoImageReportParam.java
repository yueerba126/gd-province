package com.sydata.report.api.param.manage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
 * @description 仓内视频图像上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "仓内视频图像上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GranaryVideoImageReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("视频监控设备id")
    private String spjksbid;

    @ApiModelProperty("仓房代码")
    private String cfdm;

    @ApiModelProperty("货位代码")
    private String hwdm;

    @ApiModelProperty("抓拍时间")
    private LocalDateTime zpsj;

    @ApiModelProperty("仓内图像文件流")
    private String cntxwjl;

    @ApiModelProperty("图像文件后缀名")
    private String txwjhzm;

    @JsonProperty("Yzwbh")
    @ApiModelProperty("预置位编号")
    private String yzwbh;

    @ApiModelProperty("操作标志")
    private String czbz;
}