package com.sydata.dostrict.reserve.param;

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
 * @date 2022-04-26
 * @description 粮食储备-储备规模-分页参数
 * @version: 1.0
 */
@ApiModel(description = "粮食储备-储备规模-分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorReserveScalePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "id(年份-承储企业-粮食品种-粮食性质)")
    private String id;

    @ApiModelProperty(value = "关联储备计划id")
    private String cbjhId;

    @ApiModelProperty(value = "储备规模计划文号")
    private String cbgmjhwh;

    @ApiModelProperty(value = "承储企业")
    private String ccqy;

    @ApiModelProperty(value = "粮食品种代码")
    private String ylpz;

    @ApiModelProperty(value = "粮食性质代码")
    private String ylxz;

    @ApiModelProperty(value = "年份如2022")
    private String nf;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
