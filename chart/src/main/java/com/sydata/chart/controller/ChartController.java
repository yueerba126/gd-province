package com.sydata.chart.controller;

import com.sydata.chart.service.IChartService;
import com.sydata.chart.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lzq
 * @description 卡片信息Api
 * @date 2022/10/11 18:11
 */
@Api(tags = "卡片信息Api")
@Validated
@RestController
@RequestMapping("/chart")
public class ChartController {

    @Resource
    private IChartService charService;

    @ApiOperation("质检报告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "质检数据id", dataTypeClass = String.class, required = true, example = "key"),
    })
    @PostMapping("/quality/check")
    public QualityCheckChartVo qualityCheckReport(@RequestParam("id") String id) {
        return charService.qualityCheckChart(id);
    }


    @ApiOperation("入库检斤质检结算单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "入库信息id", dataTypeClass = String.class, required = true, example = "key"),
    })
    @PostMapping("/in/stock/check")
    public InStockCheckChartVo inStockCheckReport(@RequestParam("id") String id) {
        return charService.inStockCheckChart(id);
    }


    @ApiOperation("出库检斤质检结算单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "出库信息id", dataTypeClass = String.class, required = true, example = "key"),
    })
    @PostMapping("/out/stock/check")
    public OutStockCheckChartVo outStockCheckReport(@RequestParam("id") String id) {
        return charService.outStockCheckChart(id);
    }

    @ApiOperation("货位卡片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "货位id", dataTypeClass = String.class, required = true, example = "key"),
    })
    @PostMapping("/cargo")
    public CargoChartVo cargoChart(@RequestParam("id") String id) {
        return charService.cargoChart(id);
    }

    @ApiOperation("损溢单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "损溢单id", dataTypeClass = String.class, required = true, example = "key"),
    })
    @PostMapping("/loss")
    public LossChartVo lossReport(@RequestParam("id") String id) {
        return charService.lossChart(id);
    }


    @ApiOperation("性质转变单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "性质转变单id", dataTypeClass = String.class, required = true, example = "key"),
    })
    @PostMapping("/transfer/nature")
    public TransferNatureChartVo transferNatureChart(@RequestParam("id") String id) {
        return charService.transferNatureChar(id);
    }


    @ApiOperation("通风作业卡片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "通风作业id", dataTypeClass = String.class, required = true, example = "key"),
    })
    @PostMapping("/ventilation")
    public VentilationChartVo ventilationChart(@RequestParam("id") String id) {
        return charService.ventilationChart(id);
    }


    @ApiOperation("熏蒸作业卡片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "熏蒸作业id", dataTypeClass = String.class, required = true, example = "key"),
    })
    @PostMapping("/steam/task")
    public SteamTaskChartVo steamTaskChart(@RequestParam("id") String id) {
        return charService.steamTaskChart(id);
    }
}
