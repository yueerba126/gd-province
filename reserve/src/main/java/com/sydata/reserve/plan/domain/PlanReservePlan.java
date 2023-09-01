package com.sydata.reserve.plan.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.collect.api.validated.group.BasicCheck;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 储备计划管理-储备计划对象 plan_reserve_plan
 *
 * @author fuql
 * @date 2023-05-19
 */
@ApiModel(description = "储备计划管理-储备计划")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("plan_reserve_plan")
public class PlanReservePlan extends BaseFiledEntity implements Serializable {

    @Excel(name = "主键ID")
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Excel(name = "计划文号")
    @NotBlank(message = "计划文号必填")
    @ApiModelProperty(value = "计划文号")
    private String planCode;

    @Excel(name = "计划名称")
    @ApiModelProperty(value = "计划名称")
    private String planName;

    @NotBlank(message = "粮食性质代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^121$|^122$|^123$|^129$|^200$|^270$|^280$|^290$|^310$|^320$|^330$|^340$", message = "粮食性质代码格式错误", groups = BasicCheck.class)
    @Excel(name = "粮食性质")
    @ApiModelProperty(value = "粮食性质")
    private String ylxz;

    @Excel(name = "承储总数量（吨）")
    @ApiModelProperty(value = "承储总数量（吨）")
    private BigDecimal amount;

    @Excel(name = "计划开始日期")
    @ApiModelProperty(value = "计划开始日期")
    private String beginDate;

    @Excel(name = "计划结束日期")
    @ApiModelProperty(value = "计划结束日期")
    private String endDate;

    @NotBlank(message = "承储企业单位代码必填", groups = BasicCheck.class)
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "承储企业单位代码格式错误", groups = BasicCheck.class)
    @Excel(name = "承储企业")
    @ApiModelProperty(value = "承储企业")
    private String ccqy;

    @Excel(name = "附件")
    @ApiModelProperty(value = "附件")
    private String fileAttachment;

    @Excel(name = "状态")
    @ApiModelProperty(value = "状态 字典：reserve_plan_status")
    private String billStatus;

    @Excel(name = "是否发生调整")
    @ApiModelProperty(value = "是否发生调整")
    private String isChange;

    @ApiModelProperty(value = "计划下达时间")
    private String jhxdsj;

    @ApiModelProperty(value = "年份如2022")
    private String jhnd;

    @ApiModelProperty(value = "制定计划单位")
    private String jhzddw;

    @ApiModelProperty(value = "计划单号")
    @NotBlank(message = "计划单号必填")
    private String jhdh;

    @ApiModelProperty("轮换类型")
    private String mainRotateType;

}
