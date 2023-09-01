package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.domain.AppApiStockHouse;
import com.sydata.organize.param.AppApiStockHousePageParam;
import com.sydata.organize.param.AppApiStockHouseRemoveParam;
import com.sydata.organize.service.IAppApiStockHouseService;
import com.sydata.organize.vo.AppApiStockHouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 组织架构-app对接应用关联库区API
 * @date 2022/10/21 19:41
 */
@Api(tags = "组织架构-app对接应用关联库区API")
@Validated
@RestController
@RequestMapping("/org/app/api/stock/house")
public class AppApiStockHouseController {

    @Resource
    private IAppApiStockHouseService appApiStockHouseService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<AppApiStockHouseVo> page(@RequestBody @Valid AppApiStockHousePageParam pageParam) {
        return appApiStockHouseService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public AppApiStockHouseVo detail(@RequestParam("id") String id) {
        return appApiStockHouseService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean add(@RequestBody @Valid AppApiStockHouse appApiStockHouse) {
        return appApiStockHouseService.save(appApiStockHouse);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid AppApiStockHouse appApiStockHouse) {
        return appApiStockHouseService.updateById(appApiStockHouse);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid AppApiStockHouseRemoveParam removeParam) {
        return appApiStockHouseService.remove(removeParam);
    }
}
