package com.sydata.framework.orm.config;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzq
 * @description 自定义ID生成器
 * @date 2022/11/25 18:36
 */
@Configuration
public class IdentifierGeneratorConfig {

    @Bean
    public IdentifierGenerator idGenerator(@Value("${sequence.workerId}") long workerId,
                                           @Value("${sequence.dataCenterId}") long dataCenterId) {
        return new DefaultIdentifierGenerator(workerId, dataCenterId);
    }
}
