package com.sydata.report.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @describe 国家平台响应参数
 * @date 2022/10/31 14:33
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ReportResultVo implements Serializable {

    @ApiModelProperty(value = "响应码 200正常")
    private String code;

    @ApiModelProperty(value = "响应结果")
    private String result;

    @ApiModelProperty(value = "上报开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "上报结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "上报总耗时(毫秒)")
    private Long timeConsuming;

    @ApiModelProperty(value = "上报异常信息")
    private String invokerMsg;

    @ApiModelProperty(value = "调用批次ID")
    private String invokerBatchId;

    public ReportResultVo(String code, String result) {
        this.code = code;
        this.result = result;
    }
}
