package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.service.IMonitoringStatisticsService;
import com.sydata.monitoring.vo.MonitoringStatisticsCheckPointStatisticsVo;
import com.sydata.monitoring.vo.MonitoringStatisticsDetailEditDto;
import com.sydata.monitoring.vo.MonitoringStatisticsDetailVO;
import com.sydata.monitoring.vo.MonitoringStatisticsVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 流通检测/粮油流通统计 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-粮油流通统计")
@RestController
@RequestMapping("/monitoring/statistics")
public class MonitoringStatisticsController {
    @Resource
    private IMonitoringStatisticsService iMonitoringStatisticsService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsVO> list(@RequestBody MonitoringStatisticsQueryDTO queryDTO) {
        return iMonitoringStatisticsService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsDetailVO detailById(@RequestParam(value = "id") String id) {
        return iMonitoringStatisticsService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsEditDTO editDTO) {
        return iMonitoringStatisticsService.edit(editDTO);
    }

    @ApiOperation(value = "批量保存明细")
    @PostMapping(value = "/detail/edit")
    public Boolean detailEdit(@RequestBody @Validated MonitoringStatisticsDetailEditDto editDTO) {
        return iMonitoringStatisticsService.detailBatchEdit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsAddDTO addDTO) {
        return iMonitoringStatisticsService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsDeleteDTO deleteDTO) {
        return iMonitoringStatisticsService.delete(deleteDTO);
    }

    @ApiOperation(value = "粮食价格汇总分析")
    @PostMapping(value = "/price/statistics")
    public List<MonitoringStatisticsCheckPointStatisticsVo> priceStatistics(@RequestBody @Validated MonitoringStatisticsPriceCheckStatisticsDTO queryDTO) {
        return iMonitoringStatisticsService.priceStatistics(queryDTO);
    }
}
