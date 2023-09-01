package com.sydata.dostrict.reserve.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanDtlSaveParam;
import com.sydata.dostrict.reserve.param.ApparitorRotationPlanDtlPageParam;
import com.sydata.dostrict.reserve.service.IApparitorRotationPlanDtlService;
import com.sydata.dostrict.reserve.vo.ApparitorRotationPlanDtlVo;
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
 * 粮食储备-轮换计划明细Controller
 *
 * @author: lzq
 * @create: 2023-04-26 10:08
 */
@Api(tags = "粮食储备-轮换计划明细")
@Validated
@RestController
@RequestMapping("/apparitor/rotation/plan/dtl")
public class ApparitorRotationPlanDtlController {

    @Resource
    private IApparitorRotationPlanDtlService apparitorRotationPlanDtlService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<ApparitorRotationPlanDtlVo> page(@RequestBody @Valid ApparitorRotationPlanDtlPageParam pageParam) {
        return apparitorRotationPlanDtlService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public ApparitorRotationPlanDtlVo detail(@RequestParam("id") String id) {
        return apparitorRotationPlanDtlService.detail(id);
    }

    @ApiOperation("生成轮换计划明细单号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lhjhdh", value = "轮换计划单号", dataTypeClass = String.class,required = true, example = "key"),
            @ApiImplicitParam(name = "hwdm", value = "货位代码", dataTypeClass = String.class,required = true, example = "key"),
    })
    @PostMapping("/generate")
    public String generate(@RequestParam("lhjhdh") String lhjhdh,
                           @RequestParam("hwdm") String hwdm) {
        return apparitorRotationPlanDtlService.generate(lhjhdh, hwdm);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorRotationPlanDtlSaveParam param) {
        return apparitorRotationPlanDtlService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid ApparitorRotationPlanDtlSaveParam param) {
        return apparitorRotationPlanDtlService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorRotationPlanDtlService.removeData(ids);
    }
}
