package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsCheckPointService;
import com.sydata.monitoring.dto.MonitoringStatisticsCheckPointDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsCheckPointQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsCheckPointEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsCheckPointAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsCheckPointVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/粮食市场监测点信息 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-粮食市场监测点信息")
@RestController
@RequestMapping("/monitoring/statistics/check/point")
public class MonitoringStatisticsCheckPointController{
    @Resource
    private IMonitoringStatisticsCheckPointService iMonitoringStatisticsCheckPointService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsCheckPointVO> list(@RequestBody MonitoringStatisticsCheckPointQueryDTO queryDTO){
        return iMonitoringStatisticsCheckPointService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsCheckPointVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsCheckPointService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsCheckPointEditDTO editDTO){
        return iMonitoringStatisticsCheckPointService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsCheckPointAddDTO addDTO){
        return iMonitoringStatisticsCheckPointService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsCheckPointDeleteDTO deleteDTO){
        return iMonitoringStatisticsCheckPointService.delete(deleteDTO);
    }

}
