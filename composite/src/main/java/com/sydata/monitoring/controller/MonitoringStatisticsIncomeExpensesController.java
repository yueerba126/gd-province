package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsIncomeExpensesService;
import com.sydata.monitoring.dto.MonitoringStatisticsIncomeExpensesDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsIncomeExpensesQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsIncomeExpensesEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsIncomeExpensesAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsIncomeExpensesVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/粮油收支平衡数据 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-粮油收支平衡数据")
@RestController
@RequestMapping("/monitoring/statistics/income/expenses")
public class MonitoringStatisticsIncomeExpensesController{
    @Resource
    private IMonitoringStatisticsIncomeExpensesService iMonitoringStatisticsIncomeExpensesService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsIncomeExpensesVO> list(@RequestBody MonitoringStatisticsIncomeExpensesQueryDTO queryDTO){
        return iMonitoringStatisticsIncomeExpensesService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsIncomeExpensesVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsIncomeExpensesService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsIncomeExpensesEditDTO editDTO){
        return iMonitoringStatisticsIncomeExpensesService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsIncomeExpensesAddDTO addDTO){
        return iMonitoringStatisticsIncomeExpensesService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsIncomeExpensesDeleteDTO deleteDTO){
        return iMonitoringStatisticsIncomeExpensesService.delete(deleteDTO);
    }

}
