package com.sydata.chart.vo;

import com.sydata.chart.pojo.cargo.Stock;
import com.sydata.chart.pojo.cargo.StockCheck;
import com.sydata.common.basis.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 货位信息卡片
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@ApiModel(description = "货位信息卡片Vo")
@Data
@ToString
@Accessors(chain = true)
public class CargoChartVo implements Serializable {

    @ApiModelProperty(value = "企业代码")
    private String enterpriseId;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "统计报账单位")
    private String tjbzdw;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @DataBindStockHouse
    @ApiModelProperty(value = "保管单位")
    private String bgdw;

    @ApiModelProperty("仓房代码")
    private String cfdm;

    @DataBindWarehouse
    @ApiModelProperty("仓房名称")
    private String cfmc;

    @ApiModelProperty("货位代码")
    private String hwdm;

    @DataBindCargo
    @ApiModelProperty("货位名称")
    private String hwmc;

    @DataBindCargo(dataValue = "#bgy")
    @ApiModelProperty("保管员")
    private String bgy;

    @ApiModelProperty("仓房类型代码")
    private String cflxdm;

    @DataBindDict(sourceField = "#cflxdm", sourceFieldCombination = "cflxdm")
    @ApiModelProperty("仓房类型名称")
    private String cflxdmName;

    @ApiModelProperty("设计仓容")
    private BigDecimal sjcr;

    @ApiModelProperty("货位状态")
    private String hwzt;

    @DataBindDict(sourceField = "#hwzt", sourceFieldCombination = "hwzt")
    @ApiModelProperty("货位状态名称")
    private String hwztName;

    @ApiModelProperty("库存信息")
    private List<Stock> kcxx;

    @ApiModelProperty("检验记录")
    private List<StockCheck> jyjl;
}
