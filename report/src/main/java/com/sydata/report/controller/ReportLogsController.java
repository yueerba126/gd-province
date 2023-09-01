package com.sydata.report.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.report.param.ReportLogsPageParam;
import com.sydata.report.service.IReportLogsService;
import com.sydata.report.vo.ReportLogsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author lzq
 * @description 数据上报-上报日志API
 * @date 2022/11/2 16:54
 */
@Api(tags = "数据上报-上报日志API")
@Validated
@RestController
@RequestMapping("/report/logs")
@Slf4j
public class ReportLogsController {

    @Resource
    private IReportLogsService reportLogsService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ReportLogsVo> page(@RequestBody @Valid ReportLogsPageParam pageParam) {
        return reportLogsService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ReportLogsVo detail(@RequestParam("id") String id) {
        return reportLogsService.detail(id);
    }

    @ApiOperation("重报投递(仅上报失败且未投递的数据才可重报投递)")
    @PostMapping("/delivery")
    public Boolean delivery(@RequestBody @Valid @NotEmpty(message = "投递参数必填") List<String> ids) {
        return reportLogsService.delivery(ids);
    }
}
