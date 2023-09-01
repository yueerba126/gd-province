package com.sydata.report.api.param.admin;

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
 * @description 项目信息上报参数
 * @date 2022/10/31 14:33
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "项目信息上报参数")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProjectReportParam extends BaseReportParam implements Serializable {

    @ApiModelProperty(value = "项目代码")
    private String xmdm;

    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "年份")
    private String nf;

    @ApiModelProperty(value = "项目类型")
    private String xmlx;

    @ApiModelProperty(value = "建设内容及规模")
    private String jsnr;

    @ApiModelProperty(value = "拟开工时间")
    private LocalDate nkgsj;

    @ApiModelProperty(value = "拟建成时间")
    private LocalDate njcsj;

    @ApiModelProperty(value = "建设状态；1:已立项未开工、2:建设中，3:已验收，4:已取消")
    private String jszt;

    @ApiModelProperty(value = "申报日期")
    private LocalDate sbrq;

    @ApiModelProperty(value = "审批文号")
    private String spwh;

    @ApiModelProperty(value = "项目 (法人) 单位（统一社会信用代码）")
    private String xmdw;

    @ApiModelProperty(value = "法定代表人证照类型")
    private String fddbrzzlx;

    @ApiModelProperty(value = "法定代表人证照号码")
    private String fddbrzzhm;

    @ApiModelProperty(value = "联系人")
    private String lxr;

    @ApiModelProperty(value = "联系方式")
    private String lxfs;

    @ApiModelProperty(value = "电子邮箱")
    private String dzyx;

    @ApiModelProperty(value = "建设地点")
    private String jsdd;

    @ApiModelProperty(value = "总投资（万元）")
    private BigDecimal ztz;

    @ApiModelProperty(value = "固定资产投资（万元）")
    private BigDecimal gdzctz;

    @ApiModelProperty(value = "中央财政资金（万元）")
    private BigDecimal zyczzj;

    @ApiModelProperty(value = "省财政资金（万元）")
    private BigDecimal sczzj;

    @ApiModelProperty(value = "市财政资金（万元）")
    private BigDecimal sczzj01;

    @ApiModelProperty(value = "银行贷款（万元）")
    private BigDecimal yhdk;

    @ApiModelProperty(value = "股票债券（万元）")
    private BigDecimal gpzq;

    @ApiModelProperty(value = "其他资金（万元）")
    private BigDecimal qtzj;

    @ApiModelProperty(value = "项目地址经度")
    private BigDecimal xmdzjd;

    @ApiModelProperty(value = "项目地址纬度")
    private BigDecimal zmdzwd;
}