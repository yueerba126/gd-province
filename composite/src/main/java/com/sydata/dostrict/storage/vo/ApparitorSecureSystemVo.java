/**
 * @filename:ApparitorSecureSystemBean 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.common.composite.annotation.DataBindZdglType;
import com.sydata.dostrict.storage.domain.ApparitorSecureSystem;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.organize.annotation.DataBindUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**   
 * @Description:TODO(安全仓储-安全生产-生产制度Vo)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="ApparitorSecureSystemVo对象", description="安全仓储-安全生产-生产制度Vo")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSecureSystemVo extends ApparitorSecureSystem implements Serializable {

	@DataBindCompany
	@ApiModelProperty(value = "单位名称")
	private String dwmc;

	@DataBindDept(sourceField = "#deptId")
	@ApiModelProperty(value = "部门名称")
	private String deptName;

	@DataBindZdglType(sourceField = "#typeId")
	@ApiModelProperty(value = "文件类别名称")
	private String typeName;

	@DataBindUser(sourceField = "#releaseId")
	@ApiModelProperty(value = "发布人名称")
	private String releaseName;

	@DataBindDict(sourceFieldCombination = "apparitor_status", sourceField = "#billStatus")
	@ApiModelProperty(value = "状态名称")
	private String billStatusName;

}
