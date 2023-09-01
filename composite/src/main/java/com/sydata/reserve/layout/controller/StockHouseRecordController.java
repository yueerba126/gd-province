package com.sydata.reserve.layout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.*;
import com.sydata.reserve.layout.service.IStockHouseRecordService;
import com.sydata.reserve.layout.vo.StockHouseRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 储备布局地理信息-库区信息备案Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "储备布局地理信息-粮库信息备案")
@Validated
@RestController
@RequestMapping("/reserve/layout/stock/house")
public class StockHouseRecordController {

    @Resource
    private IStockHouseRecordService stockHouseRecordService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<StockHouseRecordVo> page(@RequestBody @Valid StockHouseRecordPageParam pageParam) {
        return stockHouseRecordService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public StockHouseRecordVo detail(@RequestParam("id") String id) {
        return stockHouseRecordService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid StockHouseRecordSaveParam stockHouseRecordSaveParam) {
        return stockHouseRecordService.save(stockHouseRecordSaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid StockHouseRecordUpdateParam stockHouseRecordUpdateParam) {
        return stockHouseRecordService.update(stockHouseRecordUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return stockHouseRecordService.removeById(id);
    }

    @ApiOperation("修改数据状态")
    @PostMapping("/update/status")
    public Boolean updateStatus(@RequestParam("id") String id,@RequestParam("status") String status) {
        return stockHouseRecordService.updateStatus(id,status);
    }

}
