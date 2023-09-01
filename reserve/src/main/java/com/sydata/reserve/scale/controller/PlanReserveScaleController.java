package com.sydata.reserve.scale.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.scale.domain.PlanReserveScale;
import com.sydata.reserve.scale.param.PlanReserveScaleLogPageParam;
import com.sydata.reserve.scale.param.PlanReserveScalePageParam;
import com.sydata.reserve.scale.service.IPlanReserveScaleService;
import com.sydata.reserve.scale.vo.PlanReserveScaleLogVo;
import com.sydata.reserve.scale.vo.PlanReserveScalePageVo;
import com.sydata.reserve.scale.vo.PlanReserveScaleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 储备计划管理-储备规模Controller
 *
 * @author fuql
 * @date 2023-05-17
 */
@Api(tags = "储备计划管理-储备规模管理")
@Validated
@RestController
@RequestMapping("/plan/scale")
public class PlanReserveScaleController {

    @Resource
    private IPlanReserveScaleService planReserveScaleService;


    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid PlanReserveScale param) {
        return planReserveScaleService.saveData(param);
    }

    @ApiOperation("分页查询")
    @PostMapping("/list")
    public PlanReserveScalePageVo list(@RequestBody @Valid PlanReserveScalePageParam param) {
        return planReserveScaleService.pageData(param);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid PlanReserveScale param) {
        return planReserveScaleService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return planReserveScaleService.removeData(ids);
    }

    @ApiOperation("获取详细信息")
    @PostMapping(value = "/get")
    public PlanReserveScaleVo getInfo(@RequestParam("id") Long id) {
        return planReserveScaleService.getDataById(id);
    }

    @ApiOperation("导入模板下载")
    @PostMapping("/template/export")
    public void templateExport() {
        planReserveScaleService.templateExport();
    }

    @ApiOperation("导入")
    @PostMapping("/imports/{isUpdate}")
    public List<PlanReserveScaleVo> imports(@RequestParam(value = "file") MultipartFile file , @PathVariable("isUpdate") Boolean isUpdate) {
        return planReserveScaleService.imports(file , isUpdate);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void export(@RequestBody @Valid PlanReserveScalePageParam param) {
        planReserveScaleService.export(param);
    }

    @ApiOperation("获取调整日志列表")
    @PostMapping(value = "/list/log")
    public Page<PlanReserveScaleLogVo> getLogInfo(@RequestBody @Valid PlanReserveScaleLogPageParam param) {
        return planReserveScaleService.getLogInfo(param);
    }

}