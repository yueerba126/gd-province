package com.sydata.collect.api.security.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sydata.collect.api.security.annotation.WebSecurity;
import com.sydata.collect.api.security.config.WebSecurityConfig;
import com.sydata.framework.core.exception.WebSecurityException;
import com.sydata.framework.util.AesUtil;
import com.sydata.organize.security.UserSecurity;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Consumer;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.EQUALS;
import static java.lang.Boolean.TRUE;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author lzq
 * @describe 请求重放拦截器
 * @date 2022-08-11 09:20
 */
@Component
public class RequestReplayInterceptor implements HandlerInterceptor {

    @Resource
    private WebSecurityConfig webSecurityConfig;

    @Resource
    private RedisTemplate redisTemplate;

    private List<Consumer<HttpServletRequest>> checks = new ArrayList<>();

    @PostConstruct
    public void init() {
        checks.add(this::requestOvertimeCheck);
        checks.add(this::requestReplayCheck);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Optional.ofNullable(handlerMethod.getMethod())
                    .map(m -> m.getAnnotation(WebSecurity.class))
                    .filter(WebSecurity::requestReplay)
                    .ifPresent((t) -> checks.forEach(check -> check.accept(request)));
        }
        return TRUE;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        request.removeAttribute(webSecurityConfig.getRequestId());
    }

    /**
     * 请求时效校验
     *
     * @param request
     */
    private void requestOvertimeCheck(HttpServletRequest request) {
        // 校验必须包含请求标识
        String nonce = request.getHeader(webSecurityConfig.getNonce());
        WebSecurityException.hasLength(nonce, "请求标识验证失败");

        // 解密获取内容,验证请求标识长度、规则是否正确
        String nonceContent = AesUtil.decryptBase64(UserSecurity.loginUser().getAppSecret(), nonce);
        String tokenValue = StpUtil.getTokenValue();
        boolean state = nonceContent.length() > tokenValue.length() && nonceContent.startsWith(tokenValue);
        WebSecurityException.state(state, "请求标识验证失败");

        // 校验请求是否已过期
        long timeStampMillis = Long.parseLong(nonceContent.substring(tokenValue.length()));
        long time = System.currentTimeMillis() - timeStampMillis;
        WebSecurityException.state(webSecurityConfig.getRequestOvertime() * 1000 >= time, "请求已过期");
    }


    /**
     * 请求重放拦截
     *
     * @param request
     */
    private void requestReplayCheck(HttpServletRequest request) {
        // 校验必须包含请求唯一标识
        String requestId = request.getHeader(webSecurityConfig.getRequestId());
        WebSecurityException.hasLength(requestId, "请求唯一标识验证失败");

        // 解密获取内容,验证请求唯一标识标识长度是否正确、规则是否正确
        String requestIdContent = AesUtil.decryptBase64(UserSecurity.loginUser().getAppSecret(), requestId);
        String tokenValue = StpUtil.getTokenValue();
        boolean state = requestIdContent.length() > tokenValue.length() && requestIdContent.startsWith(tokenValue);
        WebSecurityException.state(state, "请求唯一标识验证失败");

        String requestIds = requestIdContent.substring(tokenValue.length());
        WebSecurityException.state(requestIds.length() == 19, "请求唯一标识验证失败");

        // 验证请求是否使用过
        String requestIdKey = new StringJoiner(COLON)
                .add(SpringUtil.getApplicationName())
                .add("requestReplayCheck")
                .add(webSecurityConfig.getRequestId() + EQUALS + requestIdContent)
                .toString();

        // 在请求有效期的基础上加5秒
        int overtime = webSecurityConfig.getRequestOvertime() + 5;
        boolean replay = redisTemplate.opsForValue().setIfAbsent(requestIdKey, null, overtime, SECONDS);
        WebSecurityException.state(replay, "请求已处理,不允许重复请求");
    }
}
