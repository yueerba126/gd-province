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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 药剂信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "药剂信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MedicineReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty("药剂编号")
    private String yjbh;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("采购日期")
    private LocalDate cgrq;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("库区名称")
    private String kqmc;

    @ApiModelProperty("药剂名称")
    private String yjmc;

    @ApiModelProperty("包装物")
    private String bzw;

    @ApiModelProperty("型号规格")
    private String ggxh;

    @ApiModelProperty("安全使用说明书")
    private String aqsysms;

    @ApiModelProperty("生产厂家")
    private String sccj;

    @ApiModelProperty("采购来源")
    private String cgly;

    @ApiModelProperty("储存条件")
    private String cctj;

    @ApiModelProperty("储存地点")
    private String ccdd;

    @ApiModelProperty("包装物处理方式")
    private String bzwclfs;

    @ApiModelProperty("残渣处理方式")
    private String czclfs;

    @ApiModelProperty("保质期")
    private String bzq;

    @ApiModelProperty("库存数量")
    private BigDecimal kcsl;

    @ApiModelProperty("库存数量单位")
    private String kcsldw;
}