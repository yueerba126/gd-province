package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.domain.Region;
import com.sydata.organize.param.RegionPageParam;
import com.sydata.organize.param.RegionRemoveParam;
import com.sydata.organize.service.IRegionService;
import com.sydata.organize.vo.RegionTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 组织架构-行政区域Controller
 *
 * @author lzq
 * @date 2022-06-28
 */
@Api(tags = "组织架构-行政区域")
@Validated
@RestController
@RequestMapping("/org/region")
public class RegionController {

    @Resource
    private IRegionService regionService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<Region> page(@RequestBody @Valid RegionPageParam pageParam) {
        return regionService.pages(pageParam);
    }

    @ApiOperation("树列表")
    @PostMapping("/tree")
    public List<RegionTreeVo> tree() {
        return regionService.tree();
    }

    @ApiOperation("根据ID查询")
    @PostMapping("/get/id")
    public Region getById(@RequestParam("id") String id) {
        return regionService.getById(id);
    }

    @ApiOperation("根据name查询")
    @PostMapping("/get/name")
    public List<Region> getByName(@RequestParam("name") String name) {
        return regionService.listByName(name);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid Region region) {
        return regionService.save(region);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid Region region) {
        return regionService.updateById(region);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid RegionRemoveParam removeParam) {
        return regionService.remove(removeParam);
    }

    @ApiOperation("根据父ID获取行政区域列表")
    @PostMapping("/sons")
    public List<Region> listByParentId(@RequestParam("parentId") String parentId) {
        return regionService.listByParentId(parentId);
    }

    @ApiOperation("根据行政区划id获取下级行政区域列表")
    @PostMapping("/tabulation")
    public List<Region> tabulation(@RequestParam("id") String id) {
        return regionService.tabulation(id);
    }

    @ApiOperation("根据父ID获取当前行政区域和下级行政区划代码列表--储备规模专用")
    @PostMapping("/sons/all")
    public List<Region> sonsAll(@RequestParam("parentId") String parentId) {
        return regionService.sonsAll(parentId);
    }
}