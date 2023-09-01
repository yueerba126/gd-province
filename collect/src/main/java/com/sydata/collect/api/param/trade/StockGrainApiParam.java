package com.sydata.collect.api.param.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.*;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
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

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_FORMATTER;
import static com.sydata.common.util.RegexpUtil.SPECIAL_CHECK;
import static jodd.util.StringPool.DASH;

/**
 * @author czx
 * @description 粮食库存信息API操作参数
 * @date 2022/10/20 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "粮食库存信息API操作参数")
public class StockGrainApiParam extends BaseApiParam implements Serializable {

    @NotBlank(message = "货位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "货位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @TargetAppointValue(message = "货位（油罐)状态为 2、3、4 时,粮食性质代码必填", target = "#hwzt", targetValue = {"2", "3", "4"}, groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @TargetAppointValue(message = "货位（油罐)状态为 2、3、4 时,粮食等级必填", target = "#hwzt", targetValue = {"2", "3", "4"}, groups = BasicCheck.class)
    @Pattern(regexp = "^$|^01|02|03|04|05|06$", message = "粮食等级代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @TargetAppointValue(message = "货位（油罐)状态为 2、3、4 时,收货年度必填", target = "#hwzt", targetValue = {"2", "3", "4"}, groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{4}$", message = "收获年度格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "收货年度")
    private String shnd;

    @TargetAppointValue(message = "货位（油罐)状态为 2、3、4 时,国别必填", target = "#hwzt", targetValue = {"2", "3", "4"}, groups = BasicCheck.class)
    @Pattern(regexp = "^$|^[0-9]{3}$", message = "国别格式错误,3位国别代码", groups = BasicCheck.class)
    @ApiModelProperty(value = "国别")
    private String gb;

    @TargetAppointValue(message = "货位（油罐)状态为 2、3、4 时,产地代码必填", target = "#hwzt", targetValue = {"2", "3", "4"}, groups = BasicCheck.class)
    @Pattern(regexp = "^$|^[0-9]{6}$", message = "产地代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "产地")
    private String cd;

    @TargetAppointValue(message = "货位（油罐)状态为 2、3、4 时,保管员必填", target = "#hwzt", targetValue = {"2", "3", "4"}, groups = BasicCheck.class)
    @PersonNameRule(message = "保管员只允许填写中文，且不允许填写无、暂无、空、测试等", groups = BasicCheck.class)
    @StringSize(max = 64, message = "保管员最大长度为64", groups = BasicCheck.class)
    @ApiModelProperty(value = "保管员")
    private String bgy;

    @TargetAppointValue(message = "货位（油罐)状态为 2、3、4 时,粮权归属单位代码必填", target = "#hwzt", targetValue = {"2", "3", "4"}, groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "粮权归属单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮权归属单位代码")
    private String lqgsdwdm;

    @Pattern(regexp = "^\\d{6}$", message = "粮权行政区划代码只能是6位数字", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮权行政区划代码")
    private String lqxzqhdm;

    @NotBlank(message = "管理方式必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^01|02|03|04$", message = "管理方式格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "管理方式 01：直储，02：代储，03，租仓")
    private String glfs;

    @Pattern(regexp = "^$|^1|2$", message = "收储地点格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "收储地点 1：库内。2：库外")
    private String scdd;

    @NotBlank(message = "储粮方式必填", groups = BasicCheck.class)
    @TargetAppointValue(message = "货位（油罐)状态为 2、3、4 时,储粮方式必填", target = "#hwzt", targetValue = {"2", "3", "4"}, groups = BasicCheck.class)
    @Pattern(regexp = "^$|^1|2|3|9$", message = "储粮方式格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "储粮方式 1：散装储粮，2：包装，3：围包散存，9：其他")
    private String clfs;

    @NotBlank(message = "货位（油罐）状态必填", groups = BasicCheck.class)
    @Pattern(regexp = "^$|^1|2|3|4|9$", message = "货位状态格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "货位（油罐）状态1：空仓，2：入库中，3：封仓，4：出库中，9：其他")
    private String hwzt;

    @NotNull(message = "入仓时间必填", groups = BasicCheck.class)
    @TargetIsBefore(target = "#ccwcsj", message = "入仓时间不能晚于出仓完成时间", groups = BasicCheck.class)
    @ApiModelProperty(value = "入仓时间")
    private LocalDateTime rcsj;

    @TargetAppointValue(message = "货位（油罐)状态为 3、4 时,封仓日期必填", target = "#hwzt", targetValue = {"3", "4"}, groups = BasicCheck.class)
    @ApiModelProperty(value = "封仓日期")
    private LocalDate fcrq;

    @TargetAppointValue(message = "货位（油罐)状态为1 时,出仓完成时间必填", target = "#hwzt", targetValue = {"1"}, groups = BasicCheck.class)
    @TargetIsBefore(target = "#qcsj", message = "出仓完成时间不能晚于清仓时间", groups = BasicCheck.class)
    @ApiModelProperty(value = "出仓完成时间")
    private LocalDateTime ccwcsj;

    @ApiModelProperty(value = "清仓时间")
    private LocalDateTime qcsj;

    @Digits(integer = 20, fraction = 3, message = "成货位前损耗 Decimal(20,3)", groups = BasicCheck.class)
    @ApiModelProperty(value = "成货位前损耗")
    private BigDecimal chwqsh;

    @NotNull(message = "实际数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "实际数量 Decimal(20,3)", groups = BasicCheck.class)
    @Min(value = 0, message = "实际数量不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "实际数量(公斤)")
    private BigDecimal sjsl;

    @NotNull(message = "计价数量必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "计价数量 Decimal(20,3)", groups = BasicCheck.class)
    @Min(value = 0, message = "计价数量不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "计价数量（公斤）")
    private BigDecimal jjsl;

    @TargetAppointValue(message = "储粮方式为包装时，包存粮包数必填", target = "#clfs", targetValue = {"2"}, groups = BasicCheck.class)
    @Min(value = 0, message = "包存粮包数（包）不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "包存粮包数（包）")
    private Integer bclbs;

    @NotNull(message = "实际装粮线高必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 2, message = "实际装粮线高 Decimal(20,2)", groups = BasicCheck.class)
    @Min(value = 0, message = "实际装粮线高（米）不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "实际装粮线高（米）")
    private BigDecimal sjzlxg;

    @NotNull(message = "粮堆体积必填", groups = BasicCheck.class)
    @Digits(integer = 20, fraction = 3, message = "粮堆体积 Decimal(20,3)", groups = BasicCheck.class)
    @Min(value = 0, message = "粮堆体积（立方米）不能小于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮堆体积（立方米）")
    private BigDecimal ldtj;

    @Pattern(regexp = "^[^\\/\\s]{1,400}$", message = "备注格式错误", groups = BasicCheck.class)
    @Pattern(regexp = SPECIAL_CHECK, message = "备注存在特殊字符", groups = BasicCheck.class)
    @StringSize(max = 400, message = "备注最大长度为400", groups = BasicCheck.class)
    @ApiModelProperty(value = "备注")
    private String bz;

    /****************以下字段不需要传输，做为表间校验的二次校验**********************/

    @JsonIgnore
    @NotBlank(message = "货位代码不存在", groups = CorrectCheck.class)
    @DataBindCargo
    @ApiModelProperty(value = "货位代码", hidden = true)
    private String hwmc;

    @JsonIgnore
    @TargetNotEmpty(message = "粮食品种代码国标不存在", target = "#lspzdm", groups = CorrectCheck.class)
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    @ApiModelProperty(value = "粮食品种代码国标名称", hidden = true)
    private String lspzdmName;

    @JsonIgnore
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety", dataValue = "#dictFarType")
    @ApiModelProperty(value = "粮食品种类别", hidden = true)
    private String lspzlb;

    @JsonIgnore
    @TargetNotEmpty(message = "粮权归属单位不存在", target = "#lqgsdwdm", groups = CorrectCheck.class)
    @DataBindCompany(sourceField = "#lqgsdwdm")
    @ApiModelProperty(value = "粮权归属单位名称", hidden = true)
    private String lqgsdwdmName;

    @JsonIgnore
    @TargetNotEmpty(message = "粮权行政区划代码不存在", target = "#lqxzqhdm", groups = CorrectCheck.class)
    @DataBindRegion(sourceField = "#lqxzqhdm", dataValue = "#provinceId")
    @ApiModelProperty(value = "粮权行政区划代码所在省份", hidden = true)
    private String lqxzqhdmProvinceId;

    @JsonIgnore
    @DataBindRegion(sourceField = "#lqxzqhdm", dataValue = "#type")
    @ApiModelProperty(value = "粮权行政区划类型：国、省、市、区", hidden = true)
    private String lqxzqhdmType;

    @JsonIgnore
    @TargetNotEmpty(message = "产地代码不存在", target = "#cd", groups = CorrectCheck.class)
    @DataBindRegion(sourceField = "#cd")
    @ApiModelProperty(value = "产地名称", hidden = true)
    private String cdName;

    @Override
    public String buildId() {
        return buildId(super.getZhgxsj());
    }

    @Override
    public String buildCompanyId() {
        return hwdm.substring(0, 18);
    }

    @Override
    public String buildStockHouseId() {
        return hwdm.substring(0, 21);
    }

    /**
     * 构建ID
     *
     * @param zhgxsj 最后更新时间
     * @return 主键ID
     */
    public String buildId(LocalDateTime zhgxsj) {
        return new StringJoiner(DASH)
                .add(hwdm)
                .add(lspzdm)
                .add(rcsj.format(PURE_DATETIME_FORMATTER))
                .add(zhgxsj.format(PURE_DATETIME_FORMATTER))
                .toString();
    }
}
