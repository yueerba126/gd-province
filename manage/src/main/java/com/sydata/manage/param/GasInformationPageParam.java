package com.sydata.manage.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 气体信息分页参数
 * @date 2022/10/14 19:04
 */
@Data
public class GasInformationPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id-气体浓度检测单号")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "监测开始时间")
    private LocalDate beginJcsj;

    @ApiModelProperty(value = "监测结束时间")
    private LocalDate endJcsj;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
