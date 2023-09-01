package com.sydata.collect.api.param.basis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetIsBefore;
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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;

/**
 * @author lzq
 * @description 设备API操作参数
 * @date 2022/10/20 20:13
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "设备API操作参数")
public class DeviceApiParam extends BaseApiParam implements Serializable {

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
    @ApiModelProperty("库区名称")
    private String kqmc;

    @NotBlank(message = "设备编号必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,20}$", message = "设备编号格式错误", groups = BasicCheck.class)
    @ApiModelProperty("设备编号")
    private String sbbh;

    @NotBlank(message = "设备仪器名称必填", groups = BasicCheck.class)
    @StringSize(max = 50, message = "设备仪器名称最大长度50", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,50}$", message = "设备仪器名称格式错误", groups = BasicCheck.class)
    @ApiModelProperty("设备仪器名称")
    private String sbyqmc;

    @NotBlank(message = "设备仪器代码必填", groups = BasicCheck.class)
    @StringSize(max = 8, message = "设备仪器代码最大长度8", groups = BasicCheck.class)
    @ApiModelProperty("设备仪器代码")
    private String sbyqdm;

    @StringSize(max = 50, message = "设备规格型号最大长度50", groups = BasicCheck.class)
    @ApiModelProperty("设备规格型号")
    private String sbggxh;

    @NotBlank(message = "生产厂家必填", groups = BasicCheck.class)
    @StringSize(max = 256, message = "生产厂家最大长度256", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "生产厂家格式错误", groups = BasicCheck.class)
    @ApiModelProperty("生产厂家")
    private String sccj;

    @TargetIsBefore(target = "#jdsj", message = "生产日期不得晚于检定时间", groups = BasicCheck.class)
    @ApiModelProperty("生产日期")
    private LocalDate scrq;

    @StringSize(max = 512, message = "设备描述最大长度512", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,512}$", message = "设备描述格式错误", groups = BasicCheck.class)
    @ApiModelProperty("设备描述")
    private String sbms;

    @Pattern(regexp = "^1$|^2$|^3$|^4$", message = "设备状态不存在", groups = BasicCheck.class)
    @ApiModelProperty("设备状态")
    private String sbzt;

    @ApiModelProperty("检定时间")
    private LocalDate jdsj;

    @StringSize(max = 256, message = "检定单位最大长度256", groups = BasicCheck.class)
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "检定单位格式错误", groups = BasicCheck.class)
    @ApiModelProperty("检定单位")
    private String jddw;

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
        return new StringJoiner(DASH).add(kqdm).add(sbbh).toString();
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
