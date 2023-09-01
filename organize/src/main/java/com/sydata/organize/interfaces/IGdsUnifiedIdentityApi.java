package com.sydata.organize.interfaces;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import com.sydata.organize.interfaces.vo.OauthTokenVo;
import com.sydata.organize.interfaces.vo.UserInfoDetailVo;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.github.lianjiatech.retrofit.spring.boot.log.LogLevel.INFO;
import static com.github.lianjiatech.retrofit.spring.boot.log.LogStrategy.BASIC;
import static com.sydata.organize.config.GdsUnifiedIdentityConfig.GDS_UNIFIED_IDENTITY_OK_HTTP_CLIENT;

/**
 * @author lzq
 * @description 广东省统一身份认证接口
 * @date 2023/5/23 11:13
 */
@RetrofitClient(baseUrl = "${gds-unified-identity.baseUrl}", sourceOkHttpClient = GDS_UNIFIED_IDENTITY_OK_HTTP_CLIENT)
@Retry(maxRetries = 3, intervalMs = 60 * 1000 * 10)
@Logging(logLevel = INFO, logStrategy = BASIC)
public interface IGdsUnifiedIdentityApi {

    /**
     * 获取访问令牌
     *
     * @param clientId     客户端ID
     * @param grantType    请求服务端返回的类型
     * @param redirectUri  回调地址
     * @param code         临时授权码
     * @param clientSecret 应用秘钥
     * @return 获取访问令牌VO
     */
    @FormUrlEncoded
    @POST("/zwrz/rz/sso/oauth/token")
    GdsUnifiedIdentityResult<OauthTokenVo> oauthToken(@Field("client_id") String clientId,
                                                      @Field("grant_type") String grantType,
                                                      @Field("redirect_uri") String redirectUri,
                                                      @Field("code") String code,
                                                      @Field("client_secret") String clientSecret);

    /**
     * 获取登录账号详细信息
     *
     * @param accessToken 访问token
     */
    @FormUrlEncoded
    @POST("/zwrz/rz/sso/openapi/user/getuserinfodetail")
    GdsUnifiedIdentityResult<UserInfoDetailVo> getUserInfoDetail(@Field("access_token") String accessToken);
}
