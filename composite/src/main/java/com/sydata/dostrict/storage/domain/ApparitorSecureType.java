/**
 * @filename:ApparitorSecureTypeBean 2023年04月27日
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
 * @Description:TODO(安全仓储-安全生产-制度类别实体类)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="ApparitorSecureTypeBean对象", description="安全仓储-安全生产-制度类别")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_secure_type")
public class ApparitorSecureType extends BaseFiledEntity implements Serializable {

	private static final long serialVersionUID = 1682581021991L;

	@TableId(value = "id", type = IdType.INPUT)
	@ApiModelProperty(name = "id" , value = "安全生产-制度类别管理Id")
	private String id;
    
	@ApiModelProperty(name = "isEnable" , value = "启用状态(01正常 02停用)")
	private String isEnable;
    
	@ApiModelProperty(name = "isLeafNode" , value = "是否叶节点(01是 02否)")
	private String isLeafNode;
    
	@ApiModelProperty(name = "parentId" , value = "父类ID")
	private String parentId;
    
	@ApiModelProperty(name = "remark" , value = "备注")
	private String remark;
    
	@ApiModelProperty(name = "typeName" , value = "分类名称")
	private String typeName;

	@ApiModelProperty(name = "typeCode" , value = "分类编码")
	private String typeCode;

	@ApiModelProperty(name = "czbz" , value = "操作标志（i：新增(默认)，u：更新，d：删除）")
	private String czbz;

	@ApiModelProperty(name = "zhgxsj" , value = "最后更新时间")
	private LocalDateTime zhgxsj;
}
