package com.sydata.manage.param;

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
 * @describe 粮库管理-库区视频监控异常事件告分页参数
 * @date 2022-07-25 14:54
 */
@ApiModel(description = "粮库管理-库区视频监控异常事件告分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExceptionMonitoringEventPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id:库区代码+告警时间")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "视频监控设备ID")
    private String spjksbid;

    @ApiModelProperty(value = "安装位置类型 01粮库大门 02扦样机 03化验室 04结算室 05地磅房 06库区主干道 07仓间监控 08仓内 09药品库 10器材库 11制高点 12周界 99其他")
    private String azwzlx;

    @ApiModelProperty(value = "开始告警时间")
    private LocalDateTime beginGjsj;

    @ApiModelProperty(value = "结束告警时间")
    private LocalDateTime endGjsj;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
