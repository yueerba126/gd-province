package com.sydata.common.file.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @describe 文件存储分页参数
 * @date 2022-07-10 18:41
 */
@ApiModel(description = "文件存储分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FileStoragePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "组织ID")
    private String enterpriseId;

    @ApiModelProperty(value = "状态 1未使用 2使用中 3已弃用")
    private String state;

    @ApiModelProperty(value = "开始上传时间")
    private LocalDateTime beginCreateTime;

    @ApiModelProperty(value = "结束上传时间")
    private LocalDateTime endCreateTime;
}
