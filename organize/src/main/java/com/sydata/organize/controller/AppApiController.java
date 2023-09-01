package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.domain.AppApi;
import com.sydata.organize.domain.AppApiStockHouse;
import com.sydata.organize.param.AppApiPageParam;
import com.sydata.organize.service.IAppApiService;
import com.sydata.organize.vo.AppApiVo;
import com.sydata.organize.vo.AppSecretGenerateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 组织架构-app对接应用API
 * @date 2022/10/21 19:41
 */
@Api(tags = "组织架构-app对接应用API")
@Validated
@RestController
@RequestMapping("/org/app/api")
public class AppApiController {

    @Resource
    private IAppApiService appApiService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<AppApiVo> page(@RequestBody @Valid AppApiPageParam pageParam) {
        return appApiService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public AppApiVo detail(@RequestParam("id") String id) {
        return appApiService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean add(@RequestBody @Valid AppApi appApi) {
        return appApiService.save(appApi);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid AppApi appApi) {
        return appApiService.updateById(appApi);
    }

    @ApiOperation("启用/禁用")
    @PostMapping("/eod")
    public Boolean updateEod(@RequestParam("state") Boolean state,
                             @RequestParam("appid") String appid) {
        return appApiService.updateEod(state, appid);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("appid") String appid) {
        return appApiService.removeById(appid);
    }

    @ApiOperation("生成秘钥信息")
    @PostMapping("/generate")
    public AppSecretGenerateVo generate() {
        return appApiService.generate();
    }
}
