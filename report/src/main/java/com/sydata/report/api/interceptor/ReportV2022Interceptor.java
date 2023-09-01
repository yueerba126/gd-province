package com.sydata.report.api.interceptor;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import com.sydata.report.api.config.ReportConfig;
import com.sydata.report.service.ICommandRecordService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import retrofit2.Invocation;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.UNDERSCORE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author lzq
 * @describe 2022上报API请求拦截器
 * @date 2022/10/31 14:33
 */
@Slf4j
@Component
public class ReportV2022Interceptor extends BasePathMatchInterceptor {

    private static final MediaType APPLICATION_JSON = MediaType.parse(APPLICATION_JSON_VALUE);

    private static final String RSA_KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final int MAX_ENCRYPT_BLOCK = 117;

    private final ObjectMapper objectMapper;

    private final ReportConfig reportConfig;

    private final ICommandRecordService commandRecordService;

    public ReportV2022Interceptor(MappingJackson2HttpMessageConverter messageConverter,
                                  ReportConfig reportConfig,
                                  ICommandRecordService commandRecordService) {
        this.objectMapper = messageConverter.getObjectMapper();
        this.reportConfig = reportConfig;
        this.commandRecordService = commandRecordService;
    }

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        // 获取指令ID
        String orderId = commandRecordService.lastOrderId();

        // 获取数据并序列化
        Request request = chain.request();
        Invocation invocation = request.tag(Invocation.class);
        List list = (List) invocation.arguments().get(0);
        String data = objectMapper.writeValueAsString(list);

        // 生成token
        String accessToken = reportConfig.getProvinceId() + UNDERSCORE + LocalDate.now();

        // 构建请求参数 access_token使用省平台私钥加密、data使用国家平台公钥加密
        JSONObject param = new JSONObject();
        param.put("id", IdUtil.simpleUUID());
        param.put("uid", reportConfig.getProvinceId());
        param.put("orderid", orderId);
        param.put("access_token", sign(accessToken, reportConfig.getPrivateKey()));
        param.put("datalength", list.size());
        param.put("data", encryptByPubKey(data, reportConfig.getCountryPublicKey()));
        param.put("digst", md5Digits(data));
        String json = param.toJSONString();

        // 覆盖原请求
        Request newRequest = request.newBuilder().post(RequestBody.create(APPLICATION_JSON, json)).build();
        return chain.proceed(newRequest);
    }

    /**
     * 数字签名
     *
     * @param data          待签名数据
     * @param privateKeyStr 秘钥
     * @return 签名
     */
    @SneakyThrows(Throwable.class)
    private static String sign(String data, String privateKeyStr) {
        // 取得私钥
        byte[] privateKeyBytes = Base64.decodeBase64(privateKeyStr.getBytes(UTF_8));
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(privateKey);
        // 更新
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()), UTF_8);
    }

    /**
     * 公钥加密
     *
     * @param data         待加密数据
     * @param publicKeyStr 公钥
     * @return 密文
     */
    @SneakyThrows(Throwable.class)
    private static String encryptByPubKey(String data, String publicKeyStr) {
        // 取得公钥
        byte[] publicKeyBytes = Base64.decodeBase64(publicKeyStr.getBytes(UTF_8));
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytes = data.getBytes(UTF_8);

        int inputLen = bytes.length;
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            return new String(Base64.encodeBase64(out.toByteArray()), UTF_8);
        } finally {
            IoUtil.close(out);
        }
    }

    /**
     * 生成摘要
     *
     * @param key 待加密数据
     * @return 摘要
     */
    @SneakyThrows(Throwable.class)
    private String md5Digits(String key) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = key.getBytes(StandardCharsets.UTF_8);
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char[] str = new char[j * 2];
        int k = 0;
        for (byte byte0 : md) {
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
