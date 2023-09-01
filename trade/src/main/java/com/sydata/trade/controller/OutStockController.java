package com.sydata.trade.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.OutStockPageParam;
import com.sydata.trade.service.IOutStockService;
import com.sydata.trade.vo.OutStockVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 粮食出库数据表 前端控制器
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
@RestController
@Api(tags = "粮食出库控制层API")
@RequestMapping("/trade/stock/out")
public class OutStockController {

    @Resource
    private IOutStockService stockOutService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<OutStockVo> page(@RequestBody @Valid OutStockPageParam stockOutListParam) {
        return stockOutService.pages(stockOutListParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public OutStockVo detail(@RequestParam("id") String id) {
        return stockOutService.detail(id);
    }

}
