/**
 * @filename:ApparitorSecureSystemBean 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
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
 * @Description:TODO(安全仓储-安全生产-生产制度实体类)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="ApparitorSecureSystemBean对象", description="安全仓储-安全生产-生产制度")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_secure_system")
public class ApparitorSecureSystem extends BaseFiledEntity implements Serializable {

	private static final long serialVersionUID = 1682580983798L;

	@TableId(value = "id",type = IdType.INPUT)
	@ApiModelProperty(name = "id" , value = "生产制度id")
	private String id;
    
	@ApiModelProperty(name = "billStatus" , value = "状态")
	private String billStatus;
    
	@ApiModelProperty(name = "deptId" , value = "部门")
	private String deptId;
    
	@ApiModelProperty(name = "dwdm" , value = "发布单位")
	private String dwdm;
    
	@ApiModelProperty(name = "fileAttachment" , value = "附件记录")
	private String fileAttachment;
    
	@ApiModelProperty(name = "fileName" , value = "文件标题")
	private String fileName;
    
	@ApiModelProperty(name = "number" , value = "发文号")
	private String number;
    
	@ApiModelProperty(name = "releaseId" , value = "发布人ID")
	private String releaseId;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(name = "releaseTime" , value = "发布时间")
	private LocalDateTime releaseTime;
    
	@ApiModelProperty(name = "remark" , value = "文件描述")
	private String remark;
    
	@ApiModelProperty(name = "typeId" , value = "制度管理id(文件类别)")
	private String typeId;

	@ApiModelProperty(name = "czbz" , value = "操作标志（i：新增(默认)，u：更新，d：删除）")
	private String czbz;

	@ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
	private LocalDateTime zhgxsj;
}
