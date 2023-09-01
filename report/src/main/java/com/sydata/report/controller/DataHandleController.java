package com.sydata.report.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.report.param.DataHandlePageParam;
import com.sydata.report.service.IDataHandleService;
import com.sydata.report.vo.DataHandleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 数据收集-数据处理API
 * @date 2022/10/21 16:04
 */
@Api(tags = "数据上报-数据处理API")
@RestController
@Validated
@RequestMapping("/report/data/handle")
public class DataHandleController {

    @Resource
    private IDataHandleService dataHandleService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<DataHandleVo> page(@RequestBody @Valid DataHandlePageParam pageParam) {
        return dataHandleService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public DataHandleVo detail(@RequestParam("id") String id) {
        return dataHandleService.detail(id);
    }

}
