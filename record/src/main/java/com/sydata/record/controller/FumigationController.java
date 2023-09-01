package com.sydata.record.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.record.param.FumigationApprovedParam;
import com.sydata.record.param.FumigationPageParam;
import com.sydata.record.service.IFumigationService;
import com.sydata.record.vo.FumigationDetailsVo;
import com.sydata.record.vo.FumigationPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description
 * @date 2022/12/10 9:38
 */
@Api(tags = "备案管理-熏蒸")
@Validated
@RestController
@RequestMapping("/record/fumigation")
public class FumigationController {

    @Resource
    private IFumigationService fumigationService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<FumigationPageVo> page(@RequestBody @Valid FumigationPageParam pageParam) {
        return fumigationService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public FumigationDetailsVo detail(@RequestParam("id") String id) {
        return fumigationService.detail(id);
    }

    @ApiOperation("审核")
    @PostMapping("/approved")
    public boolean approved(@RequestBody @Valid FumigationApprovedParam param) {
        return fumigationService.approved(param);
    }
}
