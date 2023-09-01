package com.sydata.trade.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.OutStockSettlementPageParam;
import com.sydata.trade.service.IOutStockSettlementService;
import com.sydata.trade.vo.OutStockSettlementVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 出库结算信息表 前端控制器
 * </p>
 *
 * @author leq
 * @since 2022-08-19
 */
@RestController
@Api(tags = "出库结算控制层API")
@RequestMapping("/trade/stock/out/accounts")
public class OutStockSettlementController {

    @Resource
    private IOutStockSettlementService stockOutAccountsService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<OutStockSettlementVo> page(@RequestBody @Valid OutStockSettlementPageParam stockInCheckListDTO) {
        return stockOutAccountsService.pages(stockInCheckListDTO);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public OutStockSettlementVo detail(@RequestParam("id") String id) {
        return stockOutAccountsService.detail(id);
    }
}
