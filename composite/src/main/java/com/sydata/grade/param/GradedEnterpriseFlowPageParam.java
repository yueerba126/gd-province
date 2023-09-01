/**
 * @filename:GradedEnterpriseFlowPageParam 2023年05月24日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.param;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**   
 * @Description:TODO(等级粮库评定管理-企业申报流程表-分页参数)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedEnterpriseFlowPageParam对象", description="等级粮库评定管理-企业申报流程表-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class GradedEnterpriseFlowPageParam extends PageQueryParam implements Serializable {

	private static final long serialVersionUID = 1684892830417L;

    @ApiModelProperty(name = "id" , value = "id")
    private String id;
    @ApiModelProperty(name = "qyid" , value = "企业id")
    private String qyid;
    @ApiModelProperty(name = "xzqhdm", value = "所在区域代码")
    private String xzqhdm;
}
