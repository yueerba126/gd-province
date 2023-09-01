package com.sydata.filing.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.filing.service.IWarehousingFilingAttachmentService;
import com.sydata.filing.param.WarehousingFilingAttachmentPageParam;
import com.sydata.filing.param.WarehousingFilingAttachmentSaveParam;
import com.sydata.filing.vo.WarehousingFilingAttachmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.sydata.filing.vo.FileVo;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:TODO(仓储备案-仓储附件API接口层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@Api(tags = "仓储备案-仓储附件",value = "仓储备案-仓储附件")
@Validated
@RestController
@RequestMapping("/filing/warehousingFilingAttachment")
public class WarehousingFilingAttachmentController {

    @Resource
    private IWarehousingFilingAttachmentService warehousingFilingAttachmentService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<WarehousingFilingAttachmentVo> page(@RequestBody @Valid WarehousingFilingAttachmentPageParam pageParam) {
        return warehousingFilingAttachmentService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public WarehousingFilingAttachmentVo detail(@RequestParam("id") String id) {
        return warehousingFilingAttachmentService.detail(id);
    }

    @ApiOperation("根据主表ID，查询从表列表")
    @PostMapping("/listByMainId")
    public List<WarehousingFilingAttachmentVo> listByMainId(@RequestParam("companyId") String companyId) {
        return warehousingFilingAttachmentService.listByMainId(companyId);
    }

    @ApiOperation("根据主表ID，删除从表")
    @PostMapping("/deleteByMainId")
    public Boolean deleteByMainId(@RequestParam("companyId") String companyId) {
        return warehousingFilingAttachmentService.deleteByMainId(companyId);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid WarehousingFilingAttachmentSaveParam param) {
        return warehousingFilingAttachmentService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid WarehousingFilingAttachmentSaveParam param) {
        return warehousingFilingAttachmentService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return warehousingFilingAttachmentService.removeData(ids);
    }

    @ApiOperation("批量新增或修改")
    @PostMapping("/saveOrUpdateBatchData")
    public Boolean saveOrUpdateBatchData(@RequestBody @Valid List<WarehousingFilingAttachmentSaveParam> param) {
        return warehousingFilingAttachmentService.saveOrUpdateBatchData(param);
    }

    @ApiOperation("导入")
    @PostMapping("/import")
    public void importData(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file) {
        warehousingFilingAttachmentService.importData(file);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void export(@RequestBody @Valid WarehousingFilingAttachmentPageParam pageParam) {
        warehousingFilingAttachmentService.exportData(pageParam);
    }

    @ApiOperation("上传文件")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileVo upload(@Valid MultipartFile file) {
        return warehousingFilingAttachmentService.uploadUse(file);
    }

    @ApiOperation("批量上传文件")
    @PostMapping(value = "/batchUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileVo> upload(@RequestParam(value = "files")  MultipartFile[] files) {
        return warehousingFilingAttachmentService.batchUploadUse(files);
    }

    @ApiOperation("下载文件")
    @PostMapping("/download")
    public void download(@RequestBody FileVo fileVo) {
        warehousingFilingAttachmentService.download(fileVo);
    }

    @ApiOperation("批量下载文件")
    @PostMapping("/batchDownload")
    public void downloadToZip(@RequestBody List<FileVo> fileVos) {
        warehousingFilingAttachmentService.downloadToZip(fileVos);
    }
                
}