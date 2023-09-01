package com.sydata.dostrict.plan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.plan.param.ApparitorPlanPageParam;
import com.sydata.dostrict.plan.param.ApparitorPlanSaveParam;
import com.sydata.dostrict.plan.service.IApparitorPlanService;
import com.sydata.dostrict.plan.vo.ApparitorPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 规划建设-仓储设施建设管理 Controller
 *
 * @author lzq
 * @date 2023-04-24
 */
@Api(tags = "规划建设-仓储设施建设管理")
@Validated
@RestController
@RequestMapping("/apparitor/plan")
public class ApparitorPlanController {

    @Resource
    private IApparitorPlanService apparitorPlanService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorPlanVo> page(@RequestBody @Valid ApparitorPlanPageParam pageParam) {
        return apparitorPlanService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorPlanVo detail(@RequestParam("id") String id) {
        return apparitorPlanService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorPlanSaveParam param) {
        return apparitorPlanService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorPlanSaveParam param) {
        return apparitorPlanService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorPlanService.removeData(ids);
    }
}
