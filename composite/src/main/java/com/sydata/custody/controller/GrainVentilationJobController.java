package com.sydata.custody.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.custody.dto.GrainCustodyJobsQueryDto;
import com.sydata.custody.service.ICustodyJobService;
import com.sydata.custody.vo.GrainSteamTaskInformationVo;
import com.sydata.custody.vo.GrainVentilationJobVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author zhangcy
 * @since 2023/4/23 15:42
 */
@Api(tags = "保管作业API")
@RequestMapping("/custody/grain")
@RestController
public class GrainVentilationJobController {

    @Resource
    private ICustodyJobService custodyJobService;

    @ApiOperation(value = "通风作业分页")
    @PostMapping("/ventilation/page")
    public Page<GrainVentilationJobVo> ventilationJobVoPage(@RequestBody @Valid GrainCustodyJobsQueryDto queryDto) {
        return custodyJobService.grainVentilationPage(queryDto);
    }

    @ApiOperation(value = "通风作业详情")
    @PostMapping("/ventilation/detail")
    public GrainVentilationJobVo ventilationJobVoDetail(@RequestParam("id") String id) {
        return custodyJobService.ventilationJobVoDetail(id);
    }

    @ApiOperation(value = "熏蒸作业分页")
    @PostMapping("/steam/task/information/page")
    public Page<GrainSteamTaskInformationVo> grainSteamTaskInformationPage(@RequestBody @Valid GrainCustodyJobsQueryDto queryDto) {
        return custodyJobService.grainSteamTaskInformationPage(queryDto);
    }

    @ApiOperation(value = "熏蒸作业详情")
    @PostMapping("/steam/task/information/detail")
    public GrainSteamTaskInformationVo grainSteamTaskInformationDetail(@RequestParam("id") String id) {
        return custodyJobService.grainSteamTaskInformationDetail(id);
    }

}
