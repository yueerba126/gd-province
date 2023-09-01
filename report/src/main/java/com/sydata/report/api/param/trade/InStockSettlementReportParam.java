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
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 入库结算信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "入库结算信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InStockSettlementReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "入库结算单号(由库点代码+结算日期（yyyyMMdd）+4 位顺序号组成)")
    private String rkjsdh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "合同号")
    private String hth;

    @ApiModelProperty(value = "结算数量")
    private BigDecimal jssl;

    @ApiModelProperty(value = "结算单价")
    private BigDecimal jsdj;

    @ApiModelProperty(value = "结算金额")
    private BigDecimal jsje;

    @ApiModelProperty(value = "结算时间")
    private LocalDateTime jssj;

    @ApiModelProperty(value = "结算方式:0：现金 1：转账")
    private String jsfs;

    @ApiModelProperty(value = "收款人")
    private String skr;

    @ApiModelProperty(value = "银行行别代码")
    private String yhhbdm;

    @ApiModelProperty(value = "收款人身份证号")
    private String skrsfzh;

    @ApiModelProperty(value = "开户行号")
    private String khhh;

    @ApiModelProperty(value = "开户行名称")
    private String khhmc;

    @ApiModelProperty(value = "银行账号")
    private String yhzh;

    @ApiModelProperty(value = "发票号码")
    private String fphm;

    @ApiModelProperty(value = "发票状态")
    private String fpzt;

    @ApiModelProperty(value = "付款单位")
    private String fkdw;
}
