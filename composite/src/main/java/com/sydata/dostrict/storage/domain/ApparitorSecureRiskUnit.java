/**
 * @filename:ApparitorSecureRiskUnitBean 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
import java.time.LocalDateTime;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**   
 * @Description:TODO(安全仓储-安全风险台账实体类)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="ApparitorSecureRiskUnitBean对象", description="安全仓储-安全风险台账")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_secure_risk_unit")
public class ApparitorSecureRiskUnit extends BaseFiledEntity implements Serializable {

	private static final long serialVersionUID = 1682580914790L;

	@TableId(value = "id", type = IdType.INPUT)
	@ApiModelProperty(name = "id" , value = "安全仓储-安全风险台账id")
	private String id;

	@ApiModelProperty(name = "beginImg" , value = "整改前照片")
	private String beginImg;
    
	@ApiModelProperty(name = "corporation" , value = "企业法人")
	private String corporation;
    
	@ApiModelProperty(name = "dwdm" , value = "企业单位")
	private String dwdm;

	@ApiModelProperty(name = "beginDateTime" , value = "整改开始时间")
	private LocalDateTime beginDateTime;

	@ApiModelProperty(name = "endDateTime" , value = "整改结束时间")
	private LocalDateTime endDateTime;
    
	@ApiModelProperty(name = "endImg" , value = "整改后照片")
	private String endImg;
    
	@ApiModelProperty(name = "headName" , value = "整改负责人")
	private String headName;
    
	@ApiModelProperty(name = "inspectionId" , value = "检查人")
	private String inspectionId;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "inspectionTime" , value = "检查时间")
	private LocalDateTime inspectionTime;
    
	@ApiModelProperty(name = "kqdm" , value = "库区代码")
	private String kqdm;
    
	@ApiModelProperty(name = "remark" , value = "隐患及问题描述")
	private String remark;
    
	@ApiModelProperty(name = "riskMeasure" , value = "整改措施")
	private String riskMeasure;

	@ApiModelProperty(name = "czbz" , value = "操作标志（i：新增(默认)，u：更新，d：删除）")
	private String czbz;

	@ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
	private LocalDateTime zhgxsj;
}
