package com.sydata.organize.controller;

import com.sydata.organize.domain.Menu;
import com.sydata.organize.param.MenuRemoveParam;
import com.sydata.organize.service.IMenuService;
import com.sydata.organize.vo.MenuTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 组织架构-菜单Controller
 *
 * @author lzq
 * @date 2022-06-28
 */
@Api(tags = "组织架构-菜单")
@Validated
@RestController
@RequestMapping("/org/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @ApiOperation("树列表")
    @PostMapping("/tree")
    public List<MenuTreeVo> tree() {
        return menuService.tree();
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid Menu menu) {
        return menuService.save(menu);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid Menu menu) {
        return menuService.updateById(menu);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid MenuRemoveParam removeParam) {
        return menuService.remove(removeParam);
    }
}