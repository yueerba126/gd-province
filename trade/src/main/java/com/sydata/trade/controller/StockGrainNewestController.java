package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.StockGrainDetailPageParam;
import com.sydata.trade.param.StockGrainPageParam;
import com.sydata.trade.service.IStockGrainNewestService;
import com.sydata.trade.vo.StockGrainDetailVo;
import com.sydata.trade.vo.StockGrainNewestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 粮油购销-粮食库存最新数据表API
 * @date 2023/4/21 10:28
 */
@RequestMapping("/trade/stock/grain/newest")
@RestController
@Api(tags = "粮油购销-粮食库存最新数据表API")
public class StockGrainNewestController {

    @Resource
    private IStockGrainNewestService stockGrainNewestService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<StockGrainNewestVo> page(@RequestBody @Valid StockGrainPageParam pageParam) {
        return stockGrainNewestService.pages(pageParam);
    }

    @ApiOperation("粮食库存明细分页列表")
    @PostMapping("/page/detail")
    public Page<StockGrainDetailVo> pageDetail(@RequestBody @Valid StockGrainDetailPageParam pageParam) {
        return stockGrainNewestService.pageDetail(pageParam);
    }

    @ApiOperation("粮食库存明细报表导出")
    @PostMapping("/detail/export")
    public void stockDetailExport(@RequestBody @Valid StockGrainDetailPageParam pageParam) {
        stockGrainNewestService.stockDetailExport(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public StockGrainNewestVo detail(@RequestParam("id") String id) {
        return stockGrainNewestService.detail(id);
    }
}
