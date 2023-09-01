package com.sydata.report.api.param.manage;

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

/**
 * @author lzq
 * @description 通风作业上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "通风作业上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VentilationReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "通风作业单号-货位代码+作业日期（yyyyMMdd）+3位顺序号")
    private String tfzydh;

    @ApiModelProperty(value = "通风日期")
    private LocalDate tfrq;

    @ApiModelProperty(value = "仓房代码")
    private String cfdm;

    @ApiModelProperty(value = "通风目的")
    private String tfmd;

    @ApiModelProperty(value = "粮堆孔隙度")
    private BigDecimal ldkxd;

    @ApiModelProperty(value = "通风类型")
    private String tflx;

    @ApiModelProperty(value = "风道型式")
    private String fdxs;

    @ApiModelProperty(value = "风网设置方式")
    private String fwszfs;

    @ApiModelProperty(value = "主风道截面积")
    private BigDecimal zfdjmj;

    @ApiModelProperty(value = "支风道截面积")
    private BigDecimal zhfdjmj;

    @ApiModelProperty(value = "支风道总长度")
    private BigDecimal zfdzcd;

    @ApiModelProperty(value = "风网开孔率")
    private BigDecimal fwkkl;

    @ApiModelProperty(value = "空气途径比")
    private BigDecimal kqtjb;

    @ApiModelProperty(value = "通风口设置个数")
    private Integer tfkszgs;

    @ApiModelProperty(value = "通风机型号")
    private String tfjxh;

    @ApiModelProperty(value = "通风机台数")
    private Integer tfjts;

    @ApiModelProperty(value = "单台风机额定全压")
    private BigDecimal dtfjedqy;

    @ApiModelProperty(value = "单台风机额定风量")
    private BigDecimal dtfjedfl;

    @ApiModelProperty(value = "单台风机额定功率")
    private BigDecimal dtfjedgl;

    @ApiModelProperty(value = "送风方式")
    private String sffs;

    @ApiModelProperty(value = "单台风机实测风量")
    private BigDecimal dtfjscfl;

    @ApiModelProperty(value = "单台风机轴功率")
    private BigDecimal dtfjzgl;

    @ApiModelProperty(value = "总风量")
    private BigDecimal zfl;

    @ApiModelProperty(value = "单位通风量")
    private BigDecimal dwtfl;

    @ApiModelProperty(value = "实测系统阻力")
    private BigDecimal scxtzl;

    @ApiModelProperty(value = "总耗电量")
    private BigDecimal zhdl;

    @ApiModelProperty(value = "作业时气温")
    private BigDecimal zysqw;

    @ApiModelProperty(value = "作业时气湿")
    private BigDecimal zysqs;

    @ApiModelProperty(value = "通风时长")
    private BigDecimal tfsc;

    @ApiModelProperty(value = "作业前平均粮温")
    private BigDecimal zyqpjlw;

    @ApiModelProperty(value = "结束后平均粮温")
    private BigDecimal jshpjlw;

    @ApiModelProperty(value = "降温幅度")
    private BigDecimal jwfd;

    @ApiModelProperty(value = "吨量降温能耗")
    private BigDecimal dljwnh;

    @ApiModelProperty(value = "失水率")
    private BigDecimal ssll;

    @ApiModelProperty(value = "保水效果评价结果")
    private String bsxgpjjg;

    @ApiModelProperty(value = "通风降温均匀性评价整仓")
    private String tfjwjyxpjzc;

    @ApiModelProperty(value = "通风降温均匀性评价上层")
    private String tfjwjyxpjsc;

    @ApiModelProperty(value = "通风降温均匀性评价中间层")
    private String tfjwjyxpjzjc;

    @ApiModelProperty(value = "通风降温均匀性评价_下层")
    private String tfjwjyxpjxc;

    @ApiModelProperty(value = "作业前平均水分")
    private BigDecimal zyqpjsf;

    @ApiModelProperty(value = "作业后平均水分")
    private BigDecimal zyhpjsf;

    @ApiModelProperty(value = "降水幅度")
    private BigDecimal jsfd;

    @ApiModelProperty(value = "吨粮降水能耗")
    private BigDecimal dljsnh;

    @ApiModelProperty(value = "通风作业人员")
    private String tfzyry;

    @ApiModelProperty(value = "通风降水均匀性分析_整仓")
    private String tfjsjyxfxzc;

    @ApiModelProperty(value = "通风降水均匀性分析_上层")
    private String tfjsjyxfxsc;

    @ApiModelProperty(value = "通风降水均匀性分析_下层")
    private String tfjsjyxfxxc;

    @ApiModelProperty(value = "通风降水性分析_中（间）层")
    private String tfjsjyxfxzjc;

    @ApiModelProperty(value = "通风作业负责人")
    private String tfzyfzr;
}