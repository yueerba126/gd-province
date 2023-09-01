package com.sydata.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.param.SafetyCheckPageParam;
import com.sydata.manage.service.ISafetyCheckService;
import com.sydata.manage.vo.SafetyCheckVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @date 2022/8/18 17:55
 */
@Api(tags = "粮库管理-安全管理API")
@Validated
@RestController
@RequestMapping("/manage/safety/check")
public class SafetyCheckController {

    @Resource
    private ISafetyCheckService safetyCheckService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<SafetyCheckVo> page(@RequestBody @Valid SafetyCheckPageParam pageParam) {
        return safetyCheckService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public SafetyCheckVo detail(@RequestParam("id") String id) {
        return safetyCheckService.detail(id);
    }

}
