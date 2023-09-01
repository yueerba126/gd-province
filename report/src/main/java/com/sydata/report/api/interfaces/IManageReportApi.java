package com.sydata.report.api.interfaces;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.sydata.collect.api.annotation.DataApi;
import com.sydata.report.api.annotation.ReportV2022Api;
import com.sydata.report.api.param.manage.*;
import com.sydata.report.api.service.IReportApiV2022;
import com.sydata.report.vo.ReportResultVo;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

import static com.github.lianjiatech.retrofit.spring.boot.log.LogLevel.INFO;
import static com.github.lianjiatech.retrofit.spring.boot.log.LogStrategy.BASIC;
import static com.sydata.collect.api.enums.DataApiEnum.*;
import static com.sydata.report.api.config.ReportOkHttpClientRegistrar.REPORT_OK_HTTP_CLIENT;

/**
 * @author lzq
 * @description 粮库管理上报接口
 * @date 2022/10/31 15:35
 */
@ReportV2022Api
@RetrofitClient(baseUrl = "${report.baseUrl}", sourceOkHttpClient = REPORT_OK_HTTP_CLIENT)
@Retry(maxRetries = 3, intervalMs = 60 * 1000 * 10)
@Logging(logLevel = INFO, logStrategy = BASIC)
public interface IManageReportApi extends IReportApiV2022 {

    /**
     * 安全管理上报
     *
     * @param reportParams 安全管理上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(SAFETY_CHECK)
    @POST("/dep/service/API/UPLOAD/V2022/1301")
    ReportResultVo safetyCheck(@Body List<SafetyCheckReportParam> reportParams);

    /**
     * 温湿度检测上报
     *
     * @param reportParams 温湿度检测上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(GRAIN_MONITOR)
    @POST("/dep/service/API/UPLOAD/V2022/1302")
    ReportResultVo grainMonitor(@Body List<GrainMonitorReportParam> reportParams);

    /**
     * 害虫检测上报
     *
     * @param reportParams 害虫检测上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(PEST_INFORMATION)
    @POST("/dep/service/API/UPLOAD/V2022/1303")
    ReportResultVo pestInformation(@Body List<PestInformationReportParam> reportParams);

    /**
     * 气体检测上报
     *
     * @param reportParams 气体检测上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(GAS_INFORMATION)
    @POST("/dep/service/API/UPLOAD/V2022/1304")
    ReportResultVo gasInformation(@Body List<GasInformationReportParam> reportParams);

    /**
     * 通风作业上报
     *
     * @param reportParams 通风作业上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(VENTILATION)
    @POST("/dep/service/API/UPLOAD/V2022/1305")
    ReportResultVo ventilation(@Body List<VentilationReportParam> reportParams);

    /**
     * 蒸熏作业信息上报
     *
     * @param reportParams 蒸熏作业信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(STEAM_TASK_INFORMATION)
    @POST("/dep/service/API/UPLOAD/V2022/1306")
    ReportResultVo steamTaskInformation(@Body List<SteamTaskInformationReportParam> reportParams);

    /**
     * 仓内视频图像上报
     *
     * @param reportParams 仓内视频图像上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(GRANARY_VIDEO_IMAGE)
    @POST("/dep/service/API/UPLOAD/V2022/1307")
    ReportResultVo granaryVideoImage(@Body List<GranaryVideoImageReportParam> reportParams);

    /**
     * 库区视频监控异常事件告警上报
     *
     * @param reportParams 库区视频监控异常事件告警上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(EXCEPTION_MONITORING_EVENT)
    @POST("/dep/service/API/UPLOAD/V2022/1308")
    ReportResultVo exceptionMonitoringEvent(@Body List<ExceptionMonitoringEventReportParam> reportParams);

    /**
     * 违规预警信息上报
     *
     * @param reportParams 违规预警信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(VIOLATION_WARNING)
    @POST("/dep/service/API/UPLOAD/V2022/1309")
    ReportResultVo violationWarning(@Body List<ViolationWarningReportParam> reportParams);

    /**
     * 质检信息上报
     *
     * @param reportParams 质检信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(QUALITY_INSPECTION)
    @POST("/dep/service/API/UPLOAD/V2022/1310")
    ReportResultVo qualityInspection(@Body List<QualityInspectionReportParam> reportParams);
}
