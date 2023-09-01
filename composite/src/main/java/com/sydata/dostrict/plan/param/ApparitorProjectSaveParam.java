package com.sydata.dostrict.plan.param;

import com.sydata.collect.api.validated.annotation.StringSize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 行政管理模块--企业人员新增参数
 *
 * @author fuql
 * @date 2022/10/21 11:30
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "规划建设-企业人员-新增参数")
public class ApparitorProjectSaveParam implements Serializable {

    @ApiModelProperty(name = "id", value = "主键ID(项目代码)")
    private String id;

    @NotBlank(message = "项目名称必填")
    @Pattern(regexp = "^[^\\/\\s]{1,128}$", message = "项目名称格式错误")
    @ApiModelProperty(name = "xmmc", value = "项目名称")
    private String xmmc;

//    @Pattern(regexp = "^[^\\/\\s]{1,64}$", message = "项目代码格式错误")
//    @ApiModelProperty(name = "xmdm" , value = "项目代码")
//    private String xmdm;

    @NotBlank(message = "单位代码必填")
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误")
    @ApiModelProperty(name = "dwdm", value = "单位代码")
    private String dwdm;

    @NotBlank(message = "库区代码必填")
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误")
    @ApiModelProperty(name = "kqdm", value = "库区代码")
    private String kqdm;

    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "项目 (法人) 单位格式错误")
    @ApiModelProperty(name = "xmdw", value = "项目 (法人) 单位（统一社会信用代码）")
    private String xmdw;

    @NotBlank(message = "行政区划代码必填")
    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误")
    @ApiModelProperty(name = "xzqhdm", value = "行政区划代码")
    private String xzqhdm;

    @NotNull(message = "年份必填")
    @ApiModelProperty(name = "nf", value = "年份")
    private String nf;

    @NotBlank(message = "项目类型必填")
    @Pattern(regexp = "^[^\\/\\s]{1,1}$", message = "项目类型格式错误")
    @ApiModelProperty(name = "xmlx", value = "项目类型")
    private String xmlx;

    @NotBlank(message = "建设内容及规模必填")
    @Pattern(regexp = "^[^\\/\\s]{1,1024}$", message = "建设内容及规模格式错误")
    @ApiModelProperty(name = "jsnr", value = "建设内容及规模")
    private String jsnr;

    @NotNull(message = "拟建成时间必填")
    @ApiModelProperty(name = "njcsj", value = "拟建成时间")
    private LocalDate njcsj;

    @NotNull(message = "拟开工时间必填")
    @ApiModelProperty(name = "nkgsj", value = "拟开工时间")
    private LocalDate nkgsj;

    @StringSize(max = 32, message = "法定代表人证照号码最大长度32")
    @ApiModelProperty(name = "fddbrzzhm", value = "法定代表人证照号码")
    private String fddbrzzhm;

    @StringSize(max = 2, message = "法定代表人证照类型最大长度2")
    @ApiModelProperty(name = "fddbrzzlx", value = "法定代表人证照类型")
    private String fddbrzzlx;

    @Pattern(regexp = "^[^\\/\\s]{1,128}$", message = "建设地点格式错误")
    @ApiModelProperty(name = "jsdd", value = "建设地点")
    private String jsdd;

    @ApiModelProperty(name = "gdzctz", value = "固定资产投资（万元）")
    private BigDecimal gdzctz;

    @ApiModelProperty(name = "gpzq", value = "股票债券（万元）")
    private BigDecimal gpzq;

    @Pattern(regexp = "^[^\\/\\s]{1,1}$", message = "建设状态格式错误")
    @ApiModelProperty(name = "jszt", value = "建设状态；1:已立项未开工、2:建设中，3:已验收，4:已取消")
    private String jszt;

    @Pattern(regexp = "^[^\\/\\s]{1,16}$", message = "联系方式格式错误")
    @ApiModelProperty(name = "lxfs", value = "联系方式")
    private String lxfs;

    @Pattern(regexp = "^[^\\/\\s]{1,16}$", message = "联系人格式错误")
    @ApiModelProperty(name = "lxr", value = "联系人")
    private String lxr;

    @StringSize(max = 32, message = "电子邮箱最大长度32")
    @ApiModelProperty(name = "dzyx", value = "电子邮箱")
    private String dzyx;

    @ApiModelProperty(name = "qtzj", value = "其他资金（万元）")
    private BigDecimal qtzj;

    @NotNull(message = "申报日期必填")
    @ApiModelProperty(name = "sbrq", value = "申报日期")
    private LocalDate sbrq;

    @ApiModelProperty(name = "sczzj", value = "省财政资金（万元）")
    private BigDecimal sczzj;

    @ApiModelProperty(name = "sczzj01", value = "市财政资金（万元）")
    private BigDecimal sczzj01;

    @StringSize(max = 128, message = "审批文号最大长度128")
    @ApiModelProperty(name = "spwh", value = "审批文号")
    private String spwh;

    @ApiModelProperty(name = "xmdzjd", value = "项目地址经度")
    private BigDecimal xmdzjd;

    @ApiModelProperty(name = "zmdzwd", value = "项目地址纬度")
    private BigDecimal zmdzwd;

    @ApiModelProperty(name = "yhdk", value = "银行贷款（万元）")
    private BigDecimal yhdk;

    @ApiModelProperty(name = "ztz", value = "总投资（万元）")
    private BigDecimal ztz;

    @ApiModelProperty(name = "zyczzj", value = "中央财政资金（万元）")
    private BigDecimal zyczzj;

    @NotNull(message = "最后更新时间必填")
    @ApiModelProperty(name = "zhgxsj", value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
