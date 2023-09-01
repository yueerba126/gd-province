package com.sydata.framework.core.global;

import cn.dev33.satoken.exception.*;
import cn.hutool.core.exceptions.ExceptionUtil;
import com.sydata.framework.core.exception.WebSecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static com.sydata.framework.core.global.CodeEnum.*;

/**
 * 全局异常处理器
 *
 * @author lzq
 */
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public WebResult<String> dbUnKeyRepeatException(SQLIntegrityConstraintViolationException e) {
        return WebResult.error(DB_UN_KEY_REPEAT.getCode(), DB_UN_KEY_REPEAT.getMsg() + COLON + e.getMessage());
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public WebResult<String> asyncRequestTimeoutException(AsyncRequestTimeoutException e) {
        return WebResult.error(ASYNC_REQUEST_TIMEOUT.getCode(), ASYNC_REQUEST_TIMEOUT.getMsg() + COLON + e.getMessage());
    }

    @ExceptionHandler(WebSecurityException.class)
    public WebResult<String> securitySignException(WebSecurityException e) {
        return WebResult.error(NOT_SECURITY_SIGN.getCode(), e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public WebResult<String> illegalStateException(IllegalStateException e) {
        return WebResult.error(ILLEGAL_STATE_EXCEPTION.getCode(), e.getMessage());
    }

    @ExceptionHandler(ApiDisabledException.class)
    public WebResult<String> apiDisabledException(ApiDisabledException e) {
        return WebResult.error(API_DISABLED.getCode(), e.getMessage());
    }

    @ExceptionHandler(IdTokenInvalidException.class)
    public WebResult<String> idTokenInvalidException(IdTokenInvalidException e) {
        return WebResult.error(TOKEN_INVALID.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    public WebResult<String> notPermissionException(NotPermissionException e) {
        return WebResult.error(NOT_PERMISSION.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotRoleException.class)
    public WebResult<String> notRoleException(NotRoleException e) {
        return WebResult.error(NOT_ROLE.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public WebResult<String> notLoginException(NotLoginException e) {
        return WebResult.error(NOT_LOGIN.getCode(), e.getMessage());
    }

    @ExceptionHandler(DisableLoginException.class)
    public WebResult<String> disableLoginException(DisableLoginException e) {
        return WebResult.error(DISABLE_LOGIN.getCode(), String.format(DISABLE_LOGIN.getMsg(), e.getDisableTime()));
    }

    @ExceptionHandler(SaTokenException.class)
    public WebResult<String> saTokenException(SaTokenException e) {
        return WebResult.error(OTHER_TOKEN.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WebResult<String> argumentValidException(MethodArgumentNotValidException e) {
        return WebResult.error(PARAM_VALID_EXCEPTION.getCode(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    public WebResult<String> bindException(BindException e) {
        return WebResult.error(PARAM_VALID_EXCEPTION.getCode(), e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public WebResult<String> constraintValidException(ConstraintViolationException e) {
        return WebResult.error(PARAM_VALID_EXCEPTION.getCode(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public WebResult<String> illegalArgumentException(IllegalArgumentException e) {
        return WebResult.error(PARAM_VALID_EXCEPTION.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public WebResult<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return WebResult.error(PARAM_VALID_EXCEPTION.getCode(), ExceptionUtil.getRootCauseMessage(e));
    }

    @ExceptionHandler(Throwable.class)
    public WebResult<String> throwable(Throwable e) {
        log.error("未知异常", e);
        return WebResult.error(THROWABLE.getCode(), ExceptionUtil.getRootCauseMessage(e));
    }
}
