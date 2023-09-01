/**
 * @filename:GradedEnterpriseProcessPageParam 2023年05月22日
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
 * @Description:TODO(等级粮库评定管理-企业申报审核详情-分页参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseProcessPageParam对象", description = "等级粮库评定管理-企业申报审核详情-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GradedEnterpriseProcessPageParam extends PageQueryParam implements Serializable {

    private static final long serialVersionUID = 1684748190668L;

    @ApiModelProperty(name = "id", value = "id")
    private String id;
    @ApiModelProperty(name = "qyid", value = "企业id")
    private String qyid;
    @ApiModelProperty(name = "qyshr", value = "审核人")
    private String qyshr;
    @ApiModelProperty(name = "shjg", value = "审核结果")
    private String shjg;
    @ApiModelProperty(name = "beginShsj", value = "开始审核时间")
    private LocalDateTime beginShsj;
    @ApiModelProperty(name = "endShsj", value = "结束审核时间")
    private LocalDateTime endShsj;
}
