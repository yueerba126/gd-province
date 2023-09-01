package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.LossPageParam;
import com.sydata.trade.service.ILossService;
import com.sydata.trade.vo.LossVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @date 2022/8/18 17:55
 */
@Api(tags = "粮油购销-损益单API")
@Validated
@RestController
@RequestMapping("/trade/income/statement")
public class LossController {

    @Resource
    private ILossService incomeStatementService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<LossVo> page(@RequestBody @Valid LossPageParam reqParam) {
        return incomeStatementService.pages(reqParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public LossVo detail(@RequestParam("id") String id) {
        return incomeStatementService.detail(id);
    }
}
