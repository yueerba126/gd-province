package com.sydata.basis.vo;

import com.sydata.basis.domain.WebcamLabel;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 库区图视频监控设备点位标注VO
 * @date 2022/10/11 18:05
 */
@ApiModel(description = "库区图视频监控设备点位标注VO")
@Data
@ToString
@Accessors(chain = true)
public class WebcamLabelVo extends WebcamLabel implements Serializable {

    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @DataBindDict(sourceField = "#spjklx", sourceFieldCombination = "spjklx")
    @ApiModelProperty(value = "视频监控类型名称")
    private String spjklxName;
}
