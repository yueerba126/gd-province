package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.DevicePageParam;
import com.sydata.basis.service.IDeviceService;
import com.sydata.basis.vo.DeviceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 基础信息-设备信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-设备信息")
@Validated
@RestController
@RequestMapping("/basis/device")
public class DeviceController {

    @Resource
    private IDeviceService deviceService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<DeviceVo> page(@RequestBody @Valid DevicePageParam pageParam) {
        return deviceService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public DeviceVo detail(@RequestParam("id") String id) {
        return deviceService.detail(id);
    }
}