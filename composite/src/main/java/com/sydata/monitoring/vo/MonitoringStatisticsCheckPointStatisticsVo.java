package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.MonitoringStatisticsPriceCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangcy
 * @since 2023/4/24 11:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("粮食价格汇总分析VO")
public class MonitoringStatisticsCheckPointStatisticsVo extends MonitoringStatisticsPriceCheck {

    public MonitoringStatisticsCheckPointStatisticsVo(MonitoringStatisticsPriceCheckVO source) {
        if (source != null) {
            setId(source.getId());
            setStatisticId(source.getStatisticId());
            setMaterialType(source.getMaterialType());
            setIncomingPrice(source.getIncomingPrice());
            setFactoryPrice(source.getFactoryPrice());
            setRetailPrice(source.getRetailPrice());
            setSalePrice(source.getSalePrice());
            setTradePrice(source.getTradePrice());
            setCreateBy(source.getCreateBy());
            setCreateTime(source.getCreateTime());
            setUpdateBy(source.getUpdateBy());
            setUpdateTime(source.getUpdateTime());
            setRegionId(source.getRegionId());
            setCountryId(source.getCountryId());
            setProvinceId(source.getProvinceId());
            setCityId(source.getCityId());
            setAreaId(source.getAreaId());
            setEnterpriseId(source.getEnterpriseId());
            setStockHouseId(source.getStockHouseId());
        }
    }
}
