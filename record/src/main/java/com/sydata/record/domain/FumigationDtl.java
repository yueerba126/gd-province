package com.sydata.record.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

/**
 * 备案管理-熏蒸明细对象 record_fumigation_dtl
 *
 * @author system
 * @date 2022-12-10
 */
@ApiModel(description = "备案管理-熏蒸明细")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("record_fumigation_dtl")
public class FumigationDtl implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "熏蒸备案ID")
    private String fumigationId;

    @ApiModelProperty(value = "仓房/油罐代码")
    private String cfdm;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "粮食数量(吨)")
    private BigDecimal lssl;

    @ApiModelProperty(value = "水份")
    private BigDecimal sf;

    @ApiModelProperty(value = "杂质(%)")
    private BigDecimal zz;

    @ApiModelProperty(value = "粮温")
    private BigDecimal lw;

    @ApiModelProperty(value = "仓温")
    private BigDecimal cw;

    @ApiModelProperty(value = "仓内湿度")
    private BigDecimal cnsd;

    @ApiModelProperty(value = "储粮方式 1：散装储粮，2：包装，3：围包散存，9：其他")
    private String clfs;

    @ApiModelProperty(value = "入库日期")
    private LocalDate rkrq;

    @ApiModelProperty(value = "害虫")
    private String hc;

    @ApiModelProperty(value = "虫粮等级判定")
    private String cldjpd;

    @ApiModelProperty(value = "粮堆体积（立方米）")
    private BigDecimal ldtj;

    @ApiModelProperty(value = "空间体积（立方米）")
    private BigDecimal kjtj;

    @ApiModelProperty(value = "粮堆单位用药量")
    private BigDecimal lddwyyl;

    @ApiModelProperty(value = "空间单位用药量")
    private BigDecimal kjdwyyl;

    @ApiModelProperty(value = "总用药量")
    private BigDecimal zyyl;

    @ApiModelProperty(value = "气密性")
    private String qmx;

    @ApiModelProperty(value = "计划熏蒸开始日期")
    private LocalDate jhxzksrq;

    @ApiModelProperty(value = "计划熏蒸结束日期")
    private LocalDate jhxzjsrq;
}