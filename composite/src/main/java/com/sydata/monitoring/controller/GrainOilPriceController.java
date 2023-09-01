package com.sydata.monitoring.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.monitoring.dto.*;
import com.sydata.monitoring.service.IGrainOilPriceService;
import com.sydata.monitoring.vo.GrainOilPriceDtlVo;
import com.sydata.monitoring.vo.MonitoringInStockSettlementVo;
import com.sydata.monitoring.vo.MonitoringOutStockSettlementVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 流通检测/粮油价格采集明主表 前端控制器
 * </p>
 *
 * @author zhangcy
 * @since 2023/04/24
 */
@Api(tags = "流通检测-粮油价格采集")
@RestController
@RequestMapping("/monitoring/grain/oil/price")
public class GrainOilPriceController {
    @Resource
    private IGrainOilPriceService grainOilPriceService;

    @ApiOperation("流通检测/粮油价格采集明细分页")
    @PostMapping("/detail/page")
    public Page<GrainOilPriceDtlVo> page(@RequestBody GrainOilPriceQueryDto queryDto){
        return grainOilPriceService.detailPagination(queryDto);
    }

    @ApiOperation("入库单据分页")
    @PostMapping("/in/bill/page")
    public Page<MonitoringInStockSettlementVo> inBillPage(@RequestBody GrainOilPriceBillQueryDto queryDto){
        return grainOilPriceService.inBillPage(queryDto);
    }

    @ApiOperation("出库单据分页")
    @PostMapping("/out/bill/page")
    public Page<MonitoringOutStockSettlementVo> outBillPage(@RequestBody GrainOilPriceBillQueryDto queryDto){
        return grainOilPriceService.outBillPage(queryDto);
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public Boolean add(@RequestBody @Validated GrainOilPriceAddDto addDto){
        return grainOilPriceService.add(addDto);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Validated GrainOilPriceRemoveDto removeDto){
        return grainOilPriceService.remove(removeDto);
    }

}

