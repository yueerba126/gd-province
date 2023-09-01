package com.sydata.data.quality.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.data.quality.domain.DataIssueDtl;
import com.sydata.data.quality.param.DataIssueExportParam;
import com.sydata.data.quality.param.DataIssuePageParam;
import com.sydata.data.quality.service.IDataIssueDtlService;
import com.sydata.data.quality.service.IDataIssueService;
import com.sydata.data.quality.vo.DataIssueVo;
import com.sydata.framework.core.global.annotation.ExcludeGlobalResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author lzq
 * @description 数据质量-数据问题API
 * @date 2023/4/25 9:33
 */
@Api(tags = "数据质量-数据问题API")
@Validated
@RestController
@RequestMapping("/data/quality/data/issue")
public class DataIssueController {

    @Resource
    private IDataIssueService dataIssueService;

    @Resource
    private IDataIssueDtlService dataIssueDtlService;

    @ApiOperation("库区统计分页")
    @PostMapping("/page/stock/house")
    public Page<DataIssueVo> pageByStockHouse(@RequestBody @Valid DataIssuePageParam pageParam) {
        return dataIssueService.pageByStockHouse(pageParam);
    }

    @ApiOperation("库区接口统计分页")
    @PostMapping("/page/stock/house/api")
    public Page<DataIssueVo> pageByStockHouseApi(@RequestBody @Valid DataIssuePageParam pageParam) {
        return dataIssueService.pageByStockHouseApi(pageParam);
    }

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<DataIssueVo> page(@RequestBody @Valid DataIssuePageParam pageParam) {
        return dataIssueService.pages(pageParam);
    }

    @ApiOperation("明细列表")
    @PostMapping("/dtls")
    public List<DataIssueDtl> listByIssueDataId(@RequestParam("issueDataId") String issueDataId) {
        return dataIssueDtlService.listByIssueDataId(issueDataId);
    }

    @ExcludeGlobalResponse
    @ApiOperation("导出")
    @PostMapping("/export")
    public void export(@Valid @RequestBody DataIssueExportParam exportParam) {
        dataIssueService.export(exportParam);
    }
}
