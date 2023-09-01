package com.sydata.framework.databind.spring;

import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.handle.DataBindQueryBootstrap;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * <p>
 * 通过扩展springIoc的生命周期,初始化扫描时将数据绑定查询服务加载
 * </p>
 *
 * @author zhoucl
 * @date 2021/3/10 0010 15:19
 */
@Component
public class DataBindProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 获取目标类的所有方法
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            // 获取方法上数据绑定注解
            DataBindService dataBindService = AnnotationUtils.findAnnotation(method, DataBindService.class);
            if (dataBindService != null) {
                method.setAccessible(true);
                DataBindQueryBootstrap.registerDataQueryService(bean, dataBindService, method);
            }
        }
        // 一定要返回bean不然该bean不会加载到spring容器
        return bean;
    }
}
