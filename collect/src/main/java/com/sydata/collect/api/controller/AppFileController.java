
package com.sydata.collect.api.controller;

import com.sydata.common.file.service.IFileStorageService;
import com.sydata.organize.security.UserSecurity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author lzq
 * @description 数据收集-API(开放对接)
 * @date 2022/10/21 18:43
 */
@Api(tags = "数据收集-文件API(开放对接)")
@RestController
@Validated
@RequestMapping("/api/v2022/file")
public class AppFileController {

    @Resource
    private IFileStorageService fileStorageService;

    @ApiOperation("文件上传(上传未使用文件)")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file) {
        return fileStorageService.uploadNotUse(file, UserSecurity.organizeId(), UserSecurity.loginUser().getStockHouseId());
    }

    @ApiOperation("文件上传(上传使用中文件)")
    @PostMapping(value = "/inside/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String insideUpload(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file) {
        return fileStorageService.uploadUse(file, UserSecurity.organizeId(), UserSecurity.loginUser().getStockHouseId());
    }
}
