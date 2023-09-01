package com.sydata.reserve.layout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.WarehouseRecordPageParam;
import com.sydata.reserve.layout.param.WarehouseRecordSaveParam;
import com.sydata.reserve.layout.param.WarehouseRecordUpdateParam;
import com.sydata.reserve.layout.service.IWarehouseRecordService;
import com.sydata.reserve.layout.vo.WarehouseRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 储备布局地理信息-仓房信息备案Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "储备布局地理信息-仓房信息备案")
@Validated
@RestController
@RequestMapping("/reserve/layout/warehouse")
public class WarehouseRecordController {

    @Resource
    private IWarehouseRecordService warehouseRecordService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<WarehouseRecordVo> page(@RequestBody @Valid WarehouseRecordPageParam pageParam) {
        return warehouseRecordService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public WarehouseRecordVo detail(@RequestParam("id") String id) {
        return warehouseRecordService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid WarehouseRecordSaveParam warehouseRecordSaveParam) {
        return warehouseRecordService.save(warehouseRecordSaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid WarehouseRecordUpdateParam warehouseRecordUpdateParam) {
        return warehouseRecordService.update(warehouseRecordUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return warehouseRecordService.removeById(id);
    }

    @ApiOperation("修改数据状态")
    @PostMapping("/update/status")
    public Boolean updateStatus(@RequestParam("id") String id,@RequestParam("status") String status) {
        return warehouseRecordService.updateStatus(id,status);
    }

}
