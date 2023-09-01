package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindGranary;
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
 * @description 货位API操作参数
 * @date 2022/10/20 19:27
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "货位API操作参数")
public class CargoApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "货位代码必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "货位代码必须以廒间代码开始", target = "#ajdm", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("货位代码")
    private String hwdm;

    @NotBlank(message = "货位名称必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "货位名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("货位名称")
    private String hwmc;

    @NotBlank(message = "廒间代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{10}$", message = "廒间代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("廒间代码")
    private String ajdm;

    @NotNull(message = "货位启用日期必填", groups = BasicCheck.class)
    @ApiModelProperty("货位启用日期")
    private LocalDate hwqyrq;

    @Digits(integer = 20, fraction = 6, message = "货位容量精度Decimal(8,4)", groups = BasicCheck.class)
    @ApiModelProperty("货位容量")
    private BigDecimal hwrl;

    @StringSize(max = 128, message = "保管单位最大长度128", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^.{0,128}$", message = "保管单位格式错误", groups = BasicCheck.class)
    @ApiModelProperty("保管单位")
    private String bgdw;

    @StringSize(max = 128, message = "保管员最大长度128", groups = BasicCheck.class)
    @PersonNameRule(split = true, groups = BasicCheck.class,
            message = "人员姓名为中文，且不允许填写:暂无，无，空测试等,多个人员使用|分割")
    @ApiModelProperty("保管员")
    private String bgy;


    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "廒间代码不存在", groups = CorrectCheck.class)
    @DataBindGranary(sourceField = "#ajdm")
    @ApiModelProperty(value = "廒间代码名称", hidden = true)
    private String ajdmName;

    @Override
    public String buildId() {
        return hwdm;
    }

    @Override
    public String buildCompanyId() {
        return ajdm.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return ajdm.substring(0, 21);
    }
}
