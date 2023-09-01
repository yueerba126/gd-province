package com.sydata.warn.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 储备计划阈值设置对象 admin_plan_threshold
 *
 * @author fuql
 * @date 2023-05-09
 */
@ApiModel(description = "储备计划阈值设置")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("admin_plan_threshold")
public class AdminPlanThreshold extends BaseFiledEntity implements Serializable {


    @Excel(name = "储备计划阈值设置id")
    @ApiModelProperty(value = "储备计划阈值设置id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @Excel(name = "操作标志（i：新增(默认)，u：更新，d：删除）")
    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @Excel(name = "最后更新时间")
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;

    @Excel(name = "行政区划代码")
    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @Excel(name = "告警阈值（%）")
    @ApiModelProperty(value = "告警阈值（%）")
    private BigDecimal gjyz;
}