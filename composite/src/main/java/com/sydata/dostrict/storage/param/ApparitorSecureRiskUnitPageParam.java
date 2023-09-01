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
 * @description 安全仓储-安全风险台账-分页参数
 * @version: 1.0
 */
@ApiModel(description = "安全仓储-安全风险台账-分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApparitorSecureRiskUnitPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "开始检查时间")
    private LocalDateTime beginJcsj;

    @ApiModelProperty(value = "结束检查时间")
    private LocalDateTime endJcsj;

    @ApiModelProperty(value = "操作标志")
    private String czbz;
}
