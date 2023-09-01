package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetNotEmpty;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
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

/**
 * @author czx
 * @description 倒仓信息API操作参数
 * @date 2022/10/20 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "倒仓信息API操作参数")
public class TransferBarnApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "倒仓单号必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "倒仓单号：倒出库区代码+倒仓日期（yyyyMMdd）+4 位顺序号组成", target = {"#dcdw", "#dcrq"}, groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{15}$", message = "倒仓单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "倒仓单号：倒仓库区代码+倒仓日期（yyyyMMdd）+4 位顺序号组成")
    private String dcdh;

    @NotBlank(message = "倒仓类型必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^0$|^1$", message = "倒仓类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "倒仓类型:0:倒仓 1:移库")
    private String dclx;

    @StringSize(max = 32, message = "倒仓计划文件编号最大长度为32", groups = BasicCheck.class)
    @ApiModelProperty(value = "倒仓计划文件编号")
    private String dcjhwjbh;

    @StringSize(max = 32, message = "通知单号最大长度为32", groups = BasicCheck.class)
    @ApiModelProperty(value = "通知单号")
    private String tzdh;

    @NotBlank(message = "倒出库区必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "倒出库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "倒出库区 关联表 E.2 库区代码")
    private String dcdw;

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "倒入库区代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "倒入库区 关联表 E.2 库区代码")
    private String drdw;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @NotBlank(message = "倒出货位编码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "倒出货位编码格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "倒出货位编码必须以倒出库区开始", target = {"#dcdw"}, groups = BasicCheck.class)
    @ApiModelProperty(value = "倒出货位编码")
    private String dchwdm;

    @NotBlank(message = "倒入货位编码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "倒入货位编码格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "倒入货位编码必须以倒入库区开始", target = {"#drdw"}, groups = BasicCheck.class)
    @ApiModelProperty(value = "倒入货位编码")
    private String drhwdm;

    @NotNull(message = "倒仓日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "倒仓日期")
    private LocalDate dcrq;

    @NotNull(message = "倒仓数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "倒仓数量 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "倒仓数量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "倒仓数量")
    private BigDecimal dcsl;

    @Pattern(regexp = "^$|^1$|^2$|^3$|^9$", message = "包装物格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "包装物")
    private String bzw;

    @Min(value = 0, message = "标准包件数不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "标准包件数")
    private Integer bzbjs;

    @StringSize(max = 256, message = "装卸作业单位最大长度为256", groups = BasicCheck.class)
    @ApiModelProperty(value = "装卸作业单位")
    private String zxzydw;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "倒出库区代码不存在", groups = CorrectCheck.class)
    @ApiModelProperty(value = "倒出库区名称", hidden = true)
    @DataBindStockHouse(sourceField = "#dcdw")
    private String dcdwName;

    @JsonIgnore
    @NotBlank(message = "粮食品种代码无对应国标码", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @JsonIgnore
    @NotBlank(message = "倒出货位代码不存在", groups = CorrectCheck.class)
    @ApiModelProperty(value = "倒出货位名称", hidden = true)
    @DataBindCargo(sourceField = "#dchwdm")
    private String dchwdmName;

    @JsonIgnore
    @NotBlank(message = "倒入货位代码不存在", groups = CorrectCheck.class)
    @ApiModelProperty(value = "倒入货位名称", hidden = true)
    @DataBindCargo(sourceField = "#drhwdm")
    private String drhwdmName;

    @JsonIgnore
    @TargetNotEmpty(message = "倒入库区代码不存在", target = "#drdw", groups = CorrectCheck.class)
    @ApiModelProperty(value = "倒入库区名称", hidden = true)
    @DataBindStockHouse(sourceField = "#drdw")
    private String drdwName;

    @Override
    public String buildId() {
        return dcdh;
    }

    @Override
    public String buildCompanyId() {
        return dcdw.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return dcdw;
    }
}
