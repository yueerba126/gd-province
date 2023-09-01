package com.sydata.reserve.http.service;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.sydata.reserve.http.DistributionResult;
import com.sydata.reserve.http.vo.DistributionResultVo;
import com.sydata.reserve.plan.param.PlanReserveDistributionParam;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.github.lianjiatech.retrofit.spring.boot.log.LogLevel.INFO;
import static com.github.lianjiatech.retrofit.spring.boot.log.LogStrategy.BASIC;
import static com.sydata.http.KuOkHttpClientConfig.KU_OK_HTTP_CLIENT;

/**
 * 市平台远程调用接口
 *
 * @author fuql
 */
@RetrofitClient(baseUrl = "${spt.host}", sourceOkHttpClient = KU_OK_HTTP_CLIENT)
@Retry(maxRetries = 3, intervalMs = 60 * 1000 * 10)
@Logging(logLevel = INFO, logStrategy = BASIC)
public interface PlanReserveFactoryService {

    /**
     * 省平台下发储备计划
     *
     * @param param 请求加密参数
     * @return 返回值
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("/saas/plan/reserve/plan/receiving/information")
    DistributionResult<DistributionResultVo> receivingInformation(@Body PlanReserveDistributionParam param);
}
