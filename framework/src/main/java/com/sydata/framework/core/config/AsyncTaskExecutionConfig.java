package com.sydata.framework.core.config;

import com.dtp.core.spring.EnableDynamicTp;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * @author lzq
 * @describe 应用线程池配置
 * @date 2022-04-12 11:24
 */
@EnableDynamicTp
@EnableAsync
@Configuration
public class AsyncTaskExecutionConfig implements AsyncConfigurer {

    @Resource
    private Executor taskExecutor;

    @Override
    public Executor getAsyncExecutor() {
        return taskExecutor;
    }
}
