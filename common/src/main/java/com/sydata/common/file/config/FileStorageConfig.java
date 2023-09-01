package com.sydata.common.file.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import java.util.Set;

/**
 * @author lzq
 * @description 文件存储配置
 * @date 2022/10/24 12:16
 */
@Configuration
@ConfigurationProperties(prefix = "file-storage")
@Data
public class FileStorageConfig {

    @ApiModelProperty(value = "文件自动弃用过期时间(分钟)")
    private int overtimeAutoDiscardTime;

    @ApiModelProperty(value = "支持的文件类型")
    private Set<String> fileTypes;

    @ApiModelProperty(value = "最大文件大小")
    private DataSize maxFileSize;
}
