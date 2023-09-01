package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.CargoPageParam;
import com.sydata.basis.service.ICargoService;
import com.sydata.basis.vo.CargoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 基础信息-货位信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-货位信息")
@Validated
@RestController
@RequestMapping("/basis/cargo")
public class CargoController {

    @Resource
    private ICargoService cargoService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<CargoVo> page(@RequestBody @Valid CargoPageParam pageParam) {
        return cargoService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public CargoVo detail(@RequestParam("id") String id) {
        return cargoService.detail(id);
    }
}