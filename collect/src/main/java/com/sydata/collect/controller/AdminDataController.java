package com.sydata.collect.controller;

import com.sydata.collect.api.annotation.DataApi;
import com.sydata.collect.api.param.admin.*;
import com.sydata.collect.api.validated.group.BasicCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.sydata.collect.api.enums.DataApiEnum.*;


/**
 * @author lzq
 * @description 数据收集-行政管理API
 * @date 2022/10/19 11:06
 */
@Api(tags = "数据收集-行政管理API")
@RestController
@Validated
@RequestMapping("/collect/admin/data")
public class AdminDataController {

    @ApiOperation("项目信息")
    @PostMapping("/project")
    @Validated(BasicCheck.class)
    @DataApi(PROJECT)
    public void project(@RequestBody @Valid ProjectApiParam apiParam) {
    }

    @ApiOperation("储备计划")
    @PostMapping("/reserve/plan")
    @Validated(BasicCheck.class)
    @DataApi(RESERVE_PLAN)
    public void reservePlan(@RequestBody ReservePlanApiParam apiParam) {
    }

    @ApiOperation("储备规模")
    @PostMapping("/reserve/scale")
    @Validated(BasicCheck.class)
    @DataApi(RESERVE_SCALE)
    public void reserveScale(@RequestBody ReserveScaleApiParam apiParam) {
    }

    @ApiOperation("轮换计划")
    @PostMapping("/rotation/plan")
    @Validated(BasicCheck.class)
    @DataApi(ROTATION_PLAN)
    public void rotationPlan(@RequestBody RotationPlanApiParam apiParam) {
    }

    @ApiOperation("轮换计划明细")
    @PostMapping("/rotation/plan/detail")
    @Validated(BasicCheck.class)
    @DataApi(ROTATION_PLAN_DTL)
    public void rotationPlanDetail(@RequestBody RotationPlanDtlApiParam apiParam) {
    }
}
