package com.sydata.reserve.layout.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 储备布局地理信息-仓房信息备案
 *
 * @author lzq
 * @date 2022-07-08
 */
@ApiModel(description = "储备布局地理信息-仓房信息备案")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("reserve_layout_warehouse")
public class WarehouseRecord extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键ID（仓房代码）库区代码+4位顺序码")
    @TableId(value = "id", type = INPUT)
    private String id;

    @ApiModelProperty("仓房代码")
    private String cfdm;

    @ApiModelProperty("仓房名称")
    private String cfmc;

    @ApiModelProperty("库区代码")
    private String kqdm;

    @ApiModelProperty("仓房类型代码")
    private String cflxdm;

    @ApiModelProperty("交付使用日期")
    private LocalDate jfsyrq;

    @ApiModelProperty("建设单位")
    private String jsdw;

    @ApiModelProperty("设计使用年限")
    private String sjsynx;

    @ApiModelProperty("设计单位")
    private String sjdw;

    @ApiModelProperty("监理单位")
    private String jldw;

    @ApiModelProperty("墙体结构")
    private String qtjg;

    @ApiModelProperty("房顶结构")
    private String fdjg;

    @ApiModelProperty("房架结构")
    private String fjjg;

    @ApiModelProperty("地面结构")
    private String dmjg;

    @ApiModelProperty("设计仓容")
    private BigDecimal sjcr;

    @ApiModelProperty("仓外长")
    private BigDecimal cwc;

    @ApiModelProperty("仓外宽")
    private BigDecimal cwk;

    @ApiModelProperty("仓外檐高")
    private BigDecimal cwyg;

    @ApiModelProperty("仓外顶高")
    private BigDecimal cwdg;

    @ApiModelProperty("筒仓外径")
    private BigDecimal tcwj;

    @ApiModelProperty("仓内长")
    private BigDecimal cnc;

    @ApiModelProperty("仓内宽")
    private BigDecimal cnk;

    @ApiModelProperty("仓内檐高")
    private BigDecimal cnyg;

    @ApiModelProperty("仓内装粮线高")
    private BigDecimal cnzlxg;

    @ApiModelProperty("筒仓内径")
    private BigDecimal tcnj;

    @ApiModelProperty("仓内体积")
    private BigDecimal cntj;

    @ApiModelProperty("仓门数量")
    private Integer cmsl;

    @ApiModelProperty("仓门位置")
    private String cmwz;

    @ApiModelProperty("仓门高度")
    private BigDecimal cmgd;

    @ApiModelProperty("仓门宽度")
    private BigDecimal cmkd;

    @ApiModelProperty("挡粮门型式")
    private String dlmxs;

    @ApiModelProperty("仓房是否完好")
    private String cfsfwh;

    @ApiModelProperty("储粮功效")
    private String clgx;

    @ApiModelProperty("能否隔热保温")
    private String nfgrbw;

    @ApiModelProperty("隔热保温措施")
    private String grbwcs;

    @ApiModelProperty("隔热性能")
    private String grxn;

    @ApiModelProperty("结构材料")
    private String jgcl;

    @ApiModelProperty("气密性")
    private String qmx;

    @ApiModelProperty("是否已进行信息化改造")
    private String sfyjxxxhgz;

    @ApiModelProperty("粮情技术")
    private String lqjs;

    @ApiModelProperty("能否散装储存")
    private String nfszcc;

    @ApiModelProperty("有无防鼠防雀防虫装置及设施")
    private String ywfsfqfczz;

    @ApiModelProperty("有无防火防爆防盗设施")
    private String ywfhfbfdss;

    @ApiModelProperty("有无机械通风设施")
    private String ywjxtfss;

    @ApiModelProperty("通风系统型式")
    private String tfxtxs;

    @ApiModelProperty("通风技术")
    private String tfjs;

    @ApiModelProperty("能否环流熏蒸杀虫")
    private String nfhlxzsc;

    @ApiModelProperty("杀虫技术")
    private String scjs;

    @ApiModelProperty("能否富氮低氧气调储粮")
    private String nffddyqtcl;

    @ApiModelProperty("控温技术")
    private String kwjs;

    @ApiModelProperty("害虫检测方式")
    private String hcjcfs;

    @ApiModelProperty("仓房状态")
    private String cfzt;

    @ApiModelProperty("保管员")
    private String bgr;

    @ApiModelProperty("仓房经度")
    private BigDecimal jd;

    @ApiModelProperty("仓房纬度")
    private BigDecimal wd;

    @ApiModelProperty("状态 0：保存，1：提交，2：审核")
    private String status;

    @ApiModelProperty("单位代码")
    private String dwdm;
}