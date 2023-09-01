package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.CargoLabelPageParam;
import com.sydata.basis.service.ICargoLabelService;
import com.sydata.basis.vo.CargoLabelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @author lzq
 * @description 基础信息-库区图仓房点位标注
 * @date 2022/10/11 18:09
 */
@Api(tags = "基础信息-库区图仓房点位标注")
@Validated
@RestController
@RequestMapping("/basis/cargo/label")
public class CargoLabelController {

    @Resource
    private ICargoLabelService cargoLabelService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<CargoLabelVo> page(@RequestBody @Valid CargoLabelPageParam pageParam) {
        return cargoLabelService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public CargoLabelVo detail(@RequestParam("id") String id) {
        return cargoLabelService.detail(id);
    }
}
