package com.sydata.report.api.param.basis;

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
 * @description 廒间信息上报参数
 * @date 2022/10/31 16:19
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "廒间信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GranaryReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty("廒间代码")
    private String ajdh;

    @ApiModelProperty("廒间名称")
    private String ajmc;

    @ApiModelProperty("仓房(或油罐)编码")
    private String cfbh;

    @ApiModelProperty("廒间长度")
    private BigDecimal ajcd;

    @ApiModelProperty("廒间宽度")
    private BigDecimal ajkd;

    @ApiModelProperty("廒间高度")
    private BigDecimal ajgd;

    @ApiModelProperty("廒间设计仓容")
    private BigDecimal ajsjcr;

    @ApiModelProperty("廒间启用日期")
    private LocalDate ajqyrq;

    @ApiModelProperty("廒间状态")
    private String ajzt;
}
