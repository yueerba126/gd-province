package com.sydata.monitoring.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.dto.CheckPointAddDto;
import com.sydata.monitoring.dto.CheckPointConfigDto;
import com.sydata.monitoring.dto.CheckPointQueryDto;
import com.sydata.monitoring.dto.CheckPointRemoveDto;
import com.sydata.monitoring.service.ICheckPointService;
import com.sydata.monitoring.vo.CheckPointConfigVo;
import com.sydata.monitoring.vo.CheckPointVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/监测点配置表 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/24
 */
@Api(tags = "流通检测-监测点配置接口")
@RestController
@RequestMapping("/monitoring/check/point")
public class CheckPointController {

    @Resource
    private ICheckPointService checkPointService;

    @ApiOperation("分页")
    @PostMapping("/page")
    public Page<CheckPointVo> page(@RequestBody CheckPointQueryDto queryDto){
        return checkPointService.pagination(queryDto);
    }

    @ApiOperation("查询当前监测点")
    @PostMapping("/current/point")
    public CheckPointVo currentPoint(){
        return checkPointService.currentPoint();
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated CheckPointAddDto addDto){
        return checkPointService.add(addDto);
    }

    @ApiOperation("配置")
    @PostMapping("/config")
    public Boolean config(@RequestBody @Validated CheckPointConfigDto configDto){
        return checkPointService.config(configDto);
    }

    @ApiOperation("查询配置详情")
    @PostMapping("/config/detail")
    public CheckPointConfigVo configDetail(@RequestBody @Validated CheckPointConfigDto configDto){
        return checkPointService.configDetail(configDto);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Validated CheckPointRemoveDto removeDto){
        return checkPointService.remove(removeDto);
    }

}

