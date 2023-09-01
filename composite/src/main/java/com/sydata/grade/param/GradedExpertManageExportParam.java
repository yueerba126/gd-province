/**
 * @filename:GradedExpertManagePageParam 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.grade.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**   
 * @Description:TODO(等级粮库评定管理-专家管理-导出参数)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedExpertManageExportParam对象", description="等级粮库评定管理-专家管理-导出参数")
@Data
@ToString
@Accessors(chain = true)
public class GradedExpertManageExportParam implements Serializable {

	private static final long serialVersionUID = 1685005952447L;

    @ApiModelProperty(name = "id" , value = "id")
    private String id;
    @ApiModelProperty(name = "expertName" , value = "姓名")
    private String expertName;
    @ApiModelProperty(name = "expertTitle" , value = "职称")
    private String expertTitle;
    @ApiModelProperty(name = "pdnx" , value = "参与评定年限")
    private String pdnx;
}
