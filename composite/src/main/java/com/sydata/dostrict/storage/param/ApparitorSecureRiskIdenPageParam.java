package com.sydata.dostrict.storage.param;

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
 * @date 2022-04-26
 * @description 安全仓储-风险智能识别-分页参数
 * @version: 1.0
 */
@ApiModel(description = "安全仓储-风险智能识别-分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorSecureRiskIdenPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "风险类型")
    private String riskType;

    @ApiModelProperty(value = "开始预警日期")
    private LocalDate beginYjrq;

    @ApiModelProperty(value = "结束预警日期")
    private LocalDate endYjrq;
}
