package com.sydata.report.api.param.admin;

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
 * @description 储备计划信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReservePlanReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "计划单号")
    private String jhdh;

    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @ApiModelProperty(value = "制定计划单位")
    private String jhzddw;

    @ApiModelProperty(value = "计划下达时间")
    private LocalDate jhxdsj;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "数量（吨）")
    private BigDecimal sl;
}