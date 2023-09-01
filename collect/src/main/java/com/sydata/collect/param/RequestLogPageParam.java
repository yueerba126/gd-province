package com.sydata.collect.param;

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
 * @description 数据收集-请求日志分页参数
 * @date 2022/10/11 17:49
 */
@ApiModel(description = "数据收集-请求日志分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RequestLogPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @ApiModelProperty(value = "请求开始时间（开始）")
    private LocalDateTime beginTimeByBegin;

    @ApiModelProperty(value = "请求开始时间（结束）")
    private LocalDateTime beginTimeByEnd;

    @ApiModelProperty(value = "请求状态 1成功 0失败")
    private Boolean state;

    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @ApiModelProperty(value = "数据ID")
    private String dataId;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;
}
