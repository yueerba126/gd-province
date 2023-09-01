package com.sydata.manage.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ：lixi
 * @date ：Created in 2022/6/7 16:38
 * @description：熏蒸备案明细查询条件DTO
 * @version: 1.0
 */
@Data
public class SteamTaskInformationPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id-熏蒸作业单号")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "仓房代码")
    private String cfdm;

    @ApiModelProperty(value = "蒸熏作业单号")
    private String xzzydh;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;

}
