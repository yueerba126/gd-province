package com.sydata.reserve.layout.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.composite.annotation.DataBindReserveCompany;
import com.sydata.common.composite.annotation.DataBindStockHouseRecord;
import com.sydata.reserve.layout.domain.RranaryRecord;
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
 * @describe 储备布局地理信息-廒间信息备案VO
 * @date 2022-07-09 16:38
 */
@ApiModel(description = "储备布局地理信息-廒间信息备案VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RranaryRecordVo extends RranaryRecord implements Serializable {


    @DataBindReserveCompany(sourceField = "#dwdm")
    @ApiModelProperty("单位代码名称")
    private String dwdmName;

    @DataBindStockHouseRecord(sourceField = "#kqdm")
    @ApiModelProperty("库区代码名称")
    private String kqdmName;

    @DataBindStockHouseRecord(sourceField = "#cfhygdm")
    @ApiModelProperty("仓房(或油罐)代码名称")
    private String cfhygdmName;

    @DataBindDict(sourceField = "#ajzt", sourceFieldCombination = "ajzt")
    @ApiModelProperty(value = "廒间状态名称")
    private String ajztName;

}
