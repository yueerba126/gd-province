package com.sydata.report.api.param.manage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 虫害信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "虫害信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PestInformationReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "害虫监测单号")
    private String hcjcdh;

    @ApiModelProperty(value = "检测时间")
    private LocalDateTime jcsj;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "检查虫害方法")
    private String jchcff;

    @ApiModelProperty(value = "发生部位")
    private String fsbw;

    @ApiModelProperty(value = "虫害种类")
    private String hczl;

    @ApiModelProperty(value = "虫口密度值集合")
    private String ckmdzjh;

    @ApiModelProperty(value = "虫粮等级判定")
    private String cldjpd;

    @ApiModelProperty(value = "害虫抗药性分析")
    private String hckyxfx;
}
