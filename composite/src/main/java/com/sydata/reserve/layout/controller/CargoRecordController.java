package com.sydata.reserve.layout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.CargoRecordPageParam;
import com.sydata.reserve.layout.param.CargoRecordSaveParam;
import com.sydata.reserve.layout.param.CargoRecordUpdateParam;
import com.sydata.reserve.layout.service.ICargoRecordService;
import com.sydata.reserve.layout.vo.CargoRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 储备布局地理信息-货位信息备案Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "储备布局地理信息-货位信息备案")
@Validated
@RestController
@RequestMapping("/reserve/layout/cargo")
public class CargoRecordController {

    @Resource
    private ICargoRecordService cargoRecordService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<CargoRecordVo> page(@RequestBody @Valid CargoRecordPageParam pageParam) {
        return cargoRecordService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public CargoRecordVo detail(@RequestParam("id") String id) {
        return cargoRecordService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid CargoRecordSaveParam cargoRecordSaveParam) {
        return cargoRecordService.save(cargoRecordSaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid CargoRecordUpdateParam cargoRecordUpdateParam) {
        return cargoRecordService.update(cargoRecordUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return cargoRecordService.removeById(id);
    }

    @ApiOperation("修改数据状态")
    @PostMapping("/update/status")
    public Boolean updateStatus(@RequestParam("id") String id,@RequestParam("status") String status) {
        return cargoRecordService.updateStatus(id,status);
    }

}
