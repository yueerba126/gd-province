package com.sydata.chart.vo;


import com.sydata.chart.pojo.ventilation.BasisVentilation;
import com.sydata.chart.pojo.ventilation.CoolingVentilation;
import com.sydata.chart.pojo.ventilation.ReduceMoistureVentilation;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 通风作业卡片
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@ApiModel(description = "通风作业卡片Vo")
@Data
@ToString
@Accessors(chain = true)
public class VentilationChartVo implements Serializable {

    @ApiModelProperty(value = "企业代码")
    private String enterpriseId;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "通风作业单位")
    private String tfzydw;

    @ApiModelProperty(value = "通风时间")
    private LocalDate tfrq;

    @ApiModelProperty(value = "通风作业编码")
    private String tfzydh;

    @ApiModelProperty(value = "通风目的")
    private String tfmd;

    @ApiModelProperty(value = "通风目的名称")
    @DataBindDict(sourceField = "#tfmd", sourceFieldCombination = "tfmd")
    private String tfmdName;

    @ApiModelProperty(value = "基础配置")
    private BasisVentilation jcpz;

    @ApiModelProperty(value = "通风作业-降温通风")
    private CoolingVentilation tfzyjwtf;

    @ApiModelProperty(value = "通风作业-降水通风")
    private ReduceMoistureVentilation tfzyjstf;

    @ApiModelProperty(value = "通风作业人员")
    private String tfzyry;

    @ApiModelProperty(value = "通风作业负责人")
    private String tfzyfzr;
}
