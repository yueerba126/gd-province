package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.InStockCheckPageParam;
import com.sydata.trade.service.IInStockCheckService;
import com.sydata.trade.vo.InStockCheckVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author chenzx
 * @Date 2022/8/19 11:44
 * @Description: 入库检验控制层
 * @Version 1.0
 */
@RestController
@Api(tags = "入库检验控制层API")
@RequestMapping("/trade/stock/in/check")
public class InStockCheckController {

    @Resource
    private IInStockCheckService stockInCheckService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<InStockCheckVo> page(@RequestBody @Valid InStockCheckPageParam inStockCheckPageParam) {
        return stockInCheckService.pages(inStockCheckPageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public InStockCheckVo detail(@RequestParam("id") String id) {
        return stockInCheckService.detail(id);
    }
}
