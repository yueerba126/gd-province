package com.sydata.framework.core.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sydata.framework.core.global.annotation.ExcludeGlobalResponse;
import lombok.SneakyThrows;
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

/**
 * <p>
 * 全局响应处理器
 * </p>
 *
 * @author lzq
 * @date 2021/8/2 10:16
 */
@ControllerAdvice
@RestControllerAdvice
@Order(2)
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {

    private static final String ROOT_PATH = "com.sydata";

    private ObjectMapper objectMapper;

    public GlobalResponseBodyAdvice(MappingJackson2HttpMessageConverter messageConverter) {
        this.objectMapper = messageConverter.getObjectMapper();
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        Method method = methodParameter.getMethod();
        // 默认全局响应，除非方法返回WebResult或标识排除全局响应注解
        return method.getDeclaringClass().getName().startsWith(ROOT_PATH) && method.getReturnType() != WebResult.class
                && !method.isAnnotationPresent(ExcludeGlobalResponse.class);
    }

    @SneakyThrows(Throwable.class)
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (body instanceof WebResult) {
            return body;
        }

        WebResult result = WebResult.success(body);
        if (body instanceof String) {
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return objectMapper.writeValueAsString(result);
        }
        return result;
    }
}
