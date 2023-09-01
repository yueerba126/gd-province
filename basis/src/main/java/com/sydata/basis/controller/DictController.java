package com.sydata.basis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.basis.domain.Dict;
import com.sydata.basis.param.DictPageParam;
import com.sydata.basis.param.DictRemoveParam;
import com.sydata.basis.service.IDictService;
import com.sydata.basis.vo.DictTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 基础信息-字典信息Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "基础信息-字典信息")
@Validated
@RestController
@RequestMapping("/basis/dict")
public class DictController {

    @Resource
    private IDictService dictService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<Dict> page(@RequestBody @Valid DictPageParam pageParam) {
        return dictService.pages(pageParam);
    }

    @ApiOperation("字典列表")
    @PostMapping("/list")
    public List<Dict> list(@RequestParam("dictType") String dictType) {
        return dictService.listByDictType(dictType);
    }

    @ApiOperation("树列表")
    @PostMapping("/tree")
    public List<DictTreeVo> tree(@RequestParam("dictType") String dictType) {
        return dictService.treeByDictType(dictType);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public Boolean save(@RequestBody @Valid Dict dict) {
        return dictService.save(dict);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public Boolean update(@RequestBody @Valid Dict dict) {
        return dictService.updateById(dict);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody @Valid DictRemoveParam removeParam) {
        return dictService.remove(removeParam);
    }
}