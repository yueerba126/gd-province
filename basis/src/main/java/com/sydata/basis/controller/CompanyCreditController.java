package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.CompanyCreditPageParam;
import com.sydata.basis.service.ICompanyCreditService;
import com.sydata.basis.vo.CompanyCreditVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 基础信息-企业信用信息
 * @date 2022/10/11 18:11
 */
@Api(tags = "基础信息-企业信用信息")
@Validated
@RestController
@RequestMapping("/basis/company/credit")
public class CompanyCreditController {

    @Resource
    private ICompanyCreditService companyCreditService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<CompanyCreditVo> page(@RequestBody @Valid CompanyCreditPageParam pageParam) {
        return companyCreditService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public CompanyCreditVo detail(@RequestParam("id") String id) {
        return companyCreditService.detail(id);
    }
}
