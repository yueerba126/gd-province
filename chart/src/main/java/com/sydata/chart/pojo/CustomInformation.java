package com.sydata.chart.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 客户信息类
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "客户信息类")
public class CustomInformation {

    @ApiModelProperty(value = "客户名称")
    private String khmc;

    @ApiModelProperty(value = "客户统一社会信用代码或身份证号")
    private String khtyshxydm;

    @ApiModelProperty(value = "联系电话")
    private String lxrdh;

    @ApiModelProperty(value = "通讯地址")
    private String txdz;
}
