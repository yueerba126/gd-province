package com.sydata.chart.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * <p>
 * 检斤信息类
 * </p>
 *
 * @author lzq
 * @since 2022-10-26
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "检斤信息类")
public class Weighing {
    @ApiModelProperty(value = "毛重(公斤)")
    private BigDecimal mz;

    @ApiModelProperty(value = "毛重监磅员")
    private String mzjby;

    @ApiModelProperty(value = "毛重计量时间：格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime mzjlsj;

    @ApiModelProperty(value = "毛重计量员")
    private String mzjly;

    @ApiModelProperty(value = "皮重(公斤)")
    private BigDecimal pz;

    @ApiModelProperty(value = "皮重监磅员")
    private String pzjby;

    @ApiModelProperty(value = "皮重计量时间：格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime pzjlsj;

    @ApiModelProperty(value = "皮重计量员")
    private String pzjly;

    @ApiModelProperty(value = "净重(公斤)")
    private BigDecimal jz;

}
