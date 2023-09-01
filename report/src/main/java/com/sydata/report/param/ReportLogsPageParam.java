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
 * @description
 * @date 2022/11/2 17:03
 */
@ApiModel(description = "数据上报-上报日志分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReportLogsPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @ApiModelProperty(value = "上报开始时间（开始）")
    private LocalDateTime beginTimeByBegin;

    @ApiModelProperty(value = "上报开始时间（结束）")
    private LocalDateTime beginTimeByEnd;

    @ApiModelProperty(value = "上报状态 1成功 0失败")
    private Boolean state;

    @ApiModelProperty(value = "重报投递：1已投递 0未投递")
    private Boolean delivery;

    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @ApiModelProperty(value = "数据ID")
    private String dataId;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;
}
