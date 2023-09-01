package com.sydata.collect.api.param.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ：xy
 * @description：气体信息数据接收对象Param
 * @version: 1.0
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "气体信息API操作参数")
public class GasInformationApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "气体浓度检测单号不能为空", groups = BasicCheck.class)
    @TargetStartsWith(message = "气体浓度检测单号必须以货位代码和检测日期开始", target = {"#hwdm", "#jcsj"}, groups = BasicCheck.class)
    @StringSize(max = 42, message = "气体浓度监测单号长度不能超过42", groups = BasicCheck.class)
    @ApiModelProperty("气体浓度监测单号")
    private String qtndjcdh;

    @NotNull(message = "检测时间不能为空", groups = BasicCheck.class)
    @ApiModelProperty(value = "检测时间")
    private LocalDateTime jcsj;

    @NotBlank(message = "货位代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty("货位代码")
    private String hwdm;

    @StringSize(max = 8000, message = "氧气含量值集合长度不能超过8000", groups = BasicCheck.class)
    @ApiModelProperty("氧气含量值集合")
    private String yqhlzjh;

    @NotBlank(message = "二氧化碳含量集合不能为空", groups = BasicCheck.class)
    @StringSize(max = 8000, message = "二氧化碳含量集合长度不能超过8000", groups = BasicCheck.class)
    @ApiModelProperty("二氧化碳含量集合")
    private String eyhthlzjh;

    @NotBlank(message = "磷化氢浓度值集合不能为空", groups = BasicCheck.class)
    @StringSize(max = 8000, message = "磷化氢浓度值集合长度不能超过8000", groups = BasicCheck.class)
    @ApiModelProperty("磷化氢浓度值集合")
    private String lhqndzjh;

    @StringSize(max = 8000, message = "硫酰氟浓度值集合长度不能超过8000", groups = BasicCheck.class)
    @ApiModelProperty("硫酰氟浓度值集合")
    private String lxfndzjh;

    @StringSize(max = 8000, message = "一氧化氮含量值集合长度不能超过8000", groups = BasicCheck.class)
    @ApiModelProperty("一氧化氮含量值集合")
    private String yyhdhlzjh;

    @StringSize(max = 8000, message = "一氧化碳含量值集合长度不能超过8000", groups = BasicCheck.class)
    @ApiModelProperty("一氧化碳含量值集合")
    private String yyhthlzjh;

    @Pattern(regexp = "^$|^[1|2|3|4|5|9]$", message = "作业类型格式错误", groups = BasicCheck.class)
    @ApiModelProperty("作业类型")
    private String zylx;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwmc;

    @Override
    public String buildId() {
        return qtndjcdh;
    }

    @Override
    public String buildCompanyId() {
        return hwdm.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return hwdm.substring(0, 21);
    }
}
