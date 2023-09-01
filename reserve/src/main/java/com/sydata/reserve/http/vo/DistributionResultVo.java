package com.sydata.reserve.http.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接收省平台下发的储备规模信息返回值
 *
 * @author fuql
 * @date 2023-05-23
 */
@Data
public class DistributionResultVo implements Serializable {

    @Excel(name = "下发返回值")
    @ApiModelProperty(value = "下发返回值")
    private String sendLog;

    @Excel(name = "下发状态")
    @ApiModelProperty(value = "下发状态")
    private String sendStatus;
}
