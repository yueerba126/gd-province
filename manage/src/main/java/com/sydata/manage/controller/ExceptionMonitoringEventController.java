package com.sydata.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.param.ExceptionMonitoringEventPageParam;
import com.sydata.manage.service.IExceptionMonitoringEventService;
import com.sydata.manage.vo.ExceptionMonitoringEventVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 粮库管理-库区视频监控异常事件告警Controller
 *
 * @author lzq
 * @describe 库区视频监控异常事件告警API
 * @date 2022-07-25 10:10
 */
@Api(tags = "粮库管理-库区视频监控异常事件告警API")
@Validated
@RestController
@RequestMapping("/manage/exception/monitoring/event")
public class ExceptionMonitoringEventController {

    @Resource
    private IExceptionMonitoringEventService exceptionMonitoringEventService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ExceptionMonitoringEventVo> page(@RequestBody @Valid ExceptionMonitoringEventPageParam pageParam) {
        return exceptionMonitoringEventService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ExceptionMonitoringEventVo detail(@RequestParam("id") String id) {
        return exceptionMonitoringEventService.detail(id);
    }
}
