package com.sydata.dostrict.plan.vo;

import com.sydata.basis.domain.StockHouse;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program: gd-province-platform
 * @description: 规划建设-库区分布
 * @author: lzq
 * @create: 2023-04-24 16:54
 */
@ApiModel(description = "规划建设-库区分布信息VO")
@Data
@ToString
@Accessors(chain = true)
public class StockHouseDistributionVo extends StockHouse implements Serializable {

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
