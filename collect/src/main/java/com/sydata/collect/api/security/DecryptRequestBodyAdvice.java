package com.sydata.collect.api.security;

import cn.hutool.core.io.IoUtil;
import com.sydata.collect.api.security.annotation.WebSecurity;
import com.sydata.collect.api.security.config.WebSecurityConfig;
import com.sydata.framework.core.exception.WebSecurityException;
import com.sydata.framework.util.AesUtil;
import com.sydata.organize.security.UserSecurity;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static java.lang.Boolean.FALSE;

/**
 * @author lzq
 * @describe 请求解密处理
 * @date 2022-04-07 11:01
 */
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Resource
    private WebSecurityConfig webSecurityConfig;

    private List<Consumer<DecryptHttpInputMessage>> checks = new ArrayList<>();

    @PostConstruct
    public void init() {
        checks.add(this::signatureCheck);
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        Method method = methodParameter.getMethod();
        return Optional.ofNullable(method.getAnnotation(WebSecurity.class))
                .map(WebSecurity::decrypt)
                .orElse(FALSE);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter,
                                           Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {

        InputStream body = inputMessage.getBody();
        HttpHeaders headers = inputMessage.getHeaders();

        // 构建签名校验传递参数，校验签名摘要
        DecryptHttpInputMessage httpInputMessage = new DecryptHttpInputMessage(body, headers);

        try {
            checks.forEach(check -> check.accept(httpInputMessage));
            httpInputMessage.setDecryptBody(new ByteArrayInputStream(httpInputMessage.getDecryptBytes()));
        } catch (WebSecurityException e) {
            throw e;
        } catch (Exception e) {
            log.error("签名验证失败", e);
            throw new WebSecurityException("签名验证失败");
        } finally {
            httpInputMessage.clean();
        }

        return httpInputMessage;

    }

    /**
     * 摘要校验
     *
     * @param message 解密HTTPInputMessage
     */
    @SneakyThrows(Throwable.class)
    private void signatureCheck(DecryptHttpInputMessage message) {
        // 校验必须包含参数签名
        List<String> digest = message.getHeaders().get(webSecurityConfig.getDigest());
        WebSecurityException.notEmpty(digest, "参数签名失败");

        // 解密参数
        byte[] data = IoUtil.readBytes(message.body);
        byte[] decryptBytes = AesUtil.decryptBase64Bytes(UserSecurity.loginUser().getAppSecret(), data);

        // 验证签名信息
        String md5Sign = DigestUtils.md5Hex(decryptBytes);
        WebSecurityException.state(Objects.equals(md5Sign, digest.get(0)), "参数签名验证失败");

        message.setDecryptBytes(decryptBytes);
    }


    /**
     * @author lzq
     * @describe 解密HTTPInputMessage
     * @date 2022-04-07 11:01
     */
    @Data
    @Accessors(chain = true)
    class DecryptHttpInputMessage implements HttpInputMessage {

        /**
         * 原body
         */
        private InputStream body;

        /**
         * 原headers
         */
        private HttpHeaders headers;

        /**
         * 数据解密字节数组
         */
        private byte[] decryptBytes;

        /**
         * 解密body
         */
        private InputStream decryptBody;

        public DecryptHttpInputMessage(InputStream body, HttpHeaders headers) {
            this.body = body;
            this.headers = headers;
        }

        @Override
        public InputStream getBody() {
            return decryptBody;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        /**
         * 清理
         */
        public void clean() {
            IoUtil.close(body);
            decryptBytes = null;
        }
    }
}
