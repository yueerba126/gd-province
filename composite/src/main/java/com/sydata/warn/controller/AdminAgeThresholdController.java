package com.sydata.warn.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.warn.domain.AdminAgeThreshold;
import com.sydata.warn.param.AdminAgeThresholdPageParam;
import com.sydata.warn.service.IAdminAgeThresholdService;
import com.sydata.warn.vo.AdminAgeThresholdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 库存年限告警阈值设置Controller
 *
 * @author fuql
 * @date 2023-05-09
 */
@Api(tags = "库存年限告警阈值设置")
@Validated
@RestController
@RequestMapping("/admin/age/threshold")
public class AdminAgeThresholdController {

    @Resource
    private IAdminAgeThresholdService adminAgeThresholdService;


    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid AdminAgeThreshold param) {
        return adminAgeThresholdService.saveData(param);
    }

    @ApiOperation("分页查询")
    @PostMapping("/list")
    public Page<AdminAgeThresholdVo> list(@RequestBody @Valid AdminAgeThresholdPageParam param) {
        return adminAgeThresholdService.pageData(param);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid AdminAgeThreshold param) {
        return adminAgeThresholdService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return adminAgeThresholdService.removeData(ids);
    }

    @ApiOperation("获取详细信息")
    @PostMapping(value = "/get")
    public AdminAgeThresholdVo getInfo(@RequestParam("id") Long id) {
        return adminAgeThresholdService.getDataById(id);
    }
}
