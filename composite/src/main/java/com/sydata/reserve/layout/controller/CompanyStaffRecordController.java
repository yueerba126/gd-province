package com.sydata.reserve.layout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.CompanyStaffRecordPageParam;
import com.sydata.reserve.layout.param.CompanyStaffRecordSaveParam;
import com.sydata.reserve.layout.param.CompanyStaffRecordUpdateParam;
import com.sydata.reserve.layout.service.ICompanyStaffRecordService;
import com.sydata.reserve.layout.vo.CompanyStaffRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 储备布局地理信息-人员信息备案Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "储备布局地理信息-人员信息备案")
@Validated
@RestController
@RequestMapping("/reserve/layout/company/staff")
public class CompanyStaffRecordController {

    @Resource
    private ICompanyStaffRecordService companyStaffRecordService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<CompanyStaffRecordVo> page(@RequestBody @Valid CompanyStaffRecordPageParam pageParam) {
        return companyStaffRecordService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public CompanyStaffRecordVo detail(@RequestParam("id") String id) {
        return companyStaffRecordService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid CompanyStaffRecordSaveParam companyStaffRecordSaveParam) {
        return companyStaffRecordService.save(companyStaffRecordSaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid CompanyStaffRecordUpdateParam companyStaffRecordUpdateParam) {
        return companyStaffRecordService.update(companyStaffRecordUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return companyStaffRecordService.removeById(id);
    }

    @ApiOperation("修改数据状态")
    @PostMapping("/update/status")
    public Boolean updateStatus(@RequestParam("id") String id,@RequestParam("status") String status) {
        return companyStaffRecordService.updateStatus(id,status);
    }

}
