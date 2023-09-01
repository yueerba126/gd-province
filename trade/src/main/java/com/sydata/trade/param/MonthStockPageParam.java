package com.sydata.trade.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @describe 粮油购销-账面库存分页参数
 * @date 2022-07-22 18:20
 */
@ApiModel(description = "粮油购销-账面库存分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MonthStockPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id（由货位id+粮食品种代码+年度+月份组成）")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "年度")
    private String nd;

    @ApiModelProperty(value = "月份")
    private String yf;

    @ApiModelProperty(value = "月结标志 0月结 1未月结")
    private String yjbz;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
