package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsStorehouseService;
import com.sydata.monitoring.dto.MonitoringStatisticsStorehouseDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorehouseQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorehouseEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorehouseAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsStorehouseVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/库点信息 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-库点信息")
@RestController
@RequestMapping("/monitoring/statistics/storehouse")
public class MonitoringStatisticsStorehouseController{
    @Resource
    private IMonitoringStatisticsStorehouseService iMonitoringStatisticsStorehouseService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsStorehouseVO> list(@RequestBody MonitoringStatisticsStorehouseQueryDTO queryDTO){
        return iMonitoringStatisticsStorehouseService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsStorehouseVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsStorehouseService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsStorehouseEditDTO editDTO){
        return iMonitoringStatisticsStorehouseService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsStorehouseAddDTO addDTO){
        return iMonitoringStatisticsStorehouseService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsStorehouseDeleteDTO deleteDTO){
        return iMonitoringStatisticsStorehouseService.delete(deleteDTO);
    }

}
