package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.CompanyPageParam;
import com.sydata.basis.service.ICompanyService;
import com.sydata.basis.vo.CompanyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 基础数据-企业 Controller
 *
 * @author lzq
 * @date 2022-08-19
 */@Api(tags = "基础数据-企业")
@Validated
@RestController
@RequestMapping("/basis/company")
public class CompanyController {

    @Resource
    private ICompanyService companyService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<CompanyVo> page(@RequestBody @Valid CompanyPageParam pageParam) {
        return companyService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public CompanyVo detail(@RequestParam("id") String id) {
        return companyService.detail(id);
    }
}
