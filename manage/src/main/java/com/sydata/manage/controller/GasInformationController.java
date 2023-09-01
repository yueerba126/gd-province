package com.sydata.manage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.manage.param.GasInformationPageParam;
import com.sydata.manage.service.IGasInformationService;
import com.sydata.manage.vo.GasInformationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 气体信息表 前端控制器
 * </p>
 *
 * @author lzq
 * @since 2022-05-06
 */
@Slf4j
@Api(tags = "粮库管理-气体检测API")
@Validated
@RestController
@RequestMapping("/manage/gas/information")
public class GasInformationController {

    @Resource
    private IGasInformationService gasInformationService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<GasInformationVo> page(@RequestBody @Valid GasInformationPageParam pageParam) {
        return gasInformationService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public GasInformationVo detail(@RequestParam("id") String id) {
        return gasInformationService.detail(id);
    }

}
