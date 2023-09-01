package com.sydata.trade.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.trade.domain.Contract;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 合同信息Vo
 *
 * @author lzq
 * @date 2022/8/19 11:42
 */
@Data
public class ContractVo extends Contract implements Serializable {

    @ApiModelProperty(value = "单位名称")
    @DataBindCompany
    private String dwmc;

    @ApiModelProperty(value = "业务类型名称")
    @DataBindDict(sourceField = "#ywlx", sourceFieldCombination = "ywlx")
    private String ywlxName;

    @ApiModelProperty(value = "客户类型名称")
    @DataBindDict(sourceField = "#khlx", sourceFieldCombination = "khlx")
    private String khlxName;

    @ApiModelProperty(value = "客户方开户行名称")
    @DataBindDict(sourceField = "#khfkhh", sourceFieldCombination = "bank_type")
    private String khfkhhName;

    @ApiModelProperty(value = "本方开户行名称")
    @DataBindDict(sourceField = "#bfkhh", sourceFieldCombination = "bank_type")
    private String bfkhhName;

    @ApiModelProperty(value = "粮食品种名称")
    @DataBindDict(sourceField = "#lspzdm", sourceFieldCombination = "food_variety")
    private String lspzName;

    @ApiModelProperty(value = "粮食性质名称")
    @DataBindDict(sourceField = "#lsxzdm", sourceFieldCombination = "food_nature")
    private String lsxzName;

    @ApiModelProperty(value = "结算与合同一致性名称")
    @DataBindDict(sourceField = "#jsyhtyzx", sourceFieldCombination = "jsyhtyzx")
    private String jsyhtyzxName;

}