package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.trade.domain.TransferBarn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 倒仓信息数据
 *
 * @Author chenzx
 */
@Data
public class TransferBarnVo extends TransferBarn implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "粮食品种名称")
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    private String lspzName;

    @ApiModelProperty(value = "倒出货位名称")
    @DataBindCargo(sourceField = "#dchwdm")
    private String dchwdmName;

    @ApiModelProperty(value = "倒入货位名称")
    @DataBindCargo(sourceField = "#drhwdm")
    private String drhwdmName;

    @ApiModelProperty(value = "倒出库区名称")
    @DataBindStockHouse(sourceField = "#dcdw")
    private String dcdwName;

    @ApiModelProperty(value = "倒入库区名称")
    @DataBindStockHouse(sourceField = "#drdw")
    private String drdwName;

    @ApiModelProperty(value = "倒仓类型名称")
    @DataBindDict(sourceField = "#dclx", sourceFieldCombination = "dclx")
    private String dclxName;

    @ApiModelProperty(value = "包装物名称")
    @DataBindDict(sourceField = "#bzw", sourceFieldCombination = "bzw")
    private String bzwName;

}
