package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author czx
 * @description 性质转变单API操作参数
 * @date 2022/10/20 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "性质转变单API操作参数")
public class TransferNatureApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "损溢单号必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "性质转变单编号(由货位代码+划转日期(yyyyMMdd)+3位顺序号组成)", target = {"#hwdm", "#hzrq"}, groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9a-zA-Z]{41}$", message = "性质转变单编号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "性质转变单编号")
    private String lsxzzbdh;

    @NotBlank(message = "货位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @NotNull(message = "粮食数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "粮食数量 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "粮食数量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食数量")
    private BigDecimal lssl;

    @NotNull(message = "划转数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "划转数量 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "划转数量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "划转数量")
    private BigDecimal hzsl;

    @StringSize(max = 128, message = "批准文号128", groups = BasicCheck.class)
    @ApiModelProperty(value = "批准文号")
    private String bzwh;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @NotBlank(message = "划转前粮食性质代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "划转前粮食性质代码")
    private String hzqlsxzdm;

    @NotBlank(message = "划转后粮食性质代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "划转后粮食性质代码")
    private String hzhlsxzdm;

    @NotNull(message = "划转日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "划转日期")
    private LocalDate hzrq;

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

    @StringSize(max = 400, message = "领导审核人最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "备注")
    private String bz;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "粮食品种代码不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwmc;

    @Override
    public String buildId() {
        return lsxzzbdh;
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
