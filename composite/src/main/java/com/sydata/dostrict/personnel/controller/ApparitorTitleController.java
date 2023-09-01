package com.sydata.dostrict.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.personnel.domain.ApparitorTitle;
import com.sydata.dostrict.personnel.param.ApparitorTitlePageParam;
import com.sydata.dostrict.personnel.service.IApparitorTitleService;
import com.sydata.dostrict.personnel.vo.ApparitorTitleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 行政管理-荣誉称号管理Controller
 *
 * @author fuql
 * @date 2023-04-25
 */
@Api(tags = "行政管理-荣誉称号管理")
@Validated
@RestController
@RequestMapping("/apparitor/title")
public class ApparitorTitleController{

    @Resource
    private IApparitorTitleService apparitorTitleService;


    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorTitleVo> page(@RequestBody @Valid ApparitorTitlePageParam pageParam) {
        return apparitorTitleService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorTitleVo detail(@RequestParam("id") String id) {
        return apparitorTitleService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorTitle param) {
        return apparitorTitleService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorTitle param) {
        return apparitorTitleService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorTitleService.removeData(ids);
    }

}
