package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.service.IMonitoringStatisticsScienceInfoService;
import com.sydata.monitoring.dto.MonitoringStatisticsScienceInfoDeleteDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsScienceInfoQueryDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsScienceInfoEditDTO;
import com.sydata.monitoring.dto.MonitoringStatisticsScienceInfoAddDTO;
import com.sydata.monitoring.vo.MonitoringStatisticsScienceInfoVO;




import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/粮油科技信息 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/26
 */
@Api(tags = "流通检测-粮油科技信息")
@RestController
@RequestMapping("/monitoring/statistics/science/info")
public class MonitoringStatisticsScienceInfoController{
    @Resource
    private IMonitoringStatisticsScienceInfoService iMonitoringStatisticsScienceInfoService;

    @ApiOperation(value = "分页查询")
    @PostMapping("/list")
    public Page<MonitoringStatisticsScienceInfoVO> list(@RequestBody MonitoringStatisticsScienceInfoQueryDTO queryDTO){
        return iMonitoringStatisticsScienceInfoService.page(queryDTO);
    }

    @ApiOperation(value = "根据id查询")
    @PostMapping(value = "/detail")
    public MonitoringStatisticsScienceInfoVO detailById(@RequestParam(value = "id") String id){
        return iMonitoringStatisticsScienceInfoService.detailById(id);
    }

    @ApiOperation(value = "保存/修改")
    @PostMapping(value = "/edit")
    public Boolean edit(@RequestBody @Validated MonitoringStatisticsScienceInfoEditDTO editDTO){
        return iMonitoringStatisticsScienceInfoService.edit(editDTO);
    }

    @ApiOperation(value = "新增")
    @PostMapping(value = "/add")
    public Boolean add(@RequestBody @Validated MonitoringStatisticsScienceInfoAddDTO addDTO){
        return iMonitoringStatisticsScienceInfoService.add(addDTO);
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "/remove")
    public Boolean delete(@RequestBody @Validated MonitoringStatisticsScienceInfoDeleteDTO deleteDTO){
        return iMonitoringStatisticsScienceInfoService.delete(deleteDTO);
    }

}
