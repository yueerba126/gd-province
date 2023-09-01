package com.sydata.report.api.param.trade;

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
 * @description 入库信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "入库信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InStockReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "入库业务单号")
    private String rkywdh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "业务类型,2：入库 (默认)")
    private String ywlx;

    @ApiModelProperty(value = "业务日期,格式：yyyy-MM-dd")
    private LocalDate ywrq;

    @ApiModelProperty(value = "计划明细号")
    private String jhmxh;

    @ApiModelProperty(value = "合同号")
    private String hth;

    @ApiModelProperty(value = "承运人")
    private String cyr;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "身份证号")
    private String sfzh;

    @ApiModelProperty(value = "详细地址")
    private String xxdz;

    @ApiModelProperty(value = "运输工具:1：汽车,2：火车,3：轮船,9：其他")
    private String ysgj;

    @ApiModelProperty(value = "车船号类型")
    private String cchlx;

    @ApiModelProperty(value = "车船号")
    private String cch;

    @ApiModelProperty(value = "挂车号")
    private String gch;

    @ApiModelProperty(value = "装粮地点")
    private String ldd;

    @ApiModelProperty(value = "登记时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime djsj;

    @ApiModelProperty(value = "登记门岗人员姓名")
    private String djmgryxm;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "收获年度")
    private String shnd;

    @ApiModelProperty(value = "产地代码")
    private String cddm;

    @ApiModelProperty(value = "检斤类型:0：称重入库 1：标准包入库 默认为称重入库，标准包入库相关字段可为空")
    private String jjlx;

    @ApiModelProperty(value = "毛重(公斤)")
    private BigDecimal mz;

    @ApiModelProperty(value = "毛重监磅员")
    private String mzjby;

    @ApiModelProperty(value = "毛重计量时间：格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime mzjlsj;

    @ApiModelProperty(value = "毛重计量员")
    private String mzjly;

    @ApiModelProperty(value = "值仓员")
    private String zcy;

    @ApiModelProperty(value = "皮重(公斤)")
    private BigDecimal pz;

    @ApiModelProperty(value = "皮重监磅员")
    private String pzjby;

    @ApiModelProperty(value = "皮重计量时间：格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime pzjlsj;

    @ApiModelProperty(value = "皮重计量员")
    private String pzjly;

    @ApiModelProperty(value = "包装物，1：麻袋 2：编织袋 3：散装 9：其他")
    private String bzw;

    @ApiModelProperty(value = "标准包单包重(公斤)")
    private BigDecimal bzbdbz;

    @ApiModelProperty(value = "标准包件数:件")
    private Integer bzbjs;

    @ApiModelProperty(value = "质检扣量 (小计):共计")
    private BigDecimal zjklxj;

    @ApiModelProperty(value = "其中：水分增扣量")
    private BigDecimal qzsfzkl;

    @ApiModelProperty(value = "其中：杂质增扣量")
    private BigDecimal qzzzzkl;

    @ApiModelProperty(value = "其中：不完善粒扣量")
    private BigDecimal qzbwslkl;

    @ApiModelProperty(value = "其中：互混扣量")
    private BigDecimal qzhhkl;

    @ApiModelProperty(value = "其中：生霉粒扣量")
    private BigDecimal qzsmlkl;

    @ApiModelProperty(value = "其中：整精米粒扣量")
    private BigDecimal qzzjmlkl;

    @ApiModelProperty(value = "其中：谷外糙米扣量")
    private BigDecimal qzgwcmkl;

    @ApiModelProperty(value = "其中黄粒米扣量")
    private BigDecimal qzhlmkl;

    @ApiModelProperty(value = "其中：其他扣量")
    private BigDecimal qzqtkl;

    @ApiModelProperty(value = "整理费用折扣量")
    private BigDecimal zlfyzkl;

    @ApiModelProperty(value = "包装物扣量")
    private BigDecimal bzwkl;

    @ApiModelProperty(value = "其他扣量")
    private BigDecimal qtkl;

    @ApiModelProperty(value = "扣量原因:其他扣量不为0 时，需注明原因")
    private String klyy;

    @ApiModelProperty(value = "现场扣量")
    private BigDecimal xckl;

    @ApiModelProperty(value = "增扣价")
    private BigDecimal zkj;

    @ApiModelProperty(value = "增扣价原因，价不为 0 时注明原因")
    private String zkhyy;

    @ApiModelProperty(value = "净重")
    private BigDecimal jz;

    @ApiModelProperty(value = "装卸作业单位")
    private String zxzydw;

    @ApiModelProperty(value = "出门时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime cmsj;

    @ApiModelProperty(value = "出门确认门岗 人员姓名")
    private String cmqrmgryxm;

    @ApiModelProperty(value = "入库结算单号")
    private String rkjsdh;

    @ApiModelProperty(value = "备注")
    private String bz;
}
