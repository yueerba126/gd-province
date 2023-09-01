package com.sydata.reserve.layout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.TankRecordPageParam;
import com.sydata.reserve.layout.param.TankRecordSaveParam;
import com.sydata.reserve.layout.param.TankRecordUpdateParam;
import com.sydata.reserve.layout.service.ITankRecordService;
import com.sydata.reserve.layout.vo.TankRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 储备布局地理信息-油罐信息备案Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "储备布局地理信息-油罐信息备案")
@Validated
@RestController
@RequestMapping("/reserve/layout/tank")
public class TankRecordController {

    @Resource
    private ITankRecordService tankRecordService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<TankRecordVo> page(@RequestBody @Valid TankRecordPageParam pageParam) {
        return tankRecordService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public TankRecordVo detail(@RequestParam("id") String id) {
        return tankRecordService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid TankRecordSaveParam tankRecordSaveParam) {
        return tankRecordService.save(tankRecordSaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid TankRecordUpdateParam tankRecordUpdateParam) {
        return tankRecordService.update(tankRecordUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return tankRecordService.removeById(id);
    }

    @ApiOperation("修改数据状态")
    @PostMapping("/update/status")
    public Boolean updateStatus(@RequestParam("id") String id,@RequestParam("status") String status) {
        return tankRecordService.updateStatus(id,status);
    }

}
