package com.sydata.warn.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.warn.domain.AdminPlanThreshold;
import com.sydata.warn.param.AdminPlanThresholdPageParam;
import com.sydata.warn.service.IAdminPlanThresholdService;
import com.sydata.warn.vo.AdminPlanThresholdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 储备计划阈值设置Controller
 *
 * @author fuql
 * @date 2023-05-09
 */
@Api(tags = "储备计划阈值设置")
@Validated
@RestController
@RequestMapping("/admin/plan/threshold")
public class AdminPlanThresholdController {

    @Resource
    private IAdminPlanThresholdService adminPlanThresholdService;

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid AdminPlanThreshold param) {
        return adminPlanThresholdService.saveData(param);
    }

    @ApiOperation("分页查询")
    @PostMapping("/list")
    public Page<AdminPlanThresholdVo> list(@RequestBody @Valid AdminPlanThresholdPageParam param) {
        return adminPlanThresholdService.pageData(param);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid AdminPlanThreshold param) {
        return adminPlanThresholdService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return adminPlanThresholdService.removeData(ids);
    }

    @ApiOperation("获取详细信息")
    @PostMapping(value = "/get")
    public AdminPlanThresholdVo getInfo(@RequestParam("id") Long id) {
        return adminPlanThresholdService.getDataById(id);
    }

}
