package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsInfrastructureConstructionService;
import com.sydata.monitoring.dto.MonitoringStatisticsInfrastructureConstructionDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsInfrastructureConstructionQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsInfrastructureConstructionEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsInfrastructureConstructionAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsInfrastructureConstructionVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/基础设施建设信息 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-基础设施建设信息")
@RestController
@RequestMapping("/monitoring/statistics/infrastructure/construction")
public class MonitoringStatisticsInfrastructureConstructionController{
    @Resource
    private IMonitoringStatisticsInfrastructureConstructionService iMonitoringStatisticsInfrastructureConstructionService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsInfrastructureConstructionVO> list(@RequestBody MonitoringStatisticsInfrastructureConstructionQueryDTO queryDTO){
        return iMonitoringStatisticsInfrastructureConstructionService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsInfrastructureConstructionVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsInfrastructureConstructionService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsInfrastructureConstructionEditDTO editDTO){
        return iMonitoringStatisticsInfrastructureConstructionService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsInfrastructureConstructionAddDTO addDTO){
        return iMonitoringStatisticsInfrastructureConstructionService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsInfrastructureConstructionDeleteDTO deleteDTO){
        return iMonitoringStatisticsInfrastructureConstructionService.delete(deleteDTO);
    }

}
