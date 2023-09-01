package com.sydata.reserve.layout.vo;

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
 * @describe 储备布局地理信息-质检人员工作量统计VO
 * @date 2022-07-09 16:29
 */
@ApiModel(description = "储备布局地理信息-质检人员工作量统计VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PersonnelWorkloadVo implements Serializable {

    @ApiModelProperty("完成报告数（份）")
    private Integer wcbgs;

    @ApiModelProperty("检验单位")
    private String jydw;

    @ApiModelProperty(value = "检验时间")
    private LocalDateTime jysj;
}
