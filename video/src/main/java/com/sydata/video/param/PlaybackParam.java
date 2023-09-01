package com.sydata.video.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 海康视频监控-回放取流参数
 * @date 2022/12/27 14:48
 */
@ApiModel(description = "海康视频监控-回放取流参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PlaybackParam implements Serializable {

    @NotBlank(message = "监控点唯一标识必填")
    @ApiModelProperty(value = "监控点唯一标识")
    private String cameraIndexCode;

    @NotNull(message = "开始时间必填")
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @NotNull(message = "结束时间必填")
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
}
