package com.sydata.collect.api.param.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
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

import static cn.hutool.core.lang.RegexPool.EMAIL;

/**
 * @author lzq
 * @description 项目信息API操作参数
 * @date 2022/10/20 19:27
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "项目信息API操作参数")
public class ProjectApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "项目代码必填", groups = BasicCheck.class)
    @StringSize(max = 64, message = "项目代码最大长度64", groups = BasicCheck.class)
    @ApiModelProperty(value = "项目代码")
    private String xmdm;

    @NotBlank(message = "项目名称必填", groups = BasicCheck.class)
    @StringSize(max = 64, message = "项目名称最大长度64", groups = BasicCheck.class)
    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    @NotBlank(message = "行政区划代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @NotBlank(message = "年份必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{4}$", message = "年份格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "年份")
    private String nf;

    @NotBlank(message = "项目类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1|2|3|4|5|6|7|8$", message = "项目类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "项目类型")
    private String xmlx;

    @NotBlank(message = "建设内容及规模必填", groups = BasicCheck.class)
    @StringSize(max = 1024, message = "建设内容及规模最大长度1024", groups = BasicCheck.class)
    @ApiModelProperty(value = "建设内容及规模")
    private String jsnr;

    @NotNull(message = "拟开工时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "拟开工时间")
    private LocalDate nkgsj;

    @NotNull(message = "拟建成时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "拟建成时间")
    private LocalDate njcsj;

    @Pattern(regexp = "^1|2|3|4$", message = "建设状态格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "建设状态；1:已立项未开工、2:建设中，3:已验收，4:已取消")
    private String jszt;

    @NotNull(message = "申报日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "申报日期")
    private LocalDate sbrq;

    @StringSize(max = 128, message = "审批文号最大长度128", groups = BasicCheck.class)
    @ApiModelProperty(value = "审批文号")
    private String spwh;

    @NotBlank(message = "项目 (法人) 单位必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "项目 (法人) 单位格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "项目 (法人) 单位（统一社会信用代码）")
    private String xmdw;

    @NotBlank(message = "法定代表人证照类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1|2|3|4|5|6$", message = "法定代表人证照类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "法定代表人证照类型")
    private String fddbrzzlx;

    @NotBlank(message = "法定代表人证照号码必填", groups = BasicCheck.class)
    @StringSize(max = 32, message = "法定代表人证照号码最大长度32", groups = BasicCheck.class)
    @ApiModelProperty(value = "法定代表人证照号码")
    private String fddbrzzhm;

    @NotBlank(message = "联系人必填", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "联系人姓名为中文，且不允许填写:暂无，无，空,测试等")
    @StringSize(max = 16, message = "联系人最大长度16", groups = BasicCheck.class)
    @ApiModelProperty(value = "联系人")
    private String lxr;

    @StringSize(max = 16, message = "联系方式最大长度16", groups = BasicCheck.class)
    @ApiModelProperty(value = "联系方式")
    private String lxfs;

    @NotBlank(message = "电子邮箱必填", groups = BasicCheck.class)
    @StringSize(max = 32, message = "电子邮箱最大长度32", groups = BasicCheck.class)
    @Pattern(regexp = EMAIL, message = "电子邮箱格式不对", groups = BasicCheck.class)
    @ApiModelProperty(value = "电子邮箱")
    private String dzyx;

    @NotBlank(message = "建设地点必填", groups = BasicCheck.class)
    @StringSize(max = 128, message = "建设地点最大长度128", groups = BasicCheck.class)
    @ApiModelProperty(value = "建设地点")
    private String jsdd;

    @NotNull(message = "总投资(万元)必填", groups = BasicCheck.class)
    @Positive(message = "总投资（万元）不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "总投资（万元）")
    private BigDecimal ztz;

    @NotNull(message = "固定资产投资必填", groups = BasicCheck.class)
    @Min(value = 0, message = "总投资（万元）不能小于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "固定资产投资（万元）")
    private BigDecimal gdzctz;

    @NotNull(message = "中央财政资金必填", groups = BasicCheck.class)
    @Min(value = 0, message = "中央财政资金不能小于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "中央财政资金（万元）")
    private BigDecimal zyczzj;

    @NotNull(message = "省财政资金必填", groups = BasicCheck.class)
    @Min(value = 0, message = "省财政资金（万元)不能小于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "省财政资金（万元）")
    private BigDecimal sczzj;

    @NotNull(message = "市财政资金必填", groups = BasicCheck.class)
    @Min(value = 0, message = "市财政资金（万元）不能小于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "市财政资金（万元）")
    private BigDecimal sczzj01;

    @NotNull(message = "银行贷款必填", groups = BasicCheck.class)
    @Min(value = 0, message = "银行贷款（万元）不能小于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "银行贷款（万元）")
    private BigDecimal yhdk;

    @NotNull(message = "股票债券必填", groups = BasicCheck.class)
    @Min(value = 0, message = "股票债券（万元）不能小于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "股票债券（万元）")
    private BigDecimal gpzq;

    @NotNull(message = "其他资金必填", groups = BasicCheck.class)
    @Min(value = 0, message = "其他资金（万元）不能小于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "其他资金（万元）")
    private BigDecimal qtzj;

    @NotNull(message = "项目地址经度必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "项目地址经度")
    private BigDecimal xmdzjd;

    @NotNull(message = "项目地址纬度必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "总投资(万元) Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty(value = "项目地址纬度")
    private BigDecimal zmdzwd;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "行政区划代码不存在", groups = CorrectCheck.class)
    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(value = "行政区划代码名称", hidden = true)
    private String xzqhdmName;

    @Override
    public String buildId() {
        return xmdm;
    }
}
