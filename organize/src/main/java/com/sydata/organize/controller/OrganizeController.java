package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.domain.Organize;
import com.sydata.organize.param.*;
import com.sydata.organize.service.IOrganizeService;
import com.sydata.organize.vo.OrganizeTreeVo;
import com.sydata.organize.vo.OrganizeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 组织架构-组织Controller
 *
 * @author lzq
 * @date 2022-06-28
 */
@Api(tags = "组织架构-组织")
@Validated
@RestController
@RequestMapping("/org/organize")
public class OrganizeController {

    @Resource
    private IOrganizeService organizeService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<OrganizeVo> page(@RequestBody @Valid OrganizePageParam pageParam) {
        return organizeService.pages(pageParam);
    }

    @ApiOperation("获取当前登录人所属组织下的树形组织")
    @PostMapping("/tree/parents")
    public List<OrganizeTreeVo> treeByParentIds() {
        return organizeService.treeByParentIds();
    }

    @ApiOperation("根据ID查询")
    @PostMapping("/get/id")
    public Organize getById(@RequestParam("id") String id) {
        return organizeService.getById(id);
    }

    @ApiOperation("根据name查询")
    @PostMapping("/get/name")
    public Organize getByName(@RequestParam("name") String name) {
        return organizeService.getByName(name);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid OrganizeOperateParam operateParam) {
        return organizeService.save(operateParam);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid OrganizeOperateParam operateParam) {
        return organizeService.update(operateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid OrganizeRemoveParam removeParam) {
        return organizeService.remove(removeParam);
    }

    @ApiOperation("设置系统")
    @PostMapping("/set/system")
    public Boolean setSystem(@RequestBody @Valid OrganizeSetSystemParam setSystemParam) {
        return organizeService.setSystem(setSystemParam);
    }

    @ApiOperation("设置业务类型")
    @PostMapping("/set/bus/type")
    public Boolean setBusType(@RequestBody @Valid OrganizeSetBusTypeParam setBusTypeParam) {
        return organizeService.setBusType(setBusTypeParam);
    }
}