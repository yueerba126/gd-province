package com.sydata.framework.databind.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 基础数据转换配置类
 *
 * @author zhoucl
 * @date 2021/5/15 17:37
 */
@Component
@ConfigurationProperties(prefix = "basic.data")
@Getter
@Setter
public class DataBindConfig {

    /**
     * 调用远程前是否先读取缓存
     */
    private Boolean isFirstCache = false;

    /**
     * 转换配置
     */
    private DataConvertConfig convert;
}
