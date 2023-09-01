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
 * @Author chenzx
 * @Date 2022/8/19 11:09
 * @Description: 粮食库存
 * @Version 1.0
 */
@ApiModel(description = "粮油购销-粮食库存")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("trade_stock_grain")
public class StockGrain extends BaseFiledEntity implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "收货年度")
    private String shnd;

    @ApiModelProperty(value = "国别")
    private String gb;

    @ApiModelProperty(value = "产地")
    private String cd;

    @ApiModelProperty(value = "保管员")
    private String bgy;

    @ApiModelProperty(value = "粮权归属单位代码")
    private String lqgsdwdm;

    @ApiModelProperty(value = "粮权行政区划代码")
    private String lqxzqhdm;

    @ApiModelProperty(value = "粮权行政区划类型：国、省、市、区")
    private String lqxzqhType;

    @ApiModelProperty(value = "管理方式 01：直储，02：代储，03，租仓")
    private String glfs;

    @ApiModelProperty(value = "收储地点 1：库内。2：库外")
    private String scdd;

    @ApiModelProperty(value = "储粮方式 1：散装储粮，2：包装，3：围包散存，9：其他")
    private String clfs;

    @ApiModelProperty(value = "货位（油罐）状态1：空仓，2：入库中，3：封仓，4：出库中，9：其他")
    private String hwzt;

    @ApiModelProperty(value = "入仓时间")
    private LocalDateTime rcsj;

    @ApiModelProperty(value = "封仓日期")
    private LocalDate fcrq;

    @ApiModelProperty(value = "出仓完成时间")
    private LocalDateTime ccwcsj;

    @ApiModelProperty(value = "清仓时间")
    private LocalDateTime qcsj;

    @ApiModelProperty(value = "成货位前损耗")
    private BigDecimal chwqsh;

    @ApiModelProperty(value = "实际数量(公斤)")
    private BigDecimal sjsl;

    @ApiModelProperty(value = "计价数量（公斤）")
    private BigDecimal jjsl;

    @ApiModelProperty(value = "包存量包数（包）")
    private Integer bclbs;

    @ApiModelProperty(value = "实际装粮线高（米）")
    private BigDecimal sjzlxg;

    @ApiModelProperty(value = "粮堆体积（立方米）")
    private BigDecimal ldtj;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "操作标志 i：新增，u：更新，d：删除")
    private String czbz;

    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
