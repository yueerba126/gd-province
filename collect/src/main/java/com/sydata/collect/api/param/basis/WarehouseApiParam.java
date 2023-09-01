

package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.common.basis.annotation.DataBindTank;
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
 * @author lzq
 * @description 仓房信息API操作参数
 * @date 2022/10/19 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "仓房信息API操作参数")
public class WarehouseApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "仓房代码必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "仓房代码必须以库区代码开始", target = "#kqdm", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{7}$", message = "仓房代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("仓房代码")
    private String cfdm;

    @NotBlank(message = "仓房名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "仓房名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("仓房名称")
    private String cfmc;

    @NotBlank(message = "库区代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区代码")
    private String kqdm;

    @NotBlank(message = "仓房类型代码必填", groups = BasicCheck.class)
    @StringSize(max = 8, min = 5, message = "仓房类型代码为5-8位数字", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{5,8}$", message = "仓房类型代码不存在", groups = BasicCheck.class)
    @ApiModelProperty("仓房类型代码")
    private String cflxdm;

    @ApiModelProperty("交付使用日期")
    private LocalDate jfsyrq;

    @StringSize(max = 256, message = "建设单位最大长度256", groups = BasicCheck.class)
    @ApiModelProperty("建设单位")
    private String jsdw;

    @StringSize(max = 32, message = "建设单位最大长度32", groups = BasicCheck.class)
    @ApiModelProperty("设计使用年限")
    private String sjsynx;

    @StringSize(max = 256, message = "设计单位最大长度256", groups = BasicCheck.class)
    @ApiModelProperty("设计单位")
    private String sjdw;

    @StringSize(max = 256, message = "监理单位最大长度256", groups = BasicCheck.class)
    @ApiModelProperty("监理单位")
    private String jldw;

    @NotBlank(message = "墙体结构必填", groups = BasicCheck.class)
    @Pattern(regexp = "^01$|^02$|^03$|^04$|^99$", message = "墙体结构不存在", groups = BasicCheck.class)
    @ApiModelProperty("墙体结构")
    private String qtjg;

    @NotBlank(message = "房顶结构必填", groups = BasicCheck.class)
    @Pattern(regexp = "^01$|^02$|^03$|^04$|^05$|^06$", message = "房顶结构不存在", groups = BasicCheck.class)
    @ApiModelProperty("房顶结构")
    private String fdjg;

    @NotBlank(message = "房架结构必填", groups = BasicCheck.class)
    @Pattern(regexp = "^01$|^02$|^03$|^04$|^05$|^06$|^07$", message = "房架结构不存在", groups = BasicCheck.class)
    @ApiModelProperty("房架结构")
    private String fjjg;

    @NotBlank(message = "地面结构必填", groups = BasicCheck.class)
    @Pattern(regexp = "^01$|^02$|^03$|^04$|^05$|^06$", message = "地面结构不存在", groups = BasicCheck.class)
    @ApiModelProperty("地面结构")
    private String dmjg;

    @NotNull(message = "设计仓容必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "设计仓容精度Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "设计仓容不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("设计仓容")
    private BigDecimal sjcr;

    @Digits(integer = 20, fraction = 6, message = "仓外长精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("仓外长")
    private BigDecimal cwc;

    @Digits(integer = 20, fraction = 6, message = "仓外宽精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("仓外宽")
    private BigDecimal cwk;

    @Digits(integer = 20, fraction = 6, message = "仓外檐高精度Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "仓外檐高不能小于或等于0", groups = BasicCheck.class)
    @ApiModelProperty("仓外檐高")
    private BigDecimal cwyg;

    @Digits(integer = 20, fraction = 6, message = "仓外顶高精度Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "仓外顶高不能小于或等于0", groups = BasicCheck.class)
    @ApiModelProperty("仓外顶高")
    private BigDecimal cwdg;

    @NotNull(message = "筒仓外径必填)", groups = BasicCheck.class)
    @Min(value = 0, message = "筒仓外径不能小于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "筒仓外径精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("筒仓外径")
    private BigDecimal tcwj;

    @Digits(integer = 20, fraction = 6, message = "仓内长精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("仓内长")
    private BigDecimal cnc;

    @Digits(integer = 20, fraction = 6, message = "仓内宽精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("仓内宽")
    private BigDecimal cnk;

    @Digits(integer = 20, fraction = 6, message = "仓内檐高精度Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "仓内檐高不能小于或等于0", groups = BasicCheck.class)
    @ApiModelProperty("仓内檐高")
    private BigDecimal cnyg;

    @Digits(integer = 20, fraction = 6, message = "仓内装粮线高精度Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "仓内装粮线高不能小于或等于0", groups = BasicCheck.class)
    @ApiModelProperty("仓内装粮线高")
    private BigDecimal cnzlxg;

    @NotNull(message = "筒仓内径必填)", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "筒仓内径精度Decimal(20,6)", groups = BasicCheck.class)
    @Min(value = 0, message = "筒仓内径不能小于0", groups = BasicCheck.class)
    @ApiModelProperty("筒仓内径")
    private BigDecimal tcnj;

    @Digits(integer = 20, fraction = 6, message = "仓内体积精度Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "仓内体积不能小于或等于0", groups = BasicCheck.class)
    @ApiModelProperty("仓内体积")
    private BigDecimal cntj;

    @Max(value = Integer.MAX_VALUE, message = "仓门数量超过最大值2147483647", groups = BasicCheck.class)
    @ApiModelProperty("仓门数量")
    private Integer cmsl;

    @NotBlank(message = "仓门位置必填", groups = BasicCheck.class)
    @StringSize(max = 256, message = "仓门位置最大长度256", groups = BasicCheck.class)
    @ApiModelProperty("仓门位置")
    private String cmwz;

    @NotNull(message = "仓门高度必填", groups = BasicCheck.class)
    @Positive(message = "仓门高度不能小于或等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "仓门高度精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("仓门高度")
    private BigDecimal cmgd;

    @NotNull(message = "仓门宽度必填", groups = BasicCheck.class)
    @Positive(message = "仓门宽度不能小于或等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "仓门宽度精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("仓门宽度")
    private BigDecimal cmkd;

    @Pattern(regexp = "^$|^01$|^02$|^03$|^04$|^05$", message = "挡粮门型式不存在", groups = BasicCheck.class)
    @ApiModelProperty("挡粮门型式")
    private String dlmxs;

    @NotBlank(message = "仓房是否完好必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^9$", message = "仓房是否完好不存在", groups = BasicCheck.class)
    @ApiModelProperty("仓房是否完好")
    private String cfsfwh;

    @NotBlank(message = "储粮功效必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^2$|^3$", message = "储粮功效不存在", groups = BasicCheck.class)
    @ApiModelProperty("储粮功效")
    private String clgx;

    @NotBlank(message = "能否隔热保温必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^9$", message = "能否隔热保温不存在", groups = BasicCheck.class)
    @ApiModelProperty("能否隔热保温")
    private String nfgrbw;

    @StringSize(max = 512, message = "隔热保温措施最大长度512", groups = BasicCheck.class)
    @ApiModelProperty("隔热保温措施")
    private String grbwcs;

    @NotBlank(message = "隔热性能必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$", message = "隔热性能不存在", groups = BasicCheck.class)
    @ApiModelProperty("隔热性能")
    private String grxn;

    @NotBlank(message = "结构材料必填", groups = BasicCheck.class)
    @StringSize(max = 1, min = 1, message = "结构材料长度为1", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$", message = "结构材料不存在", groups = BasicCheck.class)
    @ApiModelProperty("结构材料")
    private String jgcl;

    @NotBlank(message = "气密性必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$", message = "气密性不存在", groups = BasicCheck.class)
    @ApiModelProperty("气密性")
    private String qmx;

    @NotBlank(message = "是否已进行信息化改造必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$", message = "是否已进行信息化改造不存在", groups = BasicCheck.class)
    @ApiModelProperty("是否已进行信息化改造")
    private String sfyjxxxhgz;

    @NotBlank(message = "粮情技术必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$", message = "粮情技术不存在", groups = BasicCheck.class)
    @ApiModelProperty("粮情技术")
    private String lqjs;

    @NotBlank(message = "能否散装储存必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^9$", message = "能否散装储存不存在", groups = BasicCheck.class)
    @ApiModelProperty("能否散装储存")
    private String nfszcc;

    @NotBlank(message = "有无防鼠防雀防虫装置及设施必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^9$", message = "有无防鼠防雀防虫装置及设施不存在", groups = BasicCheck.class)
    @ApiModelProperty("有无防鼠防雀防虫装置及设施")
    private String ywfsfqfczz;

    @NotBlank(message = "有无防火防爆防盗设施必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^9$", message = "有无防火防爆防盗设施不存在", groups = BasicCheck.class)
    @ApiModelProperty("有无防火防爆防盗设施")
    private String ywfhfbfdss;

    @NotBlank(message = "有无机械通风设施必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^9$", message = "有无机械通风设施不存在", groups = BasicCheck.class)
    @ApiModelProperty("有无机械通风设施")
    private String ywjxtfss;

    @Pattern(regexp = "^$|^0$|^1$|^2$|^3$|^4$", message = "通风系统型式不存在", groups = BasicCheck.class)
    @ApiModelProperty("通风系统型式")
    private String tfxtxs;

    @NotBlank(message = "通风技术必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$", message = "通风技术不存在", groups = BasicCheck.class)
    @ApiModelProperty("通风技术")
    private String tfjs;

    @NotBlank(message = "能否环流熏蒸杀虫必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^9$", message = "能否环流熏蒸杀虫不存在", groups = BasicCheck.class)
    @ApiModelProperty("能否环流熏蒸杀虫")
    private String nfhlxzsc;

    @Pattern(regexp = "^$|^1$|^2$|^3$", message = "杀虫技术不存在", groups = BasicCheck.class)
    @ApiModelProperty("杀虫技术")
    private String scjs;

    @NotBlank(message = "能否富氮低氧气调储粮必填", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^9$", message = "能否富氮低氧气调储粮不存在", groups = BasicCheck.class)
    @ApiModelProperty("能否富氮低氧气调储粮")
    private String nffddyqtcl;

    @Pattern(regexp = "^$|^1$|^2$|^3$", message = "控温技术不存在", groups = BasicCheck.class)
    @ApiModelProperty("控温技术")
    private String kwjs;

    @NotBlank(message = "害虫检测方式必填", groups = BasicCheck.class)
    @Pattern(regexp = "^01$|^02$|^03$", message = "害虫检测方式不存在", groups = BasicCheck.class)
    @ApiModelProperty("害虫检测方式")
    private String hcjcfs;

    @NotBlank(message = "仓房状态必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$|^5$|^6$|^7$|^9$", message = "仓房状态不存在", groups = BasicCheck.class)
    @ApiModelProperty("仓房状态")
    private String cfzt;

    @NotBlank(message = "保管员必填", groups = BasicCheck.class)
    @PersonNameRule(split = true, groups = BasicCheck.class,
            message = "人员姓名为中文，且不允许填写:暂无，无，空测试等,多个人员使用|分割")
    @StringSize(max = 128, message = "保管员最大长度128", groups = BasicCheck.class)
    @ApiModelProperty("保管员")
    private String bgr;

    @DecimalMin(value = "73.33", message = "经度不在中国范围以内,大于73.33", groups = BasicCheck.class)
    @DecimalMax(value = "135.05", message = "经度不在中国范围以内,小于135.05", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "仓房经度精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("仓房经度")
    private BigDecimal jd;

    @DecimalMin(value = "3.51", message = "纬度不在中国范围以内,大于3.51", groups = BasicCheck.class)
    @DecimalMax(value = "53.33", message = "纬度不在中国范围以内,小于53.33", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "仓房纬度精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty("仓房纬度")
    private BigDecimal wd;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区代码名称", hidden = true)
    private String kqdmName;

    @JsonIgnore
    @NotBlank(message = "仓房类型代码不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#cflxdm", sourceFieldCombination = "cflxdm")
    @ApiModelProperty(value = "仓房类型名称", hidden = true)
    private String cflxdmName;

    @JsonIgnore
    @Null(message = "同一库区下的仓房代码与油罐代码不能冲突", groups = CorrectCheck.class)
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "油罐代码名称", hidden = true)
    private String ygdmName;

    @Override
    public String buildId() {
        return cfdm;
    }

    @Override
    public String buildCompanyId() {
        return kqdm.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return kqdm;
    }

}
