package com.sydata.reserve.layout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.AttachmentPageParam;
import com.sydata.reserve.layout.param.AttachmentSaveParam;
import com.sydata.reserve.layout.param.AttachmentUpdateParam;
import com.sydata.reserve.layout.service.IAttachmentService;
import com.sydata.reserve.layout.vo.AttachmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 储备布局地理信息-附件文件Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "储备布局地理信息-附件文件")
@Validated
@RestController
@RequestMapping("/reserve/layout/attachment")
public class AttachmentController {

    @Resource
    private IAttachmentService attachmentService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<AttachmentVo> page(@RequestBody @Valid AttachmentPageParam pageParam) {
        return attachmentService.pages(pageParam);
    }

    @ApiOperation("新增")
    @PostMapping(value = "/save")
    public Boolean save(@RequestBody @Valid AttachmentSaveParam attachmentSaveParam) {
        return attachmentService.save(attachmentSaveParam);
    }

    @ApiOperation("修改")
    @PostMapping(value = "/update")
    public Boolean update(@RequestBody @Valid AttachmentUpdateParam attachmentUpdateParam) {
        return attachmentService.update(attachmentUpdateParam);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestParam("id") String id) {
        return attachmentService.removeById(id);
    }

}
