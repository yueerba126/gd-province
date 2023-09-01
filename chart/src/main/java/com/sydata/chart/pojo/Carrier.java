package com.sydata.chart.pojo;

import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 承运信息类
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "承运信息类")
public class Carrier {

    @ApiModelProperty(value = "承运人")
    private String cyr;

    @ApiModelProperty(value = "身份证号")
    private String sfzh;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "车船号")
    private String cch;

    @ApiModelProperty(value = "运输工具:1：汽车,2：火车,3：轮船,9：其他")
    private String ysgj;

    @DataBindDict(sourceFieldCombination = "ysgj", sourceField = "#ysgj")
    @ApiModelProperty(value = "运输工具名称")
    private String ysgjName;
}
