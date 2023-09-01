package com.sydata.reserve.layout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.TestingInstitutesPageParam;
import com.sydata.reserve.layout.param.TestingInstitutesSaveParam;
import com.sydata.reserve.layout.param.TestingInstitutesUpdateParam;
import com.sydata.reserve.layout.service.ITestingInstitutesService;
import com.sydata.reserve.layout.vo.TestingInstitutesVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 储备布局地理信息-质检机构Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "储备布局地理信息-质检机构")
@Validated
@RestController
@RequestMapping("/reserve/layout/testing/institutes")
public class TestingInstitutesController {

    @Resource
    private ITestingInstitutesService testingInstitutesService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<TestingInstitutesVo> page(@RequestBody @Valid TestingInstitutesPageParam pageParam) {
        return testingInstitutesService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public TestingInstitutesVo detail(@RequestParam("id") String id) {
        return testingInstitutesService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid TestingInstitutesSaveParam testingInstitutesSaveParam) {
        return testingInstitutesService.save(testingInstitutesSaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid TestingInstitutesUpdateParam testingInstitutesUpdateParam) {
        return testingInstitutesService.update(testingInstitutesUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return testingInstitutesService.removeById(id);
    }

    @ApiOperation("修改启用状态")
    @PostMapping("/update/status")
    public Boolean updateStatus(@RequestParam("id") String id,@RequestParam("status") String status) {
        return testingInstitutesService.updateStatus(id,status);
    }

}
