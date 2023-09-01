/**
 * @filename:GradedEnterpriseSelfAssessmentPageParam 2023年05月22日
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
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:TODO(等级粮库评定管理-企业申报自评表-分页参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseSelfAssessmentPageParam对象", description = "等级粮库评定管理-企业申报自评表-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GradedEnterpriseSelfAssessmentPageParam extends PageQueryParam implements Serializable {

    private static final long serialVersionUID = 1684746677614L;

    @ApiModelProperty(name = "id", value = "id")
    private String id;
    @ApiModelProperty(name = "qyid", value = "企业id")
    private String qyid;
    @ApiModelProperty(name = "templateId", value = "模板id")
    private String templateId;
    @ApiModelProperty(name = "scoringMethodId", value = "模板树叶子节点id")
    private String scoringMethodId;
}
