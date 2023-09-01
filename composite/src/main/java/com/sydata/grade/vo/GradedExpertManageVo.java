/**
 * @filename:GradedExpertManageVo 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.vo;

import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.grade.domain.GradedExpertManage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**   
 * @Description:TODO(等级粮库评定管理-专家管理Vo)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedExpertManageVo对象", description="等级粮库评定管理-专家管理Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GradedExpertManageVo extends GradedExpertManage implements Serializable {

	private static final long serialVersionUID = 1685005952447L;

	@DataBindDict(sourceField = "#expertSex", sourceFieldCombination = "xb")
	@ApiModelProperty(name = "expertSexName" , value = "性别(1男2女)")
	private String expertSexName;
}
