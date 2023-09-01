package com.sydata.collect.api.controller;

import com.sydata.collect.api.annotation.DataApi;
import com.sydata.collect.api.param.filing.WarehousingFilingApiParam;
import com.sydata.collect.api.param.grade.GradeApiParam;
import com.sydata.collect.api.param.plan.PlanManageApiParam;
import com.sydata.collect.api.param.record.FumigationApiParam;
import com.sydata.collect.api.security.annotation.WebSecurity;
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
 * @description 数据收集-备案管理API(开放对接)
 * @date 2022/12/10 10:54
 */
@Api(tags = "数据收集-备案管理API(开放对接)")
@RestController
@Validated
@RequestMapping("/api/v2022")
public class RecordDataApiController {

    @ApiOperation("熏蒸备案API")
    @PostMapping("/xzba")
    @Validated(BasicCheck.class)
    @DataApi(value = FUMIGATION, isDataHandle = false)
    @WebSecurity
    public void fumigation(@RequestBody @Valid FumigationApiParam apiParam) {
    }

    @ApiOperation("等级粮库评定信息API")
    @PostMapping("/graded")
    @Validated(BasicCheck.class)
    @DataApi(value = GRADE_REVIEW, isDataHandle = false)
    @WebSecurity
    public void gradeReview(@RequestBody @Valid GradeApiParam apiParam) {
    }

    @ApiOperation("仓储备案审核API")
    @PostMapping("/filing")
    @Validated(BasicCheck.class)
    @DataApi(value = WAREHOUSING_FILING, isDataHandle = false)
    @WebSecurity
    public void warehousingFiling(@RequestBody @Valid WarehousingFilingApiParam apiParam) {
    }

    @ApiOperation("轮换计划信息审核API")
    @PostMapping("/plan/manage")
    @Validated(BasicCheck.class)
    @DataApi(value = PLAN_MANAGE, isDataHandle = false)
    @WebSecurity
    public void planManage(@RequestBody @Valid PlanManageApiParam apiParam) {
    }

}
