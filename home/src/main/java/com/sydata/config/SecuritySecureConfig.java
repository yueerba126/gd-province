package com.sydata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <p>
 * Security安全配置
 * </p>
 *
 * @author lzq
 * @date 2022/6/23 15:10
 */
@Configuration(proxyBeanMethods = false)
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // actuator需要认证
                .antMatchers("/actuator/**").authenticated()
                // 其余路径无条件允许访问
                .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}