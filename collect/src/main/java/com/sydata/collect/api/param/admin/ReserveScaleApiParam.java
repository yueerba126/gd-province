package com.sydata.collect.api.param.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
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

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.StringJoiner;

import static jodd.util.StringPool.DASH;

/**
 * @author lzq
 * @description 储备规模API操作参数
 * @date 2022/10/20 19:27
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备规模API操作参数")
public class ReserveScaleApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "年份必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{4}$", message = "年份格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "年份")
    private String nf;

    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @NotBlank(message = "承储企业单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "承储企业单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "承储企业")
    private String ccqy;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误")
    @ApiModelProperty(value = "粮食品种")
    private String ylpz;

    @NotBlank(message = "粮食性质代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食性质")
    private String ylxz;

    @Digits(integer = 20, fraction = 6, message = "数量（吨） Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "储备规模计划数量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "储备规模计划数量")
    private BigDecimal ylcbgmjhsl;

    @StringSize(max = 128, message = "储备规模计划文号最大长度128", groups = BasicCheck.class)
    @ApiModelProperty(value = "储备规模计划文号")
    private String cbgmjhwh;

    @StringSize(max = 256, message = "备注最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "备注")
    private String remarks;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/


    @JsonIgnore
    @NotBlank(message = "承储企业单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#ccqy")
    @ApiModelProperty(value = "承储企业单位代码名称", hidden = true)
    private String dwdmName;

    @JsonIgnore
    @TargetNotEmpty(message = "行政区划代码不存在", target = "#xzqhdm", groups = CorrectCheck.class)
    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(value = "行政区划代码名称", hidden = true)
    private String xzqhdmName;

    @JsonIgnore
    @NotBlank(message = "粮食品种代码无对应国标码", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#ylpz", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String ylpzName;

    @Override
    public String buildId() {
        return new StringJoiner(DASH).add(ccqy).add(nf).add(ylpz).add(ylxz).toString();
    }
}
