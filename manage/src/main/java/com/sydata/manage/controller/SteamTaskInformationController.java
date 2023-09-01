package com.sydata.manage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.param.SteamTaskInformationPageParam;
import com.sydata.manage.service.ISteamTaskInformationService;
import com.sydata.manage.vo.SteamTaskInformationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 蒸熏作业信息 前端控制器
 * </p>
 *
 * @author lzq
 * @since 2022-05-07
 */
@Api(tags = "粮库管理-熏蒸作业API")
@Validated
@RestController
@RequestMapping("/manage/steam/task/information")
public class SteamTaskInformationController {

    @Resource
    private ISteamTaskInformationService steamTaskInformationService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<SteamTaskInformationVo> page(@RequestBody @Valid SteamTaskInformationPageParam pageParam) {
        return steamTaskInformationService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public SteamTaskInformationVo detail(@RequestParam("id") String id) {
        return steamTaskInformationService.detail(id);
    }

}
