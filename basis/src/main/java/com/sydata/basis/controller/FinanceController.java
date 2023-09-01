package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Finance;
import com.sydata.basis.param.FinancePageParam;
import com.sydata.basis.param.FinanceReportDetailParam;
import com.sydata.basis.service.IFinanceService;
import com.sydata.basis.vo.FinanceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * 基础信息-财务报表信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-财务报表信息")
@Validated
@RestController
@RequestMapping("/basis/finance")
public class FinanceController {

    @Resource
    private IFinanceService financeService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<FinanceVo> page(@RequestBody @Valid FinancePageParam pageParam) {
        return financeService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public FinanceVo detail(@RequestParam("id") String id) {
        return financeService.detail(id);
    }

    @ApiOperation("分页报表")
    @PostMapping("/report/page")
    public Page<FinanceVo> reportPages(@RequestBody @Valid FinancePageParam pageParam) {
        return financeService.reportPages(pageParam);
    }

    @ApiOperation("报表详情")
    @PostMapping("/report/detail")
    public Map<Integer, Finance> reportDetail(@RequestBody @Valid FinanceReportDetailParam param) {
        return financeService.reportDetail(param);
    }
}