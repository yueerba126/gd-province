package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.trade.domain.OutStock;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author chenzx
 * @Date 2022/8/19 15:58
 * @Description:
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutStockVo extends OutStock implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindCargo
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @DataBindDict(sourceFieldCombination = "bzw", sourceField = "#bzw")
    @ApiModelProperty(value = "包装物字典名称")
    private String bzwName;

    @DataBindDict(sourceFieldCombination = "ywlxm", sourceField = "#ywlx")
    @ApiModelProperty(value = "业务类型名称")
    private String ywlxName;

    @DataBindDict(sourceFieldCombination = "ysgj", sourceField = "#ysgj")
    @ApiModelProperty(value = "运输工具名称")
    private String ysgjName;

    @DataBindDict(sourceFieldCombination = "food_variety", sourceField = "#lspzdm")
    @ApiModelProperty(value = "粮食品种名称")
    private String lspzdmName;

    @DataBindDict(sourceFieldCombination = "food_grade", sourceField = "#lsdjdm")
    @ApiModelProperty(value = "粮食等级名称")
    private String lsdjdmName;

    @DataBindDict(sourceFieldCombination = "food_nature", sourceField = "#lsxzdm")
    @ApiModelProperty(value = "粮食性质名称")
    private String lsxzdmName;


}
