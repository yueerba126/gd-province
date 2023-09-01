package com.sydata.basis.vo;

import com.sydata.basis.domain.Tank;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
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
 * @describe 基础信息-油罐信息VO
 * @date 2022-07-09 16:29
 */
@ApiModel(description = "基础信息-油罐信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TankVo extends Tank implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindStockHouse
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

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
