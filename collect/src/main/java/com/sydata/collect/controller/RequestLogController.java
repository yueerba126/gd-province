package com.sydata.collect.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.service.IRequestLogService;
import com.sydata.collect.param.RequestLogPageParam;
import com.sydata.collect.vo.RequestLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 数据收集-请求日志API
 * @date 2022/10/21 16:04
 */
@Api(tags = "数据收集-请求日志API")
@RestController
@Validated
@RequestMapping("/collect/log")
public class RequestLogController {

    @Resource
    private IRequestLogService requestLogService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<RequestLogVo> page(@RequestBody @Valid RequestLogPageParam pageParam) {
        return requestLogService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public RequestLogVo detail(@RequestParam("id") String id) {
        return requestLogService.detail(id);
    }
}
