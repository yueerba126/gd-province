package com.sydata.dashboard.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xy
 * @describe 监测点信息VO
 * @date 2023-02-10 14:10
 */

@ApiModel(description = "监测点信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MonitoringPointVo implements Serializable {


    @ApiModelProperty(value = "采集点")
    private String cjd;

    @ApiModelProperty(value = "温度(℃)")
    private String wd;

    @ApiModelProperty(value = "湿度(RH%)")
    private String sd;






}
