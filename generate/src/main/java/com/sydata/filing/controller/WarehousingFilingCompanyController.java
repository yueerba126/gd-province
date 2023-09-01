package com.sydata.filing.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.filing.service.IWarehousingFilingCompanyService;
import com.sydata.filing.param.WarehousingFilingCompanyPageParam;
import com.sydata.filing.param.WarehousingFilingCompanySaveParam;
import com.sydata.filing.vo.WarehousingFilingCompanyVo;
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
 * @Description:TODO(仓储备案-仓储企业API接口层)
 * @date 2023年06月16日
 * @version: V1.0
 * @author: lzq
 *
 */
@Api(tags = "仓储备案-仓储企业",value = "仓储备案-仓储企业")
@Validated
@RestController
@RequestMapping("/filing/warehousingFilingCompany")
public class WarehousingFilingCompanyController {

    @Resource
    private IWarehousingFilingCompanyService warehousingFilingCompanyService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<WarehousingFilingCompanyVo> page(@RequestBody @Valid WarehousingFilingCompanyPageParam pageParam) {
        return warehousingFilingCompanyService.pages(pageParam);
    }

    @ApiOperation("查询详情")
    @PostMapping("/detail")
    public WarehousingFilingCompanyVo detail(@RequestParam("id") String id) {
        return warehousingFilingCompanyService.detail(id);
    }

    @ApiOperation("新增")
    @PostMapping("/save")
    public String save(@RequestBody @Valid WarehousingFilingCompanySaveParam param) {
        return warehousingFilingCompanyService.saveData(param);
    }

    @ApiOperation("修改")
    @PostMapping("/update")
    public String update(@RequestBody @Valid WarehousingFilingCompanySaveParam param) {
        return warehousingFilingCompanyService.updateData(param);
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public Boolean remove(@RequestBody List<String> ids) {
        return warehousingFilingCompanyService.removeData(ids);
    }

    @ApiOperation("批量新增或修改")
    @PostMapping("/saveOrUpdateBatchData")
    public Boolean saveOrUpdateBatchData(@RequestBody @Valid List<WarehousingFilingCompanySaveParam> param) {
        return warehousingFilingCompanyService.saveOrUpdateBatchData(param);
    }

    @ApiOperation("导入")
    @PostMapping("/import")
    public void importData(@NotNull(message = "文件不能为空") @RequestPart("file") MultipartFile file) {
        warehousingFilingCompanyService.importData(file);
    }

    @ApiOperation("导出")
    @PostMapping("/export")
    public void export(@RequestBody @Valid WarehousingFilingCompanyPageParam pageParam) {
        warehousingFilingCompanyService.exportData(pageParam);
    }
                
}