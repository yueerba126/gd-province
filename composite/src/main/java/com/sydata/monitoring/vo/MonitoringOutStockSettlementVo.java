package com.sydata.monitoring.vo;

import com.sydata.common.basis.annotation.*;
import com.sydata.trade.vo.OutStockVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhangcy
 * @since 2023/4/24 18:42
 */
@Data
@ApiModel("出库结算VO（用于销售类型的出库单）")
public class MonitoringOutStockSettlementVo extends OutStockVo {

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @DataBindStockHouse
    @ApiModelProperty(value = "库区代码")
    private String kqmc;

    @ApiModelProperty(value = "仓房代码")
    private String cfdm;

    @ApiModelProperty(value = "廒间编号")
    private String ajdm;

    @DataBindWarehouse
    @ApiModelProperty(value = "仓房名称")
    private String cfmc;

    @ApiModelProperty(value = "廒间名称")
    private String ajmc;

    @ApiModelProperty(value = "数量")
    private BigDecimal qty;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "购销类型")
    private String salePurchaseType = "销售";

    public MonitoringOutStockSettlementVo(OutStockVo source) {
        if (source != null) {
            setDwmc(source.getDwmc());
            setHwmc(source.getHwmc());
            setBzwName(source.getBzwName());
            setYwlxName(source.getYwlxName());
            setYsgjName(source.getYsgjName());
            setLspzdmName(source.getLspzdmName());
            setLsdjdmName(source.getLsdjdmName());
            setLsxzdmName(source.getLsxzdmName());
            setId(source.getId());
            setCkywdh(source.getCkywdh());
            setHwdm(source.getHwdm());
            setCktzdh(source.getCktzdh());
            setYwlx(source.getYwlx());
            setYwrq(source.getYwrq());
            setHth(source.getHth());
            setCyr(source.getCyr());
            setLxdh(source.getLxdh());
            setSfzh(source.getSfzh());
            setYsgj(source.getYsgj());
            setXldd(source.getXldd());
            setCchlx(source.getCchlx());
            setCch(source.getCch());
            setGch(source.getGch());
            setDjsj(source.getDjsj());
            setDjmgryxm(source.getDjmgryxm());
            setLspzdm(source.getLspzdm());
            setLsdjdm(source.getLsdjdm());
            setLsxzdm(source.getLsxzdm());
            setShnd(source.getShnd());
            setCddm(source.getCddm());
            setPz(source.getPz());
            setPzjby(source.getPzjby());
            setPzjlsj(source.getPzjlsj());
            setPzjly(source.getPzjly());
            setMz(source.getMz());
            setMzjby(source.getMzjby());
            setMzjlsj(source.getMzjlsj());
            setMzjly(source.getMzjly());
            setBzw(source.getBzw());
            setBzbdbz(source.getBzbdbz());
            setBzbjs(source.getBzbjs());
            setJz(source.getJz());
            setKzl(source.getKzl());
            setZcbgyxm(source.getZcbgyxm());
            setZxzydw(source.getZxzydw());
            setCmsj(source.getCmsj());
            setCmqrmgryxm(source.getCmqrmgryxm());
            setCkjsdh(source.getCkjsdh());
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
