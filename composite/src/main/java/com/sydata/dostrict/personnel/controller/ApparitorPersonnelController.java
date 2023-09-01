package com.sydata.dostrict.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.personnel.param.ApparitorPersonnelPageParam;
import com.sydata.dostrict.personnel.param.ApparitorPersonnelSaveParam;
import com.sydata.dostrict.personnel.service.IApparitorPersonnelService;
import com.sydata.dostrict.personnel.vo.ApparitorPersonnelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 行政管理模块--企业人员 Controller
 *
 * @author fuql
 * @date 2022-08-19
 */
@Api(tags = "行政管理模块--企业人员")
@Validated
@RestController
@RequestMapping("/apparitor/personnel")
public class ApparitorPersonnelController {

    @Resource
    private IApparitorPersonnelService apparitorPersonnelService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorPersonnelVo> page(@RequestBody @Valid ApparitorPersonnelPageParam pageParam) {
        return apparitorPersonnelService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorPersonnelVo detail(@RequestParam("id") String id) {
        return apparitorPersonnelService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorPersonnelSaveParam param) {
        return apparitorPersonnelService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorPersonnelSaveParam param) {
        return apparitorPersonnelService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorPersonnelService.removeData(ids);
    }
}
