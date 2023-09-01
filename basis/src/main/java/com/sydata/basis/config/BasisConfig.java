package com.sydata.basis.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;

/**
 * @author lzq
 * @description 系统基础配置文件
 * @date 2022/11/12 16:32
 */
@Configuration
@ConfigurationProperties(prefix = "basis")
@Data
public class BasisConfig {

    @ApiModelProperty("储备粮性质代码")
    private Set<String> reservesFoodNatureCodes;

    @ApiModelProperty("储备粮性质代码归属行政区划类型映射")
    private Map<String, String> reservesFoodNatureCodeTypeMap;

    @ApiModelProperty("库区平面图宽度")
    private int aerialViewImgWidth;

    @ApiModelProperty("库区平面图高度")
    private int aerialViewImgHeight;
}
