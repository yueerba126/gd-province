package com.sydata.collect.api.param.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetIsBefore;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindTank;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;

/**
 * @author ：xy
 * @description：通风作业信息接收对象Param
 * @version: 1.0
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "通风作业信息API操作参数")
public class VentilationApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "通风作业单号不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}[0-9]{11}$", message = "通风作业单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("通风作业单号")
    private String tfzydh;

    @NotNull(message = "通风日期不能为空", groups = BasicCheck.class)
    @ApiModelProperty(value = "通风日期")
    private LocalDate tfrq;

    @NotBlank(message = "仓房代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{7}$", message = "仓房代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("仓房代码")
    private String cfdm;

    @NotBlank(message = "通风目的不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[1|2|3|4|5]$", message = "通风目的格式错误", groups = BasicCheck.class)
    @ApiModelProperty("通风目的")
    private String tfmd;

    @Digits(integer = 10, fraction = 3, message = "粮堆孔隙度长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "粮堆孔隙度不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("粮堆孔隙度")
    private BigDecimal ldkxd;

    @NotBlank(message = "通风类型不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^0$|^1$|^2$|^3$|^4$|^5$|^6$", message = "通风类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty("通风类型")
    private String tflx;

    @Pattern(regexp = "^$|^1$|^2$|^3$|^4$|^5$|^6$|^7$|^8$|^9$", message = "风道形式格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "风道形式")
    private String fdxs;

    @NotBlank(message = "风网设置方式必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^1$|^2$|^3$|^4$|^5$", message = "风网设置方式格式错误", groups = BasicCheck.class)
    @ApiModelProperty("风网设置方式")
    private String fwszfs;

    @Positive(message = "主风道截面积不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "主风道截面积长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty("主风道截面积")
    private BigDecimal zfdjmj;

    @Positive(message = "支风道截面积不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "支风道截面积长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "支风道截面积")
    private BigDecimal zhfdjmj;

    @Positive(message = "支风道总长度不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "支风道总长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "支风道总长度")
    private BigDecimal zfdzcd;

    @Positive(message = "风网开孔率不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "风网开孔率长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty("风网开孔率")
    private BigDecimal fwkkl;

    @Digits(integer = 10, fraction = 3, message = "空气途径比长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "空气途径比不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("空气途径比")
    private BigDecimal kqtjb;

    @Positive(message = "通风口设置个数不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("通风口设置个数")
    private Integer tfkszgs;

    @Pattern(regexp = "^$|^.{0,128}$", message = "通风机型号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("通风机型号")
    private String tfjxh;

    @ApiModelProperty("通风机台数")
    private Integer tfjts;

    @Digits(integer = 10, fraction = 3, message = "单台风机额定全压长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "单台风机额定全压不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("单台风机额定全压")
    private BigDecimal dtfjedqy;

    @Digits(integer = 10, fraction = 3, message = "单台风机额定风量长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "单台风机额定风量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("单台风机额定风量")
    private BigDecimal dtfjedfl;

    @Digits(integer = 10, fraction = 3, message = "单台风机额定功率长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "单台风机额定功率不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("单台风机额定功率")
    private BigDecimal dtfjedgl;

    @NotBlank(message = "送风方式不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$|^9$", message = "送风方式格式错误", groups = BasicCheck.class)
    @ApiModelProperty("送风方式")
    private String sffs;

    @Digits(integer = 10, fraction = 3, message = "单台风机实测风量长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "单台风机实测风量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("单台风机实测风量")
    private BigDecimal dtfjscfl;

    @Digits(integer = 10, fraction = 3, message = "单台风机轴功率长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "单台风机轴功率不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("单台风机轴功率")
    private BigDecimal dtfjzgl;

    @Digits(integer = 10, fraction = 3, message = "总风量长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "总风量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("总风量")
    private BigDecimal zfl;

    @Digits(integer = 10, fraction = 3, message = "单位通风量长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "单位通风量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("单位通风量")
    private BigDecimal dwtfl;

    @Digits(integer = 10, fraction = 3, message = "实测系统阻力长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "实测系统阻力不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("实测系统阻力")
    private BigDecimal scxtzl;

    @Digits(integer = 10, fraction = 3, message = "总耗电量长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @Positive(message = "总耗电量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("总耗电量")
    private BigDecimal zhdl;

    @Positive(message = "作业时气温不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "作业时气温长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "作业时气温")
    private BigDecimal zysqw;

    @Positive(message = "作业时气湿不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "作业时气湿长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "作业时气湿")
    private BigDecimal zysqs;

    @Positive(message = "作业时气温不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "作业时气温长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "作业时气温")
    private BigDecimal tfsc;

    @NotNull(message = "作业前平均粮温不能为空", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "作业前平均粮温长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty("作业前平均粮温")
    private BigDecimal zyqpjlw;

    @Digits(integer = 10, fraction = 3, message = "结束后平均粮温长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @NotNull(message = "结束后平均粮温不能为空", groups = BasicCheck.class)
    @TargetIsBefore(target = "#zyqpjlw", message = "结束后平均粮温不能高于作业前平均粮温", groups = BasicCheck.class)
    @ApiModelProperty("结束后平均粮温")
    private BigDecimal jshpjlw;

    @Positive(message = "降温幅度不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "降温幅度长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty("降温幅度")
    private BigDecimal jwfd;

    @Positive(message = "吨量降温能耗不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "吨量降温能耗长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty("吨量降温能耗")
    private BigDecimal dljwnh;

    @Positive(message = "失水率不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "失水率长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty("失水率")
    private BigDecimal ssl;

    @StringSize(max = 128, message = "保水效果评价结果长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("保水效果评价结果")
    private String bsxgpjjg;

    @StringSize(max = 128, message = "通风降温均匀性评价整仓长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("通风降温均匀性评价整仓")
    private String tfjwjyxpjzc;

    @StringSize(max = 128, message = "通风降温均匀性评价上层长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("通风降温均匀性评价上层")
    private String tfjwjyxpjsc;

    @StringSize(max = 128, message = "通风降温均匀性评价中间层长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("通风降温均匀性评价中间层")
    private String tfjwjyxpjzjc;

    @StringSize(max = 128, message = "通风降温均匀性评价下层长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("通风降温均匀性评价下层")
    private String tfjwjyxpjxc;

    @Digits(integer = 10, fraction = 3, message = "作业前平均水分长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @NotNull(message = "作业前平均水分不能为空", groups = BasicCheck.class)
    @ApiModelProperty("作业前平均水分")
    private BigDecimal zyqpjsf;

    @Digits(integer = 10, fraction = 3, message = "作业后平均水分长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @NotNull(message = "作业后平均水分不能为空", groups = BasicCheck.class)
    @TargetIsBefore(target = "#zyqpjsf", message = "作业后平均水分不能高于作业前平均水分", groups = BasicCheck.class)
    @Positive(message = "作业后平均水分不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("作业后平均水分")
    private BigDecimal zyhpjsf;

    @Positive(message = "降水幅度不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "降水幅度长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty("降水幅度")
    private BigDecimal jsfd;

    @Positive(message = "吨粮降水能耗不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 10, fraction = 3, message = "吨粮降水能耗长度不正确 Decimal(10,3)", groups = BasicCheck.class)
    @ApiModelProperty("吨粮降水能耗")
    private BigDecimal dljsnh;

    @StringSize(max = 128, message = "通风降水均匀性分析整仓长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("通风降水均匀性分析整仓")
    private String tfjsjyxfxzc;

    @StringSize(max = 128, message = "通风降水均匀性分析上层长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("通风降水均匀性分析上层")
    private String tfjsjyxfxsc;

    @StringSize(max = 128, message = "通风降水均匀性分析下层长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("通风降水均匀性分析下层")
    private String tfjsjyxfxxc;

    @StringSize(max = 128, message = "通风降水均匀性分析中间层长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("通风降水均匀性分析中间层")
    private String tfjsjyxfxzjc;

    @StringSize(max = 64, message = "通风作业负责人长度不能超过64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("通风作业负责人")
    private String tfzyfzr;

    @StringSize(max = 128, message = "通风作业人员长度不能超过128", groups = BasicCheck.class)
    @PersonNameRule(split = true, groups = BasicCheck.class, splitSign = COMMA,
            message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等,多个人员使用,分割")
    @ApiModelProperty("通风作业人员")
    private String tfzyry;


    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "仓房(或油罐)编码不存在", groups = CorrectCheck.class)
    @DataBindWarehouse(sourceField = "#cfdm")
    @DataBindTank(sourceField = "#cfdm")
    @ApiModelProperty(value = "仓房(或油罐)编码名称", hidden = true)
    private String cfdmName;

    @Override
    public String buildId() {
        return tfzydh;
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
