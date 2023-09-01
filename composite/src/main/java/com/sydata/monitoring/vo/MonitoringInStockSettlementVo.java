package com.sydata.monitoring.vo;

import com.sydata.common.basis.annotation.DataBindGranary;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.trade.vo.InStockVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhangcy
 * @since 2023/4/24 18:42
 */
@Data
@ApiModel("入库结算VO（用于采购类型的入库单）")
public class MonitoringInStockSettlementVo extends InStockVo {
    @ApiModelProperty
    private String kqdm;

    @DataBindStockHouse
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "仓房代码")
    private String cfdm;

    @ApiModelProperty(value = "廒间编号")
    private String ajdm;

    @DataBindWarehouse
    @ApiModelProperty(value = "仓房名称")
    private String cfmc;

    @DataBindGranary
    @ApiModelProperty(value = "廒间名称")
    private String ajmc;

    @ApiModelProperty(value = "数量")
    private BigDecimal qty;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "购销类型")
    private String salePurchaseType = "采购";

    public MonitoringInStockSettlementVo(InStockVo source) {
        if (source != null) {
            setDwmc(source.getDwmc());
            setHwmc(source.getHwmc());
            setBzwName(source.getBzwName());
            setYsgjName(source.getYsgjName());
            setJjlxName(source.getJjlxName());
            setYwlxName(source.getYwlxName());
            setLspzdmName(source.getLspzdmName());
            setLsxzdmName(source.getLsxzdmName());
            setId(source.getId());
            setRkywdh(source.getRkywdh());
            setHwdm(source.getHwdm());
            setYwlx(source.getYwlx());
            setYwrq(source.getYwrq());
            setJhmxh(source.getJhmxh());
            setHth(source.getHth());
            setCyr(source.getCyr());
            setLxdh(source.getLxdh());
            setSfzh(source.getSfzh());
            setXxdz(source.getXxdz());
            setYsgj(source.getYsgj());
            setCchlx(source.getCchlx());
            setCch(source.getCch());
            setGch(source.getGch());
            setLdd(source.getLdd());
            setDjsj(source.getDjsj());
            setDjmgryxm(source.getDjmgryxm());
            setLspzdm(source.getLspzdm());
            setLsxzdm(source.getLsxzdm());
            setShnd(source.getShnd());
            setCddm(source.getCddm());
            setJjlx(source.getJjlx());
            setMz(source.getMz());
            setMzjby(source.getMzjby());
            setMzjlsj(source.getMzjlsj());
            setMzjly(source.getMzjly());
            setZcy(source.getZcy());
            setPz(source.getPz());
            setPzjby(source.getPzjby());
            setPzjlsj(source.getPzjlsj());
            setPzjly(source.getPzjly());
            setBzw(source.getBzw());
            setBzbdbz(source.getBzbdbz());
            setBzbjs(source.getBzbjs());
            setZjklxj(source.getZjklxj());
            setQzsfzkl(source.getQzsfzkl());
            setQzzzzkl(source.getQzzzzkl());
            setQzbwslkl(source.getQzbwslkl());
            setQzhhkl(source.getQzhhkl());
            setQzsmlkl(source.getQzsmlkl());
            setQzzjmlkl(source.getQzzjmlkl());
            setQzgwcmkl(source.getQzgwcmkl());
            setQzhlmkl(source.getQzhlmkl());
            setQzqtkl(source.getQzqtkl());
            setZlfyzkl(source.getZlfyzkl());
            setBzwkl(source.getBzwkl());
            setQtkl(source.getQtkl());
            setKlyy(source.getKlyy());
            setXckl(source.getXckl());
            setZkj(source.getZkj());
            setZkhyy(source.getZkhyy());
            setJz(source.getJz());
            setZxzydw(source.getZxzydw());
            setCmsj(source.getCmsj());
            setCmqrmgryxm(source.getCmqrmgryxm());
            setRkjsdh(source.getRkjsdh());
            setBz(source.getBz());
            setCzbz(source.getCzbz());
            setZhgxsj(source.getZhgxsj());
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
