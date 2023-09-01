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


/**
 * @author lzq
 * @description 轮换计划明细上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "轮换计划明细上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RotationPlanDtlReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "计划明细单号")
    private String jhmxdh;

    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "收获年度")
    private String shnd;

    @ApiModelProperty(value = "轮换货位代码")
    private String lhhwdm;

    @ApiModelProperty(value = "轮换数量")
    private BigDecimal lhsl;

    @ApiModelProperty(value = "轮换类型(1.轮出 2.轮入)")
    private String lhlx;
}
