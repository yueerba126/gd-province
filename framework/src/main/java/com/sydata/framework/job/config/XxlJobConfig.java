package com.sydata.framework.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lzq
 * @describe xxl-job 配置信息
 * @date 2022/10/25 19:36
 */
@Slf4j
@Data
@Configuration
@Component
@ConfigurationProperties(prefix = "xxl-job")
@ConditionalOnProperty(name = "xxl-job.enabled", matchIfMissing = true)
public class XxlJobConfig {

    private boolean enabled;

    private String adminAddresses;

    private String accessToken;

    private String appname;

    private String address;

    private String ip;

    private int port;

    private String logPath;

    private int logRetentionDays;

    private String loginIdentity;


    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(@Value("${server.port}") int port) {
        log.info(">>>>>>>>>>> xxl-job config init.");
        if (this.port == 0) {
            this.port = port + 100;
        }

        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname(appname);
        xxlJobSpringExecutor.setAddress(address);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(this.port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobSpringExecutor;
    }
}
