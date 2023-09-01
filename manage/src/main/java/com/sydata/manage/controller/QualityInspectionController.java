package com.sydata.manage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.param.QualityInspectionPageParam;
import com.sydata.manage.service.IQualityInspectionService;
import com.sydata.manage.vo.QualityInspectionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 质检信息表 前端控制器
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@Slf4j
@Api(tags = "粮库管理-质检API")
@Validated
@RestController
@RequestMapping("/manage/quality/inspection")
public class QualityInspectionController {

    @Resource
    private IQualityInspectionService qualityInspectionService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<QualityInspectionVo> page(@RequestBody @Valid QualityInspectionPageParam pageParam) {
        return qualityInspectionService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public QualityInspectionVo detail(@RequestParam("id") String id) {
        return qualityInspectionService.detail(id);
    }

}
