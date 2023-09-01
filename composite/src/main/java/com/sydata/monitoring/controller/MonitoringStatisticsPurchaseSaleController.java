package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsPurchaseSaleService;
import com.sydata.monitoring.dto.MonitoringStatisticsPurchaseSaleDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsPurchaseSaleQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsPurchaseSaleEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsPurchaseSaleAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsPurchaseSaleVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/粮油购销信息 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-粮油购销信息")
@RestController
@RequestMapping("/monitoring/statistics/purchase/sale")
public class MonitoringStatisticsPurchaseSaleController{
    @Resource
    private IMonitoringStatisticsPurchaseSaleService iMonitoringStatisticsPurchaseSaleService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsPurchaseSaleVO> list(@RequestBody MonitoringStatisticsPurchaseSaleQueryDTO queryDTO){
        return iMonitoringStatisticsPurchaseSaleService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsPurchaseSaleVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsPurchaseSaleService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsPurchaseSaleEditDTO editDTO){
        return iMonitoringStatisticsPurchaseSaleService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsPurchaseSaleAddDTO addDTO){
        return iMonitoringStatisticsPurchaseSaleService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsPurchaseSaleDeleteDTO deleteDTO){
        return iMonitoringStatisticsPurchaseSaleService.delete(deleteDTO);
    }

}
