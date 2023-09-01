package com.sydata.dostrict.reserve.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author lzq
 * @date 2022-04-26
 * @description：粮食储备-查询参数
 * @version: 1.0
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "粮食储备-查询参数")
public class GrainSelectParam implements Serializable {

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误")
    @ApiModelProperty("单位代码")
    private String dwdm;

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误")
    @ApiModelProperty("库区代码")
    private String kqdm;

    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误")
    @ApiModelProperty("行政区划代码")
    private String xzqhdm;
}
