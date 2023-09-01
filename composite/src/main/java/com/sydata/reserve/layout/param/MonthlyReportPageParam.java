package com.sydata.reserve.layout.param;

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
 * @describe 检测业务月报表参数
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "检测业务月报表参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MonthlyReportPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "检验单位")
    private String jydw;

    @ApiModelProperty(value = "开始检验时间时间")
    private String beginDate;

    @ApiModelProperty(value = "结束检验时间时间")
    private String endDate;

    @ApiModelProperty(value = "开始检验时间时间")
    private LocalDate beginQueryDate;

    @ApiModelProperty(value = "结束检验时间时间")
    private LocalDate endQueryDate;
}
