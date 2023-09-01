package com.sydata.chart.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 登记信息类
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "登记信息类")
public class Register {

    @ApiModelProperty(value = "入门时间")
    private LocalDateTime djsj;

    @ApiModelProperty(value = "入门登记员")
    private String djmgryxm;

    @ApiModelProperty(value = "出门时间")
    private LocalDateTime cmsj;

    @ApiModelProperty(value = "出门登记员")
    private String cmqrmgryxm;
}

