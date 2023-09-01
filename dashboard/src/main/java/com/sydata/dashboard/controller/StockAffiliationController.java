package com.sydata.dashboard.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dashboard.param.*;
import com.sydata.dashboard.service.IStockAffiliationService;
import com.sydata.dashboard.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 报表管理-库存归属报表Controller
 *
 * @author system
 * @date 2023-04-21
 */
@Api(tags = "报表管理-库存归属报表")
@Validated
@RestController
@RequestMapping("/dashboard/stock/affiliation")
public class StockAffiliationController {

    @Resource
    private IStockAffiliationService stockAffiliationService;

    @ApiOperation("查询库存归属列表")
    @PostMapping("/page")
    public Page<StockAffiliationVo> page(@RequestBody StockAffiliationPageParam pageParam) {
        return stockAffiliationService.pages(pageParam);
    }

    @ApiOperation("库存统计")
    @PostMapping("/statistics")
    public StockAffiliationStatisticsVo statistics() {
        return stockAffiliationService.statistics();
    }

    @ApiOperation("储备粮实物库存报表")
    @PostMapping("/physical/inventory")
    public PhysicalInventoryPageVo physicalInventory(@RequestBody PhysicalInventoryQueryParam pageParam) {
        return stockAffiliationService.physicalInventoryPage(pageParam);
    }

    @ApiOperation("储备油实物库存报表")
    @PostMapping("/oil/physical/inventory")
    public PhysicalInventoryOilPageVo oilPhysicalInventory(@RequestBody PhysicalInventoryQueryParam pageParam) {
        return stockAffiliationService.oilPhysicalInventoryPage(pageParam);
    }

    @ApiOperation("储备粮实物库存报表导出")
    @PostMapping("/physical/export")
    public void physicalExport(@RequestBody PhysicalInventoryQueryParam pageParam) {
        stockAffiliationService.physicalExport(pageParam);
    }

    @ApiOperation("储备油实物库存报表导出")
    @PostMapping("/oil/physical/export")
    public void oilPhysicalExport(@RequestBody PhysicalInventoryQueryParam pageParam) {
        stockAffiliationService.oilPhysicalExport(pageParam);
    }

    @ApiOperation("粮食实时库存监督")
    @PostMapping("/inventory/monitoring")
    public List<InventoryMonitoringVo> inventoryMonitoring(@RequestBody InventoryMonitoringQueryParam param) {
        return stockAffiliationService.inventoryMonitoring(param);
    }

    @ApiOperation("储备粮油折合报表")
    @PostMapping("/reserve/grain/oil/equivalent")
    public List<ReserveGrainOilEquivalentVo> reserveGrainOilEquivalent(@RequestBody @Valid ReserveGrainOilEquivalentParam param) {
        return stockAffiliationService.reserveGrainOilEquivalent(param);
    }

    @ApiOperation("储备粮油折合报表excel模板下载")
    @PostMapping("/reserve/grain/oil/equivalent/excel/download")
    public void reserveGrainOilEquivalentExcelDownload() {
        stockAffiliationService.reserveGrainOilEquivalentExcelDownload();
    }

    @ApiOperation("储备粮油折合报表excel比对")
    @PostMapping(value = "/reserve/grain/oil/equivalent/excel/comparison")
    public void reserveGrainOilEquivalentExcelComparison(@Valid ReserveGrainOilEquivalentExcelComparisonParam param) {
        stockAffiliationService.reserveGrainOilEquivalentExcelComparison(param);
    }
}