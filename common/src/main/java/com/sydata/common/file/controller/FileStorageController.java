package com.sydata.common.file.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.common.file.domain.FileStorage;
import com.sydata.common.file.param.FileStoragePageParam;
import com.sydata.common.file.param.FileStorageUploadParam;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.common.file.vo.FileStorageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author lzq
 * @describe 文件存储API
 * @date 2022-06-23 20:17
 */
@Api(tags = "公共模块-文件存储API")
@Validated
@RestController
@RequestMapping("/common/file/storage")
public class FileStorageController {

    @Resource
    private IFileStorageService fileStorageService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<FileStorageVo> page(@RequestBody @Valid FileStoragePageParam pageParam) {
        return fileStorageService.pages(pageParam);
    }

    @ApiOperation("批量查询文件存储信息")
    @PostMapping("/list")
    public List<FileStorage> listByIds(@NotEmpty(message = "id不能为空") @RequestBody List<String> ids) {
        return fileStorageService.listByIds(ids);
    }

    @ApiOperation("上传文件(到期未使用会自动过期)")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadNotUse(@Valid FileStorageUploadParam file) {
        return fileStorageService.uploadNotUse(file.getFile(), file.getOrganizeId(), file.getStockHouseId());
    }

    @ApiOperation("上传文件")
    @PostMapping(value = "/inside/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@Valid FileStorageUploadParam file) {
        return fileStorageService.uploadUse(file.getFile(), file.getOrganizeId(), file.getStockHouseId());
    }

    @ApiOperation("弃用文件")
    @PostMapping(value = "/discard")
    public Boolean upload(@RequestParam("fileStorageId") String fileStorageId) {
        return fileStorageService.discardFile(fileStorageId);
    }

    @ApiOperation("文件下载")
    @PostMapping("/download")
    public void download(@RequestParam("fileStorageId") String fileStorageId) {
        fileStorageService.download(fileStorageId);
    }
}
