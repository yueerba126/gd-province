package com.sydata;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * <p>
 * 广东省平台服务启动类
 * </p>
 *
 * @author lzq
 * @date 2022/6/23 15:10
 */
@Slf4j
@ServletComponentScan
@SpringBootApplication
@MapperScan("com.sydata.**.mapper")
public class GdProvincePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(GdProvincePlatformApplication.class, args);
        log.info("广东省平台服务启动成功");
    }
}
