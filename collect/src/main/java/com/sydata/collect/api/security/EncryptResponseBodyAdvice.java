package com.sydata.collect.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sydata.collect.api.security.annotation.WebSecurity;
import com.sydata.framework.util.AesUtil;
import com.sydata.organize.security.UserSecurity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Optional;

import static java.lang.Boolean.FALSE;


/**
 * @author lzq
 * @describe 响应加密处理
 * @date 2022-04-07 11:01
 */
@Slf4j
@ControllerAdvice
@RestControllerAdvice
@Order(1)
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice {

    private ObjectMapper objectMapper;

    public EncryptResponseBodyAdvice(MappingJackson2HttpMessageConverter messageConverter) {
        this.objectMapper = messageConverter.getObjectMapper();
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        Method method = methodParameter.getMethod();
        return Optional.ofNullable(method.getAnnotation(WebSecurity.class))
                .map(WebSecurity::encrypt)
                .orElse(FALSE);
    }


    @SneakyThrows(Throwable.class)
    @Override
    public Object beforeBodyWrite(Object result, MethodParameter methodParameter, MediaType mediaType,
                                  Class aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        String json = objectMapper.writeValueAsString(result);

        String appSecret = UserSecurity.loginUser().getAppSecret();
        return AesUtil.encryptBase64(appSecret, json);
    }
}
