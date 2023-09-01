package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.trade.annotation.DataBindInStock;
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
import java.util.StringJoiner;

import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;
import static jodd.util.StringPool.DASH;

/**
 * @author czx
 * @description 粮食入库检验信息API操作参数
 * @date 2022/10/20 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "粮食入库检验信息API操作参数")
public class InStockCheckApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "入库检验单号必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[14]{2}[0-9]{10}$", message = "入库检验单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "入库检验单号")
    private String rkjydh;

    @NotBlank(message = "货位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @NotBlank(message = "入库业务单号必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[14]{2}[0-9]{10}$", message = "入库业务单号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "入库业务单号")
    private String rkywdh;

    @NotNull(message = "扦样时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "扦样时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime qysj;

    @NotBlank(message = "扦样人姓名必填", groups = BasicCheck.class)
    @StringSize(max = 64, message = "扦样人姓名最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "扦样人姓名")
    private String qyrxm;

    @NotBlank(message = "扦样方式必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0|1|2]$", message = "扦样方式格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "扦样方式:0：人工； 1： 自动； 2：智能随机")
    private String qyfs;

    @NotBlank(message = "检验项目必填", groups = BasicCheck.class)
    @StringSize(max = 1024, message = "检验项目最大长度为1024", groups = BasicCheck.class)
    @ApiModelProperty(value = "检验项目")
    private String jyxm;

    @NotBlank(message = "检验值必填", groups = BasicCheck.class)
    @StringSize(max = 1024, message = "检验值最大长度为1024", groups = BasicCheck.class)
    @ApiModelProperty(value = "检验值")
    private String jyz;

    @NotBlank(message = "增扣价必填", groups = BasicCheck.class)
    @StringSize(max = 1024, message = "增扣价最大长度为1024", groups = BasicCheck.class)
    @Pattern(regexp = "^([0-9]\\d*|[0-9]\\d*.\\d*|0.\\d*[0-9]\\d*,)*[0-9]|[0-9]\\d*.\\d*|0.\\d*[0-9]\\d*\\d*",
            message = "增扣价只允许填写数值，多个数值使用英文,号分割", groups = BasicCheck.class)
    @ApiModelProperty(value = "增扣价")
    private String zkj;

    @NotBlank(message = "增扣量必填", groups = BasicCheck.class)
    @StringSize(max = 1024, message = "增扣量最大长度为1024", groups = BasicCheck.class)
    @Pattern(regexp = "^([0-9]\\d*|[0-9]\\d*.\\d*|0.\\d*[0-9]\\d*,)*[0-9]|[0-9]\\d*.\\d*|0.\\d*[0-9]\\d*\\d*",
            message = "增扣量只允许填写数值，多个数值使用英文,号分割", groups = BasicCheck.class)
    @ApiModelProperty(value = "增扣量")
    private String zkl;

    @NotBlank(message = "检验人姓名必填", groups = BasicCheck.class)
    @StringSize(max = 64, message = "检验人姓名最大长度为64", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "检验人姓名")
    private String jyrxm;

    @NotNull(message = "检验时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "检验时间 格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime jysj;

    @NotBlank(message = "检验结果必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0|1]$", message = "检验结果格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "检验结果:0：不合格  1：合格")
    private String jyjg;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @NotBlank(message = "粮食等级代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^01|02|03|04|05|06$", message = "粮食等级代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食定等")
    private String lsdd;

    @Pattern(regexp = "^[0|1]$", message = "保管员复核格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "保管员复核:0：不合格 1：合格")
    private String bgyfh;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "粮食品种代码不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @JsonIgnore
    @NotBlank(message = "检验指标代码不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#jyxm", sourceFieldCombination = "jyxm", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "检验指标名称,号分割", hidden = true)
    private String jyxmName;

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwmc;

    @JsonIgnore
    @NotBlank(message = "入库业务单号不存在", groups = CorrectCheck.class)
    @DataBindInStock(sourceField = "#hwdm+'-'+#rkywdh")
    @ApiModelProperty(value = "入库ID", hidden = true)
    private String rkywId;

    @Override
    public String buildId() {
        return new StringJoiner(DASH).add(hwdm).add(rkjydh).toString();
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
