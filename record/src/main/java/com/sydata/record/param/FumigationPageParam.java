package com.sydata.record.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 备案管理-熏蒸分页参数
 * @date 2022/12/10 9:46
 */
@ApiModel(description = "备案管理-熏蒸分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FumigationPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID（货位代码）廒间代码+2位顺序码")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    @ApiModelProperty(value = "开始填报日期")
    private LocalDate beginTbrq;

    @ApiModelProperty(value = "结束填报日期")
    private LocalDate endTbrq;

    @ApiModelProperty(value = "开始申请熏蒸日期")
    private LocalDate beginSqxzrq;

    @ApiModelProperty(value = "结束申请熏蒸日期")
    private LocalDate endSqxzrq;

    @ApiModelProperty(value = "审核状态：1待审核、2同意、3退回")
    private String shzt;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
