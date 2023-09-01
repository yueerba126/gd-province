package com.sydata.collect.api.param.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.admin.annotation.DataBindRotationPlan;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzq
 * @description 轮换计划明细信息API操作参数
 * @date 2022/10/20 19:27
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "轮换计划明细信息API操作参数")
public class RotationPlanDtlApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "计划明细单号必填", groups = BasicCheck.class)
    @StringSize(max = 50, message = "计划明细单号最大长度50", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划明细单号 轮换计划单号+库区代码+四位顺序码组成")
    private String jhmxdh;

    @NotBlank(message = "轮换计划单号必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "轮换计划单号")
    private String lhjhdh;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误")
    @ApiModelProperty("粮食品种代码")
    private String lspzdm;

    @Pattern(regexp = "^$|^01|02|03|04|05|06$", message = "粮食等级代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @NotBlank(message = "粮食性质代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("粮食性质代码")
    private String lsxzdm;

    @NotBlank(message = "年份必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{4}$", message = "年份格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "收获年度")
    private String shnd;

    @NotBlank(message = "轮换货位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "轮换货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "轮换货位代码")
    private String lhhwdm;

    @NotNull(message = "轮换数量必填", groups = BasicCheck.class)
    @Positive(message = "轮换数量不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "轮换数量（吨） Decimal(20,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "轮换数量")
    private BigDecimal lhsl;

    @NotBlank(message = "轮换类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$", message = "轮换类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "轮换类型(1.轮出 2.轮入)")
    private String lhlx;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "粮食品种代码无对应国标码", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @JsonIgnore
    @NotBlank(message = "轮换货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo(sourceField = "#lhhwdm")
    @ApiModelProperty(value = "轮换货位名称", hidden = true)
    private String lhhwdmName;

    @JsonIgnore
    @NotBlank(message = "轮换计划不存在", groups = CorrectCheck.class)
    @DataBindRotationPlan(sourceField = "#lhjhdh")
    @ApiModelProperty(value = "轮换计划名称", hidden = true)
    private String lhjhdhName;

    @Override
    public String buildId() {
        return jhmxdh;
    }
}
