package com.sydata.dashboard.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sydata.framework.core.jackson.format.BigDecimalFormat;
import com.sydata.framework.core.jackson.serialize.BigDecimalSerialize;
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
 * 粮食实时库存监督真实数据VO
 *
 * @author fuql
 * @date 2023-02-10 14:10
 */
@ApiModel(description = "粮食实时库存监督真实数据VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InventoryMonitoringCityVo implements Serializable {

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "省级储备粮")
    private BigDecimal sjcb;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "广州市")
    private BigDecimal gzs;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "韶关市")
    private BigDecimal sgs;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "深圳市")
    private BigDecimal szs;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "珠海市")
    private BigDecimal zhs;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "汕头市")
    private BigDecimal sts;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "佛山市")
    private BigDecimal fss;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "江门市")
    private BigDecimal jms;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "湛江市")
    private BigDecimal zjs;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "茂名市")
    private BigDecimal mms;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "肇庆市")
    private BigDecimal zqs;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "惠州市")
    private BigDecimal hzs;


    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "梅州市")
    private BigDecimal mzs;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "汕尾市")
    private BigDecimal sws;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "河源市")
    private BigDecimal hys;


    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "阳江市")
    private BigDecimal yjs;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "清远市")
    private BigDecimal qys;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "东莞市")
    private BigDecimal dgs;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "中山市")
    private BigDecimal zss;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "潮州市")
    private BigDecimal css;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "揭阳市")
    private BigDecimal jys;

    @BigDecimalFormat(scale = 3)
    @JsonSerialize(using = BigDecimalSerialize.class)
    @ApiModelProperty(value = "云浮市")
    private BigDecimal yfs;
}
