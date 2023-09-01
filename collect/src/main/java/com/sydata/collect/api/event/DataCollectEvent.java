package com.sydata.collect.api.event;

import com.sydata.collect.api.param.BaseApiParam;
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
 * @description 数据收集事件
 * @date 2022/11/29 9:56
 */
@ApiModel(description = "数据收集事件")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DataCollectEvent implements Serializable {

    @ApiModelProperty(value = "开始请求时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "数据接口编号")
    private String apiCode;

    @ApiModelProperty(value = "请求数据")
    private BaseApiParam param;

    @ApiModelProperty(value = "数据ID")
    private String dataId;
}
