package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.TankPageParam;
import com.sydata.basis.service.ITankService;
import com.sydata.basis.vo.TankVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 基础信息-油罐信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-油罐信息")
@Validated
@RestController
@RequestMapping("/basis/tank")
public class TankController {

    @Resource
    private ITankService tankService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<TankVo> page(@RequestBody @Valid TankPageParam pageParam) {
        return tankService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public TankVo detail(@RequestParam("id") String id) {
        return tankService.detail(id);
    }
}