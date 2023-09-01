package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.MonthStockPageParam;
import com.sydata.trade.service.IMonthStockService;
import com.sydata.trade.vo.MonthStockVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 粮油购销-账面库存Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "粮油购销-账面库存API")
@Validated
@RestController
@RequestMapping("/trade/month/stock")
public class MonthStockController {

    @Resource
    private IMonthStockService monthStockService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<MonthStockVo> page(@RequestBody @Valid MonthStockPageParam pageParam) {
        return monthStockService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public MonthStockVo detail(@RequestParam("id") String id) {
        return monthStockService.detail(id);
    }

}
