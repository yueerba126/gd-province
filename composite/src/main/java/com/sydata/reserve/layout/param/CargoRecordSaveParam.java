package com.sydata.reserve.layout.param;

import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.reserve.layout.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 储备布局地理信息-货位信息备案
 * </p>
 *
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "储备布局地理信息-货位信息备案新增参数")
public class CargoRecordSaveParam extends BaseEntity implements Serializable {


    @NotBlank(message = "货位代码必填", groups = BasicCheck.class)
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

    @ApiModelProperty("状态 0：保存，1：提交，2：审核")
    private String status;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("仓房代码")
    private String cfdm;


}
