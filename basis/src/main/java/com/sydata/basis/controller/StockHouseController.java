package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.StockHouse;
import com.sydata.basis.param.StockHousePageParam;
import com.sydata.basis.service.IStockHouseService;
import com.sydata.basis.vo.StockHouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 基础信息-库区信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-库区信息")
@Validated
@RestController
@RequestMapping("/basis/stock/house")
public class StockHouseController {

    @Resource
    private IStockHouseService stockHouseService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<StockHouseVo> page(@RequestBody @Valid StockHousePageParam pageParam) {
        return stockHouseService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public StockHouseVo detail(@RequestParam("id") String id) {
        return stockHouseService.detail(id);
    }

    @ApiOperation("数据视图选择库区的分页列表")
    @PostMapping("/data/view")
    public Page<StockHouseVo> dataViewPage(@RequestBody @Valid StockHousePageParam pageParam) {
        return stockHouseService.dataViewPage(pageParam);
    }

    @ApiOperation("根据行政区划查询库区列表")
    @PostMapping("/regions")
    public List<StockHouse> listByRegionId(@RequestParam("regionId") String regionId) {
        return stockHouseService.listByRegionId(regionId);
    }
}
