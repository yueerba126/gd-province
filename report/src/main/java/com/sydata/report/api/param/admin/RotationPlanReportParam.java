package com.sydata.report.api.param.admin;

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
 * @description 轮换计划上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "轮换计划上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RotationPlanReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @ApiModelProperty(value = "开始执行日期")
    private LocalDate kszxrq;

    @ApiModelProperty(value = "截止执行日期")
    private LocalDate jzzxrq;

    @ApiModelProperty(value = "计划下达单位")
    private String jhxddw;

    @ApiModelProperty(value = "计划下达单位名称")
    private String jhxddwmc;

    @ApiModelProperty(value = "计划下达时间")
    private LocalDate jhxdsj;
}
