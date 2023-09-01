package com.sydata.dostrict.reserve.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.validated.annotation.StringSize;
import com.sydata.collect.api.validated.annotation.TargetNotEmpty;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.collect.api.validated.group.CorrectCheck;
import com.sydata.common.basis.annotation.DataBindDict;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022-04-26
 * @description 粮食储备-储备计划-新增操作参数
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-储备计划-新增操作参数")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorReservePlanSaveParam implements Serializable {

    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id(计划单号)")
    private String id;

    @NotBlank(message = "计划单号必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[a-zA-Z0-9]{1,21}$", message = "计划单号为数字和字母组成，最大长度21", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划单号")
    private String jhdh;

    @StringSize(max = 128, message = "计划文号最大长度128", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划文号")
    private String jhwh;

    @StringSize(max = 256, message = "计划名称最大长度256", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划名称")
    private String jhmc;

    @Pattern(regexp = "^\\d{4}$", message = "计划年度错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "计划年度")
    private String jhnd;

    @StringSize(max = 128, message = "制定计划单位最大长度128", groups = BasicCheck.class)
    @ApiModelProperty(value = "制定计划单位")
    private String jhzddw;

    @ApiModelProperty(value = "计划下达时间")
    private LocalDateTime jhxdsj;

    @Pattern(regexp = "^[0-9]{7}$", message = "粮食品种代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @Pattern(regexp = "^$|^01|02|03|04|05|06$", message = "粮食等级代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @Digits(integer = 20, fraction = 3, message = "数量（吨） Decimal(20,3)", groups = BasicCheck.class)
    @Positive(message = "数量（吨）不能小于等于0", groups = BasicCheck.class)
    @ApiModelProperty(value = "数量（吨）")
    private BigDecimal sl;

    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;
}
