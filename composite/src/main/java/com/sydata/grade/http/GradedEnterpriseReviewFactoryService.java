package com.sydata.grade.http;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.sydata.grade.param.GradedEnterpriseReviewSaveParam;
import com.sydata.grade.param.GradedEnterpriseStockSaveParam;
import com.sydata.grade.param.GradedGrainDepotTemplateSaveParam;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.List;

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
public interface GradedEnterpriseReviewFactoryService {

    /**
     * 申报信息下发信息至库软件
     *
     * @param param 请求加密参数
     * @return 返回值
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("/saas/graded/gradedEnterpriseReview/doSptReturnInfo")
    Boolean doSptReturnInfo(@Body GradedEnterpriseReviewSaveParam param);

    /**
     * 模板信息下发信息至库软件
     *
     * @param param 请求加密参数
     * @return 返回值
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("/saas/graded/gradedGrainDepotTemplate/saveOrUpdate")
    Boolean saveOrUpdateTemplate(@Body GradedGrainDepotTemplateSaveParam param);

    /**
     * 模板信息删除下发信息至库软件
     *
     * @param ids 请求加密参数
     * @return 返回值
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("/saas/graded/gradedGrainDepotTemplate/remove")
    Boolean removeTemplate(@Body List<String> ids);

    /**
     * 粮库信息下发信息至库软件
     *
     * @param param 请求加密参数
     * @return 返回值
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("/saas/graded/gradedEnterpriseStock/saveOrUpdate")
    Boolean saveOrUpdateStock(@Body GradedEnterpriseStockSaveParam param);

    /**
     * 粮库信息删除下发信息至库软件
     *
     * @param ids 请求加密参数
     * @return 返回值
     */
    @Headers({"Content-Type:application/json;charset=UTF-8"})
    @POST("/saas/graded/gradedEnterpriseStock/remove")
    Boolean removeStock(@Body List<String> ids);
}
