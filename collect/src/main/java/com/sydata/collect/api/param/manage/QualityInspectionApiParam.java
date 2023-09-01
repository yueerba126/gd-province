package com.sydata.collect.api.param.manage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.PersonNameRule;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetIsBefore;
import com.sydata.collect.api.validated.annotation.TargetStartsWith;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringJoiner;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.HASH;
import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * @author ：xy
 * @description：质检信息表接收对象Param
 * @version: 1.0
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "质检信息API操作参数")
public class QualityInspectionApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "质检报告单号不能为空", groups = BasicCheck.class)
    @TargetStartsWith(message = "质检报告单号必须以检验类别和检测日期开始", target = {"#jylb", "#jysj"}, groups = BasicCheck.class)
    @Pattern(regexp = "^[0]{1}[0-8]{1}[0-9]{12}$", message = "质检报告单编码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "质检报告单号")
    private String zjbgdh;

    @NotBlank(message = "货位代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @NotBlank(message = "粮食品种代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @NotBlank(message = "粮食等级代码不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^01|02|03|04|05|06$", message = "粮食等级代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "入库日期")
    private LocalDate rkrq;

    @NotBlank(message = "检验类别不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^01|02|03|04|05|06$|07$|08$", message = "检验类别格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "检验类别")
    private String jylb;

    @ApiModelProperty(value = "检验时间")
    private LocalDateTime jysj;

    @NotBlank(message = "检验单位不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,128}$", message = "检验单位格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "检验单位")
    private String jydw;

    @StringSize(max = 128, message = "检验人最大长度为100", groups = BasicCheck.class)
    @PersonNameRule(split = true, groups = BasicCheck.class,
            message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等,多个人员使用|分割")
    @ApiModelProperty(value = "检验人")
    private String jyr;

    @NotBlank(message = "检验依据不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^0|1$", message = "检验依据格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "检验依据")
    private String jyyj;

    @NotBlank(message = "指标类别不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,8}$", message = "指标类别格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "指标类别")
    private String zblb;

    @NotBlank(message = "检验项目不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,1000}$", message = "检验项目格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "检验项目")
    private String jyxm;

    @NotBlank(message = "检验项目值不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,1000}$", message = "检验项目值格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "检验项目值")
    private String jyxmz;

    @NotBlank(message = "指标结果判定不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^.{1,128}$", message = "指标结果判定格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "指标结果判定")
    private String zbjgpd;

    @ApiModelProperty(value = "签发日期")
    private LocalDate qfrq;

    @ApiModelProperty(value = "报告出具时间")
    private LocalDateTime bgcjsj;

    @NotBlank(message = "审核人姓名不能为空", groups = BasicCheck.class)
    @StringSize(max = 64, message = "审核人姓名最大长度为64位", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "审核人姓名")
    private String shrxm;

    @Pattern(regexp = "^$|^[A-Za-z0-9]{0,64}$", message = "扦样单编号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "扦样单编号")
    private String qydbh;

    @NotNull(message = "扦样时间不能为空", groups = BasicCheck.class)
    @ApiModelProperty(value = "扦样时间")
    private LocalDateTime qysj;

    @Pattern(regexp = "^.{0,64}$", message = "扦样区域格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "扦样区域")
    private String qyqy;

    @NotBlank(message = "扦样人姓名不能为空", groups = BasicCheck.class)
    @StringSize(max = 64, message = "扦样人姓名最大长度为64位", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "扦样人姓名")
    private String qyrxm;

    @StringSize(max = 64, message = "监督人姓名最大长度为64位", groups = BasicCheck.class)
    @PersonNameRule(groups = BasicCheck.class, message = "人员姓名为中文，且不允许填写:暂无，无，空,测试等")
    @ApiModelProperty(value = "监督人姓名")
    private String jdrxm;

    @Pattern(regexp = "^$|^[A-Za-z0-9]{0,64}$", message = "样品编号格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "样品编号")
    private String ypbh;

    @Digits(integer = 20, fraction = 3, message = "样品数量长度不正确 Decimal(20,3)", groups = BasicCheck.class)
    @NotNull(message = "样品数量不能为空", groups = BasicCheck.class)
    @Positive(message = "样品数量不能小于等于0", groups = BasicCheck.class)
    @TargetIsBefore(target = "#dbsl", message = "样品数量不能大于代表数量", groups = BasicCheck.class)
    @ApiModelProperty(value = "样品数量")
    private BigDecimal ypsl;

    @Digits(integer = 20, fraction = 3, message = "代表数量长度不正确 Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "代表数量不能小于等于0", groups = BasicCheck.class)
    @NotNull(message = "代表数量不能为空", groups = BasicCheck.class)
    @ApiModelProperty(value = "代表数量")
    private BigDecimal dbsl;

    @NotBlank(message = "样品等级不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^01|02|03|04|05|06$", message = "样品等级格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "样品等级")
    private String ypdj;

    @NotBlank(message = "是否正常存储年限不能为空", groups = BasicCheck.class)
    @Pattern(regexp = "^0|1$", message = "是否正常存储年限格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "是否正常存储年限")
    private String sfzcccnx;

    @Pattern(regexp = "^$|^.{0,400}$", message = "备注格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "备注")
    private String bz;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位名称", hidden = true)
    private String hwmc;

    @JsonIgnore
    @NotBlank(message = "粮食品种代码不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @JsonIgnore
    @NotBlank(message = "指标类别代码不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#zblb", sourceFieldCombination = "zblb", valueBindStrategy = SEPARATED, bindSeparated = HASH)
    @ApiModelProperty(value = "指标类别名称", hidden = true)
    private String zblbName;

    @JsonIgnore
    @NotBlank(message = "检验指标代码不存在", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#jyxm", sourceFieldCombination = "jyxm", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "检验指标名称,号分割", hidden = true)
    private String jyxmName;

    @Override
    public String buildId() {
        return new StringJoiner(DASH).add(hwdm).add(zjbgdh).toString();
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
