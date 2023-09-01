package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsStockScaleService;
import com.sydata.monitoring.dto.MonitoringStatisticsStockScaleDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStockScaleQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStockScaleEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStockScaleAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsStockScaleVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/库存规模 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-库存规模")
@RestController
@RequestMapping("/monitoring/statistics/stock/scale")
public class MonitoringStatisticsStockScaleController{
    @Resource
    private IMonitoringStatisticsStockScaleService iMonitoringStatisticsStockScaleService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsStockScaleVO> list(@RequestBody MonitoringStatisticsStockScaleQueryDTO queryDTO){
        return iMonitoringStatisticsStockScaleService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsStockScaleVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsStockScaleService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsStockScaleEditDTO editDTO){
        return iMonitoringStatisticsStockScaleService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsStockScaleAddDTO addDTO){
        return iMonitoringStatisticsStockScaleService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsStockScaleDeleteDTO deleteDTO){
        return iMonitoringStatisticsStockScaleService.delete(deleteDTO);
    }

}
