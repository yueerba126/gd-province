package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
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

/**
 * @author lzq
 * @description 廒间API操作参数
 * @date 2022/10/20 19:21
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "廒间API操作参数")
public class GranaryApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "廒间代码必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "廒间代码必须以仓房(或油罐)编码开始", target = "#cfbh", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{10}$", message = "廒间代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("廒间代码")
    private String ajdh;

    @NotBlank(message = "廒间名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "廒间名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("廒间名称")
    private String ajmc;

    @NotBlank(message = "仓房(或油罐)编码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{7}$", message = "仓房(或油罐)编码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("仓房(或油罐)编码")
    private String cfbh;

    @Min(message = "廒间长度不能小于0", value = 0, groups = BasicCheck.class)
    @NotNull(message = "廒间长度必填", groups = BasicCheck.class)
    @Digits(integer = 8, fraction = 4, message = "廒间长度精度Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty("廒间长度")
    private BigDecimal ajcd;

    @Min(message = "廒间宽度不能小于0", value = 0, groups = BasicCheck.class)
    @NotNull(message = "廒间宽度必填", groups = BasicCheck.class)
    @Digits(integer = 8, fraction = 4, message = "廒间宽度精度Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty("廒间宽度")
    private BigDecimal ajkd;

    @Positive(message = "廒间高度不能小于等于0", groups = BasicCheck.class)
    @NotNull(message = "廒间高度必填", groups = BasicCheck.class)
    @Digits(integer = 8, fraction = 4, message = "廒间高度精度Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty("廒间高度")
    private BigDecimal ajgd;

    @NotNull(message = "廒间设计仓容必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "廒间设计仓容Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "廒间设计仓容不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("廒间设计仓容")
    private BigDecimal ajsjcr;

    @ApiModelProperty("廒间启用日期")
    private LocalDate ajqyrq;

    @NotBlank(message = "廒间状态必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$|^5$|^6$|^9$", message = "廒间状态格式错误", groups = BasicCheck.class)
    @ApiModelProperty("廒间状态")
    private String ajzt;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "仓房(或油罐)编码不存在", groups = CorrectCheck.class)
    @DataBindWarehouse(sourceField = "#cfbh")
    @DataBindTank(sourceField = "#cfbh")
    @ApiModelProperty(value = "仓房(或油罐)编码名称", hidden = true)
    private String cfbhName;

    @JsonIgnore
    @DataBindWarehouse(sourceField = "#cfbh", dataValue = "#cflxdm")
    @ApiModelProperty(value = "仓房类型代码", hidden = true)
    private String cflxdm;

    @JsonIgnore
    @DataBindWarehouse(sourceField = "#cfbh", dataValue = "#cnc")
    @ApiModelProperty(value = "仓房仓内长", hidden = true)
    private BigDecimal cfcnc;

    @JsonIgnore
    @DataBindWarehouse(sourceField = "#cfbh", dataValue = "#cnk")
    @ApiModelProperty(value = "仓房仓内宽", hidden = true)
    private BigDecimal cfcnk;

    @Override
    public String buildId() {
        return ajdh;
    }

    @Override
    public String buildCompanyId() {
        return cfbh.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return cfbh.substring(0, 21);
    }
}
