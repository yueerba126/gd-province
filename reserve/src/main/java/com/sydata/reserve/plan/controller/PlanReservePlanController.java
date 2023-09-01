package com.sydata.reserve.plan.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.plan.param.PlanReservePlanAdjustParam;
import com.sydata.reserve.plan.param.PlanReservePlanPageParam;
import com.sydata.reserve.plan.param.PlanReservePlanSaveParam;
import com.sydata.reserve.plan.param.PlanReservePlanUpdateParam;
import com.sydata.reserve.plan.service.IPlanReservePlanService;
import com.sydata.reserve.plan.vo.PlanReservePlanLogVo;
import com.sydata.reserve.plan.vo.PlanReservePlanSendLogVo;
import com.sydata.reserve.plan.vo.PlanReservePlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 储备计划管理-储备计划Controller
 *
 * @author fuql
 * @date 2023-05-19
 */
@Api(tags = "储备计划管理-储备计划")
@Validated
@RestController
@RequestMapping("/plan/reserve/plan")
public class PlanReservePlanController {

    @Resource
    private IPlanReservePlanService planReservePlanService;

    @ApiOperation("分页查询")
    @PostMapping("/list")
    public Page<PlanReservePlanVo> list(@RequestBody @Valid PlanReservePlanPageParam param) {
        return planReservePlanService.pageData(param);
    }

    @ApiOperation("生成单据唯一编号")
    @GetMapping("/create/bill/no")
    public String createBillNo() {
        return planReservePlanService.generateBillMainCode();
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid PlanReservePlanSaveParam param) {
        return planReservePlanService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid PlanReservePlanUpdateParam param) {
        return planReservePlanService.updateData(param);
    }

    @ApiOperation("批量删除")
    @GetMapping("/batch/abolish")
    public Boolean batchAbolish(@RequestParam(value = "mainIds") List<String> mainIds) {
        return planReservePlanService.batchAbolish(mainIds);
    }

    @ApiOperation("批量提交")
    @GetMapping("/submit")
    public Boolean submit(@RequestParam(value = "mainIds") List<String> mainIds) {
        return planReservePlanService.submit(mainIds);
    }

    @ApiOperation("批量反提交")
    @GetMapping("/un/submit")
    public Boolean unSubmit(@RequestParam(value = "mainIds") List<String> mainIds) {
        return planReservePlanService.unSubmit(mainIds);
    }

    @ApiOperation("下发")
    @GetMapping("/distribution")
    public Boolean distribution(@RequestParam(value = "mainId") String mainId) {
        return planReservePlanService.distribution(mainId);
    }

    @ApiOperation("调整")
    @PostMapping(value = "/adjust")
    public String adjust(@RequestBody @Valid PlanReservePlanAdjustParam param) {
        return planReservePlanService.adjust(param);
    }

    @ApiOperation("下发日志")
    @GetMapping("/distribution/log")
    public List<PlanReservePlanSendLogVo> distributionLog(@RequestParam(value = "mainId") String mainId) {
        return planReservePlanService.distributionLog(mainId);
    }

    @ApiOperation("调整日志")
    @GetMapping(value = "/adjust/log")
    public List<PlanReservePlanLogVo> adjustLog(@RequestParam(value = "mainId") String mainId) {
        return planReservePlanService.adjustLog(mainId);
    }

}
