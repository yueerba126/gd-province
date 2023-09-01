package com.sydata.basis.param;

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
 * @describe 基础信息-财务报表分页参数
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "基础信息-财务报表分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FinancePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID（货位代码）廒间代码+2位顺序码")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "报表名 01资产负债表 02现金流量表 03利润表")
    private String bbm;

    @ApiModelProperty(value = "指标名称")
    private String zbmc;

    @ApiModelProperty(value = "报表类型：年报、月报、日报")
    private String type;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
