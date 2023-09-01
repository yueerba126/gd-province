package com.sydata.reserve.layout.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.MonthlyReportPageParam;
import com.sydata.reserve.layout.param.PersonnelWorkloadPageParam;
import com.sydata.reserve.layout.service.IPersonnelWorkloadService;
import com.sydata.reserve.layout.vo.MonthlyReportVo;
import com.sydata.reserve.layout.vo.PersonnelWorkloadVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 储备布局地理信息-质检人员工作量统计Controller
 *
 * @author lzq
 * @date 2022-07-08
 */
@Api(tags = "粮食质量")
@Validated
@RestController
@RequestMapping("/basis/personnel/workload")
public class PersonnelWorkloadController {

    @Resource
    private IPersonnelWorkloadService personnelWorkloadService;

    @ApiOperation("质检人员工作量统计")
    @PostMapping("/statistics")
    public Page<PersonnelWorkloadVo> page(@RequestBody @Valid PersonnelWorkloadPageParam pageParam) {
        return personnelWorkloadService.pageWorkload(pageParam);
    }

    @ApiOperation("检测业务月报表")
    @PostMapping("/monthlyReport")
    public Page<MonthlyReportVo> monthlyReport(@RequestBody @Valid MonthlyReportPageParam pageParam) {
        return personnelWorkloadService.monthlyReport(pageParam);
    }


}