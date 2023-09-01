
package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.organize.annotation.DataBindRegion;
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
 * @description 库区API操作参数
 * @date 2022/10/19 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "库区API操作参数")
public class StockHouseApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "库区代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "库区代码必须以单位代码开始", target = "#dwdm", groups = BasicCheck.class)
    @ApiModelProperty("库区代码")
    private String kqdm;

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("单位代码")
    private String dwdm;

    @NotBlank(message = "库区名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "库区名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @NotBlank(message = "库区地址必填", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,512}$", message = "库区地址格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "库区地址")
    private String kqdz;

    @NotBlank(message = "行政区划代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @NotBlank(message = "库区产权必填", groups = BasicCheck.class)
    @StringSize(max = 1, min = 1, message = "库区产权只有1位数", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$|^9$", message = "库区产权不存在", groups = BasicCheck.class)
    @ApiModelProperty(value = "库区产权")
    private String kqcq;

    @NotNull(message = "有效仓容不能为空", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "有效仓容精度Decimal(20,6)", groups = BasicCheck.class)
    @DecimalMin(value = "0.000000", message = "有效仓容最小为0.000000", groups = BasicCheck.class)
    @ApiModelProperty(value = "有效仓容（单位：吨）")
    private BigDecimal yxcr;

    @NotNull(message = "有效罐容不能为空", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "有效罐容精度Decimal(20,6)", groups = BasicCheck.class)
    @DecimalMin(value = "0.000000", message = "有效罐容最小为0.000000", groups = BasicCheck.class)
    @ApiModelProperty(value = "有效罐容（单位：吨）")
    private BigDecimal yxgr;

    @NotNull(message = "占地面积不能为空", groups = BasicCheck.class)
    @Positive(message = "占地面积不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "占地面积精度Decimal(20,6)", groups = BasicCheck.class)
    @ApiModelProperty(value = "占地面积（指库区土地面积，单位：平方米）")
    private BigDecimal zdmj;

    @NotNull(message = "仓房数不能为空", groups = BasicCheck.class)
    @Max(value = Integer.MAX_VALUE, message = "仓房数超过最大值2147483647", groups = BasicCheck.class)
    @Min(value = 0, message = "仓房数最小是0", groups = BasicCheck.class)
    @ApiModelProperty(value = "仓房数")
    private Integer cfs;

    @NotNull(message = "油罐数不能为空", groups = BasicCheck.class)
    @Max(value = Integer.MAX_VALUE, message = "油罐数超过最大值2147483647", groups = BasicCheck.class)
    @Min(value = 0, message = "油罐数最小是0", groups = BasicCheck.class)
    @ApiModelProperty(value = "油罐数")
    private Integer ygs;

    @NotNull(message = "库区经度不能为空", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "库区经度精度Decimal(20,6)", groups = BasicCheck.class)
    @DecimalMin(value = "73.33", message = "经度不在中国范围以内,大于73.33", groups = BasicCheck.class)
    @DecimalMax(value = "135.05", message = "经度不在中国范围以内,小于135.05", groups = BasicCheck.class)
    private BigDecimal jd;

    @NotNull(message = "库区纬度不能为空", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "库区纬度精度Decimal(20,6)", groups = BasicCheck.class)
    @DecimalMin(value = "3.51", message = "纬度不在中国范围以内,大于3.51", groups = BasicCheck.class)
    @DecimalMax(value = "53.33", message = "纬度不在中国范围以内,小于53.33", groups = BasicCheck.class)
    @ApiModelProperty(value = "库区纬度")
    private BigDecimal wd;

    @StringSize(max = 36, min = 36, message = "海康区域ID只能是36位", groups = BasicCheck.class)
    @ApiModelProperty(value = "海康区域ID")
    private String hkRegionId;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位代码名称", hidden = true)
    private String dwdmName;

    @JsonIgnore
    @NotBlank(message = "行政区划代码不存在", groups = CorrectCheck.class)
    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty(value = "行政区划代码名称", hidden = true)
    private String xzqhdmName;

    @Override
    public String buildId() {
        return kqdm;
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
