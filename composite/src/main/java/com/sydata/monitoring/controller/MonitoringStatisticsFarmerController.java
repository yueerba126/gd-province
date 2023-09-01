package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsFarmerService;
import com.sydata.monitoring.dto.MonitoringStatisticsFarmerDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsFarmerQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsFarmerEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsFarmerAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsFarmerVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/居民农户信息 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-居民农户信息")
@RestController
@RequestMapping("/monitoring/statistics/farmer")
public class MonitoringStatisticsFarmerController{
    @Resource
    private IMonitoringStatisticsFarmerService iMonitoringStatisticsFarmerService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsFarmerVO> list(@RequestBody MonitoringStatisticsFarmerQueryDTO queryDTO){
        return iMonitoringStatisticsFarmerService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsFarmerVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsFarmerService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsFarmerEditDTO editDTO){
        return iMonitoringStatisticsFarmerService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsFarmerAddDTO addDTO){
        return iMonitoringStatisticsFarmerService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsFarmerDeleteDTO deleteDTO){
        return iMonitoringStatisticsFarmerService.delete(deleteDTO);
    }

}
