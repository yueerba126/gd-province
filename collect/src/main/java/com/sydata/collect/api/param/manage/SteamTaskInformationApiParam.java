package com.sydata.collect.api.param.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.*;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindTank;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.common.manage.annotation.DataBindPestInformation;
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
 * @author ：xy
 * @description：熏蒸作业监管信息接收对象Param
 * @version: 1.0
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "熏蒸作业监管API操作参数")
public class SteamTaskInformationApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "蒸熏作业单号不能为空", groups = BasicCheck.class)
    @TargetStartsWith(message = "熏蒸作业单号必须以仓房(或油罐)编号和开始作业时间日期开始", target = {"#cfdm", "#xzkssj"}, groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{7}[0-9]{11}$", message = "熏蒸作业单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("熏蒸作业单号")
    private String xzzydh;

    @NotBlank(message = "仓房代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{7}$", message = "仓房代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("仓房代码")
    private String cfdm;

    @TargetIsBefore(target = "#xzjssj", message = "熏蒸开始时间不能晚于蒸熏结束时间", groups = BasicCheck.class)
    @NotNull(message = "熏蒸开始时间不能为空", groups = BasicCheck.class)
    @ApiModelProperty("熏蒸开始时间")
    private LocalDate xzkssj;

    @NotNull(message = "蒸熏结束时间不能为空", groups = BasicCheck.class)
    @ApiModelProperty("熏蒸结束时间")
    private LocalDate xzjssj;

    @ApiModelProperty("害虫监测单号")
    private String hcjcdh;

    @NotNull(message = "第几次熏蒸不能为空", groups = BasicCheck.class)
    @Positive(message = "第几次熏蒸不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("第几次熏蒸")
    private Integer djcxz;

    @Pattern(regexp = "^$|^0$|^1$|^2$|^3$", message = "常规熏蒸方式格式错误", groups = BasicCheck.class)
    @ApiModelProperty("常规熏蒸方式")
    private String cgxzfs;

    @Pattern(regexp = "^$|^0$|^1$", message = "环流熏蒸方式格式错误", groups = BasicCheck.class)
    @ApiModelProperty("环流熏蒸方式")
    private String hlxzfs;

    @Pattern(regexp = "^$|^0$|^1$|^2$|^3$", message = "环流熏蒸与内环流技术结合格式错误", groups = BasicCheck.class)
    @ApiModelProperty("环流熏蒸与内环流技术结合")
    private String hlxzynhljsjh;

    @StringSize(max = 128, message = "熏蒸方案指定最大长度128", groups = BasicCheck.class)
    @Pattern(regexp = "^.{0,128}$", message = "熏蒸方案指定格式错误", groups = BasicCheck.class)
    @ApiModelProperty("熏蒸方案指定")
    private String xzfazd;

    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @StringSize(max = 64, message = "熏蒸方案起草人长度不能超过64", groups = BasicCheck.class)
    @ApiModelProperty("熏蒸方案起草人")
    private String xzfaqcr;

    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @StringSize(max = 64, message = "熏蒸方案批准人长度不能超过64", groups = BasicCheck.class)
    @ApiModelProperty("熏蒸方案批准人")
    private String xzfapzr;

    @StringSize(max = 64, message = "熏蒸方案报备情况长度不能超过64", groups = BasicCheck.class)
    @ApiModelProperty("熏蒸方案报备情况")
    private String xzfabbqk;

    @StringSize(max = 64, message = "施药人员资质情况长度不能超过64", groups = BasicCheck.class)
    @ApiModelProperty("施药人员资质情况")
    private String syryzzqk;

    @Pattern(regexp = "^.{0,64}$", message = "施药资质审核格式错误", groups = BasicCheck.class)
    @ApiModelProperty("施药资质审核")
    private String syzzsh;

    @NotBlank(message = "药剂名称不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,64}$", message = "药剂名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("药剂名称")
    private String yjmc;

    @Pattern(regexp = "^.{0,64}$", message = "剂型格式错误", groups = BasicCheck.class)
    @ApiModelProperty("剂型")
    private String jx;

    @Positive(message = "浓度不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "浓度长度不正确 BigDecimal（20，3）", groups = BasicCheck.class)
    @ApiModelProperty("浓度")
    private BigDecimal nd;

    @Digits(integer = 20, fraction = 3, message = "粮堆单位用药量长度不正确 BigDecimal（20，3）", groups = BasicCheck.class)
    @Positive(message = "粮堆单位用药量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("粮堆单位用药量")
    private BigDecimal lddwyyl;

    @TargetIsBefore(target = "#zyyl", rate = 1000d, message = "空间单位用药量必须小于总用药量", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "空间单位用药量长度不正确 BigDecimal（20，3），单位：g/m³", groups = BasicCheck.class)
    @Positive(message = "空间单位用药量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("空间单位用药量")
    private BigDecimal kjdwyyl;

    @Digits(integer = 20, fraction = 3, message = "总用药量长度不正确 BigDecimal（20，3，单位：kg）", groups = BasicCheck.class)
    @Positive(message = "总用药量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("总用药量")
    private BigDecimal zyyl;

    @Pattern(regexp = "^0$|^1$|^2$", message = "施药方法格式错误", groups = BasicCheck.class)
    @NotBlank(message = "施药方法不能为空", groups = BasicCheck.class)
    @ApiModelProperty("施药方法")
    private String syff;

    @Pattern(regexp = "^.{0,128}$", message = "辅助施药措施格式错误", groups = BasicCheck.class)
    @ApiModelProperty("辅助施药措施")
    private String fzsycs;

    @StringSize(max = 128, message = "空气呼吸器及安全检查情况长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("空气呼吸器及安全检查情况")
    private String kqhqjaqjcqk;

    @StringSize(max = 128, message = "磷化氢监测装置调试情况长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("磷化氢监测装置调试情况")
    private String lhqjczztsqk;

    @StringSize(max = 128, message = "氧气深度监测装置调试情况长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("氧气深度监测装置调试情况")
    private String yqsdjczztsqk;

    @StringSize(max = 128, message = "磷化氢报警仪安全检查长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("磷化氢报警仪安全检查")
    private String lhqbjyaqjc;

    @StringSize(max = 128, message = "氧气报警仪安全检查长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("氧气报警仪安全检查")
    private String yqbjyaqjc;

    @Digits(integer = 20, fraction = 3, message = "补药前仓内磷化氢浓度长度不正确 BigDecimal（20，3）")
    @ApiModelProperty("补药前仓内磷化氢浓度")
    private BigDecimal byqcnlhqnd;

    @StringSize(max = 1, message = "磷化氢浓度单位不能超过1", groups = BasicCheck.class)
    @ApiModelProperty("磷化氢浓度单位")
    private String lhqnddw;

    @Digits(integer = 20, fraction = 3, message = "目标浓度长度不正确 BigDecimal（20，3）", groups = BasicCheck.class)
    @ApiModelProperty("目标浓度")
    private BigDecimal mbnd;

    @Digits(integer = 20, fraction = 3, message = "计算补药量长度不正确 BigDecimal（20，3）", groups = BasicCheck.class)
    @ApiModelProperty("计算补药量")
    private BigDecimal jsbyl;

    @Digits(integer = 20, fraction = 3, message = "实际补药量长度不正确 BigDecimal（20，3）", groups = BasicCheck.class)
    @ApiModelProperty("实际补药量")
    private BigDecimal sjbyl;

    @Pattern(regexp = "^.{0,128}$", message = "补药方法格式错误", groups = BasicCheck.class)
    @ApiModelProperty("补药方法")
    private String byff;

    @Positive(message = "作业人数不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("作业人数")
    private Integer zyrs;

    @StringSize(max = 64, message = "补药作业批准人长度不能超过64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("补药作业批准人")
    private String byzypzr;

    @StringSize(max = 64, message = "现场指挥人长度不能超过64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("现场指挥人")
    private String xczhr;

    @Digits(integer = 20, fraction = 3, message = "峰值浓度长度不正确 BigDecimal（20，3）", groups = BasicCheck.class)
    @ApiModelProperty("峰值浓度")
    private BigDecimal fznd;

    @ApiModelProperty("目标浓度维持天数")
    private Integer mbndwcts;

    @Pattern(regexp = "^.{0,128}$", message = "漏气位置监测格式错误", groups = BasicCheck.class)
    @ApiModelProperty("漏气位置监测")
    private String lswzjc;

    @StringSize(max = 128, message = "漏气部位采取的补救措施长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("漏气部位采取的补救措施")
    private String lqbwcqdbjcs;

    @Positive(message = "密闭时间不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("密闭时间")
    private Integer mbsj;

    @Digits(integer = 20, fraction = 3, message = "CT值格式不正确 BigDecimal（20，3）", groups = BasicCheck.class)
    @ApiModelProperty("CT值")
    private BigDecimal ctz;

    @Digits(integer = 20, fraction = 3, message = "散气前磷化氢浓度不正确 BigDecimal（20，3）", groups = BasicCheck.class)
    @ApiModelProperty("散气前磷化氢浓度")
    private BigDecimal sqqlhqnd;

    @ApiModelProperty("散气日期")
    private LocalDate sqrq;

    @Pattern(regexp = "^.{0,128}$", message = "散气方法格式错误", groups = BasicCheck.class)
    @NotBlank(message = "散气方法不能为空", groups = BasicCheck.class)
    @ApiModelProperty("散气方法")
    private String sqff;

    @StringSize(max = 64, message = "散气批准人长度不能超过64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("散气批准人")
    private String sqpzr;

    @NotNull(message = "散气持续天数不能为空", groups = BasicCheck.class)
    @Positive(message = "散气持续天数不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("散气持续天数")
    private Integer sqcsts;

    @TargetIsBefore(target = "#sqqlhqnd", message = "散气结束时磷化氢浓度不能高于散气前磷化氢浓度", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "散气结束时磷化氢浓度格式不正确 BigDecimal（20，3）", groups = BasicCheck.class)
    @ApiModelProperty("散气结束时磷化氢浓度")
    private BigDecimal sqjsslhqnd;

    @ApiModelProperty("残渣收集作业时间")
    private LocalDate czsjzysj;

    @Positive(message = "残渣收集作业人数不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("残渣收集作业人数")
    private Integer czsjzyrs;

    @Pattern(regexp = "^.{0,128}$", message = "残渣收集方法格式错误", groups = BasicCheck.class)
    @ApiModelProperty("残渣收集方法")
    private String czsjff;

    @StringSize(max = 64, message = "残渣收集作业批准人长度不能超过64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("残渣收集作业批准人")
    private String czsjzypzr;

    @Pattern(regexp = "^.{0,128}$", message = "残渣处理措施格式错误", groups = BasicCheck.class)
    @ApiModelProperty("残渣处理措施")
    private String czclcs;

    @Positive(message = "残渣处理作业人数不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("残渣处理作业人数")
    private Integer czclzyrs;

    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @StringSize(max = 64, message = "残渣处理批准人长度不能超过64", groups = BasicCheck.class)
    @ApiModelProperty("残渣处理批准人")
    private String czclpzr;

    @StringSize(max = 128, message = "熏蒸后活虫检出情况长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("熏蒸后活虫检出情况")
    private String xzhhcjcqk;

    @ApiModelProperty("熏蒸后活虫密度")
    private Integer xzhckmd;

    @ApiModelProperty("培养15天后活虫数")
    private Integer pyswthhcs;

    @ApiModelProperty("培养45天后活虫数")
    private Integer pysswthhcs;

    @NotBlank(message = "熏蒸效果评价不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^2$", message = "熏蒸效果评价格式错误", groups = BasicCheck.class)
    @ApiModelProperty("熏蒸效果评价")
    private String xzxgpj;

    @StringSize(max = 64, message = "熏蒸负责人长度不能超过64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("熏蒸负责人")
    private String xzfzr;

    @StringSize(max = 128, message = "熏蒸作业人员长度不能超过128", groups = BasicCheck.class)
    @PersonNameRule(split = true, groups = BasicCheck.class,
            message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等,多个人员使用|分割")
    @ApiModelProperty("熏蒸作业人员")
    private String xzzyry;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "仓房(或油罐)编码不存在", groups = CorrectCheck.class)
    @DataBindWarehouse
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "仓房(或油罐)编码名称", hidden = true)
    private String cfdmName;

    @JsonIgnore
    @TargetNotEmpty(target = "#hcjcdh", message = "关联的虫害单号不存在，请先上报虫害单", groups = CorrectCheck.class)
    @DataBindPestInformation
    @ApiModelProperty(value = "虫害单号", hidden = true)
    private String hcjcdhCode;

    @Override
    public String buildId() {
        return xzzydh;
    }

    @Override
    public String buildCompanyId() {
        return cfdm.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return cfdm.substring(0, 21);
    }
}
