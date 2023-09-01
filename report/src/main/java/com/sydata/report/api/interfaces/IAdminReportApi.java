package com.sydata.report.api.interfaces;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.sydata.collect.api.annotation.DataApi;
import com.sydata.report.api.annotation.ReportV2022Api;
import com.sydata.report.api.param.admin.*;
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
 * @description 行政管理上报接口
 * @date 2022/10/31 15:35
 */
@ReportV2022Api
@RetrofitClient(baseUrl = "${report.baseUrl}", sourceOkHttpClient = REPORT_OK_HTTP_CLIENT)
@Retry(maxRetries = 3, intervalMs = 60 * 1000 * 10)
@Logging(logLevel = INFO, logStrategy = BASIC)
public interface IAdminReportApi extends IReportApiV2022 {

    /**
     * 储备规模上报
     *
     * @param reportParams 储备规模上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(RESERVE_SCALE)
    @POST("/dep/service/API/UPLOAD/V2022/1401")
    ReportResultVo reserveScale(@Body List<ReserveScaleReportParam> reportParams);

    /**
     * 储备计划信息上报
     *
     * @param reportParams 储备计划信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(RESERVE_PLAN)
    @POST("/dep/service/API/UPLOAD/V2022/1402")
    ReportResultVo reservePlan(@Body List<ReservePlanReportParam> reportParams);

    /**
     * 轮换计划上报
     *
     * @param reportParams 轮换计划上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(ROTATION_PLAN)
    @POST("/dep/service/API/UPLOAD/V2022/1403")
    ReportResultVo rotationPlan(@Body List<RotationPlanReportParam> reportParams);

    /**
     * 轮换计划明细上报
     *
     * @param reportParams 轮换计划明细上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(ROTATION_PLAN_DTL)
    @POST("/dep/service/API/UPLOAD/V2022/1404")
    ReportResultVo rotationPlanDtl(@Body List<RotationPlanDtlReportParam> reportParams);

    /**
     * 项目信息上报
     *
     * @param reportParams 项目信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(PROJECT)
    @POST("/dep/service/API/UPLOAD/V2022/1405")
    ReportResultVo project(@Body List<ProjectReportParam> reportParams);
}
