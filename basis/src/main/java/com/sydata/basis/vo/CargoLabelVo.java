package com.sydata.basis.vo;

import com.sydata.basis.domain.CargoLabel;
import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 库区图仓房点位标注VO
 * @date 2022/10/11 17:56
 */
@ApiModel(description = "库区图仓房点位标注VO")
@Data
@ToString
@Accessors(chain = true)
public class CargoLabelVo extends CargoLabel implements Serializable {

    @DataBindCompany(sourceField = "#dwdm")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindStockHouse(sourceField = "#kqdm")
    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @DataBindCargo(sourceField = "#hwdm")
    @ApiModelProperty(value = "货位名称")
    private String hwmc;
}
