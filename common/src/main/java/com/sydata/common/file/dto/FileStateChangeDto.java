package com.sydata.common.file.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 文件状态变更DTO
 * @date 2022/10/24 11:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStateChangeDto implements Serializable {

    @ApiModelProperty(value = "文件存储ID")
    private String fileStorageId;

    @ApiModelProperty(value = "状态 1未使用 2使用中 3已弃用")
    private String state;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operationTime;
}
