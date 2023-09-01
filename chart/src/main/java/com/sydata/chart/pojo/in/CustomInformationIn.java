package com.sydata.chart.pojo.in;

import com.sydata.chart.pojo.CustomInformation;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 入库质检检斤卡片-客户及结算
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "入库质检检斤卡片-客户及结算")
public class CustomInformationIn extends CustomInformation {

    @ApiModelProperty(value = "合同号")
    private String hth;

    @ApiModelProperty("银行账号账号")
    private String khfzh;

    @ApiModelProperty("开户行")
    private String khfkhh;

    @ApiModelProperty("结算单价")
    private BigDecimal dj;

    @ApiModelProperty("结算总金额")
    private BigDecimal zje;

    @DataBindDict(sourceFieldCombination = "khhh", sourceField = "#khfkhh")
    @ApiModelProperty(value = "银行行别代码名称")
    private String khfkhhName;

}
