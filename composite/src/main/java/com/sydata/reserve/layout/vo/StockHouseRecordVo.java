package com.sydata.reserve.layout.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.reserve.layout.domain.StockHouseRecord;
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
 * @describe 储备布局地理信息-库区信息备案VO
 * @date 2022-07-09 16:13
 */
@ApiModel(description = "储备布局地理信息-库区信息备案VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockHouseRecordVo extends StockHouseRecord implements Serializable {


    @DataBindDict(sourceField = "#kqcq", sourceFieldCombination = "kqcq")
    @ApiModelProperty(value = "库区产权名称")
    private String kqcqName;

    @DataBindRegion(sourceField = "#xzqhdm")
    @ApiModelProperty("行政区划代码名称")
    private String xzqhdmName;
}
