package com.sydata.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.param.VentilationPageParam;
import com.sydata.manage.service.IVentilationService;
import com.sydata.manage.vo.VentilationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 粮库管理-通风作业Controller
 *
 * @author lzq
 * @describe 通风作业API
 * @date 2022-07-25 10:10
 */
@Api(tags = "粮库管理-通风作业API")
@Validated
@RestController
@RequestMapping("/manage/ventilation")
public class VentilationController {

    @Resource
    private IVentilationService ventilationService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<VentilationVo> page(@RequestBody @Valid VentilationPageParam pageParam) {
        return ventilationService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public VentilationVo detail(@RequestParam("id") String id) {
        return ventilationService.detail(id);
    }

}
