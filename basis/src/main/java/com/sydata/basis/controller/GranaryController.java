package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.GranaryPageParam;
import com.sydata.basis.service.IGranaryService;
import com.sydata.basis.vo.GranaryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 基础信息-廒间信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-廒间信息")
@Validated
@RestController
@RequestMapping("/basis/granary")
public class GranaryController {

    @Resource
    private IGranaryService granaryService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GranaryVo> page(@RequestBody @Valid GranaryPageParam pageParam) {
        return granaryService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GranaryVo detail(@RequestParam("id") String id) {
        return granaryService.detail(id);
    }
}