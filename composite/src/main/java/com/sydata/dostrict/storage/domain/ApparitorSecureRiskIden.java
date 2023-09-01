/**
 * @filename:ApparitorSecureRiskIdenBean 2023年04月28日
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
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**   
 * @Description:TODO(安全仓储-风险智能识别实体类)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="ApparitorSecureRiskIden对象", description="安全仓储-风险智能识别")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_secure_risk_iden")
public class ApparitorSecureRiskIden extends BaseFiledEntity implements Serializable {

	private static final long serialVersionUID = 1682668721992L;
    
	@ApiModelProperty(name = "czbz" , value = "操作标志（i：新增(默认)，u：更新，d：删除）")
	private String czbz;
    
	@ApiModelProperty(name = "dwdm" , value = "企业")
	private String dwdm;
    
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
	@ApiModelProperty(name = "id" , value = "安全生产-风险智能识别Id")
	private String id;
    
	@ApiModelProperty(name = "kqdm" , value = "库点")
	private String kqdm;
    
	@ApiModelProperty(name = "remark" , value = "线索描述")
	private String remark;
    
	@ApiModelProperty(name = "riskType" , value = "风险类型")
	private String riskType;

	@ApiModelProperty(name = "yjlxType" , value = "预警类型")
	private String yjlxType;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	@ApiModelProperty(name = "yjrq" , value = "预警日期")
	private LocalDate yjrq;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
	private LocalDateTime zhgxsj;
}
