package com.sydata.dostrict.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.personnel.domain.ApparitorTitleType;
import com.sydata.dostrict.personnel.param.ApparitorTitleTypePageParam;
import com.sydata.dostrict.personnel.param.ApparitorTitleTypeParam;
import com.sydata.dostrict.personnel.service.IApparitorTitleTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 行政管理-称号类别管理Controller
 *
 * @author fuql
 * @date 2023-04-25
 */
@Api(tags = "行政管理-称号类别管理")
@Validated
@RestController
@RequestMapping("/apparitor/title/type")
public class ApparitorTitleTypeController {

    @Resource
    private IApparitorTitleTypeService apparitorTitleTypeService;


    @ApiOperation("查询行政管理-文件类别管理列表")
    @PostMapping("/list")
    public Page<ApparitorTitleType> list(@RequestBody @Valid ApparitorTitleTypePageParam param) {
        return apparitorTitleTypeService.pageData(param);
    }

    @ApiOperation("树形列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isShowNum", value = "是否显示数量",  dataTypeClass = String.class,paramType = "query"),
            @ApiImplicitParam(name = "isShowHave", value = "是否显示（无）分类",  dataTypeClass = String.class,paramType = "query")
    })
    @PostMapping("/treeList")
    public List<KuNode> treeList() {
        return apparitorTitleTypeService.treeList();
    }

    @ApiOperation("获取层级分类详细信息")
    @PostMapping(value = "/get")
    public ApparitorTitleType getInfo(@RequestParam("typeId") Long typeId) {
        return apparitorTitleTypeService.getById(typeId);
    }

    @ApiOperation("新增文件类别分类")
    @PostMapping(value = "/save")
    public String add(@RequestBody @Valid ApparitorTitleType titleType) {
        return apparitorTitleTypeService.insertTitleType(titleType);
    }

    @ApiOperation("修改文件类别分类")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid ApparitorTitleType titleType) {
        return apparitorTitleTypeService.updateTitleType(titleType);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorTitleTypeService.removeData(ids);
    }

}
