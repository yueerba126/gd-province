package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

import static jodd.util.StringPool.DASH;

/**
 * @author czx
 * @description 账面库存API操作参数
 * @date 2022/10/20 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "账面库存API操作参数")
public class MonthStockApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "库区代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @NotBlank(message = "收获年度必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{4}$", message = "收获年度格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "年度")
    private String nd;

    @NotBlank(message = "月份必填", groups = BasicCheck.class)
    @Pattern(regexp = "^(0?[1-9]|1[012])$", message = "月份格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "月份")
    private String yf;

    @NotNull(message = "期初数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "期初数量 Decimal(20,3)", groups = BasicCheck.class)
    @Min(value = 0, message = "期初数量不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "期初数量")
    private BigDecimal qcsl;

    @NotNull(message = "本期收入数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "本期收入数量 Decimal(20,3)", groups = BasicCheck.class)
    @Min(value = 0, message = "本期收入数量不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "本期收入数量")
    private BigDecimal bqsrsl;

    @NotNull(message = "本期支出数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "本期支出数量 Decimal(20,3)", groups = BasicCheck.class)
    @Min(value = 0, message = "本期支出数量不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "本期支出数量")
    private BigDecimal bqzcsl;

    @NotNull(message = "期末数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "期末数量 Decimal(20,3)", groups = BasicCheck.class)
    @Min(value = 0, message = "期末数量不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "期末数量")
    private BigDecimal qmye;

    @NotNull(message = "月结标志必填", groups = BasicCheck.class)
    @Min(value = 0, message = "月结标志最小是0", groups = BasicCheck.class)
    @Max(value = 1, message = "月结标志最大是1", groups = BasicCheck.class)
    @ApiModelProperty(value = "月结标志")
    private Integer yjbz;

    @NotNull(message = "业务日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "业务日期")
    private LocalDate ywrq;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "粮食品种代码不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse
    @ApiModelProperty(value = "库区名称", hidden = true)
    private String kqmc;

    @Override
    public String buildId() {
        return new StringJoiner(DASH).add(kqdm).add(lspzdm).add(nd).add(yf).toString();
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
