package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsProcessingRotationService;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessingRotationDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessingRotationQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessingRotationEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsProcessingRotationAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsProcessingRotationVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/加工轮换 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-加工轮换")
@RestController
@RequestMapping("/monitoring/statistics/processing/rotation")
public class MonitoringStatisticsProcessingRotationController{
    @Resource
    private IMonitoringStatisticsProcessingRotationService iMonitoringStatisticsProcessingRotationService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsProcessingRotationVO> list(@RequestBody MonitoringStatisticsProcessingRotationQueryDTO queryDTO){
        return iMonitoringStatisticsProcessingRotationService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsProcessingRotationVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsProcessingRotationService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsProcessingRotationEditDTO editDTO){
        return iMonitoringStatisticsProcessingRotationService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsProcessingRotationAddDTO addDTO){
        return iMonitoringStatisticsProcessingRotationService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsProcessingRotationDeleteDTO deleteDTO){
        return iMonitoringStatisticsProcessingRotationService.delete(deleteDTO);
    }

}
