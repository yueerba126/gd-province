package com.sydata.report.param;

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
 * @describe 数据上报-国家平台指令接收记录列表参数
 * @date 2022/10/31 14:33
 */
@ApiModel(description = "数据上报-国家平台指令接收记录分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommandRecordPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "指令ID")
    private String orderid;

    @ApiModelProperty(value = "指令类型 0：心跳指令 1：数据指令")
    private String type;

    @ApiModelProperty(value = "开始接收时间")
    private LocalDateTime beginReceiveTime;

    @ApiModelProperty(value = "结束接收时间")
    private LocalDateTime endReceiveTime;
}
