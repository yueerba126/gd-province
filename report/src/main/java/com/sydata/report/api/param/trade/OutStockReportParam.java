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
 * @description 出库信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "出库信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OutStockReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "出库业务单号")
    private String ckywdh;

    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "出库通知单号")
    private String cktzdh;

    @ApiModelProperty(value = "业务类型,1：出库 (默认)")
    private String ywlx;

    @ApiModelProperty(value = "业务日期,格式：yyyy-MM-dd")
    private LocalDate ywrq;

    @ApiModelProperty(value = "合同号")
    private String hth;

    @ApiModelProperty(value = "承运人")
    private String cyr;

    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    @ApiModelProperty(value = "身份证号")
    private String sfzh;

    @ApiModelProperty(value = "运输工具:1：汽车 2：火车 3：轮船 9：其他")
    private String ysgj;

    @ApiModelProperty(value = "卸粮地点")
    private String xldd;

    @ApiModelProperty(value = "车船号类型")
    private String cchlx;

    @ApiModelProperty(value = "车船号")
    private String cch;

    @ApiModelProperty(value = "挂车号")
    private String gch;

    @ApiModelProperty(value = "登记时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime djsj;

    @ApiModelProperty(value = "登记门岗人员姓名")
    private String djmgryxm;

    @ApiModelProperty(value = "粮食品种代码")
    private String lspzdm;

    @ApiModelProperty(value = "粮食等级代码")
    private String lsdjdm;

    @ApiModelProperty(value = "粮食性质代码")
    private String lsxzdm;

    @ApiModelProperty(value = "收获年度")
    private String shnd;

    @ApiModelProperty(value = "产地代码")
    private String cddm;

    @ApiModelProperty(value = "皮重（公斤）")
    private BigDecimal pz;

    @ApiModelProperty(value = "皮重监磅员")
    private String pzjby;

    @ApiModelProperty(value = "皮重计量时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime pzjlsj;

    @ApiModelProperty(value = "皮重计量员")
    private String pzjly;

    @ApiModelProperty(value = "毛重(公斤)")
    private BigDecimal mz;

    @ApiModelProperty(value = "毛重监磅员")
    private String mzjby;

    @ApiModelProperty(value = "毛重计量时间：格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime mzjlsj;

    @ApiModelProperty(value = "毛重计量员")
    private String mzjly;

    @ApiModelProperty(value = "包装物：1：麻袋 2：编织袋 3：散装 9：其他")
    private String bzw;

    @ApiModelProperty(value = "标准包单包重")
    private BigDecimal bzbdbz;

    @ApiModelProperty(value = "标准包件数(件)")
    private Integer bzbjs;

    @ApiModelProperty(value = "净重")
    private BigDecimal jz;

    @ApiModelProperty(value = "扣 (增) 量")
    private BigDecimal kzl;

    @ApiModelProperty(value = "值仓保管员姓 名")
    private String zcbgyxm;

    @ApiModelProperty(value = "装卸作业单位")
    private String zxzydw;

    @ApiModelProperty(value = "出门时间:格式：yyyy-MM-dd HH:mm :ss")
    private LocalDateTime cmsj;

    @ApiModelProperty(value = "出门确认门岗 人员姓名")
    private String cmqrmgryxm;

    @ApiModelProperty(value = "出库结算单号")
    private String ckjsdh;

    @ApiModelProperty(value = "备注")
    private String bz;
}
