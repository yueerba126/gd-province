package com.sydata.chart.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.basis.annotation.DataBindWarehouse;
import com.sydata.trade.domain.TransferNature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 性质转变单卡片
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@ApiModel(description = "性质转变单卡片Vo")
@Data
@ToString
@Accessors(chain = true)
public class TransferNatureChartVo extends TransferNature implements Serializable {

    @ApiModelProperty("仓房代码")
    private String cfdm;

    @DataBindWarehouse
    @ApiModelProperty("仓房名称")
    private String cfmc;

    @ApiModelProperty(value = "货位名称")
    @DataBindCargo
    private String hwdmName;

    @ApiModelProperty(value = "粮食品种名称")
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    private String lspzdmName;

    @ApiModelProperty(value = "划转前粮食性质名称")
    @DataBindDict(sourceField = "#hzqlsxzdm", sourceFieldCombination = "food_nature")
    private String hzqlsxzName;

    @ApiModelProperty(value = "划转后粮食性质名称")
    @DataBindDict(sourceField = "#hzhlsxzdm", sourceFieldCombination = "food_nature")
    private String hzhlsxzName;

}
