package com.sydata.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.param.RotationPlanPageParam;
import com.sydata.admin.service.IRotationPlanService;
import com.sydata.admin.vo.RotationPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 行政管理-轮换计划Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "行政管理-轮换计划API")
@Validated
@RestController
@RequestMapping("/admin/rotation/plan")
public class RotationPlanController {

    @Resource
    private IRotationPlanService rotationPlanService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<RotationPlanVo> page(@RequestBody @Valid RotationPlanPageParam pageParam) {
        return rotationPlanService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public RotationPlanVo detail(@RequestParam("id") String id) {
        return rotationPlanService.detail(id);
    }

    @ApiOperation("生成轮换计划单号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jhxddw", value = "计划下达单位代码", dataTypeClass = String.class, required = true, example = "key"),
            @ApiImplicitParam(name = "jhnd", value = "计划年度", dataTypeClass = String.class, required = true, example = "2022"),
    })
    @PostMapping("/generate")
    public String generate(@RequestParam("jhxddw") String jhxddw,
                           @RequestParam("jhnd") String jhnd) {
        return rotationPlanService.generate(jhxddw, jhnd);
    }
}
