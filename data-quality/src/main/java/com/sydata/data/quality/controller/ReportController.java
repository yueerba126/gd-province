package com.sydata.data.quality.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.data.quality.param.ReportPageParam;
import com.sydata.data.quality.service.IReportDtlService;
import com.sydata.data.quality.service.IReportService;
import com.sydata.data.quality.vo.ReportDtlVo;
import com.sydata.data.quality.vo.ReportVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lzq
 * @description 数据质量-数据报告API
 * @date 2023/4/25 9:33
 */
@Api(tags = "数据质量-数据报告API")
@Validated
@RestController
@RequestMapping("/data/quality/report")
public class ReportController {

    @Resource
    private IReportService reportService;

    @Resource
    private IReportDtlService reportDtlService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ReportVo> page(@RequestBody @Valid ReportPageParam pageParam) {
        return reportService.pages(pageParam);
    }

    @ApiOperation("明细列表")
    @PostMapping("/dtls")
    public List<ReportDtlVo> listByReportId(@RequestParam("reportId") String reportId) {
        return reportDtlService.listByReportId(reportId);
    }
}
