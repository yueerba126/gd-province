package com.sydata.dostrict.personnel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.personnel.domain.ApparitorCulture;
import com.sydata.dostrict.personnel.param.ApparitorCulturePageParam;
import com.sydata.dostrict.personnel.service.IApparitorCultureService;
import com.sydata.dostrict.personnel.vo.ApparitorCultureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 行政管理-人才培养计划Controller
 *
 * @author fuql
 * @date 2023-04-25
 */
@Api(tags = "行政管理-人才培养计划")
@Validated
@RestController
@RequestMapping("/apparitor/culture")
public class ApparitorCultureController {

    @Resource
    private IApparitorCultureService apparitorCultureService;

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorCulture param) {
        return apparitorCultureService.saveData(param);
    }

    @ApiOperation("分页查询")
    @PostMapping("/list")
    public Page<ApparitorCultureVo> list(@RequestBody @Valid ApparitorCulturePageParam param) {
        return apparitorCultureService.pageData(param);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid ApparitorCulture param) {
        return apparitorCultureService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorCultureService.removeData(ids);
    }

    @ApiOperation("获取详细信息")
    @PostMapping(value = "/get")
    public ApparitorCultureVo getInfo(@RequestParam("id") Long id) {
        return apparitorCultureService.getDataById(id);
    }

    @ApiOperation("提交")
    @PostMapping(value = "/submit")
    public boolean submit(@RequestBody @Valid ApparitorCulture param) {
        return apparitorCultureService.submitDataByParam(param);
    }

    @ApiOperation("撤回")
    @PostMapping(value = "/revocation")
    public boolean revocation(@RequestParam("id") Long id) {
        return apparitorCultureService.revocation(id);
    }

    @ApiOperation("反审核")
    @PostMapping(value = "/unAudit")
    public boolean unAudit(@RequestParam("id") Long id) {
        return apparitorCultureService.unAudit(id);
    }

    @ApiOperation("审核")
    @PostMapping(value = "/audit")
    public boolean audit(@RequestParam("id") Long id) {
        return apparitorCultureService.audit(id);
    }

}
