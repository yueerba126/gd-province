package com.sydata.dostrict.personnel.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.dostrict.personnel.param.ApparitorSystemPageParam;
import com.sydata.dostrict.personnel.param.ApparitorSystemSaveParam;
import com.sydata.dostrict.personnel.service.IApparitorSystemService;
import com.sydata.dostrict.personnel.vo.ApparitorSystemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 行政管理-人员制度管理Controller
 *
 * @author fuql
 * @date 2023-04-24
 */
@Api(tags = "行政管理-人员制度管理")
@Validated
@RestController
@RequestMapping("/apparitor/system")
public class ApparitorSystemController {

    @Resource
    private IApparitorSystemService apparitorSystemService;

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid ApparitorSystemSaveParam param) {
        return apparitorSystemService.saveData(param);
    }

    @ApiOperation("分页查询")
    @PostMapping("/list")
    public Page<ApparitorSystemVo> list(@RequestBody @Valid ApparitorSystemPageParam param) {
        return apparitorSystemService.pageData(param);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public String update(@RequestBody @Valid ApparitorSystemSaveParam param) {
        return apparitorSystemService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return apparitorSystemService.removeData(ids);
    }

    @ApiOperation("获取详细信息")
    @PostMapping(value = "/get")
    public ApparitorSystemVo getInfo(@RequestParam("id") Long id) {
        return apparitorSystemService.getDataById(id);
    }


    @ApiOperation("发布")
    @PostMapping(value = "/push")
    public boolean push(@RequestParam("id") Long id) {
        return apparitorSystemService.pushDataById(id);
    }

    @ApiOperation("下载附件")
    @PostMapping("/download")
    public void remove(@RequestParam("fileId") String fileId) {
        apparitorSystemService.download(fileId);
    }

}
