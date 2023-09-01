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
 * @description 出库结算信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "出库结算信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OutStockSettlementReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "出库结算单号,由库点代码+结算日期（yyyyMMdd）+4 位顺序号组成")
    private String ckjsdh;

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

    @ApiModelProperty(value = "付款人")
    private String fkr;

    @ApiModelProperty(value = "银行行别代码，102 ：中国工商银行，103 ：中国农业银行，104：中国银行，105：中国建设银行，314 ：农村商业银行，402 ：农村信用合作社，403：中国邮政储蓄，999：其它银行")
    private String yhhbdm;

    @ApiModelProperty(value = "开户行号")
    private String khhh;

    @ApiModelProperty(value = "开户行名称")
    private String khhmc;

    @ApiModelProperty(value = "银行账号")
    private String yhzh;

    @ApiModelProperty(value = "发票号码")
    private String fphm;

    @ApiModelProperty(value = "发票状态:1：正常 0：作废")
    private String fpzt;
}
