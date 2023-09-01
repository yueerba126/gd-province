package com.sydata.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.param.GranaryVideoImagePageParam;
import com.sydata.manage.service.IGranaryVideoImageService;
import com.sydata.manage.vo.GranaryVideoImageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 粮库管理-仓内视频图像Controller
 *
 * @author lzq
 * @describe 仓内视频图像API
 * @date 2022-07-25 10:10
 */
@Api(tags = "粮库管理-仓内视频图像API")
@Validated
@RestController
@RequestMapping("/manage/granary/video/image")
public class GranaryVideoImageController {

    @Resource
    private IGranaryVideoImageService granaryVideoImageService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GranaryVideoImageVo> page(@RequestBody @Valid GranaryVideoImagePageParam pageParam) {
        return granaryVideoImageService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GranaryVideoImageVo detail(@RequestParam("id") String id) {
        return granaryVideoImageService.detail(id);
    }

}
