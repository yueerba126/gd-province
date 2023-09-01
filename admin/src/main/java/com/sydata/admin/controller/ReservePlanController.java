package com.sydata.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.param.ReservePlanPageParam;
import com.sydata.admin.service.IReservePlanService;
import com.sydata.admin.vo.ReservePlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 行政管理-储备计划Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "行政管理-储备计划API")
@Validated
@RestController
@RequestMapping("/admin/reserve/plan")
public class ReservePlanController {

    @Resource
    private IReservePlanService reservePlanService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ReservePlanVo> page(@RequestBody @Valid ReservePlanPageParam pageParam) {
        return reservePlanService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ReservePlanVo detail(@RequestParam("id") String id) {
        return reservePlanService.detail(id);
    }
}
