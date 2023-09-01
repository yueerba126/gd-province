package com.sydata.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.param.ReserveScalePageParam;
import com.sydata.admin.service.IReserveScaleService;
import com.sydata.admin.vo.ReserveScaleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 行政管理-储备规模APIController
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "行政管理-储备规模API")
@Validated
@RestController
@RequestMapping("/admin/reserve/scale")
public class ReserveScaleController {

    @Resource
    private IReserveScaleService reserveScaleService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ReserveScaleVo> page(@RequestBody @Valid ReserveScalePageParam pageParam) {
        return reserveScaleService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ReserveScaleVo detail(@RequestParam("id") String id) {
        return reserveScaleService.detail(id);
    }
}
