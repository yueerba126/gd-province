package com.sydata.monitoring.vo;

import com.sydata.monitoring.entity.CheckPoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhangcy
 * @since 2023/4/25 16:39
 */
@Data
@ApiModel("检查点配置VO")
public class CheckPointConfigVo extends CheckPoint {

    @ApiModelProperty("价格列表：字典monitoring_price")
    private List<String> priceList;

    @ApiModelProperty("品种列表：字典food_variety")
    private List<String> materialTypeList;

    public CheckPointConfigVo(CheckPoint source) {
        if (source != null) {
            setId(source.getId());
            setName(source.getName());
            setStatus(source.getStatus());
            setJd(source.getJd());
            setWd(source.getWd());
            setAccountUserId(source.getAccountUserId());
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
