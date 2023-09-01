package com.sydata.manage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.param.GrainMonitorPageParam;
import com.sydata.manage.service.IGrainMonitorService;
import com.sydata.manage.vo.GrainMonitorVo;
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
@Api(tags = "粮库管理-温湿度检测API")
@Validated
@RestController
@RequestMapping("/manage/grain/monitor")
public class GrainMonitorController {

    @Resource
    private IGrainMonitorService grainMonitorService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GrainMonitorVo> page(@RequestBody @Valid GrainMonitorPageParam pageParam) {
        return grainMonitorService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GrainMonitorVo detail(@RequestParam("id") String id) {
        return grainMonitorService.detail(id);
    }

}
