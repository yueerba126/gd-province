package com.sydata.collect.api.param.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * @author ：xy
 * @description：安全生产检查接收对象Param
 * @version: 1.0
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "安全生产API操作参数")
public class SafetyCheckApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "库区代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区代码")
    private String kqdm;

    @NotBlank(message = "单位代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("单位代码")
    private String dwdm;

    @NotBlank(message = "地点不能为空", groups = BasicCheck.class)
    @StringSize(max = 128, message = "地点长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("地点")
    private String dd;

    @NotBlank(message = "风险点编码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{18}$", message = "风险点编码为18位数字，格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "风险点编码前6位行政区划代码需与库区行政区划代码一致,具体参照文档规则", target = "#kqdmXzqhdm",
            groups = CorrectCheck.class)
    @ApiModelProperty("风险点编码")
    private String fxdbm;

    @NotBlank(message = "识别人不能为空", groups = BasicCheck.class)
    @StringSize(max = 32, message = "识别人长度不能超过32", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("识别人")
    private String sbr;

    @NotBlank(message = "环节/部位不能为空", groups = BasicCheck.class)
    @StringSize(max = 128, message = "环节/部位长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("环节/部位")
    private String hjbw;

    @NotBlank(message = "风险信息不能为空", groups = BasicCheck.class)
    @StringSize(max = 256, message = "风险信息长度不能超过256", groups = BasicCheck.class)
    @ApiModelProperty("风险信息")
    private String fxxx;

    @Pattern(regexp = "^$|^[1|2|3|4|5|6|7|8]$", message = "风险类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty("风险类型")
    private String fxlx;

    @Pattern(regexp = "^$|^[1|2|3|4]$", message = "风险分级格式错误", groups = BasicCheck.class)
    @ApiModelProperty("风险分级")
    private String fxfj;

    @StringSize(max = 1000, message = "风险管控措施长度不能超过1000", groups = BasicCheck.class)
    @ApiModelProperty("风险管控措施")
    private String fxglcs;

    @JsonProperty("Yhxx")
    @NotBlank(message = "隐患信息必填", groups = BasicCheck.class)
    @StringSize(max = 256, message = "隐患信息长度不能超过256", groups = BasicCheck.class)
    @ApiModelProperty("隐患信息")
    private String yhxx;

    @NotBlank(message = "隐患排查信息必填", groups = BasicCheck.class)
    @StringSize(max = 1000, message = "隐患排查信息长度不能超过1000", groups = BasicCheck.class)
    @ApiModelProperty("隐患排查信息")
    private String yhpcxx;

    @NotBlank(message = "隐患整改信息必填", groups = BasicCheck.class)
    @StringSize(max = 1000, message = "隐患整改信息长度不能超过1000", groups = BasicCheck.class)
    @ApiModelProperty("隐患整改信息")
    private String yhzgxx;

    @NotBlank(message = "隐患整改验收信息必填", groups = BasicCheck.class)
    @StringSize(max = 1000, message = "隐患整改验收信息长度不能超过1000", groups = BasicCheck.class)
    @ApiModelProperty("隐患整改验收信息")
    private String yhysxx;

    @NotBlank(message = "事故基本信息必填", groups = BasicCheck.class)
    @StringSize(max = 1000, message = "事故基本信息长度不能超过1000", groups = BasicCheck.class)
    @ApiModelProperty("事故基本信息")
    private String sgjbxx;

    @ApiModelProperty("整改时限")
    private LocalDate zgsx;

    @NotBlank(message = "责任单位必填", groups = BasicCheck.class)
    @StringSize(max = 128, message = "责任单位长度不能超过128", groups = BasicCheck.class)
    @ApiModelProperty("责任单位")
    private String zrdw;

    @StringSize(max = 32, message = "责任人长度不能超过32", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("责任人")
    private String zrr;

    @StringSize(max = 1024, message = "整改验收信息长度不能超过1024", groups = BasicCheck.class)
    @ApiModelProperty("整改验收信息")
    private String zgysxx;

    @StringSize(max = 32, message = "风险跟踪监管责任人长度不能超过32", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty("风险跟踪监管责任人")
    private String fxgzjgzrr;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse(sourceField = "#kqdm", dataValue = "#xzqhdm")
    @ApiModelProperty(value = "库区代码行政区划代码", hidden = true)
    private String kqdmXzqhdm;

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位代码名称", hidden = true)
    private String dwdmName;

    @Override
    public String buildId() {
        return new StringJoiner(DASH).add(kqdm).add(fxdbm).toString();
    }

    @Override
    public String buildCompanyId() {
        return dwdm;
    }

    @Override
    public String buildStockHouseId() {
        return kqdm;
    }
}
