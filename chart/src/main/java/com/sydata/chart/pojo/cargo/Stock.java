package com.sydata.chart.pojo.cargo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 货位卡片-库存信息
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "货位卡片-库存信息")
public class Stock {

    @ApiModelProperty(value = "存储方式")
    private String glfs;

    @DataBindDict(sourceField = "#glfs", sourceFieldCombination = "glfs")
    @ApiModelProperty(value = "存储方式名称")
    private String glfsName;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;

    @ApiModelProperty(value = "收储地点")
    private String scdd;

    @DataBindDict(sourceFieldCombination = "scdd", sourceField = "#scdd")
    @ApiModelProperty(value = "收储地点名称")
    private String scddName;

    @ApiModelProperty(value = "储粮方式")
    private String clfs;

    @DataBindDict(sourceFieldCombination = "clfs", sourceField = "#scdd")
    @ApiModelProperty(value = "储粮方式名称")
    private String clfsName;

    @ApiModelProperty(value = "实际数量(公斤)")
    private BigDecimal sjsl;

    @ApiModelProperty(value = "计价数量（公斤）")
    private BigDecimal jjsl;

    @ApiModelProperty(value = "实际装粮线高（米）")
    private BigDecimal sjzlxg;

    @ApiModelProperty(value = "包存量包数（包）")
    private Integer bclbs;

    @ApiModelProperty(value = "产地")
    private String cd;

    @DataBindRegion(sourceField = "#cd")
    @ApiModelProperty("产地名称")
    private String cdName;

    @ApiModelProperty(value = "收货年度")
    private String shnd;

    @ApiModelProperty(value = "入仓时间")
    private LocalDateTime rcsj;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;
}
