package com.sydata.trade.domain;

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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 粮油购销-账面库存对象 trade_month_stock
 *
 * @author lzq
 * @date 2022-07-22
 */
@ApiModel(description = "粮油购销-账面库存")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("trade_month_stock")
public class MonthStock extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id（由货位id+粮食品种代码+年度+月份组成）")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "年度")
    private String nd;

    @ApiModelProperty(value = "月份")
    private String yf;

    @ApiModelProperty(value = "期初数量")
    private BigDecimal qcsl;

    @ApiModelProperty(value = "本期收入数量")
    private BigDecimal bqsrsl;

    @ApiModelProperty(value = "本期支出数量")
    private BigDecimal bqzcsl;

    @ApiModelProperty(value = "期末数量")
    private BigDecimal qmye;

    @ApiModelProperty(value = "月结标志")
    private Integer yjbz;

    @ApiModelProperty(value = "业务日期")
    private LocalDate ywrq;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}