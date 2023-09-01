package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.service.IMonitoringStatisticsPriceCheckService;
import com.sydata.monitoring.vo.MonitoringStatisticsPriceCheckVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/价格检测信息 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-价格检测信息")
@RestController
@RequestMapping("/monitoring/statistics/price/check")
public class MonitoringStatisticsPriceCheckController{
    @Resource
    private IMonitoringStatisticsPriceCheckService iMonitoringStatisticsPriceCheckService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsPriceCheckVO> list(@RequestBody MonitoringStatisticsPriceCheckQueryDTO queryDTO){
        return iMonitoringStatisticsPriceCheckService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsPriceCheckVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsPriceCheckService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsPriceCheckEditDTO editDTO){
        return iMonitoringStatisticsPriceCheckService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsPriceCheckAddDTO addDTO){
        return iMonitoringStatisticsPriceCheckService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsPriceCheckDeleteDTO deleteDTO){
        return iMonitoringStatisticsPriceCheckService.delete(deleteDTO);
    }
}
