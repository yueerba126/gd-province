package com.sydata.reserve.layout.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.sydata.reserve.layout.param.ReserveCompanyPageParam;
import com.sydata.reserve.layout.param.ReserveCompanySaveParam;
import com.sydata.reserve.layout.param.ReserveCompanyUpdateParam;
import com.sydata.reserve.layout.service.IReserveCompanyService;
import com.sydata.reserve.layout.vo.ReserveCompanyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 储备布局地理信息-企业 Controller
 *
 * @author lzq
 * @date 2022-08-19
 */
@Api(tags = "储备布局地理信息-企业")
@Validated
@RestController
@RequestMapping("/reserve/layout/company")
public class ReserveCompanyController {

    private final IReserveCompanyService companyService;

    public ReserveCompanyController(IReserveCompanyService companyService) {
        this.companyService = companyService;
    }

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ReserveCompanyVo> page(@RequestBody @Valid ReserveCompanyPageParam pageParam) {
        return companyService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ReserveCompanyVo detail(@RequestParam("id") String id) {
        return companyService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid ReserveCompanySaveParam companySaveParam) {
        return companyService.save(companySaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid ReserveCompanyUpdateParam companyUpdateParam) {
        return companyService.update(companyUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return companyService.removeById(id);
    }

    @ApiOperation("修改数据状态")
    @PostMapping("/update/status")
    public Boolean updateStatus(@RequestParam("id") String id,@RequestParam("status") String status) {
        return companyService.updateStatus(id,status);
    }
}
