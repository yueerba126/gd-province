package com.sydata.dostrict.reserve.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanMasterSlaveParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanPageParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanSaveParam;
import com.sydata.dostrict.reserve.service.IApparitorRotationPlanService;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanMasterSlaveVo;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * 粮食储备-轮换计划Controller
 *
 * @author: lzq
 * @create: 2023-04-26 10:08
 */
@Api(tags = "粮食储备-轮换计划")
@Validated
@RestController
@RequestMapping("/apparitor/rotation/plan")
public class ApparitorRotationPlanController {

    @Resource
    private IApparitorRotationPlanService apparitorRotationPlanService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorRotationPlanVo> page(@RequestBody @Valid ApparitorRotationPlanPageParam pageParam) {
        return apparitorRotationPlanService.pages(pageParam);
    }

    @ApiOperation("查询主从详情")
    @PostMapping("/detailMasterSlave")
    public ApparitorRotationPlanMasterSlaveVo detailMasterSlave(@RequestParam("id") String id) {
        return apparitorRotationPlanService.detailMasterSlave(id);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorRotationPlanVo detail(@RequestParam("id") String id) {
        return apparitorRotationPlanService.detail(id);
    }

    @ApiOperation("生成轮换计划单号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jhxddw", value = "计划下达单位代码",  dataTypeClass = String.class,required = true, example = "key"),
            @ApiImplicitParam(name = "jhnd", value = "计划年度",  dataTypeClass = String.class,required = true, example = "2022"),
    })
    @PostMapping("/generate")
    public String generate(@RequestParam("jhxddw") String jhxddw,
                           @RequestParam("jhnd") String jhnd) {
        return apparitorRotationPlanService.generate(jhxddw, jhnd);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorRotationPlanSaveParam param) {
        return apparitorRotationPlanService.saveData(param);
    }

    @ApiOperation("保存或是更新主从表")
    @PostMapping("/saveOrUpdateMasterSlaveData")
    public String saveOrUpdateMasterSlaveData(@RequestBody @Valid ApparitorRotationPlanMasterSlaveParam param) {
        return apparitorRotationPlanService.saveOrUpdateMasterSlaveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorRotationPlanSaveParam param) {
        return apparitorRotationPlanService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorRotationPlanService.removeData(ids);
    }
}
