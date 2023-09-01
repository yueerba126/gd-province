package com.sydata.chart.pojo.steam;

import com.sydata.common.basis.annotation.DataBindWarehouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;


/**
 * 熏蒸作业-粮仓基本情况
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "熏蒸作业-粮仓基本情况")
public class WarehouseInfo {

    @ApiModelProperty(value = "仓房代码")
    private String cfdm;

    @DataBindWarehouse
    @ApiModelProperty(value = "仓房名称")
    private String cfmc;

    @ApiModelProperty("仓房类型代码")
    private String cflxdm;

    @ApiModelProperty("品种代码，#号分割")
    private String lspzdm;

    @ApiModelProperty("品种,#号分割")
    private String lspzdmName;

    @ApiModelProperty("数量")
    private BigDecimal sl;

    @ApiModelProperty("收获年限")
    private int shnx;

    @ApiModelProperty("入库年限")
    private int rknx;

    @ApiModelProperty("历年熏蒸情况")
    private List<SteamHistory> lnxzqk;
}
