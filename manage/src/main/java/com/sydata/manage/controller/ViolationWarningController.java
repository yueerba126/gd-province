package com.sydata.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.domain.ViolationWarning;
import com.sydata.manage.param.ViolationWarningPageParam;
import com.sydata.manage.service.IViolationWarningService;
import com.sydata.manage.vo.ViolationWarningVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 粮库管理-违规预警信息Controller
 *
 * @author lzq
 * @describe 违规预警信息API
 * @date 2022-07-25 10:10
 */
@Api(tags = "粮库管理-违规预警信息API")
@Validated
@RestController
@RequestMapping("/manage/violation/warning")
public class ViolationWarningController {

    @Resource
    private IViolationWarningService violationWarningService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ViolationWarningVo> page(@RequestBody @Valid ViolationWarningPageParam pageParam) {
        return violationWarningService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ViolationWarning detail(@RequestParam("id") String id) {
        return violationWarningService.detail(id);
    }

}
