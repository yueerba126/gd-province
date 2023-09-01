package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.*;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author czx
 * @description 损溢单API操作参数
 * @date 2022/10/20 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "损溢单API操作参数")
public class LossApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "损溢单号必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "损溢单号(由货位代码+业务日期 （yyyyMMdd）+3 位顺序号组成)", target = {"#hwdm", "#ywrq"},
            groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9a-zA-Z]{41}$", message = "损溢单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "损溢单号(由货位代码+业务日期 （yyyyMMdd）+3 位顺序号组成)")
    private String sydh;

    @NotBlank(message = "货位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @NotNull(message = "业务日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "业务日期,格式：yyyy-MM-dd")
    private LocalDate ywrq;

    @NotNull(message = "入库净重必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "入库净重 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "入库净重不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "入库净重")
    private BigDecimal rkjz;

    @NotNull(message = "入库时间必填", groups = BasicCheck.class)
    @TargetIsBefore(target = "#cksj", message = "入库时间不得晚于出库时间", groups = BasicCheck.class)
    @ApiModelProperty(value = "入库时间")
    private LocalDateTime rksj;

    @Min(value = 0, message = "入库水分不能小于0", groups = BasicCheck.class)
    @Digits(integer = 6, fraction = 2, message = "入库水分 Decimal(6,2)", groups = BasicCheck.class)
    @ApiModelProperty(value = "入库水分")
    private BigDecimal rksf;

    @Min(value = 0, message = "入库杂质不能小于0", groups = BasicCheck.class)
    @Digits(integer = 6, fraction = 2, message = "入库杂质 Decimal(6,2)", groups = BasicCheck.class)
    @ApiModelProperty(value = "入库杂质")
    private BigDecimal rkzz;

    @NotNull(message = "出库时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "出库时间")
    private LocalDateTime cksj;

    @NotNull(message = "出库净重必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "出库净重 Decimal(20,3)", groups = BasicCheck.class)
    @Min(value = 0, message = "出库净重不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "出库净重")
    private BigDecimal ckjz;

    @Min(value = 0, message = "出库水分不能小于0", groups = BasicCheck.class)
    @Digits(integer = 6, fraction = 2, message = "出库水分 Decimal(6,2)", groups = BasicCheck.class)
    @ApiModelProperty(value = "出库水分")
    private BigDecimal cksf;

    @Min(value = 0, message = "出库杂质不能小于0", groups = BasicCheck.class)
    @Digits(integer = 6, fraction = 2, message = "出库杂质 Decimal(6,2)", groups = BasicCheck.class)
    @ApiModelProperty(value = "出库杂质")
    private BigDecimal ckzz;

    @NotNull(message = "净重损溢数量必填", groups = BasicCheck.class)
    @NotZeroCheck(message = "净重损溢数量不能等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "净重损溢数量 Decimal(20,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "净重损溢数量")
    private BigDecimal jzsysl;

    @Digits(integer = 20, fraction = 3, message = "其中水杂减量 Decimal(20,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "其中水杂减量")
    private BigDecimal qzszkl;

    @Digits(integer = 20, fraction = 3, message = "其中：自然损耗定额 Decimal(20,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "其中：自然损耗定额")
    private BigDecimal qzzrshde;

    @Digits(integer = 20, fraction = 3, message = "超耗数量 Decimal(20,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "超耗数量")
    private BigDecimal chsl;

    @NotBlank(message = "损益是否正常必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0|1]$", message = "损益是否正常格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "损益是否正常:0：正常 1：不正常")
    private String sysfzc;

    @StringSize(max = 400, message = "损溢原因最大长度为400", groups = BasicCheck.class)
    @ApiModelProperty(value = "损溢原因")
    private String syyy;

    @StringSize(max = 64, message = "仓储审核人最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "仓储审核人")
    private String ccshr;

    @StringSize(max = 64, message = "质检审核人最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "质检审核人")
    private String zjshr;

    @StringSize(max = 64, message = "统计审核人最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "统计审核人")
    private String tjshr;

    @StringSize(max = 64, message = "会计审核人最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "会计审核人")
    private String kjshr;

    @StringSize(max = 64, message = "领导审核人最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "领导审核人")
    private String ldshr;

    @StringSize(max = 400, message = "备注最大长度为400", groups = BasicCheck.class)
    @ApiModelProperty(value = "备注")
    private String bz;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwmc;

    @Override
    public String buildId() {
        return sydh;
    }

    @Override
    public String buildCompanyId() {
        return hwdm.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return hwdm.substring(0, 21);
    }
}
