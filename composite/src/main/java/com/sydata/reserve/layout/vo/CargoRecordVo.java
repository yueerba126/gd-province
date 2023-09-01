package com.sydata.reserve.layout.vo;

import com.sydata.basis.domain.Cargo;
import com.sydata.common.composite.annotation.DataBindReserveCompany;
import com.sydata.common.composite.annotation.DataBindRranaryRecord;
import com.sydata.common.composite.annotation.DataBindStockHouseRecord;
import com.sydata.reserve.layout.domain.CargoRecord;
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
 * @describe 储备布局地理信息-货位信息备案VO
 * @date 2022-07-09 16:29
 */
@ApiModel(description = "储备布局地理信息-货位信息备案VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CargoRecordVo extends CargoRecord implements Serializable {


    @DataBindRranaryRecord(sourceField = "#ajdm")
    @ApiModelProperty(value = "廒间名称")
    private String ajdmName;

    @DataBindReserveCompany(sourceField = "#dwdm")
    @ApiModelProperty("单位代码名称")
    private String dwdmName;

    @DataBindStockHouseRecord(sourceField = "#kqdm")
    @ApiModelProperty("库区代码名称")
    private String kqdmName;

    @DataBindStockHouseRecord(sourceField = "#cfdm")
    @ApiModelProperty("仓房代码名称")
    private String cfdmName;
}
