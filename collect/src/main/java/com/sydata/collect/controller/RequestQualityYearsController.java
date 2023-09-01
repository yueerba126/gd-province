package com.sydata.collect.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.collect.param.QualityYearsPageParam;
import com.sydata.collect.service.IRequestQualityYearsService;
import com.sydata.collect.vo.CollectQualityVo;
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
 * @description 数据收集-请求数据质量年报API
 * @date 2022/10/28 10:30
 */
@Api(tags = "数据收集-请求数据质量年报API")
@RestController
@Validated
@RequestMapping("/collect/quality/years")
public class RequestQualityYearsController {

    @Resource
    private IRequestQualityYearsService requestQualityYearsService;

    @ApiOperation("查看报表")
    @PostMapping("/report")
    public Page<CollectQualityVo> report(@RequestBody @Valid QualityYearsPageParam param) {
        return requestQualityYearsService.report(param);
    }
}
