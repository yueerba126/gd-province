package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.TransferNaturePageParam;
import com.sydata.trade.service.ITransferNatureService;
import com.sydata.trade.vo.TransferNatureVo;
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
@Api(tags = "粮油购销-性质转变单API")
@Validated
@RestController
@RequestMapping("/trade/transfer/nature")
public class TransferNatureController {

    @Resource
    private ITransferNatureService transferNatureService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<TransferNatureVo> page(@RequestBody @Valid TransferNaturePageParam reqParam) {
        return transferNatureService.pages(reqParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public TransferNatureVo detail(@RequestParam("id") String id) {
        return transferNatureService.detail(id);
    }
}
