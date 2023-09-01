package com.sydata.trade.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;

/**
 * 倒仓信息
 *
 * @author lzq
 * @date 2022/8/18 17:49
 */

@ApiModel(description = "粮油购销-倒仓信息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("trade_transfer_barn")
public class TransferBarn extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "倒仓单号")
    private String dcdh;

    @ApiModelProperty(value = "倒仓类型:0:倒仓 1:移库")
    private String dclx;

    @ApiModelProperty(value = "倒仓计划文件编号")
    private String dcjhwjbh;

    @ApiModelProperty(value = "通知单号")
    private String tzdh;

    @ApiModelProperty(value = "倒出库区 关联表 E.2 库区代码")
    private String dcdw;

    @ApiModelProperty(value = "倒入库区 关联表 E.2 库区代码")
    private String drdw;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "倒出货位编码")
    private String dchwdm;

    @ApiModelProperty(value = "导入货位编码")
    private String drhwdm;

    @ApiModelProperty(value = "倒仓日期")
    private LocalDate dcrq;

    @ApiModelProperty(value = "倒仓数量")
    private BigDecimal dcsl;

    @ApiModelProperty(value = "包装物")
    private String bzw;

    @ApiModelProperty(value = "标准包件数")
    private Integer bzbjs;

    @ApiModelProperty(value = "装卸作业单位")
    private String zxzydw;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}