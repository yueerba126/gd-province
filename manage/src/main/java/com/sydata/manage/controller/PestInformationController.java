package com.sydata.manage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.param.PestInformationPageParam;
import com.sydata.manage.service.IPestInformationService;
import com.sydata.manage.vo.PestInformationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 虫害信息表 前端控制器
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */

@Slf4j
@Api(tags = "粮库管理-害虫检测API")
@Validated
@RestController
@RequestMapping("/manage/pest/information")
public class PestInformationController {

    @Resource
    private IPestInformationService pestInformationService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<PestInformationVo> page(@RequestBody @Valid PestInformationPageParam pageParam) {
        return pestInformationService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public PestInformationVo detail(@RequestParam("id") String id) {
        return pestInformationService.detail(id);
    }

}
