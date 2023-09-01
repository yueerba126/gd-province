package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.CompanyStaffPageParam;
import com.sydata.basis.service.ICompanyStaffService;
import com.sydata.basis.vo.CompanyStaffVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 基础数据-企业人员 Controller
 *
 * @author lzq
 * @date 2022-08-19
 */
@Api(tags = "基础数据-企业人员")
@Validated
@RestController
@RequestMapping("/basis/company/staff")
public class CompanyStaffController {

    @Resource
    private ICompanyStaffService companyStaffService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<CompanyStaffVo> page(@RequestBody @Valid CompanyStaffPageParam pageParam) {
        return companyStaffService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public CompanyStaffVo detail(@RequestParam("id") String id) {
        return companyStaffService.detail(id);
    }
}
