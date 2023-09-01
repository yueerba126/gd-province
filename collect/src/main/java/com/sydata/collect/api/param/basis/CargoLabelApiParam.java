package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.ImgPositionCheck;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author lzq
 * @description 库区图仓房点位标注API参数
 * @date 2022/10/21 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "库区图仓房点位标注API参数")
public class CargoLabelApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @NotBlank(message = "库区代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误", groups = BasicCheck.class)
    @TargetStartsWith(message = "库区代码必须以单位代码开始", target = "#dwdm", groups = BasicCheck.class)
    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @NotBlank(message = "货位代码必填", groups = BasicCheck.class)
    @TargetStartsWith(message = "货位代码必须以库区代码开始", target = "#kqdm", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ImgPositionCheck(message = "货位相对位置不在库区平面图xy轴范围之内", groups = BasicCheck.class)
    @StringSize(max = 20, message = "货位相对位置最大长度20", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位相对位置")
    private String hwxdwz;

    @NotBlank(message = "货位位置样式必填", groups = BasicCheck.class)
    @StringSize(max = 256, message = "货位位置样式最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位位置样式")
    private String hwwzys;

    @StringSize(max = 1000, message = "备注最大长度1000", groups = BasicCheck.class)
    @ApiModelProperty(value = "备注")
    private String bz;


    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "单位代码不存在", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位代码名称", hidden = true)
    private String dwdmName;

    @JsonIgnore
    @NotBlank(message = "库区代码不存在", groups = CorrectCheck.class)
    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区代码名称", hidden = true)
    private String kqdmName;

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo(sourceField = "#hwdm")
    @ApiModelProperty(value = "货位代码名称", hidden = true)
    private String hwdmName;

    @Override
    public String buildId() {
        return hwdm;
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
