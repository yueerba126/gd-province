package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindStockHouse;
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
 * @description 油罐API操作参数
 * @date 2022/10/20 19:35
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "油罐API操作参数")
public class TankApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "油罐代码必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "油罐代码必须以库区代码开始", target = "#kqdm", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{7}$", message = "油罐代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("油罐代码")
    private String ygdm;

    @NotBlank(message = "油罐名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "油罐名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("油罐名称")
    private String ygmc;

    @NotBlank(message = "库区代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区代码")
    private String kqdm;

    @NotNull(message = "罐容不能为空", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 6, message = "罐容精度Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "罐容不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("罐容")
    private BigDecimal gr;

    @NotNull(message = "建造时间必填", groups = BasicCheck.class)
    @ApiModelProperty("建造时间")
    private LocalDate jzsj;

    @NotBlank(message = "油罐及附属设施是否完好必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0|1|9]$", message = "油罐及附属设施是否完好不存在", groups = BasicCheck.class)
    @ApiModelProperty("油罐及附属设施是否完好")
    private String ygjfssssfwh;

    @NotBlank(message = "有无加热装置必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0|1|9]$", message = "有无加热装置不存在", groups = BasicCheck.class)
    @ApiModelProperty("有无加热装置")
    private String ywjrzz;

    @NotBlank(message = "油罐类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$|^5$", message = "油罐类型不存在", groups = BasicCheck.class)
    @ApiModelProperty("油罐类型")
    private String yglx;

    @Digits(integer = 20, fraction = 6, message = "罐内直径Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "罐内直径不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("罐内直径")
    private BigDecimal gnzj;

    @Digits(integer = 20, fraction = 6, message = "罐内高度Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "罐内高度不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty("罐内高度")
    private BigDecimal gngd;

    @Pattern(regexp = "^[0|1|9]$", message = "检定方式不存在", groups = BasicCheck.class)
    @ApiModelProperty("检定方式")
    private String jdfs;

    @Pattern(regexp = "^1$|^2$|^9$", message = "焊接方式不存在", groups = BasicCheck.class)
    @ApiModelProperty("焊接方式")
    private String hjfs;

    @NotBlank(message = "油罐状态必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$|^4$|^5$|^6$|^9$", message = "油罐状态不存在", groups = BasicCheck.class)
    @ApiModelProperty("油罐状态")
    private String ygzt;

    @StringSize(max = 256, message = "设计单位最大长度256", groups = BasicCheck.class)
    @ApiModelProperty("设计单位")
    private String sjdw;

    @StringSize(max = 256, message = "建设单位最大长度256", groups = BasicCheck.class)
    @ApiModelProperty("建设单位")
    private String jsdw;

    @StringSize(max = 256, message = "监理单位最大长度256", groups = BasicCheck.class)
    @ApiModelProperty("监理单位")
    private String jldw;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse
    @ApiModelProperty(value = "库区代码名称", hidden = true)
    private String kqdmName;

    @JsonIgnore
    @Null(message = "同一库区下的仓房代码与油罐代码不能冲突", groups = CorrectCheck.class)
    @DataBindWarehouse(sourceField = "#ygdm")
    @ApiModelProperty(value = "仓房代码名称", hidden = true)
    private String cfdmName;

    @Override
    public String buildId() {
        return ygdm;
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
