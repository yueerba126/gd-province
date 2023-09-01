package com.sydata.manage.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 安全管理分页参数
 *
 * @author lzq
 * @date 2022/8/18 17:49
 */
@Data
public class SafetyCheckPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "Id主键 库区代码+风险点编码")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "开始整改时限")
    private LocalDate beginZgsx;

    @ApiModelProperty(value = "结束整改时限")
    private LocalDate endZgsx;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}