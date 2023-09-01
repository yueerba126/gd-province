package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.*;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.trade.annotation.DataBindCustomer;
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

import static com.sydata.common.util.RegexpUtil.SPECIAL_CHECK;

/**
 * @author czx
 * @description 合同API操作参数
 * @date 2022/10/19 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "合同API操作参数")
public class ContractApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "合同编号必填", groups = BasicCheck.class)
    @Pattern(regexp = SPECIAL_CHECK, message = "合同编号存在特殊字符", groups = BasicCheck.class)
    @TargetStartsWith(message = "合同编号必须以单位代码开始", target = "#dwdm", groups = BasicCheck.class)
    @StringSize(min = 19, max = 64, message = "合同编号长度为19到64位", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[\\s\\S]{1,46}$", message = "合同编号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("合同编号")
    private String hth;

    @StringSize(max = 128, message = "合同名称最大长度为128", groups = BasicCheck.class)
    @NotBlank(message = "合同名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,128}$", message = "合同名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("合同名称")
    private String htmc;

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("单位代码")
    private String dwdm;

    @NotBlank(message = "业务类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$", message = "业务类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty("业务类型")
    private String ywlx;

    @NotBlank(message = "客户类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$", message = "业务类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty("客户类型")
    private String khlx;

    @TargetAppointValue(message = "客户类型为企业用户时候，客户统一社会信用代码必填", targetValue = {"1"}, groups = BasicCheck.class, target = "#khlx")
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "客户统一社会信用代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("客户统一社会信用代码")
    private String khtyshxydm;

    @StringSize(max = 256, message = "客户名称最大长度为256位", groups = BasicCheck.class)
    @NotBlank(message = "客户名称必填", groups = BasicCheck.class)
    @ApiModelProperty("客户名称")
    private String khmc;

    @StringSize(max = 64, message = "法定代表人最大长度为64位", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("法定代表人")
    private String fddbr;

    @StringSize(max = 512, message = "通讯地址最大长度为512位", groups = BasicCheck.class)
    @ApiModelProperty("通讯地址")
    private String txdz;

    @Pattern(regexp = "^\\d{6}$", message = "邮政编码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("邮政编码")
    private String yzbm;

    @StringSize(max = 64, message = "联系人姓名最大长度为64位", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("联系人姓名")
    private String lxrxm;

    @Pattern(regexp = "^$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$|^\\d{11}$", message = "联系人电话格式错误", groups = BasicCheck.class)
    @ApiModelProperty("联系人电话")
    private String lxrdh;

    @Pattern(regexp = "^$|^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$", message = "身份证号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("身份证号")
    private String sfzh;

    @StringSize(max = 64, message = "电子邮箱最大长度为64位", groups = BasicCheck.class)
    @ApiModelProperty("电子邮箱")
    private String dzyx;

    @NotNull(message = "签订日期必填", groups = BasicCheck.class)
    @ApiModelProperty("签订日期")
    private LocalDate qdrq;

    @ApiModelProperty("约定完成时间")
    private LocalDate ydwcsj;

    @StringSize(max = 512, message = "签订地点最大为512位", groups = BasicCheck.class)
    @ApiModelProperty("签订地点")
    private String qddd;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("粮食品种代码")
    private String lspzdm;

    @NotBlank(message = "粮食性质代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$",
            message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("粮食性质代码")
    private String lsxzdm;

    @NotNull(message = "合同单价必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 2, message = "合同单价Decimal(20,2)", groups = BasicCheck.class)
    @Positive(message = "合同单价不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("合同单价")
    private BigDecimal htdj;

    @NotNull(message = "约定购销粮食数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "约定购销粮食数量Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "约定购销粮食数量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("约定购销粮食数量")
    private BigDecimal ydgxlssl;

    @NotNull(message = "合同总金额必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "合同总金额Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "合同总金额不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("合同总金额")
    private BigDecimal htzje;

    @Digits(integer = 20, fraction = 2, message = "履约保证金Decimal(20,2)", groups = BasicCheck.class)
    @ApiModelProperty("履约保证金")
    private BigDecimal lybzj;

    @ApiModelProperty("实际完成时间")
    private LocalDate sswcsj;

    @Digits(integer = 20, fraction = 3, message = "履约数量Decimal(20,3)", groups = BasicCheck.class)
    @TargetNotEmpty(message = "填写结算总金额时履约数量必填", target = "#jszje", groups = BasicCheck.class)
    @ApiModelProperty("履约数量")
    private BigDecimal lysl;

    @Digits(integer = 20, fraction = 2, message = "履约率Decimal(20,2)", groups = BasicCheck.class)
    @TargetNotEmpty(message = "填写结算总金额时履约率必填", target = "#jszje", groups = BasicCheck.class)
    @ApiModelProperty("履约率")
    private BigDecimal lyl;

    @Digits(integer = 20, fraction = 3, message = "结算价格Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "结算价格不能小于等于0", groups = BasicCheck.class)
    @TargetNotEmpty(message = "填写结算总金额时结算价格必填", target = "#jszje", groups = BasicCheck.class)
    @ApiModelProperty("结算价格")
    private BigDecimal jsjg;

    @Digits(integer = 20, fraction = 3, message = "结算总金额Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "结算总金额不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("结算总金额")
    private BigDecimal jszje;

    @Pattern(regexp = "^1$|^2$", message = "结算与合同一致性格式错误", groups = BasicCheck.class)
    @ApiModelProperty("结算与合同一致性")
    private String jsyhtyzx;

    @StringSize(max = 512, message = "结算与合同不一致原因最大为512位", groups = BasicCheck.class)
    @ApiModelProperty("结算与合同不一致原因")
    private String jsyhtbyzyy;

    @Pattern(regexp = "^$|^102$|^103$|^104$|^105$|^203$|^314$|^402$|^403$|^999$", message = "客户方开户行号格式错误",
            groups = BasicCheck.class)
    @ApiModelProperty("客户方开户行")
    private String khfkhh;

    @StringSize(max = 30, message = "客户方账号最大为30位", groups = BasicCheck.class)
    @ApiModelProperty("客户方账号")
    private String khfzh;

    @StringSize(max = 64, message = "客户签约人最大为64位", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("客户签约人")
    private String khqyr;

    @NotBlank(message = "本方开户行必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^102$|^103$|^104$|^105$|^203$|^314$|^402$|^403$|^999$", message = "本方开户行号格式错误",
            groups = BasicCheck.class)
    @ApiModelProperty("本方开户行")
    private String bfkhh;

    @NotBlank(message = "本方账号必填", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,30}$", message = "本方账号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("本方账号")
    private String bfzh;

    @StringSize(max = 64, message = "本方签约人最大为64位", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("本方签约人")
    private String bfqyr;

    @StringSize(max = 64, message = "审核人最大为64位", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("审核人")
    private String spr;

    @ApiModelProperty("审核时间")
    private LocalDateTime shsj;

    @ApiModelProperty("完成日期")
    private LocalDate wcrq;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany
    @ApiModelProperty(value = "单位名称", hidden = true)
    private String dwdmName;

    @JsonIgnore
    @NotBlank(message = "粮食品种代码无对应国标码", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @JsonIgnore
    @TargetNotEmpty(message = "客户统一社会信用代码不存在", target = "#khtyshxydm", groups = CorrectCheck.class)
    @DataBindCustomer(sourceField = "#dwdm+'-'+#khtyshxydm")
    @ApiModelProperty("客户名称")
    private String customerName;

    @Override
    public String buildId() {
        return hth;
    }

    @Override
    public String buildCompanyId() {
        return dwdm;
    }
}
