package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.trade.domain.InStockCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;
import static jodd.util.StringPool.COMMA;

/**
 * @Author chenzx
 * @Date 2022/8/19 11:50
 * @Description: 入库检验返回参
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "入库检验返回参数")
public class InStockCheckVo extends InStockCheck implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindDict(sourceField = "#jyxm", sourceFieldCombination = "jyxm", valueBindStrategy = SEPARATED, bindSeparated = COMMA)
    @ApiModelProperty(value = "检验项目名称")
    private String jyxmName;

    @DataBindDict(sourceFieldCombination = "jyjg", sourceField = "#jyjg")
    @ApiModelProperty(value = "检验结果名称")
    private String jyjgName;

    @DataBindDict(sourceFieldCombination = "bgyfh", sourceField = "#bgyfh")
    @ApiModelProperty(value = "保管员符合结果名称")
    private String bgyfhName;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "qyfs", sourceField = "#qyfs")
    @ApiModelProperty(value = "扦样方式名称")
    private String qyfsName;

}
