package com.sydata.report.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.param.QualityYearsPageParam;
import com.sydata.report.service.IReportQualityYearsService;
import com.sydata.report.vo.ReportQualityVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 数据上报-上报数据质量年报API
 * @date 2022/10/28 10:30
 */
@Api(tags = "数据上报-上报数据质量年报API")
@RestController
@Validated
@RequestMapping("/report/quality/years")
public class ReportQualityYearsController {

    @Resource
    private IReportQualityYearsService reportQualityYearsService;

    @ApiOperation("查看报表")
    @PostMapping("/report")
    public Page<ReportQualityVo> report(@RequestBody @Valid QualityYearsPageParam param) {
        return reportQualityYearsService.report(param);
    }
}
