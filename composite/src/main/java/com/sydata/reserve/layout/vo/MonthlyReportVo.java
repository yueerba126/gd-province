package com.sydata.reserve.layout.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @describe 储备布局地理信息-质检人员工作量统计VO
 * @date 2022-07-09 16:29
 */
@ApiModel(description = "储备布局地理信息-质检人员工作量统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MonthlyReportVo implements Serializable {

    @ApiModelProperty("完成报告数（份）")
    private Integer wcbgs;

    @ApiModelProperty("完成入库报告数（份）")
    private Integer wcrkbgs;

    @ApiModelProperty("完成出库报告数（份）")
    private Integer wcckbgs;

    @ApiModelProperty("3月末普检（份）")
    private Integer sywpj;

    @ApiModelProperty("9月末普检（份）")
    private Integer jywpj;

    @ApiModelProperty("入仓初检（份）")
    private Integer rccj;

    @ApiModelProperty("月度检查（份）")
    private Integer ydjc;

    @ApiModelProperty("3月末库内普查（份）")
    private Integer symknjc;

    @ApiModelProperty("9月末库内普查（份）")
    private Integer jymknjc;

    @ApiModelProperty("检验单位")
    private String jydw;

    @ApiModelProperty(value = "年月")
    private String ny;
}
