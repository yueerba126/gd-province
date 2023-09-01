package com.sydata.reserve.layout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.RranaryRecordPageParam;
import com.sydata.reserve.layout.param.RranaryRecordSaveParam;
import com.sydata.reserve.layout.param.RranaryRecordUpdateParam;
import com.sydata.reserve.layout.service.IRranaryRecordService;
import com.sydata.reserve.layout.vo.RranaryRecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 储备布局地理信息-廒间信息备案Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "储备布局地理信息-廒间信息备案")
@Validated
@RestController
@RequestMapping("/reserve/layout/granary")
public class RranaryRecordController {

    @Resource
    private IRranaryRecordService rranaryRecordService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<RranaryRecordVo> page(@RequestBody @Valid RranaryRecordPageParam pageParam) {
        return rranaryRecordService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public RranaryRecordVo detail(@RequestParam("id") String id) {
        return rranaryRecordService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid RranaryRecordSaveParam rranaryRecordSaveParam) {
        return rranaryRecordService.save(rranaryRecordSaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid RranaryRecordUpdateParam rranaryRecordUpdateParam) {
        return rranaryRecordService.update(rranaryRecordUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return rranaryRecordService.removeById(id);
    }

    @ApiOperation("修改数据状态")
    @PostMapping("/update/status")
    public Boolean updateStatus(@RequestParam("id") String id,@RequestParam("status") String status) {
        return rranaryRecordService.updateStatus(id,status);
    }

}
