package com.sydata.trade.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author chenzx
 * @Date 2022/8/18 18:15
 * @Description:
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockGrainPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "粮权归属单位代码")
    private String lqgsdwdm;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "开始入仓时间")
    private LocalDateTime beginrcsj;

    @ApiModelProperty(value = "结束入仓时间")
    private LocalDateTime endrcsj;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;

}
