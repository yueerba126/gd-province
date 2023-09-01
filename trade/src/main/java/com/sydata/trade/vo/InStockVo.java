package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.trade.domain.InStock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author chenzx
 * @Date 2022/8/19 11:28
 * @Description: 粮食入库列表返回参
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "粮食入库列表返回参数")
public class InStockVo extends InStock implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindCargo
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @DataBindDict(sourceFieldCombination = "bzw", sourceField = "#bzw")
    @ApiModelProperty(value = "包装物字典名称")
    private String bzwName;

    @DataBindDict(sourceFieldCombination = "ysgj", sourceField = "#ysgj")
    @ApiModelProperty(value = "运输工具名称")
    private String ysgjName;

    @DataBindDict(sourceFieldCombination = "jjlx", sourceField = "#jjlx")
    @ApiModelProperty(value = "检斤类型名称")
    private String jjlxName;

    @DataBindDict(sourceFieldCombination = "ywlxm", sourceField = "#ywlx")
    @ApiModelProperty(value = "业务类型名称")
    private String ywlxName;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;
}
