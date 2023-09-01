package com.sydata.monitoring.entity;

import com.sydata.basis.domain.Granary;
import com.sydata.basis.domain.Warehouse;
import com.sydata.trade.domain.Contract;
import com.sydata.trade.domain.InStockSettlement;
import com.sydata.trade.domain.OutStockSettlement;

import java.util.Map;

/**
 * 入库关联信息
 *
 * @author zhangcy
 * @since 20230425
 */
public class StockRelationInfo {
    /**
     * Map<合同号, 合同信息>
     */
    private Map<String, Contract> contractMap;
    /**
     * Map<入库结算单号, 入库结算单信息>
     */
    private Map<String, InStockSettlement> inStockSettlementMap;
    /**
     * Map<出库结算单号, 出库结算单信息>
     */
    private Map<String, OutStockSettlement> outStockSettlementMap;
    /**
     * Map<货位代码, 廒间代码>
     */
    private Map<String, String> cargoGranaryCodeMap;
    /**
     * Map<廒间代码, 廒间信息>
     */
    private Map<String, Granary> granaryMap;
    /**
     * Map<仓房代码, 仓房信息>
     */
    private Map<String, Warehouse> warehouseMap;


    public Map<String, Granary> getGranaryMap() {
        return granaryMap;
    }

    public void setGranaryMap(Map<String, Granary> granaryMap) {
        this.granaryMap = granaryMap;
    }

    public Map<String, Contract> getContractMap() {
        return contractMap;
    }

    public Map<String, InStockSettlement> getInStockSettlementMap() {
        return inStockSettlementMap;
    }

    public Map<String, String> getCargoGranaryCodeMap() {
        return cargoGranaryCodeMap;
    }

    public void setContractMap(Map<String, Contract> contractMap) {
        this.contractMap = contractMap;
    }

    public void setInStockSettlementMap(Map<String, InStockSettlement> inStockSettlementMap) {
        this.inStockSettlementMap = inStockSettlementMap;
    }

    public Map<String, OutStockSettlement> getOutStockSettlementMap() {
        return outStockSettlementMap;
    }

    public void setOutStockSettlementMap(Map<String, OutStockSettlement> outStockSettlementMap) {
        this.outStockSettlementMap = outStockSettlementMap;
    }

    public void setCargoGranaryCodeMap(Map<String, String> cargoGranaryCodeMap) {
        this.cargoGranaryCodeMap = cargoGranaryCodeMap;
    }

    public Map<String, Warehouse> getWarehouseMap() {
        return warehouseMap;
    }

    public void setWarehouseMap(Map<String, Warehouse> warehouseMap) {
        this.warehouseMap = warehouseMap;
    }
}