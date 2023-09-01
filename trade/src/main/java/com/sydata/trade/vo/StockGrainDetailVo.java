package com.sydata.trade.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.trade.domain.StockGrainNewest;
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

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;

/**
 * @author lzq
 * @description 粮油购销-粮食库存明细VO
 * @date 2023/4/21 10:28
 */
@ApiModel(description = "粮油购销-粮食库存明细")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockGrainDetailVo  implements Serializable {

    @Excel(name = "地市")
    @DataBindRegion(sourceField = "#cityId")
    @ApiModelProperty("地市")
    private String cityName;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @Excel(name = "粮权归属单位名称")
    @DataBindCompany(sourceField = "#lqgsdwdm")
    @ApiModelProperty(value = "粮权归属单位名称")
    private String lqgsdwdmName;

    @ApiModelProperty(value = "粮权归属单位代码")
    private String lqgsdwdm;

    @Excel(name = "粮权行政区划名称")
    @DataBindRegion(sourceField = "#lqxzqhdm")
    @ApiModelProperty(value = "粮权行政区划名称")
    private String lqxzqhdmName;

    @ApiModelProperty(value = "粮权行政区划代码")
    private String lqxzqhdm;

    @Excel(name = "库点名称")
    @DataBindCompany(sourceField = "#stockHouseId")
    @ApiModelProperty(value = "库点名称")
    private String kdmc;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @DataBindGranary(dataValue = "#cfbh")
    @ApiModelProperty("仓房编号")
    private String cfdm;

    @Excel(name = "仓房名称")
    @DataBindWarehouse()
    @ApiModelProperty("仓房名称")
    private String cfmc;

    @Excel(name = "货位代码")
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @Excel(name = "存储量（吨）")
    @ApiModelProperty(value = "存储量（吨）")
    private BigDecimal sjsl;

    @Excel(name = "粮食性质")
    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @Excel(name = "粮食等级")
    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @Excel(name = "粮食品种(大类)")
    @ApiModelProperty(value = "粮食品种(大类)")
    private String lspzlb;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @Excel(name = "粮食品种名称")
    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @Excel(name = "入仓时间")
    @ApiModelProperty(value = "入仓时间")
    private LocalDateTime rcsj;

    @Excel(name = "生产年份")
    @ApiModelProperty(value = "生产年份")
    private String shnd;

    @Excel(name = "上报时间")
    @ApiModelProperty(value = "上报时间")
    private LocalDateTime zhgxsj;



}
