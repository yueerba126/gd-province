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
import java.time.LocalDateTime;

/**
 * 库存数量-粮食库存异常告警对象 warn_foodstuff_message
 *
 * @author fuql
 * @date 2023-04-28
 */
@ApiModel(description = "库存数量-粮食库存异常告警")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("warn_foodstuff_message")
public class WarnFoodstuffMessage extends BaseFiledEntity implements Serializable {

    @Excel(name = "库存数量-粮食库存异常告警id")
    @ApiModelProperty(value = "库存数量-粮食库存异常告警id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @Excel(name = "预警等级字典：warn_level")
    @ApiModelProperty(value = "预警等级字典：warn_level")
    private String warnLevel;

    @Excel(name = "预警类型字典：warn_type")
    @ApiModelProperty(value = "预警类型字典：warn_type")
    private String warnType;

    @Excel(name = "预警时间")
    @ApiModelProperty(value = "预警时间")
    private LocalDateTime warnDate;

    @Excel(name = "单位代码")
    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @Excel(name = "预警内容")
    @ApiModelProperty(value = "预警内容")
    private String warnContent;

    @Excel(name = "处理状态字典：handle_status")
    @ApiModelProperty(value = "处理状态字典：handle_status")
    private String handleStatus;

    @Excel(name = "处理时间")
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime handleDate;

    @Excel(name = "处理人")
    @ApiModelProperty(value = "处理人 暂时手填")
    private String handlePeople;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Excel(name = "预留字段：预警规则id")
    @ApiModelProperty(value = "预留字段：预警规则id")
    private String ruleId;

    @Excel(name = "预留字段：接收人")
    @ApiModelProperty(value = "预留字段：接收人")
    private String recipient;
}