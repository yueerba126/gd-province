package com.sydata.dostrict.reserve.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.reserve.param.ApparitorReserveScaleSaveParam;
import com.sydata.dostrict.reserve.param.ApparitorReserveScalePageParam;
import com.sydata.dostrict.reserve.service.IApparitorReserveScaleService;
import com.sydata.dostrict.reserve.vo.ApparitorReserveScaleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * 粮食储备-储备规模APIController
 *
 * @author: lzq
 * @create: 2023-04-26 10:08
 */
@Api(tags = "粮食储备-储备规模")
@Validated
@RestController
@RequestMapping("/apparitor/reserve/scale")
public class ApparitorReserveScaleController {

    @Resource
    private IApparitorReserveScaleService apparitorReserveScaleService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorReserveScaleVo> page(@RequestBody @Valid ApparitorReserveScalePageParam pageParam) {
        return apparitorReserveScaleService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorReserveScaleVo detail(@RequestParam("id") String id) {
        return apparitorReserveScaleService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorReserveScaleSaveParam param) {
        return apparitorReserveScaleService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorReserveScaleSaveParam param) {
        return apparitorReserveScaleService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorReserveScaleService.removeData(ids);
    }
}
