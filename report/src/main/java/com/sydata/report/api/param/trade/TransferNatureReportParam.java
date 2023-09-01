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
 * @description 性质转变上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "性质转变上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransferNatureReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "性质转变单编号")
    private String lsxzzbdh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "粮食数量")
    private BigDecimal lssl;

    @ApiModelProperty(value = "划转数量")
    private BigDecimal hzsl;

    @ApiModelProperty(value = "批准文号")
    private String bzwh;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "划转前粮食性 质代码")
    private String hzqlsxzdm;

    @ApiModelProperty(value = "划转后粮食性 质代码")
    private String hzhlsxzdm;

    @ApiModelProperty(value = "划转日期")
    private LocalDate hzrq;

    @ApiModelProperty(value = "仓储审核人")
    private String ccshr;

    @ApiModelProperty(value = "质检审核人")
    private String zjshr;

    @ApiModelProperty(value = "统计审核人")
    private String tjshr;

    @ApiModelProperty(value = "会计审核人")
    private String kjshr;

    @ApiModelProperty(value = "领导审核人")
    private String ldshr;

    @ApiModelProperty(value = "备注")
    private String bz;
}