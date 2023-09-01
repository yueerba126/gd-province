package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.CarNumCheck;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetNotEmpty;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.trade.annotation.DataBindContract;
import com.sydata.common.trade.annotation.DataBindOutStockSettlement;
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
import java.util.StringJoiner;

import static cn.hutool.core.lang.RegexPool.PLATE_NUMBER;
import static com.sydata.common.util.RegexpUtil.SPECIAL_CHECK;
import static jodd.util.StringPool.DASH;

/**
 * @author czx
 * @description 粮食出库信息API操作参数
 * @date 2022/10/20 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "粮食出库信息API操作参数")
public class OutStockApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "出库业务单号必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[15]{2}[0-9]{10}$", message = "出库业务单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "出库业务单号")
    private String ckywdh;

    @NotBlank(message = "货位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @NotBlank(message = "出库通知单号必填", groups = BasicCheck.class)
    @StringSize(max = 32, message = "出库通知单号最大长度为32", groups = BasicCheck.class)
    @ApiModelProperty(value = "出库通知单号")
    private String cktzdh;

    @NotBlank(message = "业务类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[1]{1}$", message = "业务类型 1：出库 (默认)", groups = BasicCheck.class)
    @ApiModelProperty(value = "业务类型,1：出库 (默认)")
    private String ywlx;

    @NotNull(message = "业务日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "业务日期,格式：yyyy-MM-dd")
    private LocalDate ywrq;

    @NotBlank(message = "合同号必填", groups = BasicCheck.class)
    @Pattern(regexp = SPECIAL_CHECK, message = "合同号存在特殊字符", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[\\s\\S]{1,46}$", message = "合同编号格式错误", groups = BasicCheck.class)
    @StringSize(max = 64, message = "合同编号最大长度为64", groups = BasicCheck.class)
    @ApiModelProperty(value = "合同号")
    private String hth;

    @StringSize(max = 64, message = "承运人最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "承运人")
    private String cyr;

    @Pattern(regexp = "^$|^\\d{3}-\\d{8}|\\d{4}-\\d{7,8}|\\d{11}$", message = "联系电话格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @Pattern(regexp = "^$|^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$", message = "身份证号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "身份证号")
    private String sfzh;

    @NotBlank(message = "运输工具必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^9$", message = "运输工具格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "运输工具:1：汽车 2：火车 3：轮船 9：其他")
    private String ysgj;

    @StringSize(max = 256, message = "卸粮地点最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "卸粮地点")
    private String xldd;

    @Pattern(regexp = "^01$|^02$|^03$|^04$|^LS$", message = "车船号类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "车船号类型")
    private String cchlx;

    @NotBlank(message = "车船号必填", groups = BasicCheck.class)
    @CarNumCheck(target = "#ysgj", message = "运输工具为汽车时，车牌号不符合规则", groups = BasicCheck.class)
    @StringSize(max = 32, message = "车船号最大长度为32", groups = BasicCheck.class)
    @ApiModelProperty(value = "车船号")
    private String cch;

    @Pattern(regexp = PLATE_NUMBER, message = "挂车号格式错误", groups = BasicCheck.class)
    @StringSize(max = 8, message = "挂车号最大长度为8", groups = BasicCheck.class)
    @ApiModelProperty(value = "挂车号")
    private String gch;

    @NotNull(message = "登记时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "登记时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime djsj;

    @StringSize(max = 64, message = "登记门岗人员姓名最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "登记门岗人员姓名")
    private String djmgryxm;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @NotBlank(message = "粮食等级代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^01|02|03|04|05|06$", message = "粮食等级代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @NotBlank(message = "粮食性质代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @NotBlank(message = "收获年度必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{4}$", message = "收获年度格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "收获年度")
    private String shnd;

    @NotBlank(message = "产地代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{6}$", message = "产地代码格式错误")
    @ApiModelProperty(value = "产地代码")
    private String cddm;

    @NotNull(message = "皮重(公斤)必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "皮重(公斤) Decimal(20,3)", groups = BasicCheck.class)
    @Min(message = "皮重不能小于0", value = 0, groups = BasicCheck.class)
    @ApiModelProperty(value = "皮重（公斤）")
    private BigDecimal pz;

    @StringSize(max = 64, message = "皮重监磅员姓名最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "皮重监磅员")
    private String pzjby;

    @NotNull(message = "皮重计量时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "皮重计量时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime pzjlsj;

    @StringSize(max = 64, message = "皮重计量员最大长度为64", groups = BasicCheck.class)
    @ApiModelProperty(value = "皮重计量员")
    private String pzjly;

    @NotNull(message = "毛重(公斤)必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "毛重(公斤) Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "毛重不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "毛重(公斤)")
    private BigDecimal mz;

    @StringSize(max = 64, message = "毛重监磅员最大长度为64", groups = BasicCheck.class)
    @ApiModelProperty(value = "毛重监磅员")
    private String mzjby;

    @NotNull(message = "毛重计量时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "毛重计量时间：格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime mzjlsj;

    @StringSize(max = 64, message = "毛重计量员最大长度为64", groups = BasicCheck.class)
    @ApiModelProperty(value = "毛重计量员")
    private String mzjly;

    @Pattern(regexp = "^$|^1$|^2$|^3$|^9$", message = "包装物格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "包装物：1：麻袋 2：编织袋 3：散装 9：其他")
    private String bzw;

    @Digits(integer = 20, fraction = 3, message = "标准包单包重 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "标准包单包重不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "标准包单包重")
    private BigDecimal bzbdbz;

    @Min(value = 0, message = "标准包件数不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "标准包件数(件)")
    private Integer bzbjs;

    @NotNull(message = "净重必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "净重 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "净重不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "净重")
    private BigDecimal jz;

    @Digits(integer = 20, fraction = 3, message = "扣 (增) 量 Decimal(20,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "扣 (增) 量")
    private BigDecimal kzl;

    @StringSize(max = 256, message = "值仓保管员姓名最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "值仓保管员姓名")
    private String zcbgyxm;

    @StringSize(max = 256, message = "装卸作业单位最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "装卸作业单位")
    private String zxzydw;

    @NotNull(message = "出门时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "出门时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime cmsj;

    @StringSize(max = 64, message = "出门确认门岗人员姓名最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "出门确认门岗人员姓名")
    private String cmqrmgryxm;

    @Pattern(regexp = "^$|^[0-9a-zA-Z]{33}$", message = "出库结算单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "出库结算单号")
    private String ckjsdh;

    @Pattern(regexp = SPECIAL_CHECK, message = "备注存在特殊字符", groups = BasicCheck.class)
    @StringSize(max = 400, message = "备注最大长度为400", groups = BasicCheck.class)
    @ApiModelProperty(value = "备注")
    private String bz;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwmc;

    @JsonIgnore
    @NotBlank(message = "粮食品种代码无对应国标码", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @JsonIgnore
    @TargetNotEmpty(message = "合同编号不存在", target = "#hth", groups = CorrectCheck.class)
    @DataBindContract(dataValue = "#ywlx")
    @ApiModelProperty(value = "合同业务类型", hidden = true)
    private String htywlx;

    @JsonIgnore
    @TargetNotEmpty(message = "出库结算单号不存在", target = "#ckjsdh", groups = CorrectCheck.class)
    @DataBindOutStockSettlement(dataValue = "#jssl")
    @ApiModelProperty(value = "出库结算数量")
    private BigDecimal jssl;

    @JsonIgnore
    @DataBindOutStockSettlement(dataValue = "#hth")
    @ApiModelProperty(value = "出库结算单合同号")
    private String ckjsdHth;

    @Override
    public String buildId() {
        return new StringJoiner(DASH).add(hwdm).add(ckywdh).toString();
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
