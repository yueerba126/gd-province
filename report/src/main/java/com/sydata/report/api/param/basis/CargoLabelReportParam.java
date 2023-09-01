package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 库区图仓房点位标注信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "库区图仓房点位标注信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CargoLabelReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "货位相对位置")
    private String hwxdwz;

    @ApiModelProperty(value = "货位位置样式")
    private String hwwzys;

    @ApiModelProperty(value = "备注")
    private String bz;
}