package com.sydata.trade.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.InStockSettlementPageParam;
import com.sydata.trade.service.IInStockSettlementService;
import com.sydata.trade.vo.InStockSettlementVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 入库结算信息表 前端控制器
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
@RestController
@Api(tags = "入库结算控制层API")
@RequestMapping("/trade/stock/in/accounts")
public class InStockSettlementController {

    @Resource
    private IInStockSettlementService stockInAccountsService;

    @PostMapping("/page")
    public Page<InStockSettlementVo> page(@RequestBody @Valid InStockSettlementPageParam inStockSettlementPageParam) {
        return stockInAccountsService.pages(inStockSettlementPageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public InStockSettlementVo detail(@RequestParam("id") String id) {
        return stockInAccountsService.detail(id);
    }
}
