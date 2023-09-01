package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.InStockPageParam;
import com.sydata.trade.service.IInStockService;
import com.sydata.trade.vo.InStockVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author chenzx
 * @Date 2022/8/19 11:03
 * @Description: 粮食入库控制层
 * @Version 1.0
 */
@Api(tags = "粮食入库控制层API")
@RestController
@RequestMapping("/trade/stock/in")
public class InStockController {

    @Resource
    private IInStockService stockInService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<InStockVo> page(@RequestBody @Valid InStockPageParam grainListDTO) {
        return stockInService.pages(grainListDTO);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public InStockVo detail(@RequestParam("id") String id) {
        return stockInService.detail(id);
    }
}
