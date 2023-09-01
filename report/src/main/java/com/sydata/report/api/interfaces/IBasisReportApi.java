package com.sydata.report.api.interfaces;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.sydata.collect.api.annotation.DataApi;
import com.sydata.report.api.annotation.ReportV2022Api;
import com.sydata.report.api.param.basis.*;
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
 * @description 基础信息上报接口
 * @date 2022/10/31 15:35
 */
@ReportV2022Api
@RetrofitClient(baseUrl = "${report.baseUrl}", sourceOkHttpClient = REPORT_OK_HTTP_CLIENT)
@Retry(maxRetries = 3, intervalMs = 60 * 1000 * 10)
@Logging(logLevel = INFO, logStrategy = BASIC)
public interface IBasisReportApi extends IReportApiV2022 {

    /**
     * 企业单位上报
     *
     * @param reportParams 企业单位上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(COMPANY)
    @POST("/dep/service/API/UPLOAD/V2022/1101")
    ReportResultVo company(@Body List<CompanyReportParam> reportParams);

    /**
     * 库区信息上报
     *
     * @param reportParams 库区信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(STOCK_HOUSE)
    @POST("/dep/service/API/UPLOAD/V2022/1102")
    ReportResultVo stockHouse(@Body List<StockHouseReportParam> reportParams);

    /**
     * 仓房信息上报
     *
     * @param reportParams 仓房信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(WAREHOUSE)
    @POST("/dep/service/API/UPLOAD/V2022/1103")
    ReportResultVo warehouse(@Body List<WarehouseReportParam> reportParams);

    /**
     * 廒间信息上报
     *
     * @param reportParams 廒间信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(GRANARY)
    @POST("/dep/service/API/UPLOAD/V2022/1104")
    ReportResultVo granary(@Body List<GranaryReportParam> reportParams);

    /**
     * 货位信息上报
     *
     * @param reportParams 货位信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(CARGO)
    @POST("/dep/service/API/UPLOAD/V2022/1105")
    ReportResultVo cargo(@Body List<CargoReportParam> reportParams);

    /**
     * 油罐信息上报
     *
     * @param reportParams 油罐信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(TANK)
    @POST("/dep/service/API/UPLOAD/V2022/1106")
    ReportResultVo tank(@Body List<TankReportParam> reportParams);

    /**
     * 设备信息上报
     *
     * @param reportParams 设备信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(DEVICE)
    @POST("/dep/service/API/UPLOAD/V2022/1107")
    ReportResultVo device(@Body List<DeviceReportParam> reportParams);

    /**
     * 药剂信息上报
     *
     * @param reportParams 药剂信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(MEDICINE)
    @POST("/dep/service/API/UPLOAD/V2022/1108")
    ReportResultVo medicine(@Body List<MedicineReportParam> reportParams);

    /**
     * 文件信息上报
     *
     * @param reportParams 文件信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(FILE)
    @POST("/dep/service/API/UPLOAD/V2022/1109")
    ReportResultVo file(@Body List<FileReportParam> reportParams);

    /**
     * 库区图仓房点位标注信息上报
     *
     * @param reportParams 库区图仓房点位标注信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(CARGO_LABEL)
    @POST("/dep/service/API/UPLOAD/V2022/1110")
    ReportResultVo cargoLabel(@Body List<CargoLabelReportParam> reportParams);

    /**
     * 库区图视频监控设备点位标注上报
     *
     * @param reportParams 库区图视频监控设备点位标注上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(WEBCAM_LABEL)
    @POST("/dep/service/API/UPLOAD/V2022/1111")
    ReportResultVo webcamLabel(@Body List<WebcamLabelReportParam> reportParams);

    /**
     * 企业人员信息上报
     *
     * @param reportParams 企业人员信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(COMPANY_STAFF)
    @POST("/dep/service/API/UPLOAD/V2022/1112")
    ReportResultVo companyStaff(@Body List<CompanyStaffReportParam> reportParams);

    /**
     * 企业信用信息上报
     *
     * @param reportParams 企业信用信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(COMPANY_CREDIT)
    @POST("/dep/service/API/UPLOAD/V2022/1113")
    ReportResultVo companyCredit(@Body List<CompanyCreditReportParam> reportParams);

    /**
     * 财务报表信息上报
     *
     * @param reportParams 企业信用信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(FINANCE)
    @POST("/dep/service/API/UPLOAD/V2022/1114")
    ReportResultVo finance(@Body List<FinanceReportParam> reportParams);
}
