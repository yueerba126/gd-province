package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetAppointValue;
import com.sydata.collect.api.validated.annotation.TargetNotEmpty;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.trade.annotation.DataBindContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.sydata.common.util.RegexpUtil.SPECIAL_CHECK;

/**
 * @author czx
 * @description 入库结算信息API操作参数
 * @date 2022/10/20 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "入库结算信息API操作参数")
public class InStockSettlementApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "入库结算单号必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^[0-9a-zA-Z]{33}$", message = "入库结算单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "入库结算单号(由库点代码+结算日期（yyyyMMdd）+4 位顺序号组成)")
    private String rkjsdh;

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @NotBlank(message = "合同号必填", groups = BasicCheck.class)
    @Pattern(regexp = SPECIAL_CHECK, message = "合同号存在特殊字符", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[\\s\\S]{1,46}$", message = "合同编号格式错误", groups = BasicCheck.class)
    @StringSize(max = 64, message = "合同编号最大长度为64", groups = BasicCheck.class)
    @ApiModelProperty("合同编号")
    private String hth;

    @NotNull(message = "结算数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "结算数量 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "结算数量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "结算数量")
    private BigDecimal jssl;

    @NotNull(message = "结算单价必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "结算单价 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "结算单价不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "结算单价")
    private BigDecimal jsdj;

    @NotNull(message = "结算金额必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "结算金额 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "结算金额不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "结算金额")
    private BigDecimal jsje;

    @NotNull(message = "结算时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "结算时间")
    private LocalDateTime jssj;

    @NotBlank(message = "结算方式必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0|1]$", message = "结算方式格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "结算方式:0：现金 1：转账")
    private String jsfs;

    @NotBlank(message = "收款人必填", groups = BasicCheck.class)
    @StringSize(max = 256, message = "收款人最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "收款人")
    private String skr;

    @TargetAppointValue(message = "结算方式为转账时，银行行别代码必填", target = "#jsfs", targetValue = {"1"}, groups = BasicCheck.class)
    @Pattern(regexp = "^$|^102$|^103$|^104$|^105$|^203$|^314$|^402$|^403$|^999$", message = "银行行别代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "银行行别代码")
    private String yhhbdm;

    @Pattern(regexp = "^$|^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$", message = "收款人身份证号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "收款人身份证号")
    private String skrsfzh;

    @TargetAppointValue(message = "结算方式为转账时，开户行号必填", target = "#jsfs", targetValue = {"1"}, groups = BasicCheck.class)
    @StringSize(max = 32, message = "开户行号最大长度为32", groups = BasicCheck.class)
    @ApiModelProperty(value = "开户行号")
    private String khhh;

    @TargetAppointValue(message = "结算方式为转账时，开户行名称必填", target = "#jsfs", targetValue = {"1"}, groups = BasicCheck.class)
    @StringSize(max = 128, message = "开户行名称最大长度为128", groups = BasicCheck.class)
    @ApiModelProperty(value = "开户行名称")
    private String khhmc;

    @TargetAppointValue(message = "结算方式为转账时，银行账号必填", target = "#jsfs", targetValue = {"1"}, groups = BasicCheck.class)
    @StringSize(max = 32, message = "银行账号最大长度为32", groups = BasicCheck.class)
    @ApiModelProperty(value = "银行账号")
    private String yhzh;

    @StringSize(max = 10, message = "发票号码最大长度为10", groups = BasicCheck.class)
    @ApiModelProperty(value = "发票号码")
    private String fphm;

    @Pattern(regexp = "^$|^[0|1]$", message = "发票状态格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "发票状态")
    private String fpzt;

    @StringSize(max = 256, message = "付款单位最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "付款单位")
    private String fkdw;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwmc;

    @JsonIgnore
    @TargetNotEmpty(message = "合同号不存在", target = "#hth", groups = CorrectCheck.class)
    @DataBindContract(dataValue = "#ywlx")
    @ApiModelProperty(value = "合同业务类型", hidden = true)
    private String htywlx;

    @Override
    public String buildId() {
        return rkjsdh;
    }

    @Override
    public String buildCompanyId() {
        return rkjsdh.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return rkjsdh.substring(0, 21);
    }
}
