package com.sydata.filing.http;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.sydata.filing.param.WarehousingFilingCompanySaveParam;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.github.lianjiatech.retrofit.spring.boot.log.LogLevel.INFO;
import static com.github.lianjiatech.retrofit.spring.boot.log.LogStrategy.BASIC;
import static com.sydata.http.KuOkHttpClientConfig.KU_OK_HTTP_CLIENT;

/**
 * @Author lzq
 * @Description 远程调用库软件接口
 * @Date 16:37 2023/5/29
 * @Param
 * @return
 **/
@RetrofitClient(baseUrl = "${spt.host}", sourceOkHttpClient = KU_OK_HTTP_CLIENT)
@Retry(maxRetries = 3, intervalMs = 60 * 1000 * 10)
@Logging(logLevel = INFO, logStrategy = BASIC)
public interface WarehousingFilingFactoryService {

    /**
     * 申报信息下发信息至库软件
     *
     * @param param 请求加密参数
     * @return 返回值
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("/saas/filing/warehousingFilingCompany/doSptReturnInfo")
    Boolean doSptReturnInfo(@Body WarehousingFilingCompanySaveParam param);
}
