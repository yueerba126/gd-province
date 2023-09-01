package com.sydata.basis.vo;

import com.sydata.basis.domain.StockHouse;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
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
 * @describe 基础信息-库区信息VO
 * @date 2022-07-09 16:13
 */
@ApiModel(description = "基础信息-库区信息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockHouseVo extends StockHouse implements Serializable {

    @DataBindCompany
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindDict(sourceField = "#kqcq", sourceFieldCombination = "kqcq")
    @ApiModelProperty(value = "库区产权名称")
    private String kqcqName;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty("行政区划代码名称")
    private String xzqhdmName;
}
