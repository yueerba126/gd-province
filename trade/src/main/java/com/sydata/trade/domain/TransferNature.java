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
 * 性质转变单信息
 *
 * @author lzq
 * @date 2022/8/19 9:48
 */
@ApiModel(description = "粮油购销-性质转变")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "trade_transfer_nature")
public class TransferNature extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "性质转变单编号")
    private String lsxzzbdh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "粮食数量")
    private BigDecimal lssl;

    @ApiModelProperty(value = "划转数量")
    private BigDecimal hzsl;

    @ApiModelProperty(value = "批准文号")
    private String bzwh;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "划转前粮食性质代码")
    private String hzqlsxzdm;

    @ApiModelProperty(value = "划转后粮食性质代码")
    private String hzhlsxzdm;

    @ApiModelProperty(value = "划转日期")
    private LocalDate hzrq;

    @ApiModelProperty(value = "仓储审核人")
    private String ccshr;

    @ApiModelProperty(value = "质检审核人")
    private String zjshr;

    @ApiModelProperty(value = "统计审核人")
    private String tjshr;

    @ApiModelProperty(value = "会计审核人")
    private String kjshr;

    @ApiModelProperty(value = "领导审核人")
    private String ldshr;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}