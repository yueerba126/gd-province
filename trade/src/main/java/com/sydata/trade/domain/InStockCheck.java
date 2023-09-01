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
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;

/**
 * <p>
 * 入库检验信息表
 * </p>
 *
 * @Author chenzx
 * @since 2022-05-05
 */
@ApiModel(description = "粮油购销-入库检验")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("trade_in_stock_check")
public class InStockCheck extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "入库检验单号")
    private String rkjydh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "入库业务单号")
    private String rkywdh;

    @ApiModelProperty(value = "扦样时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime qysj;

    @ApiModelProperty(value = "扦样人姓名")
    private String qyrxm;

    @ApiModelProperty(value = "扦样方式:0：人工； 1： 自动； 2：智能随机")
    private String qyfs;

    @ApiModelProperty(value = "检验项目")
    private String jyxm;

    @ApiModelProperty(value = "检验值")
    private String jyz;

    @ApiModelProperty(value = "增扣价")
    private String zkj;

    @ApiModelProperty(value = "增扣量")
    private String zkl;

    @ApiModelProperty(value = "检验人姓名")
    private String jyrxm;

    @ApiModelProperty(value = "检验时间 格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime jysj;

    @ApiModelProperty(value = "检验结果:0：不合格  1：合格")
    private String jyjg;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食定等")
    private String lsdd;

    @ApiModelProperty(value = "保管员复核:0：不合格 1：合格")
    private String bgyfh;

    @ApiModelProperty(value = "操作标志:i:新增数据 (默认) u:更新数据    d:删除数据")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
