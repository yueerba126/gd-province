package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.StockGrainPageParam;
import com.sydata.trade.service.IStockGrainService;
import com.sydata.trade.vo.StockGrainVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author chenzx
 * @Date 2022/8/18 18:05
 * @Description: 粮食库存控制层
 * @Version 1.0
 */
@RequestMapping("/trade/stock/grain")
@RestController
@Api(tags = "粮食库存控制层API")
public class StockGrainController {

    @Resource
    private IStockGrainService stockGrainService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<StockGrainVo> page(@RequestBody @Valid StockGrainPageParam pageParam) {
        return stockGrainService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public StockGrainVo detail(@RequestParam("id") String id) {
        return stockGrainService.detail(id);
    }
}
