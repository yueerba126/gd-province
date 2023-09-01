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
 * 粮情预警阈值对象 admin_grain_threshold
 *
 * @author fuql
 * @date 2023-05-09
 */
@ApiModel(description = "粮情预警阈值")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("admin_grain_threshold")
public class AdminGrainThreshold extends BaseFiledEntity implements Serializable {

    @Excel(name = "粮情预警阈值设置id")
    @ApiModelProperty(value = "粮情预警阈值设置id")
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

    @Excel(name = "告警天数")
    @ApiModelProperty(value = "告警天数")
    private Long gjts;

    @Excel(name = "最高粮温（℃）")
    @ApiModelProperty(value = "最高粮温（℃）")
    private BigDecimal zglw;

    @Excel(name = "最低粮温（℃）")
    @ApiModelProperty(value = "最低粮温（℃）")
    private BigDecimal zdlw;

    @Excel(name = "最高粮湿（%）")
    @ApiModelProperty(value = "最高粮湿（%）")
    private BigDecimal zgls;

    @Excel(name = "最低粮湿（%）")
    @ApiModelProperty(value = "最低粮湿（%）")
    private BigDecimal zdls;

    @Excel(name = "最高仓内温（℃）")
    @ApiModelProperty(value = "最高仓内温（℃）")
    private BigDecimal zgcnw;

    @Excel(name = "最低仓内温（℃）")
    @ApiModelProperty(value = "最低仓内温（℃）")
    private BigDecimal zdcnw;

    @Excel(name = "最高仓内湿度（%）")
    @ApiModelProperty(value = "最高仓内湿度（%）")
    private BigDecimal zgcnsd;

    @Excel(name = "最低仓内湿度（%）")
    @ApiModelProperty(value = "最低仓内湿度（%）")
    private BigDecimal zdcnsd;

    @Excel(name = "最高仓外温（℃）")
    @ApiModelProperty(value = "最高仓外温（℃）")
    private BigDecimal zgcww;

    @Excel(name = "最低仓外温（℃）")
    @ApiModelProperty(value = "最低仓外温（℃）")
    private BigDecimal zdcww;

    @Excel(name = "最高仓外湿度（%）")
    @ApiModelProperty(value = "最高仓外湿度（%）")
    private BigDecimal zgcwsd;

    @Excel(name = "最低仓外湿度（%）")
    @ApiModelProperty(value = "最低仓外湿度（%）")
    private BigDecimal zdcwsd;

    @Excel(name = "最高虫害（头）")
    @ApiModelProperty(value = "最高虫害（头）")
    private BigDecimal zgch;

    @Excel(name = "最高氧气")
    @ApiModelProperty(value = "最高氧气")
    private BigDecimal zgyq;

    @Excel(name = "最低氧气")
    @ApiModelProperty(value = "最低氧气")
    private BigDecimal zdyq;

    @Excel(name = "最高氮气")
    @ApiModelProperty(value = "最高氮气")
    private BigDecimal zgdq;

    @Excel(name = "最低氮气")
    @ApiModelProperty(value = "最低氮气")
    private BigDecimal zddq;

    @Excel(name = "最高二氧化碳")
    @ApiModelProperty(value = "最高二氧化碳")
    private BigDecimal zgeyht;

    @Excel(name = "最低二氧化碳")
    @ApiModelProperty(value = "最低二氧化碳")
    private BigDecimal zdeyht;

    @Excel(name = "最高硫化氢")
    @ApiModelProperty(value = "最高硫化氢")
    private BigDecimal zglhq;

    @Excel(name = "最低硫化氢")
    @ApiModelProperty(value = "最低硫化氢")
    private BigDecimal zdlhq;
}