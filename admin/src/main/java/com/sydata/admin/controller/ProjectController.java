
package com.sydata.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.admin.param.ProjectPageParam;
import com.sydata.admin.service.IProjectService;
import com.sydata.admin.vo.ProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * 行政管理-项目Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "行政管理-项目API")
@Validated
@RestController
@RequestMapping("/admin/project")
public class ProjectController {

    @Resource
    private IProjectService projectService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ProjectVo> page(@RequestBody @Valid ProjectPageParam pageParam) {
        return projectService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ProjectVo detail(@RequestParam("id") String id) {
        return projectService.detail(id);
    }

    @ApiOperation("生成项目代码")
    @PostMapping("/generate")
    public String generate() {
        return projectService.generate();
    }
}
