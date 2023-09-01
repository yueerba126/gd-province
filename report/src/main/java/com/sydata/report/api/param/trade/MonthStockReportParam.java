package com.sydata.report.api.param.trade;

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
 * @description 账面库存信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "账面库存信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MonthStockReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "年度")
    private String nd;

    @ApiModelProperty(value = "月份")
    private String yf;

    @ApiModelProperty(value = "期初数量")
    private BigDecimal qcsl;

    @ApiModelProperty(value = "本期收入数量")
    private BigDecimal bqsrsl;

    @ApiModelProperty(value = "本期支出数量")
    private BigDecimal bqzcsl;

    @ApiModelProperty(value = "期末数量")
    private BigDecimal qmye;

    @ApiModelProperty(value = "月结标志")
    private Integer yjbz;

    @ApiModelProperty(value = "业务日期")
    private LocalDate ywrq;
}