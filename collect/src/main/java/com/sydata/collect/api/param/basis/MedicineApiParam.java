package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
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

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * @author lzq
 * @description 药剂API操作参数
 * @date 2022/10/20 20:21
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "药剂API操作参数")
public class MedicineApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "药剂编号必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "药剂编号必须以库区代码开始", target = "#kqdm", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{6}$", message = "药剂编号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("药剂编号")
    private String yjbh;

    @NotBlank(message = "库区代码必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "库区代码必须以单位代码开始", target = "#dwdm", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区代码")
    private String kqdm;

    @NotNull(message = "采购日期必填", groups = BasicCheck.class)
    @ApiModelProperty("采购日期")
    private LocalDate cgrq;

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("单位代码")
    private String dwdm;

    @NotBlank(message = "库区名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "库区名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("库区名称")
    private String kqmc;

    @NotBlank(message = "药剂名称必填", groups = BasicCheck.class)
    @StringSize(max = 20, message = "药剂名称最大长度20", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,20}$", message = "药剂名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("药剂名称")
    private String yjmc;

    @NotBlank(message = "包装物必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$|^3$", message = "包装物不存在", groups = BasicCheck.class)
    @ApiModelProperty("包装物")
    private String bzw;

    @NotBlank(message = "型号规格必填", groups = BasicCheck.class)
    @StringSize(max = 256, message = "药剂名称最大长度256", groups = BasicCheck.class)
    @ApiModelProperty("型号规格")
    private String ggxh;

    @StringSize(max = 1000, message = "安全使用说明书最大长度1000", groups = BasicCheck.class)
    @ApiModelProperty("安全使用说明书")
    private String aqsysms;

    @NotBlank(message = "生产厂家必填", groups = BasicCheck.class)
    @StringSize(max = 40, message = "生产厂家最大长度40", groups = BasicCheck.class)
    @ApiModelProperty("生产厂家")
    private String sccj;

    @StringSize(max = 40, message = "采购来源最大长度40", groups = BasicCheck.class)
    @ApiModelProperty("采购来源")
    private String cgly;

    @StringSize(max = 200, message = "储存条件最大长度200", groups = BasicCheck.class)
    @ApiModelProperty("储存条件")
    private String cctj;

    @NotBlank(message = "储存地点必填", groups = BasicCheck.class)
    @Pattern(regexp = "^1$|^2$", message = "储存地点不存在", groups = BasicCheck.class)
    @ApiModelProperty("储存地点")
    private String ccdd;

    @StringSize(max = 1000, message = "包装物处理方式最大长度1000", groups = BasicCheck.class)
    @ApiModelProperty("包装物处理方式")
    private String bzwclfs;

    @StringSize(max = 1000, message = "残渣处理方式最大长度1000", groups = BasicCheck.class)
    @ApiModelProperty("残渣处理方式")
    private String czclfs;

    @NotBlank(message = "保质期必填", groups = BasicCheck.class)
    @StringSize(max = 20, message = "保质期最大长度20", groups = BasicCheck.class)
    @ApiModelProperty("保质期")
    private String bzq;

    @NotNull(message = "库存数量必填", groups = BasicCheck.class)
    @Positive(message = "库存数不能小于等于0", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "库存数量精度Decimal(20,3)", groups = BasicCheck.class)
    @ApiModelProperty("库存数量")
    private BigDecimal kcsl;

    @Pattern(regexp = "^1$|^2$|^3$", message = "库存数量单位不存在", groups = BasicCheck.class)
    @ApiModelProperty("库存数量单位")
    private String kcsldw;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区代码名称", hidden = true)
    private String kqdmName;

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位代码名称", hidden = true)
    private String dwdmName;

    @Override
    public String buildId() {
        return new StringJoiner(DASH).add(yjbh).add(kqdm).add(cgrq.toString()).toString();
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
