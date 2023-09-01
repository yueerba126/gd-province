package com.sydata.dostrict.casetarget.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.casetarget.domain.ApparitorCase;
import com.sydata.dostrict.casetarget.param.ApparitorCasePageParam;
import com.sydata.dostrict.casetarget.param.ApparitorCaseSavaParam;
import com.sydata.dostrict.casetarget.service.IApparitorCaseService;
import com.sydata.dostrict.casetarget.vo.ApparitorCaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 行政管理-执法案件Controller
 *
 * @author fuql
 * @date 2023-04-26
 */
@Api(tags = "行政管理-执法案件")
@Validated
@RestController
@RequestMapping("/apparitor/case")
public class ApparitorCaseController {

    @Resource
    private IApparitorCaseService apparitorCaseService;


    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorCaseSavaParam param) {
        return apparitorCaseService.saveData(param);
    }

    @ApiOperation("分页查询")
    @PostMapping("/list")
    public Page<ApparitorCaseVo> list(@RequestBody @Valid ApparitorCasePageParam param) {
        return apparitorCaseService.pageData(param);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid ApparitorCase param) {
        return apparitorCaseService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorCaseService.removeData(ids);
    }

    @ApiOperation("获取详细信息")
    @PostMapping(value = "/get")
    public ApparitorCaseVo getInfo(@RequestParam("id") Long id) {
        return apparitorCaseService.getDataById(id);
    }
}
