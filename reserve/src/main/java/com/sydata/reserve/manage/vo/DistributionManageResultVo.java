package com.sydata.reserve.manage.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接收省平台的储备规模信息返回值
 *
 * @author fuql
 * @date 2023-05-23
 */
@Data
public class DistributionManageResultVo implements Serializable {

    @ApiModelProperty(value = "接收返回值、审核返回值")
    private String receiveLog;

    @ApiModelProperty(value = "接收状态")
    private String receiveStatus;
}
