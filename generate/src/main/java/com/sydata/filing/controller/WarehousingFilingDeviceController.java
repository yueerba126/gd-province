package com.sydata.filing.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.filing.service.IWarehousingFilingDeviceService;
import com.sydata.filing.param.WarehousingFilingDevicePageParam;
import com.sydata.filing.param.WarehousingFilingDeviceSaveParam;
import com.sydata.filing.vo.WarehousingFilingDeviceVo;
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
 * @Description:TODO(仓储备案-仓储设备API接口层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@Api(tags = "仓储备案-仓储设备",value = "仓储备案-仓储设备")
@Validated
@RestController
@RequestMapping("/filing/warehousingFilingDevice")
public class WarehousingFilingDeviceController {

    @Resource
    private IWarehousingFilingDeviceService warehousingFilingDeviceService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<WarehousingFilingDeviceVo> page(@RequestBody @Valid WarehousingFilingDevicePageParam pageParam) {
        return warehousingFilingDeviceService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public WarehousingFilingDeviceVo detail(@RequestParam("id") String id) {
        return warehousingFilingDeviceService.detail(id);
    }

    @ApiOperation("根据主表ID，查询从表列表")
    @PostMapping("/listByMainId")
    public List<WarehousingFilingDeviceVo> listByMainId(@RequestParam("stockId") String stockId) {
        return warehousingFilingDeviceService.listByMainId(stockId);
    }

    @ApiOperation("根据主表ID，删除从表")
    @PostMapping("/deleteByMainId")
    public Boolean deleteByMainId(@RequestParam("stockId") String stockId) {
        return warehousingFilingDeviceService.deleteByMainId(stockId);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid WarehousingFilingDeviceSaveParam param) {
        return warehousingFilingDeviceService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid WarehousingFilingDeviceSaveParam param) {
        return warehousingFilingDeviceService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return warehousingFilingDeviceService.removeData(ids);
    }

    @ApiOperation("批量新增或修改")
    @PostMapping("/saveOrUpdateBatchData")
    public Boolean saveOrUpdateBatchData(@RequestBody @Valid List<WarehousingFilingDeviceSaveParam> param) {
        return warehousingFilingDeviceService.saveOrUpdateBatchData(param);
    }

    @ApiOperation("导入")
    @PostMapping("/import")
    public void importData(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file) {
        warehousingFilingDeviceService.importData(file);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void export(@RequestBody @Valid WarehousingFilingDevicePageParam pageParam) {
        warehousingFilingDeviceService.exportData(pageParam);
    }

    @ApiOperation("上传文件")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileVo upload(@Valid MultipartFile file) {
        return warehousingFilingDeviceService.uploadUse(file);
    }

    @ApiOperation("批量上传文件")
    @PostMapping(value = "/batchUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<FileVo> upload(@RequestParam(value = "files")  MultipartFile[] files) {
        return warehousingFilingDeviceService.batchUploadUse(files);
    }

    @ApiOperation("下载文件")
    @PostMapping("/download")
    public void download(@RequestBody FileVo fileVo) {
        warehousingFilingDeviceService.download(fileVo);
    }

    @ApiOperation("批量下载文件")
    @PostMapping("/batchDownload")
    public void downloadToZip(@RequestBody List<FileVo> fileVos) {
        warehousingFilingDeviceService.downloadToZip(fileVos);
    }
                
}