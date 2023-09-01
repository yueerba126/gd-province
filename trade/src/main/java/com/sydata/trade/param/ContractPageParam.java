package com.sydata.trade.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @date 2022/8/18 17:49
 */
@Data
public class ContractPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty("主键id(单位代码+合同编号)")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "合同名称")
    private String htmc;

    @ApiModelProperty(value = "合同号")
    private String hth;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}