package com.sydata.reserve.layout.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.composite.annotation.DataBindReserveCompany;
import com.sydata.common.composite.annotation.DataBindStockHouseRecord;
import com.sydata.reserve.layout.domain.TankRecord;
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
 * @describe 储备布局地理信息-油罐信息备案VO
 * @date 2022-07-09 16:29
 */
@ApiModel(description = "储备布局地理信息-油罐信息备案VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TankRecordVo extends TankRecord implements Serializable {

    @DataBindReserveCompany(sourceField = "#dwdm")
    @ApiModelProperty("单位代码名称")
    private String dwdmName;

    @DataBindStockHouseRecord(sourceField = "#kqdm")
    @ApiModelProperty("库区代码名称")
    private String kqdmName;

    @DataBindDict(sourceField = "#ygjfssssfwh", sourceFieldCombination = "ygjfssssfwh")
    @ApiModelProperty(value = "油罐及附属设施是否完好名称")
    private String ygjfssssfwhName;

    @DataBindDict(sourceField = "#ywjrzz", sourceFieldCombination = "ywjrzz")
    @ApiModelProperty(value = "有无加热装置名称")
    private String ywjrzzName;

    @DataBindDict(sourceField = "#yglx", sourceFieldCombination = "yglx")
    @ApiModelProperty(value = "油罐类型名称")
    private String yglxName;

    @DataBindDict(sourceField = "#jdfs", sourceFieldCombination = "jdfs")
    @ApiModelProperty(value = "检定方式名称")
    private String jdfsName;

    @DataBindDict(sourceField = "#hjfs", sourceFieldCombination = "hjfs")
    @ApiModelProperty(value = "焊接方式名称")
    private String hjfsName;

    @DataBindDict(sourceField = "#ygzt", sourceFieldCombination = "ygzt")
    @ApiModelProperty(value = "油罐状态名称")
    private String ygztName;


}
