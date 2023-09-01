/**
 * @filename:AdminPlanBean 2023年04月24日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.plan.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**   
 * @Description:TODO(规划建设-仓储设施建设管理实体类)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="AdminPlanBean对象", description="规划建设-仓储设施建设管理")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_plan")
public class ApparitorPlan extends BaseFiledEntity implements Serializable {

	private static final long serialVersionUID = 1682329474303L;
	
	@ApiModelProperty(name = "auditUnit" , value = "审计单位")
	private String auditUnit;
    
	@ApiModelProperty(name = "constructionUnit" , value = "监理单位")
	private String constructionUnit;
    
	@ApiModelProperty(name = "designUnit" , value = "设计单位")
	private String designUnit;
    
	@ApiModelProperty(name = "dwdm" , value = "单位代码")
	private String dwdm;
    
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
	@ApiModelProperty(name = "id" , value = "主键ID(单位代码)")
	private String id;
    
	@ApiModelProperty(name = "jhtz" , value = "计划投资(万元)")
	private BigDecimal jhtz;
    
	@ApiModelProperty(name = "jsje" , value = "决算金额(万元)")
	private BigDecimal jsje;
    
	@ApiModelProperty(name = "jsnr" , value = "主要建设内容")
	private String jsnr;
    
	@ApiModelProperty(name = "kqdm" , value = "库区代码")
	private String kqdm;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@ApiModelProperty(name = "lxrq" , value = "立项日期,格式：yyyy-MM-dd")
	private LocalDate lxrq;
    
	@ApiModelProperty(name = "tenderingUnit" , value = "招标单位")
	private String tenderingUnit;
    
	@ApiModelProperty(name = "thirdPartyEvaluationUnit" , value = "第三方评测单位")
	private String thirdPartyEvaluationUnit;
    
	@ApiModelProperty(name = "winningBidderUnit" , value = "中标单位")
	private String winningBidderUnit;
    
	@ApiModelProperty(name = "xmjzCode" , value = "项目进展(设计01、立项02、招标03、开工04、完工05、验收06)")
	private String xmjzCode;
    
	@ApiModelProperty(name = "xmmc" , value = "项目名称")
	private String xmmc;

	@TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
	@ApiModelProperty(name = "xmdm" , value = "项目代码")
	private String xmdm;
    
	@ApiModelProperty(name = "xzqhdm" , value = "行政区划代码")
	private String xzqhdm;
    
	@ApiModelProperty(name = "zbje" , value = "中标金额(万元)")
	private BigDecimal zbje;

	@ApiModelProperty(name = "czbz" , value = "操作标志（i：新增(默认)，u：更新，d：删除）")
	private String czbz;

	@ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
	private LocalDateTime zhgxsj;
}
