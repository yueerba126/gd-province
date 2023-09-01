package com.sydata.chart.vo;

import com.sydata.chart.pojo.steam.PestInfo;
import com.sydata.chart.pojo.steam.WarehouseInfo;
import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 熏蒸作业卡片
 * </p>
 *
 * @author lzq
 * @since 2022-05-08
 */
@ApiModel(description = "熏蒸作业卡片Vo")
@Data
@ToString
@Accessors(chain = true)
public class SteamTaskChartVo implements Serializable {


    @ApiModelProperty(value = "企业代码")
    private String enterpriseId;

    @DataBindCompany(sourceField = "#enterpriseId")
    @ApiModelProperty(value = "熏蒸作业单位")
    private String xzzydw;

    @ApiModelProperty(value = "熏蒸开始时间")
    private LocalDate xzkssj;

    @ApiModelProperty(value = "熏蒸结束时间")
    private LocalDate xzjssj;

    @ApiModelProperty(value = "粮仓基本情况")
    private WarehouseInfo lcjbqk;

    @ApiModelProperty(value = "虫害发生情况")
    private PestInfo chfsqk;

    @ApiModelProperty(value = "常规蒸熏方式")
    private String cgxzfs;

    @ApiModelProperty(value = "常规熏蒸方式名称")
    @DataBindDict(sourceField = "#cgxzfs", sourceFieldCombination = "cgxzfs")
    private String cgxzfsName;

    @ApiModelProperty(value = "环流蒸熏方式")
    private String hlxzfs;

    @ApiModelProperty(value = "环流熏蒸方式名称")
    @DataBindDict(sourceField = "#hlxzfs", sourceFieldCombination = "hlxzfs")
    private String hlxzfsName;

    @ApiModelProperty(value = "环流熏蒸与内环流技术结合名称")
    @DataBindDict(sourceField = "#hlxzynhljsjh", sourceFieldCombination = "hlxzynhljsjh")
    private String hlxzynhljsjhName;

    @ApiModelProperty(value = "环流蒸熏与内环流技术结合")
    private String hlxzynhljsjh;

    @ApiModelProperty(value = "蒸熏方案制定")
    private String xzfazd;

    @ApiModelProperty(value = "蒸熏方案起草人")
    private String xzfaqcr;

    @ApiModelProperty(value = "蒸熏方案批准人")
    private String xzfapzr;

    @ApiModelProperty(value = "蒸熏方案报备情况")
    private String xzfabbqk;

    @ApiModelProperty(value = "施药人员资质情况")
    private String syryzzqk;

    @ApiModelProperty(value = "施药资质审核")
    private String syzzsh;

    @ApiModelProperty(value = "药剂名称")
    private String yjmc;

    @ApiModelProperty(value = "剂型")
    private String jx;

    @ApiModelProperty(value = "浓度")
    private BigDecimal nd;

    @ApiModelProperty(value = "粮堆单位用药量")
    private BigDecimal lddwyyl;

    @ApiModelProperty(value = "空间单位用药量")
    private BigDecimal kjdwyyl;

    @ApiModelProperty(value = "总用药量")
    private BigDecimal zyyl;

    @ApiModelProperty(value = "施药方法")
    private String syff;

    @ApiModelProperty(value = "施药方法名称")
    @DataBindDict(sourceField = "#syff", sourceFieldCombination = "syff")
    private String syffName;

    @ApiModelProperty(value = "辅助施药措施")
    private String fzsycs;

    @ApiModelProperty(value = "空气呼吸器及安全检查情况")
    private String kqhqjaqjcqk;

    @ApiModelProperty(value = "磷化氢监测装置调试情况")
    private String lhqjczztsqk;

    @ApiModelProperty(value = "氧气深度监测装置调试情况")
    private String yqsdjczztsqk;

    @ApiModelProperty(value = "磷化氢报警仪安全检查")
    private String lhqbjyaqjc;

    @ApiModelProperty(value = "氧气报警仪安全检查")
    private String yqbjyaqjc;

    @ApiModelProperty(value = "补药前仓内磷化氢浓度")
    private BigDecimal byqcnlhqnd;

    @ApiModelProperty(value = "目标浓度")
    private BigDecimal mbnd;

    @ApiModelProperty(value = "计算补药量")
    private BigDecimal jsbyl;

    @ApiModelProperty(value = "实际补药量")
    private BigDecimal sjbyl;

    @ApiModelProperty(value = "补药方法")
    private String byff;

    @ApiModelProperty(value = "作业人数")
    private Integer zyrs;

    @ApiModelProperty(value = "补药作业批准人")
    private String byzypzr;

    @ApiModelProperty(value = "现场指挥人")
    private String xczhr;

    @ApiModelProperty(value = "峰值浓度")
    private BigDecimal fznd;

    @ApiModelProperty(value = "目标浓度维持天数")
    private Integer mbndwcts;

    @ApiModelProperty(value = "漏气未知监测")
    private String lswzjc;

    @ApiModelProperty(value = "漏气部位采取的补救措施")
    private String lqbwcqdbjcs;

    @ApiModelProperty(value = "密闭时间")
    private Integer mbsj;

    @ApiModelProperty(value = "ct值")
    private BigDecimal ctz;

    @ApiModelProperty(value = "散气前磷化氢浓度")
    private BigDecimal sqqlhqnd;

    @ApiModelProperty(value = "散气日期")
    private LocalDate sqrq;

    @ApiModelProperty(value = "散气方法")
    private String sqff;

    @ApiModelProperty(value = "散气批准人")
    private String sqpzr;

    @ApiModelProperty(value = "散气持续天数")
    private Integer sqcsts;

    @ApiModelProperty(value = "散气结束时磷化氢浓度")
    private BigDecimal sqjsslhqnd;

    @ApiModelProperty(value = "残渣收集作业时间")
    private LocalDate czsjzysj;

    @ApiModelProperty(value = "残渣收集作业人数")
    private Integer czsjzyrs;

    @ApiModelProperty(value = "残渣收集方法")
    private String czsjff;

    @ApiModelProperty(value = "残渣收集作业批准人")
    private String czsjzypzr;

    @ApiModelProperty(value = "残渣处理措施")
    private String czclcs;

    @ApiModelProperty(value = "残渣处理作业人数")
    private Integer czclzyrs;

    @ApiModelProperty(value = "残渣处理批准人")
    private String czclpzr;

    @ApiModelProperty(value = "熏蒸后活虫检出情况")
    private String xzhhcjcqk;

    @ApiModelProperty(value = "熏蒸后活虫密度")
    private Integer xzhckmd;

    @ApiModelProperty(value = "培养15天后活虫数")
    private Integer pyswthhcs;

    @ApiModelProperty(value = "培养45天后活虫数")
    private Integer pysswthhcs;

    @ApiModelProperty(value = "熏蒸效果评价")
    private String xzxgpj;

    @ApiModelProperty(value = "熏蒸效果评价名称")
    @DataBindDict(sourceField = "#xzxgpj", sourceFieldCombination = "xzxgpj")
    private String xzxgpjName;

    @ApiModelProperty(value = "熏蒸负责人")
    private String xzfzr;

    @ApiModelProperty(value = "熏蒸作业人员")
    private String xzzyry;

}
