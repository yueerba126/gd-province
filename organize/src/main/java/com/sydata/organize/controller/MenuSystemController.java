package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.domain.MenuSystem;
import com.sydata.organize.param.MenuSystemPageParam;
import com.sydata.organize.param.MenuSystemRemoveParam;
import com.sydata.organize.param.MenuSystemSaveParam;
import com.sydata.organize.param.MenuSystemUpdateParam;
import com.sydata.organize.service.IMenuSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author lzq
 * @description 组织架构-菜单系统Controller
 * @date 2023/5/22 15:04
 */
@Api(tags = "组织架构-菜单系统")
@Validated
@RestController
@RequestMapping("/org/menu/system")
public class MenuSystemController {

    @Resource
    private IMenuSystemService menuSystemService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<MenuSystem> page(@RequestBody @Valid MenuSystemPageParam pageParam) {
        return menuSystemService.pages(pageParam);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid MenuSystemSaveParam saveParam) {
        return menuSystemService.save(saveParam);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid MenuSystemUpdateParam updateParam) {
        return menuSystemService.update(updateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid MenuSystemRemoveParam removeParam) {
        return menuSystemService.remove(removeParam);
    }
}
