package com.sydata.framework.core.exception;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author lzq
 * @describe 安全签名异常
 * @date 2022-06-23 16:27
 */
public class WebSecurityException extends RuntimeException {

    public WebSecurityException(String message) {
        super(message);
    }


    /**
     * 状态校验
     *
     * @param expression 异常码
     * @param message    异常信息
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new WebSecurityException(message);
        }
    }

    /**
     * 集合非空校验
     *
     * @param collection 集合
     * @param message    异常信息
     */
    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new WebSecurityException(message);
        }
    }

    /**
     * 字符串非空校验
     *
     * @param text    文本
     * @param message 异常信息
     */
    public static void hasLength(@Nullable String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new WebSecurityException(message);
        }
    }
}
