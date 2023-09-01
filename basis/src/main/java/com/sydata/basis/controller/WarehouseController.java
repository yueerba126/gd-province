package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.WarehousePageParam;
import com.sydata.basis.service.IWarehouseService;
import com.sydata.basis.vo.WarehouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 基础信息-仓房信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-仓房信息")
@Validated
@RestController
@RequestMapping("/basis/warehouse")
public class WarehouseController {

    @Resource
    private IWarehouseService warehouseService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<WarehouseVo> page(@RequestBody @Valid WarehousePageParam pageParam) {
        return warehouseService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public WarehouseVo detail(@RequestParam("id") String id) {
        return warehouseService.detail(id);
    }
}