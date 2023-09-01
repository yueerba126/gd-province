package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.trade.domain.TransferNature;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 性质转变单信息
 *
 * @author lzq
 * @date 2022/8/19 9:48
 */
@Data
public class TransferNatureVo extends TransferNature implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

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