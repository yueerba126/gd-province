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
 * @description 违规预警信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "违规预警信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ViolationWarningReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "预警信息代码")
    private String yjxxdm;

    @ApiModelProperty(value = "预警发布时间")
    private LocalDateTime fbsj;

    @ApiModelProperty(value = "违规主体类型（01:单位，02:个人）")
    private String wgztlx;

    @ApiModelProperty(value = "违规单位代码（统一社会信用代码）")
    private String qydm;

    @ApiModelProperty(value = "违规行为人")
    private String wgxwr;

    @ApiModelProperty(value = "涉及库点")
    private String sjkd;

    @ApiModelProperty(value = "违规类型")
    private String wglx;

    @ApiModelProperty(value = "违规详情")
    private String wgqk;

    @ApiModelProperty(value = "当前处置状态（01:未处置，02:已处置）")
    private String czzt;

    @ApiModelProperty(value = "处置内容")
    private String hxczqk;

    @ApiModelProperty(value = "处置人")
    private String hxczr;

    @ApiModelProperty(value = "处置时间")
    private LocalDateTime hxczsj;
}