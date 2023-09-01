package com.sydata.warn.controller;

import com.sydata.warn.domain.AdminGrainThreshold;
import com.sydata.warn.service.IAdminGrainThresholdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 粮情预警阈值Controller
 *
 * @author fuql
 * @date 2023-05-09
 */
@Api(tags = "粮情预警阈值")
@Validated
@RestController
@RequestMapping("/admin/grain/threshold")
public class AdminGrainThresholdController {

    @Resource
    private IAdminGrainThresholdService adminGrainThresholdService;

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid AdminGrainThreshold param) {
        return adminGrainThresholdService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid AdminGrainThreshold param) {
        return adminGrainThresholdService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return adminGrainThresholdService.removeData(ids);
    }

    @ApiOperation("获取详细信息")
    @PostMapping(value = "/get")
    public AdminGrainThreshold getInfo(@RequestParam("id") Long id) {
        return adminGrainThresholdService.getDataById(id);
    }

}
