package com.sydata.collect.api.param.plan;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetIsBefore;
import com.sydata.collect.api.validated.group.BasicCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 轮换计划审核新增参数
 *
 * @author fuql
 * @date 2023-03-31
 */
@ApiModel(description = "轮换计划审核新增参数")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlanManageApiParam extends BaseApiParam implements Serializable {

    @ApiModelProperty(value = "计划管理Id")
    private String mainId;

    @ApiModelProperty(value = "轮换类型：1静态轮换，2.自主轮换 字典：main_rotate_type")
    private String mainRotateType;

    @ApiModelProperty(value = "轮换类型：1静态轮换，2.自主轮换 字典：main_rotate_type")
    private String mainRotateTypeName;

    @ApiModelProperty(value = "计划下达单位id")
    private String planTenantId;

    @ApiModelProperty(value = "计划下达单位名称")
    private String planTenantName;

    @NotBlank(message = "计划下达单位单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "计划下达单位单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划下达单位名称统一社会信用代码")
    private String jhxddw;

    @StringSize(max = 256, message = "计划下达单位名称最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划下达单位名称")
    private String jhxddwmc;

    @NotBlank(message = "年份必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{4}$", message = "年份格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @NotBlank(message = "轮换计划单号必填", groups = BasicCheck.class)
    @StringSize(max = 25, message = "轮换计划单号最大长度25", groups = BasicCheck.class)
    @ApiModelProperty(value = "单据编号：计划下达单位代码 + 计划年度")
    private String lhjhdh;

    @StringSize(max = 256, message = "计划名称最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @ApiModelProperty(value = "管理方式 字典：management_style")
    private String managementStyle;

    @ApiModelProperty(value = "管理方式名称 ： 1直储 ， 2 代储， 3  租仓， 4委托 字典：management_style")
    private String managementStyleName;

    @StringSize(max = 64, message = "计划文号最大长度64", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @ApiModelProperty(value = "轮入数量（t）")
    private BigDecimal planInQty;

    @ApiModelProperty(value = "轮出数量（t）")
    private BigDecimal planOutQty;

    @ApiModelProperty(value = "粮食性质代码")
    @NotBlank(message = "粮食性质代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    private String lsxzdm;

    @TargetIsBefore(target = "#kszxrq", message = "计划下达时间不能晚于开始执行日期", groups = BasicCheck.class)
    @NotNull(message = "计划下达时间必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划下达时间")
    private LocalDate jhxdsj;

    @NotNull(message = "开始执行日期必填", groups = BasicCheck.class)
    @TargetIsBefore(target = "#jzzxrq", message = "开始执行日期不能晚于截止执行日期", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划执行日期开始")
    private LocalDate kszxrq;

    @NotNull(message = "截止执行日期必填", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划执行日期结束")
    private LocalDate jzzxrq;

    @ApiModelProperty(value = "单据状态 字典：billStatus")
    private String billStatus;

    @ApiModelProperty(value = "单据公共主表状态名称 字典：billStatus")
    private String billStatusName;

    @Excel(name = "计划状态")
    @ApiModelProperty(value = "计划状态")
    private String planStatus;

    @ApiModelProperty(value = "计划状态名称")
    private String planStatusName;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "需要新增的计划管理明细Param列表")
    @Valid
    private List<PlanManageApiDetailParam> detail;

    @Override
    protected String buildId() {
        return lhjhdh;
    }

    /**
     * 轮换计划审核明细表新增参数
     *
     * @author fuql
     * @date 2023-03-31
     */
    @ApiModel(description = "轮换计划审核明细表新增参数")
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public static class PlanManageApiDetailParam implements Serializable {

        @ApiModelProperty(value = "详情Id")
        private String id;

        @ApiModelProperty(value = "计划管理详情Id")
        private String detailId;

        @ApiModelProperty(value = "计划管理id")
        private String mainId;

        @ApiModelProperty(value = "轮换计划代码")
        private String lhjhdh;

        @NotBlank(message = "轮换货位代码必填", groups = BasicCheck.class)
        @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{12}$", message = "轮换货位代码格式错误", groups = BasicCheck.class)
        @ApiModelProperty(value = "轮换货位代码")
        private String lhhwdm;

        @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
        @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误")
        @ApiModelProperty(value = "物料品种代码")
        private String lspzdm;

        @Pattern(regexp = "^$|^01|02|03|04|05|06$", message = "粮食等级代码格式错误", groups = BasicCheck.class)
        @ApiModelProperty(value = "粮食等级字典：food_grade")
        private String lsdjdm;

        @ApiModelProperty(value = "粮食等级")
        private String lsdjdmName;

        @ApiModelProperty(value = "收获年度")
        private String shnd;

        @NotBlank(message = "轮换类型必填", groups = BasicCheck.class)
        @Pattern(regexp = "^1$|^2$|^3$", message = "轮换类型格式错误", groups = BasicCheck.class)
        @ApiModelProperty(value = "轮换类型：1轮入，2轮出 字典：rotate_type")
        private String lhlx;

        @ApiModelProperty(value = "轮换类型")
        private String lhlxName;

        @NotNull(message = "轮换数量必填", groups = BasicCheck.class)
        @Positive(message = "轮换数量不能小于等于0", groups = BasicCheck.class)
        @Digits(integer = 20, fraction = 3, message = "轮换数量（吨） Decimal(20,3)", groups = BasicCheck.class)
        @ApiModelProperty(value = "粮油数量（t）")
        private BigDecimal lhsl;

        @ApiModelProperty(value = "基准价(元/公斤)")
        private BigDecimal price;

    }
}