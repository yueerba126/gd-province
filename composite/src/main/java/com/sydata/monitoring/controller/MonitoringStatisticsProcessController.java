package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsProcessService;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/粮油加工信息 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-粮油加工信息")
@RestController
@RequestMapping("/monitoring/statistics/process")
public class MonitoringStatisticsProcessController{
    @Resource
    private IMonitoringStatisticsProcessService iMonitoringStatisticsProcessService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsProcessVO> list(@RequestBody MonitoringStatisticsProcessQueryDTO queryDTO){
        return iMonitoringStatisticsProcessService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsProcessVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsProcessService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsProcessEditDTO editDTO){
        return iMonitoringStatisticsProcessService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsProcessAddDTO addDTO){
        return iMonitoringStatisticsProcessService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsProcessDeleteDTO deleteDTO){
        return iMonitoringStatisticsProcessService.delete(deleteDTO);
    }

}
