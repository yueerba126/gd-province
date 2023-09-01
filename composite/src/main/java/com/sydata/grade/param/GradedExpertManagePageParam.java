/**
 * @filename:GradedExpertManagePageParam 2023年05月25日
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
import java.util.List;

/**   
 * @Description:TODO(等级粮库评定管理-专家管理-分页参数)
 * 
 * @version: V1.0
 * @author: lzq
 * 
 */
@ApiModel(value="GradedExpertManagePageParam对象", description="等级粮库评定管理-专家管理-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=true)
public class GradedExpertManagePageParam extends PageQueryParam implements Serializable {

	private static final long serialVersionUID = 1685005952447L;

    @ApiModelProperty(name = "id" , value = "id")
    private String id;
    @ApiModelProperty(name = "expertName" , value = "姓名")
    private String expertName;
    @ApiModelProperty(name = "expertTitle" , value = "职称")
    private String expertTitle;
    @ApiModelProperty(name = "pdnx" , value = "参与评定年限")
    private String pdnx;
    @ApiModelProperty(name = "pdnxs" , value = "参与评定年限集合")
    private List<String> pdnxs;
    @ApiModelProperty(name = "ids", value = "ids")
    private List<String> ids;
}
