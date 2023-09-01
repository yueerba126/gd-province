/**
 * @filename:GradedEnterpriseFlowInitParam 2023年05月24日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**   
 * @Description:TODO(等级粮库评定管理-企业申报流程表-初始化)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedEnterpriseFlowInitParam对象", description="等级粮库评定管理-企业申报流程表-初始化")
@Data
@ToString
@Accessors(chain = true)
public class GradedEnterpriseFlowInitParam implements Serializable {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "qyid", value = "企业id")
    private String qyid;

    @ApiModelProperty(name = "sbdj", value = "申报等级(1,2,3,4,5)(A,AA,AAA,AAAA,AAAAA)")
    private String sbdj;

    @ApiModelProperty(name = "xzqhdm", value = "所在区域名称")
    private String xzqhdm;
}
