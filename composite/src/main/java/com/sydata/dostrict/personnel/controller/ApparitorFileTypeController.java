package com.sydata.dostrict.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.personnel.domain.ApparitorFileType;
import com.sydata.dostrict.personnel.param.ApparitorFileTypePageParam;
import com.sydata.dostrict.personnel.param.ApparitorFileTypeParam;
import com.sydata.dostrict.personnel.service.IApparitorFileTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 行政管理-文件类别管理Controller
 *
 * @author fuql
 * @date 2023-04-24
 */
@Api(tags = "行政管理-文件类别管理")
@Validated
@RestController
@RequestMapping("/apparitor/file/type")
public class ApparitorFileTypeController {

    @Resource
    private IApparitorFileTypeService apparitorFileTypeService;

    @ApiOperation("查询行政管理-文件类别管理列表")
    @PostMapping("/list")
    public Page<ApparitorFileType> list(@RequestBody @Valid ApparitorFileTypePageParam param) {
        return apparitorFileTypeService.pageData(param);
    }

    @ApiOperation("树形列表")
    @PostMapping("/treeList")
    public List<KuNode> treeList() {
        return apparitorFileTypeService.treeList();
    }

    @ApiOperation("获取层级分类详细信息")
    @PostMapping(value = "/get")
    public ApparitorFileType getInfo(@RequestParam("typeId") String typeId) {
        return apparitorFileTypeService.getById(typeId);
    }

    @ApiOperation("新增文件类别分类")
    @PostMapping(value = "/save")
    public String add(@RequestBody @Valid ApparitorFileType fileType) {
        return apparitorFileTypeService.insertFileType(fileType);
    }

    @ApiOperation("修改文件类别分类")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid ApparitorFileType fileType) {
        return apparitorFileTypeService.updateFileType(fileType);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorFileTypeService.removeData(ids);
    }
}
