package com.sydata.dostrict.plan.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

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
public class ApparitorPlanSaveParam implements Serializable {

    @ApiModelProperty(name = "id" , value = "主键ID(单位代码)")
    private String id;

    @NotBlank(message = "单位代码必填")
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误")
    @ApiModelProperty(name = "dwdm" , value = "单位代码")
    private String dwdm;

    @NotBlank(message = "库区代码必填")
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误")
    @ApiModelProperty(name = "kqdm" , value = "库区代码")
    private String kqdm;

    @NotNull(message = "立项日期必填")
    @ApiModelProperty(name = "lxrq" , value = "立项日期,格式：yyyy-MM-dd")
    private LocalDate lxrq;

    @NotBlank(message = "行政区划代码必填")
    @Pattern(regexp = "^\\d{6}$", message = "行政区划代码格式错误")
    @ApiModelProperty(name = "xzqhdm" , value = "行政区划代码")
    private String xzqhdm;

    @NotBlank(message = "项目名称必填")
    @Pattern(regexp = "^[^\\/\\s]{1,128}$", message = "项目名称格式错误")
    @ApiModelProperty(name = "xmmc" , value = "项目名称")
    private String xmmc;

//    @Pattern(regexp = "^[^\\/\\s]{1,64}$", message = "项目代码格式错误")
//    @ApiModelProperty(name = "xmdm" , value = "项目代码")
//    private String xmdm;

    @NotBlank(message = "主要建设内容必填")
    @Pattern(regexp = "^[^\\/\\s]{1,1024}$", message = "主要建设内容格式错误")
    @ApiModelProperty(name = "jsnr" , value = "主要建设内容")
    private String jsnr;

    @ApiModelProperty(name = "jhtz" , value = "计划投资(万元)")
    private BigDecimal jhtz;

    @ApiModelProperty(name = "jsje" , value = "决算金额(万元)")
    private BigDecimal jsje;

    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "审计单位格式错误")
    @ApiModelProperty(name = "auditUnit" , value = "审计单位")
    private String auditUnit;

    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "监理单位格式错误")
    @ApiModelProperty(name = "constructionUnit" , value = "监理单位")
    private String constructionUnit;

    @NotBlank(message = "设计单位必填")
    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "设计单位格式错误")
    @ApiModelProperty(name = "designUnit" , value = "设计单位")
    private String designUnit;

    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "招标单位格式错误")
    @ApiModelProperty(name = "tenderingUnit" , value = "招标单位")
    private String tenderingUnit;

    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "第三方评测单位格式错误")
    @ApiModelProperty(name = "thirdPartyEvaluationUnit" , value = "第三方评测单位")
    private String thirdPartyEvaluationUnit;

    @Pattern(regexp = "^[^\\/\\s]{1,256}$", message = "中标单位格式错误")
    @ApiModelProperty(name = "winningBidderUnit" , value = "中标单位")
    private String winningBidderUnit;

    @NotBlank(message = "项目进展代码必填")
    @Pattern(regexp = "^01$|^02$|^03$|^04$|^05$|^06$", message = "项目进展代码格式错误")
    @ApiModelProperty(name = "xmjzCode" , value = "项目进展代码(设计01、立项02、招标03、开工04、完工05、验收06)")
    private String xmjzCode;

    @ApiModelProperty(name = "zbje" , value = "中标金额(万元)")
    private BigDecimal zbje;

    @NotNull(message = "最后更新时间必填")
    @ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
    private LocalDateTime zhgxsj;
}
