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
 * @description 文件信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "文件信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FileReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty("文件名称")
    private String wjmc;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("文件类型")
    private String wjlx;

    @ApiModelProperty("文件流")
    private String wjl;
}