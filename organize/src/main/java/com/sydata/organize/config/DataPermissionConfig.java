package com.sydata.organize.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lzq
 * @describe 数据权限配置
 * @date 2022-07-03 19:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "data.permission")
public class DataPermissionConfig {

    @ApiModelProperty(value = "超管角色")
    private Long adminRoleId;

    @ApiModelProperty(value = "耕地保护和粮食安全责任制考核-考核办角色")
    private Long safeAssessRoleId;

    @ApiModelProperty(value = "耕地保护和粮食安全责任制考核-抽查人员角色")
    private Long safeAssessCheckRoleId;
}
