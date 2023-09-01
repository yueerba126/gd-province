package com.sydata.framework.core.log;


import com.sydata.framework.core.util.IpUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;


/**
 * @author lzq
 * @description 接口请求日志打印
 * @date 2021-03-11 14:51
 **/
@Aspect
@Component
@Slf4j
@Order(1)
public class RequestLogPrintAspect {

    @Resource
    private HttpServletRequest request;

    private static final int RESULT_LENGTH = 500;
    private static final int PARAM_LENGTH = 500;

    /**
     * 环绕通知,记录请求日志
     */
    @Around("@annotation(apiOperation)")
    public Object controllerLog(ProceedingJoinPoint pjp, ApiOperation apiOperation) throws Throwable {
        LocalDateTime beginTime = LocalDateTime.now();

        StringBuilder sb = new StringBuilder();
        sb.append("\r\n==========================================").append("\r\n");

        sb.append("Request API : ").append(apiOperation.value()).append("\r\n");


        sb.append("Request Start : ").append(beginTime).append("\r\n");
        sb.append("Request IP : ").append(IpUtils.getIpAddress(request)).append("\r\n");
        sb.append("Request url : ").append(request.getRequestURL().toString()).append("\r\n");
        sb.append("Http Method : ").append(request.getMethod()).append("\r\n");
        sb.append("CLASS_METHOD : ").append(pjp.getTarget().getClass().getSimpleName()).append(".").append(pjp.getSignature().getName()).append("\r\n");

        String params = Arrays.toString(pjp.getArgs());
        String paramStr = params.length() > PARAM_LENGTH ? params.substring(0, PARAM_LENGTH) + "..." : params;
        sb.append("Params : ").append(paramStr).append("\r\n");

        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            sb.append("Request Error : ").append(e.getMessage()).append("\r\n");
            throw e;
        } finally {
            LocalDateTime endTime = LocalDateTime.now();

            String resultStr = String.valueOf(result);
            resultStr = resultStr.length() > RESULT_LENGTH ? resultStr.substring(0, RESULT_LENGTH) + "..." : resultStr;

            sb.append("Request Result : ").append(resultStr).append("\r\n");
            sb.append("Request End : ").append(endTime).append("\r\n");
            sb.append("Request Time : ").append(Duration.between(beginTime, endTime).toMillis()).append("MS").append("\r\n");
            sb.append("==========================================").append("\r\n");
            log.info(sb.toString());
        }
        return result;
    }
}
