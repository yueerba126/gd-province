/**
 * @filename:ApparitorSecureRiskUnitBean 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.vo;

import com.sydata.common.basis.annotation.DataBindCompany;
import com.sydata.common.basis.annotation.DataBindStockHouse;
import com.sydata.dostrict.storage.domain.ApparitorSecureRiskUnit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**   
 * @Description:TODO(安全仓储-安全风险台账Vo)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="ApparitorSecureRiskUnitVo对象", description="安全仓储-安全风险台账Vo")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSecureRiskUnitVo extends ApparitorSecureRiskUnit implements Serializable {

	@DataBindCompany
	@ApiModelProperty(value = "单位名称")
	private String dwmc;

	@DataBindStockHouse
	@ApiModelProperty(value = "库区名称")
	private String kqmc;

}
