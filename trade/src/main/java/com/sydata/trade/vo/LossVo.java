package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.DataBindCargo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.trade.domain.Loss;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 损益单信息
 *
 * @author lzq
 * @date 2022/8/19 9:37
 */
@Data
public class LossVo extends Loss implements Serializable {

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @DataBindCargo
    @ApiModelProperty(value = "货位名称")
    private String hwmc;

    @DataBindDict(sourceFieldCombination = "sysfzc", sourceField = "#sysfzc")
    @ApiModelProperty(value = "损益是否正常名称")
    private String sysfzcName;
}