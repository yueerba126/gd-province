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
 * @description 储备规模信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备规模信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReserveScaleReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "年份")
    private String nf;

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "承储企业")
    private String ccqy;

    @ApiModelProperty(value = "粮食品种")
    private String ylpz;

    @ApiModelProperty(value = "粮食性质")
    private String ylxz;

    @ApiModelProperty(value = "储备规模计划数量")
    private BigDecimal ylcbgmjhsl;

    @ApiModelProperty(value = "储备规模计划文号")
    private String cbgmjhwh;

    @ApiModelProperty(value = "备注")
    private String remarks;
}