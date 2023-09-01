package com.sydata.framework.databind.aspect;

import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 基础数据转换切面
 *
 * @author zhoucl
 * @date 2021/5/12 19:22
 */
@Aspect
@Component
@Slf4j
public class DataBindAspect {

    @Around(value = "@annotation(dataBindFieldConvert)")
    public Object operationRecordAround(ProceedingJoinPoint joinPoint, DataBindFieldConvert dataBindFieldConvert) throws Throwable {
        Object proceed = joinPoint.proceed();
        DataBindHandleBootstrap.dataHandConvert(proceed, dataBindFieldConvert.convertNumber());
        return proceed;
    }
}
