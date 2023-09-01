package com.sydata.report.api.interfaces;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.sydata.collect.api.annotation.DataApi;
import com.sydata.report.api.annotation.ReportV2022Api;
import com.sydata.report.api.param.trade.*;
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
 * @description 粮油购销上报接口
 * @date 2022/10/31 15:35
 */
@ReportV2022Api
@RetrofitClient(baseUrl = "${report.baseUrl}", sourceOkHttpClient = REPORT_OK_HTTP_CLIENT)
@Retry(maxRetries = 3, intervalMs = 60 * 1000 * 10)
@Logging(logLevel = INFO, logStrategy = BASIC)
public interface ITradeReportApi extends IReportApiV2022 {

    /**
     * 合同信息上报
     *
     * @param reportParams 合同信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(CONTRACT)
    @POST("/dep/service/API/UPLOAD/V2022/1201")
    ReportResultVo contract(@Body List<ContractReportParam> reportParams);


    /**
     * 入库信息上报
     *
     * @param reportParams 入库信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(IN_STOCK)
    @POST("/dep/service/API/UPLOAD/V2022/1202")
    ReportResultVo inStock(@Body List<InStockReportParam> reportParams);

    /**
     * 入库检验信息上报
     *
     * @param reportParams 入库检验信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(IN_STOCK_CHECK)
    @POST("/dep/service/API/UPLOAD/V2022/1203")
    ReportResultVo inStockCheck(@Body List<InStockCheckReportParam> reportParams);

    /**
     * 入库结算信息上报
     *
     * @param reportParams 入库结算信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(IN_STOCK_SETTLEMENT)
    @POST("/dep/service/API/UPLOAD/V2022/1204")
    ReportResultVo inStockSettlement(@Body List<InStockSettlementReportParam> reportParams);

    /**
     * 出库信息上报
     *
     * @param reportParams 出库信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(OUT_STOCK)
    @POST("/dep/service/API/UPLOAD/V2022/1205")
    ReportResultVo outStock(@Body List<OutStockReportParam> reportParams);

    /**
     * 出库结算信息上报
     *
     * @param reportParams 出库结算信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(OUT_STOCK_SETTLEMENT)
    @POST("/dep/service/API/UPLOAD/V2022/1206")
    ReportResultVo outStockSettlement(@Body List<OutStockSettlementReportParam> reportParams);

    /**
     * 倒仓信息上报
     *
     * @param reportParams 倒仓信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(TRANSFER_BARN)
    @POST("/dep/service/API/UPLOAD/V2022/1207")
    ReportResultVo transferBarn(@Body List<TransferBarnReportParam> reportParams);

    /**
     * 库存信息上报
     *
     * @param reportParams 库存信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(STOCK_GRAIN)
    @POST("/dep/service/API/UPLOAD/V2022/1208")
    ReportResultVo stockGrain(@Body List<StockGrainReportParam> reportParams);

    /**
     * 损溢单上报
     *
     * @param reportParams 损溢单上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(LOSS)
    @POST("/dep/service/API/UPLOAD/V2022/1209")
    ReportResultVo loss(@Body List<LossReportParam> reportParams);

    /**
     * 性质转变上报
     *
     * @param reportParams 性质转变上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(TRANSFER_NATURE)
    @POST("/dep/service/API/UPLOAD/V2022/1210")
    ReportResultVo transferNature(@Body List<TransferNatureReportParam> reportParams);

    /**
     * 账面库存信息上报
     *
     * @param reportParams 账面库存信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(MONTH_STOCK)
    @POST("/dep/service/API/UPLOAD/V2022/1211")
    ReportResultVo monthStock(@Body List<MonthStockReportParam> reportParams);

    /**
     * 客户信息上报
     *
     * @param reportParams 客户信息上报参数列表
     * @return 国家平台响应参数
     */
    @DataApi(CUSTOMER)
    @POST("/dep/service/API/UPLOAD/V2022/1212")
    ReportResultVo customer(@Body List<CustomerReportParam> reportParams);
}
