package com.sydata.report.api.param.basis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sydata.report.api.param.BaseReportParam;
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

/**
 * @author lzq
 * @description 企业单位上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "企业单位上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompanyReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("单位名称")
    private String dwmc;

    @ApiModelProperty("单位类型")
    private String dwlx;

    @ApiModelProperty("注册日期")
    private LocalDate zcrq;

    @ApiModelProperty("注册资本")
    private BigDecimal zczb;

    @ApiModelProperty("资产总额")
    private BigDecimal zcze;

    @ApiModelProperty("法定代表人")
    private String fddbr;

    @ApiModelProperty("法人身份证号")
    private String frsfzh;

    @ApiModelProperty("法人联系方式")
    private String frlxfs;

    @ApiModelProperty("企业联系人")
    private String qylxr;

    @ApiModelProperty("办公电话")
    private String bgdh;

    @ApiModelProperty("注册地址")
    private String zcdz;

    @ApiModelProperty("电子邮箱")
    private String dzyx;

    @ApiModelProperty("企业官方网站地址")
    private String qygfwzdz;

    @ApiModelProperty("传真号码")
    private String czhm;

    @ApiModelProperty("邮政编码")
    private String yzbm;

    @ApiModelProperty("行政区划代码")
    private String xzqhdm;

    @ApiModelProperty("上级单位名称")
    private String sjdwmc;

    @ApiModelProperty("上级单位代码")
    private String sjdwdm;

    @ApiModelProperty("库区数")
    private Integer kqs;

    @ApiModelProperty("仓房数")
    private Integer cfs;

    @ApiModelProperty("油罐数")
    private Integer ygs;

    @ApiModelProperty("经度")
    private BigDecimal jd;

    @ApiModelProperty("纬度")
    private BigDecimal wd;
}
