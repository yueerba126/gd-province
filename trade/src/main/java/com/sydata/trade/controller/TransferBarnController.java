package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.TransferBarnPageParam;
import com.sydata.trade.service.ITransferBarnService;
import com.sydata.trade.vo.TransferBarnVo;
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
@Api(tags = "粮油购销-倒仓信息API")
@Validated
@RestController
@RequestMapping("/trade/transfer/barn")
public class TransferBarnController {

    @Resource
    private ITransferBarnService transferBarnService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<TransferBarnVo> page(@RequestBody @Valid TransferBarnPageParam reqParam) {
        return transferBarnService.pages(reqParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public TransferBarnVo detail(@RequestParam("id") String id) {
        return transferBarnService.detail(id);
    }
}
