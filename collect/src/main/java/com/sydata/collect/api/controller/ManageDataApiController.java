package com.sydata.collect.api.controller;

import com.sydata.collect.api.annotation.DataApi;
import com.sydata.collect.api.param.manage.*;
import com.sydata.collect.api.security.annotation.WebSecurity;
import com.sydata.collect.api.validated.group.BasicCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.sydata.collect.api.enums.DataApiEnum.*;


/**
 * @author lzq
 * @description 数据收集-粮食管理API(开放对接)
 * @date 2022/10/19 11:06
 */
@Api(tags = "数据收集-粮食管理API(开放对接)")
@RestController
@Validated
@RequestMapping("/api/v2022")
public class ManageDataApiController {

    @ApiOperation("安全管理API")
    @PostMapping("/aqgl")
    @Validated(BasicCheck.class)
    @DataApi(SAFETY_CHECK)
    @WebSecurity
    public void safetyCheck(@RequestBody @Valid SafetyCheckApiParam apiParam) {
    }

    @ApiOperation("粮情温湿度监测API")
    @PostMapping("/wsdjc")
    @Validated(BasicCheck.class)
    @DataApi(GRAIN_MONITOR)
    @WebSecurity
    public void grainMonitor(@RequestBody @Valid GrainMonitorApiParam apiParam) {
    }

    @ApiOperation("害虫检测")
    @PostMapping("/hcjc")
    @Validated(BasicCheck.class)
    @DataApi(PEST_INFORMATION)
    @WebSecurity
    public void pestInformation(@RequestBody @Valid PestInformationApiParam apiParam) {
    }

    @ApiOperation("气体检测API")
    @PostMapping("/qtjc")
    @Validated(BasicCheck.class)
    @DataApi(GAS_INFORMATION)
    @WebSecurity
    public void gasInformation(@RequestBody @Valid GasInformationApiParam apiParam) {
    }

    @ApiOperation("通风作业API")
    @PostMapping("/tfzy")
    @Validated(BasicCheck.class)
    @DataApi(VENTILATION)
    @WebSecurity
    public void ventilation(@RequestBody @Valid VentilationApiParam apiParam) {
    }

    @ApiOperation("熏蒸作业API")
    @PostMapping("/xzzy")
    @Validated(BasicCheck.class)
    @DataApi(STEAM_TASK_INFORMATION)
    @WebSecurity
    public void steamTaskInformation(@RequestBody @Valid SteamTaskInformationApiParam apiParam) {
    }

    @ApiOperation("仓内视频图像API")
    @PostMapping("/cnsptx")
    @Validated(BasicCheck.class)
    @DataApi(GRANARY_VIDEO_IMAGE)
    @WebSecurity
    public void granaryVideoImage(@RequestBody @Valid GranaryVideoImageApiParam apiParam) {
    }

    @ApiOperation("库区视频监控异常事件告警API")
    @PostMapping("/spjkycsjgjjbxx")
    @Validated(BasicCheck.class)
    @DataApi(EXCEPTION_MONITORING_EVENT)
    @WebSecurity
    public void exceptionMonitoringEvent(@RequestBody @Valid ExceptionMonitoringEventApiParam apiParam) {
    }

    @ApiOperation("违规预警API")
    @PostMapping("/wgyjxx")
    @Validated(BasicCheck.class)
    @DataApi(VIOLATION_WARNING)
    @WebSecurity
    public void violationWarning(@RequestBody @Valid ViolationWarningApiParam apiParam) {
    }

    @ApiOperation("质检数据API")
    @PostMapping("/zjsj")
    @Validated(BasicCheck.class)
    @DataApi(QUALITY_INSPECTION)
    @WebSecurity
    public void qualityInspection(@RequestBody @Valid QualityInspectionApiParam apiParam) {
    }

}
