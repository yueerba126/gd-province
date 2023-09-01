package com.sydata.dostrict.reserve.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.reserve.param.ApparitorReservePlanMasterSlaveParam;
import com.sydata.dostrict.reserve.param.ApparitorReservePlanPageParam;
import com.sydata.dostrict.reserve.param.ApparitorReservePlanSaveParam;
import com.sydata.dostrict.reserve.service.IApparitorReservePlanService;
import com.sydata.dostrict.reserve.vo.ApparitorReservePlanMasterSlaveVo;
import com.sydata.dostrict.reserve.vo.ApparitorReservePlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * 粮食储备-储备计划Controller
 *
 * @author: lzq
 * @create: 2023-04-26 10:08
 */
@Api(tags = "粮食储备-储备计划")
@Validated
@RestController
@RequestMapping("/apparitor/reserve/plan")
public class ApparitorReservePlanController {

    @Resource
    private IApparitorReservePlanService apparitorReservePlanService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorReservePlanVo> page(@RequestBody @Valid ApparitorReservePlanPageParam pageParam) {
        return apparitorReservePlanService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorReservePlanVo detail(@RequestParam("id") String id) {
        return apparitorReservePlanService.detail(id);
    }

    @ApiOperation("查询主从详情")
    @PostMapping("/detailMasterSlave")
    public ApparitorReservePlanMasterSlaveVo detailMasterSlave(@RequestParam("id") String id) {
        return apparitorReservePlanService.detailMasterSlave(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorReservePlanSaveParam param) {
        return apparitorReservePlanService.saveData(param);
    }

    @ApiOperation("保存或是更新主从表")
    @PostMapping("/saveOrUpdateMasterSlaveData")
    public Boolean saveOrUpdateMasterSlaveData(@RequestBody @Valid ApparitorReservePlanMasterSlaveParam param) {
        return apparitorReservePlanService.saveOrUpdateMasterSlaveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorReservePlanSaveParam param) {
        return apparitorReservePlanService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorReservePlanService.removeData(ids);
    }
}
