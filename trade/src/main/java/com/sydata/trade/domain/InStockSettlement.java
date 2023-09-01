package com.sydata.trade.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;

/**
 * <p>
 * 入库结算实体表
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
@ApiModel(description = "粮油购销-入库结算")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("trade_in_stock_settlement")
public class InStockSettlement extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "入库结算单号(由库点代码+结算日期（yyyyMMdd）+4 位顺序号组成)")
    private String rkjsdh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "合同号")
    private String hth;

    @ApiModelProperty(value = "结算数量")
    private BigDecimal jssl;

    @ApiModelProperty(value = "结算单价")
    private BigDecimal jsdj;

    @ApiModelProperty(value = "结算金额")
    private BigDecimal jsje;

    @ApiModelProperty(value = "结算时间")
    private LocalDateTime jssj;

    @ApiModelProperty(value = "结算方式:0：现金 1：转账")
    private String jsfs;

    @ApiModelProperty(value = "收款人")
    private String skr;

    @ApiModelProperty(value = "银行行别代码")
    private String yhhbdm;

    @ApiModelProperty(value = "收款人身份证号")
    private String skrsfzh;

    @ApiModelProperty(value = "开户行号")
    private String khhh;

    @ApiModelProperty(value = "开户行名称")
    private String khhmc;

    @ApiModelProperty(value = "银行账号")
    private String yhzh;

    @ApiModelProperty(value = "发票号码")
    private String fphm;

    @ApiModelProperty(value = "发票状态")
    private String fpzt;

    @ApiModelProperty(value = "付款单位")
    private String fkdw;

    @ApiModelProperty(value = "操作标志:i:新增数据 (默认) u:更新数据 d:删除数据")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
