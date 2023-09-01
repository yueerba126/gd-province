package com.sydata.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.param.RotationPlanDtlPageParam;
import com.sydata.admin.service.IRotationPlanDtlService;
import com.sydata.admin.vo.RotationPlanDtlVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 行政管理-轮换计划明细Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "行政管理-轮换计划明细API")
@Validated
@RestController
@RequestMapping("/admin/rotation/plan/dtl")
public class RotationPlanDtlController {

    @Resource
    private IRotationPlanDtlService rotationPlanDtlService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<RotationPlanDtlVo> page(@RequestBody @Valid RotationPlanDtlPageParam pageParam) {
        return rotationPlanDtlService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public RotationPlanDtlVo detail(@RequestParam("id") String id) {
        return rotationPlanDtlService.detail(id);
    }

    @ApiOperation("生成轮换计划明细单号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lhjhdh", value = "轮换计划单号", dataTypeClass = String.class, required = true, example = "key"),
            @ApiImplicitParam(name = "hwdm", value = "货位代码", dataTypeClass = String.class, required = true, example = "key"),
    })
    @PostMapping("/generate")
    public String generate(@RequestParam("lhjhdh") String lhjhdh,
                           @RequestParam("hwdm") String hwdm) {
        return rotationPlanDtlService.generate(lhjhdh, hwdm);
    }
}
