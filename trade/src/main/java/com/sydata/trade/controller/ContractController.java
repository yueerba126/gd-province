package com.sydata.trade.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.trade.param.ContractPageParam;
import com.sydata.trade.service.IContractService;
import com.sydata.trade.vo.ContractVo;
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
@Api(tags = "粮油购销-合同API")
@Validated
@RestController
@RequestMapping("/trade/contract")
public class ContractController {

    @Resource
    private IContractService contractsService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ContractVo> page(@RequestBody @Valid ContractPageParam reqParam) {
        return contractsService.pages(reqParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ContractVo detail(@RequestParam("id") String id) {
        return contractsService.detail(id);
    }
}
