package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsStorageFacilitiesService;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsStorageFacilitiesAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsStorageFacilitiesVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/仓储设施 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-仓储设施")
@RestController
@RequestMapping("/monitoring/statistics/storage/facilities")
public class MonitoringStatisticsStorageFacilitiesController{
    @Resource
    private IMonitoringStatisticsStorageFacilitiesService iMonitoringStatisticsStorageFacilitiesService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsStorageFacilitiesVO> list(@RequestBody MonitoringStatisticsStorageFacilitiesQueryDTO queryDTO){
        return iMonitoringStatisticsStorageFacilitiesService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsStorageFacilitiesVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsStorageFacilitiesService.detailById(id);
    }

    @ApiOperation(value = "根据粮油统计id查询")
    @PostMapping(value = "/statistics/detail")
    public MonitoringStatisticsStorageFacilitiesVO detailByStatisticsId(@RequestParam(value = "statisticsId") String statisticsId){
        return iMonitoringStatisticsStorageFacilitiesService.getByStatisticsId(statisticsId);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsStorageFacilitiesEditDTO editDTO){
        return iMonitoringStatisticsStorageFacilitiesService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsStorageFacilitiesAddDTO addDTO){
        return iMonitoringStatisticsStorageFacilitiesService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsStorageFacilitiesDeleteDTO deleteDTO){
        return iMonitoringStatisticsStorageFacilitiesService.delete(deleteDTO);
    }

}
