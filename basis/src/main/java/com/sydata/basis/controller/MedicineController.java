package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.param.MedicinePageParam;
import com.sydata.basis.service.IMedicineService;
import com.sydata.basis.vo.MedicineVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 基础信息-药剂信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-药剂信息")
@Validated
@RestController
@RequestMapping("/basis/medicine")
public class MedicineController {

    @Resource
    private IMedicineService medicineService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<MedicineVo> page(@RequestBody @Valid MedicinePageParam pageParam) {
        return medicineService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public MedicineVo detail(@RequestParam("id") String id) {
        return medicineService.detail(id);
    }
}