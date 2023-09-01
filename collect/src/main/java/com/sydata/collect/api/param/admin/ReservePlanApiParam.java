package com.sydata.collect.api.param.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetNotEmpty;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindDict;
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
 * @description 储备计划API操作参数
 * @date 2022/10/20 19:27
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "储备计划API操作参数")
public class ReservePlanApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "计划单号必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[a-zA-Z0-9]{1,21}$", message = "计划单号为数字和字母组成，最大长度21", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划单号")
    private String jhdh;

    @StringSize(max = 128, message = "计划文号最大长度128", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @StringSize(max = 256, message = "计划名称最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @Pattern(regexp = "^\\d{4}$", message = "计划年度错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @StringSize(max = 128, message = "制定计划单位最大长度128", groups = BasicCheck.class)
    @ApiModelProperty(value = "制定计划单位")
    private String jhzddw;

    @ApiModelProperty(value = "计划下达时间")
    private LocalDate jhxdsj;

    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @Pattern(regexp = "^$|^01|02|03|04|05|06$", message = "粮食等级代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @Digits(integer = 20, fraction = 3, message = "数量（吨） Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "数量（吨）不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "数量（吨）")
    private BigDecimal sl;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/


    @JsonIgnore
    @TargetNotEmpty(message = "粮食品种代码不存在", target = "#lspzdm", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @Override
    public String buildId() {
        return jhdh;
    }
}
