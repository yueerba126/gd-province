package com.sydata.dostrict.reserve.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetNotEmpty;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.StringJoiner;

import static jodd.util.StringPool.DASH;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 粮食储备-储备规模-新增参数
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-储备规模-新增参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorReserveScaleSaveParam implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id(计划单号)")
    private String id;

    @NotBlank(message = "关联储备计划id必填")
    @ApiModelProperty(value = "关联储备计划id")
    private String cbjhId;

    @NotBlank(message = "年份必填", groups = BasicCheck.class)
    @Pattern(regexp = "^\\d{4}$", message = "年份格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "年份")
    private String nf;

    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @NotBlank(message = "承储企业单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "承储企业单位代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "承储企业")
    private String ccqy;

    @NotBlank(message = "粮食品种代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误")
    @ApiModelProperty(value = "粮食品种")
    private String ylpz;

    @NotBlank(message = "粮食性质代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食性质")
    private String ylxz;

    @Digits(integer = 20, fraction = 6, message = "数量（吨） Decimal(20,6)", groups = BasicCheck.class)
    @Positive(message = "储备规模计划数量不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "储备规模计划数量")
    private BigDecimal ylcbgmjhsl;

    @StringSize(max = 128, message = "储备规模计划文号最大长度128", groups = BasicCheck.class)
    @ApiModelProperty(value = "储备规模计划文号")
    private String cbgmjhwh;

    @StringSize(max = 256, message = "备注最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;
}
