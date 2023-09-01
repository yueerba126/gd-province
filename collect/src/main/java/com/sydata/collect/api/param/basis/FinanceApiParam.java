package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.StringJoiner;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * @author lzq
 * @description 财务报表信息API参数
 * @date 2022/10/21 11:30
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "财务报表信息API参数")
public class FinanceApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @NotNull(message = "报表时间不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{4,8}$", message = "报表时间格式不正确", groups = BasicCheck.class)
    @Size(min = 4, max = 8, message = "报表时间长度为4-8", groups = BasicCheck.class)
    @ApiModelProperty(value = "报表时间")
    private String bbsj;

    @NotBlank(message = "报表名不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^01$|^02$|^03$", message = "报表名不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "报表名 01资产负债表 02现金流量表 03利润表")
    private String bbm;

    @NotNull(message = "指标序号不能为空", groups = BasicCheck.class)
    @Max(value = Integer.MAX_VALUE, message = "指标序号超过最大值2147483647", groups = BasicCheck.class)
    @Min(value = 1, message = "指标序号最小值1", groups = BasicCheck.class)
    @JsonProperty("Zbxh")
    @ApiModelProperty(value = "指标序号")
    private Integer zbxh;

    @NotBlank(message = "指标名称不能为空", groups = BasicCheck.class)
    @StringSize(max = 128, message = "指标名称最大长度为128", groups = BasicCheck.class)
    @ApiModelProperty(value = "指标名称")
    private String zbmc;

    @StringSize(max = 128, message = "指标值1最大长度为128", groups = BasicCheck.class)
    @ApiModelProperty(value = "指标值1")
    private String zbz1;

    @StringSize(max = 128, message = "指标值2最大长度为128", groups = BasicCheck.class)
    @ApiModelProperty(value = "指标值2")
    private String zbz2;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位代码名称", hidden = true)
    private String dwdmName;

    @JsonIgnore
    @DataBindDict(sourceField = "#bbm == '01' ? #zbxh : null", sourceFieldCombination = "zcfzb")
    @ApiModelProperty(value = "资产负债表指标名称")
    private String zcfzbzbmc;

    @JsonIgnore
    @DataBindDict(sourceField = "#bbm == '02' ? #zbxh : null", sourceFieldCombination = "xjllb")
    @ApiModelProperty(value = "现金流量表指标名称")
    private String xjllbzbmc;

    @JsonIgnore
    @DataBindDict(sourceField = "#bbm == '03' ? #zbxh : null", sourceFieldCombination = "lrb")
    @ApiModelProperty(value = "利润表指标名称")
    private String lrbzbmc;

    @Override
    public String buildId() {
        return new StringJoiner(DASH)
                .add(dwdm)
                .add(bbsj)
                .add(bbm)
                .add(String.valueOf(zbxh))
                .toString();
    }

    @Override
    public String buildCompanyId() {
        return dwdm;
    }
}
