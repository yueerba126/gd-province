package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetNotEmpty;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * @author lzq
 * @description 企业单位API操作参数
 * @date 2022/10/19 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "企业单位API操作参数")
public class CompanyApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("单位代码")
    private String dwdm;

    @NotBlank(message = "单位名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "单位名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("单位名称")
    private String dwmc;

    @NotBlank(message = "单位类型必填", groups = BasicCheck.class)
    @StringSize(max = 2, min = 1, message = "单位类型最少1位数,最大两位数", groups = BasicCheck.class)
    @Pattern(regexp = "^2$|^3$|^4$|^9$|^11$|^12$|^13$|^14$|^15$|^19$", message = "单位类型不存在", groups = BasicCheck.class)
    @ApiModelProperty("单位类型")
    private String dwlx;

    @Past(message = "注册日期必须在当前日期之前", groups = BasicCheck.class)
    @NotNull(message = "注册日期必填", groups = BasicCheck.class)
    @ApiModelProperty("注册日期")
    private LocalDate zcrq;

    @Positive(message = "注册资本不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "注册资本精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("注册资本")
    private BigDecimal zczb;

    @Positive(message = "资产总额不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "资产总额精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("资产总额")
    private BigDecimal zcze;

    @NotBlank(message = "法定代表人必填", groups = BasicCheck.class)
    @StringSize(max = 100, message = "法定代表人最大长度为100", groups = BasicCheck.class)
    @PersonNameRule(message = "人员姓名为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
    @ApiModelProperty("法定代表人")
    private String fddbr;

    @NotBlank(message = "法人身份证号必填", groups = BasicCheck.class)
    @StringSize(max = 18, min = 18, message = "法人身份证号只能是18位", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$", message = "法人身份证号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("法人身份证号")
    private String frsfzh;

    @StringSize(max = 50, message = "法人联系方式最大长度为50", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^\\d{3}-\\d{8}|\\d{4}-\\d{7,8}|\\d{11}$", message = "法人联系方式格式错误", groups = BasicCheck.class)
    @ApiModelProperty("法人联系方式")
    private String frlxfs;

    @NotBlank(message = "企业联系人必填", groups = BasicCheck.class)
    @PersonNameRule(message = "人员姓名为中文，且不允许填写:暂无，无，空测试等", groups = BasicCheck.class)
    @StringSize(max = 100, message = "企业联系人最大长度为100", groups = BasicCheck.class)
    @ApiModelProperty("企业联系人")
    private String qylxr;

    @NotBlank(message = "办公电话必填", groups = BasicCheck.class)
    @StringSize(max = 50, message = "办公电话最大长度为50", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$|^\\d{11}$", message = "办公电话格式错误", groups = BasicCheck.class)
    @ApiModelProperty("办公电话")
    private String bgdh;

    @NotBlank(message = "注册地址必填", groups = BasicCheck.class)
    @StringSize(max = 512, message = "注册地址最大长度为512", groups = BasicCheck.class)
    @ApiModelProperty("注册地址")
    private String zcdz;

    @StringSize(max = 50, message = "电子邮箱最大长度为50", groups = BasicCheck.class)
    @ApiModelProperty("电子邮箱")
    private String dzyx;

    @StringSize(max = 128, message = "企业官方网站地址最大长度为128", groups = BasicCheck.class)
    @ApiModelProperty("企业官方网站地址")
    private String qygfwzdz;

    @StringSize(max = 32, message = "传真号码最大长度为32", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^\\d{3}-\\d{8}$|^\\d{4}-\\d{7,8}$", message = "传真号码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("传真号码")
    private String czhm;

    @Pattern(regexp = "^\\d{6}$", message = "邮政编码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("邮政编码")
    private String yzbm;

    @NotBlank(message = "行政区划代码不能为空", groups = BasicCheck.class)
    @StringSize(max = 6, min = 6, message = "行政区划代码长度只能为6", groups = BasicCheck.class)
    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @StringSize(max = 256, message = "上级单位名称最大256位", groups = BasicCheck.class)
    @TargetNotEmpty(message = "填写上级单位代码时上级单位名称必填", target = "#sjdwdm", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "上级单位名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("上级单位名称")
    private String sjdwmc;

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "上级单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("上级单位代码")
    private String sjdwdm;

    @NotNull(message = "库区数不能为空", groups = BasicCheck.class)
    @Max(value = Integer.MAX_VALUE, message = "库区数超过最大值2147483647", groups = BasicCheck.class)
    @Positive(message = "库区数不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("库区数")
    private Integer kqs;

    @NotNull(message = "仓房数不能为空", groups = BasicCheck.class)
    @Max(value = Integer.MAX_VALUE, message = "仓房数超过最大值2147483647", groups = BasicCheck.class)
    @Min(value = 0, message = "仓房数最小是0", groups = BasicCheck.class)
    @ApiModelProperty("仓房数")
    private Integer cfs;

    @NotNull(message = "油罐数不能为空", groups = BasicCheck.class)
    @Max(value = Integer.MAX_VALUE, message = "油罐数超过最大值2147483647", groups = BasicCheck.class)
    @Min(value = 0, message = "油罐数最小是0", groups = BasicCheck.class)
    @ApiModelProperty("油罐数")
    private Integer ygs;

    @Digits(integer = 20, fraction = 6, message = "经度精度Decimal(20,6)", groups = BasicCheck.class)
    @DecimalMin(value = "73.33", message = "经度不在中国范围以内,大于73.33", groups = BasicCheck.class)
    @DecimalMax(value = "135.05", message = "经度不在中国范围以内,小于135.05", groups = BasicCheck.class)
    @ApiModelProperty("经度")
    private BigDecimal jd;

    @Digits(integer = 20, fraction = 6, message = "纬度精度Decimal(20,6)", groups = BasicCheck.class)
    @DecimalMin(value = "3.51", message = "纬度不在中国范围以内,大于3.51", groups = BasicCheck.class)
    @DecimalMax(value = "53.33", message = "纬度不在中国范围以内,小于53.33", groups = BasicCheck.class)
    @ApiModelProperty("纬度")
    private BigDecimal wd;

    @NotBlank(message = "备案类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^2$", message = "备案类型不存在", groups = BasicCheck.class)
    @ApiModelProperty("备案类型 0:初次备案 1:变更备案 2:重新备案")
    private String balx;

    @NotBlank(message = "仓储业务类型必填", groups = BasicCheck.class)
    @ApiModelProperty("仓储业务类型")
    private String ccywlx;

    @NotBlank(message = "仓储品种必填", groups = BasicCheck.class)
    @ApiModelProperty("仓储品种")
    private String ccpz;

    @NotBlank(message = "备案状态必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^2$|^3$|^4$|^5$|^6$|^7$|^8$", message = "备案类型不存在", groups = BasicCheck.class)
    @ApiModelProperty("备案状态")
    private String bazt;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "行政区划代码不存在", groups = CorrectCheck.class)
    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(value = "行政区划代码名称", hidden = true)
    private String xzqhdmName;

    @JsonIgnore
    @DataBindCompany(sourceField = "#sjdwdm")
    @TargetNotEmpty(message = "上级单位代码不存在", target = "#sjdwdm", groups = CorrectCheck.class)
    @ApiModelProperty("上级单位名称")
    private String sjdwdmName;

    @JsonIgnore
    @NotBlank(message = "仓储业务类型不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#ccywlx", sourceFieldCombination = "ccywlx", valueBindStrategy = SEPARATED)
    @ApiModelProperty("仓储业务类型逗号分隔 0:储备 1:收购 2:加工 3:销售 4:运输 5:中转 6:进出口 7:其他")
    private String ccywlxName;

    @JsonIgnore
    @NotBlank(message = "仓储品种不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#ccpz", sourceFieldCombination = "ccpz", valueBindStrategy = SEPARATED)
    @ApiModelProperty("仓储品种逗号分隔 0:小麦 1:玉米 2:稻谷 3:大豆 4:成品粮 5:食用植物油 6:其他")
    private String ccpzName;

    @Override
    public String buildId() {
        return dwdm;
    }

    @Override
    public String buildCompanyId() {
        return dwdm;
    }
}
