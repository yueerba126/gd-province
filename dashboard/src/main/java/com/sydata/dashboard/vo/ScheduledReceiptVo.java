package com.sydata.dashboard.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xy
 * @describe 入库量VO
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "入库量VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ScheduledReceiptVo implements Serializable {

    @ApiModelProperty(value = "粮食性质名称")
    @DataBindDict(sourceField = "#lsxzdm", sourceFieldCombination = "food_nature", dataValue = "#remark")
    private String lsxzName;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "净重（万吨）")
    private BigDecimal jz;

}
