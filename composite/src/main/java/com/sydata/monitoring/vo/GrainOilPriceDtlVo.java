package com.sydata.monitoring.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.monitoring.entity.GrainOilPriceDtl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangcy
 * @since 2023/4/24 17:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("粮油加个采集明细VO")
public class GrainOilPriceDtlVo extends GrainOilPriceDtl {

    @DataBindCompany
    @ApiModelProperty(value = "企业名称")
    private String dwmc;

    @DataBindStockHouse(sourceField = "#storeHouseId")
    @ApiModelProperty(value = "库点名称")
    private String kqmc;

    @ApiModelProperty(value = "仓库")
    private String cfmc;

    @ApiModelProperty(value = "廒间")
    private String ajmc;

    @ApiModelProperty(value = "堆位")
    private String hwmc;

    public GrainOilPriceDtlVo(GrainOilPriceDtl source) {

        if (source != null) {
            setId(source.getId());
            setSalePurchaseType(getSalePurchaseType());
            setMainId(source.getMainId());
            setBillCode(source.getBillCode());
            setMonitorDate(source.getMonitorDate());
            setBillDate(source.getBillDate());
            setVolatility(source.getVolatility());
            setQty(source.getQty());
            setBastQty(source.getBastQty());
            setBastUnit(source.getBastUnit());
            setTaxPrice(source.getTaxPrice());
            setHwdm(source.getHwdm());
            setStockHouseId(source.getStockHouseId());
            setCfdm(source.getCfdm());
            setAjdm(source.getAjdm());
            setHwdm(source.getHwdm());
            setMaterialName(source.getMaterialName());
            setLspzdm(source.getLspzdm());
            setAdministrativeDivisions(source.getAdministrativeDivisions());
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
