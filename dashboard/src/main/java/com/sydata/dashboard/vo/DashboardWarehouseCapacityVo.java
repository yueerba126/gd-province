package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.dashboard.domain.DashboardWarehouseCapacity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * @author zhangcy
 * @since 2023/5/6 9:30
 */
@Data
@ApiModel("库容总览统计VO")
public class DashboardWarehouseCapacityVo extends DashboardWarehouseCapacity {

    @ApiModelProperty("利用率（%）")
    private BigDecimal useRatio;
    /**
     * 仓房类型
     */
    @DataBindDict(sourceField = "#cflxdm", sourceFieldCombination = "cflx")
    @ApiModelProperty(name = "仓房类型名称")
    private String cflxmc;


    @DataBindWarehouse
    @ApiModelProperty(name = "仓房名称")
    private String cfmc;

    @DataBindStockHouse(sourceField = "#stockHouseId")
    @ApiModelProperty(name = "库区名称")
    private String kqmc;

    public DashboardWarehouseCapacityVo(DashboardWarehouseCapacity source) {
        if (source != null) {
            setId(source.getId());
            setJfsyrq(source.getJfsyrq());
            setCfdm(source.getCfdm());
            setCflxdm(source.getCflxdm());
            setRegionId(source.getRegionId());
            setCountryId(source.getCountryId());
            setProvinceId(source.getProvinceId());
            setCityId(source.getCityId());
            setAreaId(source.getAreaId());
            setEnterpriseId(source.getEnterpriseId());
            setStockHouseId(source.getStockHouseId());

            // 设计仓容、计价数量转为万吨
            setSjcr(source.getSjcr());
            setJjsl(source.getJjsl());

            calculateUseRatio();
        }
    }

    public DashboardWarehouseCapacityVo() {
    }

    @Override
    public void setSjcr(BigDecimal sjcr) {
        super.setSjcr(Optional.ofNullable(sjcr).map(cr -> cr.movePointLeft(4).setScale(3, RoundingMode.HALF_UP)).orElse(BigDecimal.ZERO));
    }

    @Override
    public void setJjsl(BigDecimal jjsl) {
        super.setJjsl(Optional.ofNullable(jjsl).map(cr -> cr.movePointLeft(4).setScale(3, RoundingMode.HALF_UP)).orElse(BigDecimal.ZERO));
    }

    /**
     * 计算利用率
     */
    public void calculateUseRatio() {
        if (getSjcr().compareTo(BigDecimal.ZERO) > 0) {
            setUseRatio(getJjsl().divide(getSjcr(), 4, RoundingMode.HALF_UP).movePointRight(2));
        }
    }
}
