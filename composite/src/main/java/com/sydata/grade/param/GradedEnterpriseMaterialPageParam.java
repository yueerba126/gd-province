/**
 * @filename:GradedEnterpriseMaterialPageParam 2023年05月22日
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
 * @Description:TODO(等级粮库评定管理-企业申报证明材料-分页参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseMaterialPageParam对象", description = "等级粮库评定管理-企业申报证明材料-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GradedEnterpriseMaterialPageParam extends PageQueryParam implements Serializable {

    private static final long serialVersionUID = 1684747364536L;

    @ApiModelProperty(name = "id", value = "id")
    private String id;
    @ApiModelProperty(name = "qyid", value = "企业id")
    private String qyid;
    @ApiModelProperty(name = "fileName", value = "材料名称")
    private String fileName;
}
