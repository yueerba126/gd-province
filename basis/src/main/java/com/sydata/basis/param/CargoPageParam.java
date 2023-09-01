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
 * @describe 基础信息-货位分页参数
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "基础信息-货位分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CargoPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID（货位代码）廒间代码+2位顺序码")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "廒间代码")
    private String ajdm;

    @ApiModelProperty("货位代码")
    private String hwdm;

    @ApiModelProperty("货位名称")
    private String hwmc;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
