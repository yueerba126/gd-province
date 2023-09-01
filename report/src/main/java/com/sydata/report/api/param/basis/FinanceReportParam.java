package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 财务报表信息上报参数
 * @date 2022/10/31 16:19
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "财务报表信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FinanceReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "报表时间")
    private String bbsj;

    @ApiModelProperty(value = "报表名 01资产负债表 02现金流量表 03利润表")
    private String bbm;

    @JsonProperty("Zbxh")
    @ApiModelProperty(value = "指标序号")
    private Integer zbxh;

    @ApiModelProperty(value = "指标名称")
    private String zbmc;

    @ApiModelProperty(value = "指标值1")
    private String zbz1;

    @ApiModelProperty(value = "指标值2")
    private String zbz2;
}
