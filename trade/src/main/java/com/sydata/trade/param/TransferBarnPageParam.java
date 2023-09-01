package com.sydata.trade.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022/8/18 17:49
 */
@Data
public class TransferBarnPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "倒仓单号")
    private String dcdh;

    @ApiModelProperty(value = "倒出货位编码")
    private String dchwdm;

    @ApiModelProperty(value = "导入货位编码")
    private String drhwdm;

    @ApiModelProperty(value = "倒仓开始日期")
    private LocalDate dcrqkssj;

    @ApiModelProperty(value = "倒仓结束日期")
    private LocalDate dcrqjssj;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}