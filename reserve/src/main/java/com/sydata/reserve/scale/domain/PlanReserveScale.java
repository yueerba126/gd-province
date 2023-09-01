package com.sydata.reserve.scale.domain;

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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 储备计划管理-储备规模对象 plan_reserve_scale
 *
 * @author fuql
 * @date 2023-05-17
 */
@ApiModel(description = "储备计划管理-储备规模")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("plan_reserve_scale")
public class PlanReserveScale extends BaseFiledEntity implements Serializable {

    @Excel(name = "主键ID")
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remarks;

    @NotBlank(message = "行政区划代码必填")
    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误")
    @ApiModelProperty(value = "行政区划代码")
    private String regionCode;

    @ApiModelProperty(value = "粮食储备规模数量（万吨）")
    private BigDecimal grainAmount;

    @ApiModelProperty(value = "油储备规模数量（万吨）")
    private BigDecimal oilAmount;

    @ApiModelProperty(value = "上级行政区划代码")
    @Pattern(regexp = "^\\d{6}$", message = "上级行政区划代码格式错误")
    private String regionParentCode;

    @Excel(name = "是否有区县")
    @ApiModelProperty(value = "是否有区县")
    private String isArea;
}
