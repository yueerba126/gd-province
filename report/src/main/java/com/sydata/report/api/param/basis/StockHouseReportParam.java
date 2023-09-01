
package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author lzq
 * @description 库区信息上报参数
 * @date 2022/10/19 11:15
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "库区信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StockHouseReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区名称")
    private String kqmc;

    @ApiModelProperty(value = "库区地址")
    private String kqdz;

    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "库区产权")
    private String kqcq;

    @ApiModelProperty(value = "有效仓容（单位：吨）")
    private BigDecimal yxcr;

    @ApiModelProperty(value = "有效罐容（单位：吨）")
    private BigDecimal yxgr;

    @ApiModelProperty(value = "占地面积（指库区土地面积，单位：平方米）")
    private BigDecimal zdmj;

    @ApiModelProperty(value = "仓房数")
    private Integer cfs;

    @ApiModelProperty(value = "油罐数")
    private Integer ygs;

    @ApiModelProperty(value = "库区经度")
    private BigDecimal jd;

    @ApiModelProperty(value = "库区纬度")
    private BigDecimal wd;
}
