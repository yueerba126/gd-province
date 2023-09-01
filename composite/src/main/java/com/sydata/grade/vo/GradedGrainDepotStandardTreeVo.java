/**
 * @filename:GradedGrainDepotStandardTreeVo 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.vo;

import com.sydata.grade.annotation.DataBindGradedGrainDepotTemplate;
import com.sydata.grade.domain.GradedGrainDepotStandard;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定标准TreeVo)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedGrainDepotStandardTreeVo对象", description = "等级粮库评定管理-等级粮库评定标准TreeVo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GradedGrainDepotStandardTreeVo extends GradedGrainDepotStandard implements Serializable {

    private static final long serialVersionUID = 1684315566154L;

    @ApiModelProperty(value = "子节点")
    private List<GradedGrainDepotStandardTreeVo> child;

    @DataBindGradedGrainDepotTemplate(sourceField = "#templateId")
    @ApiModelProperty(name = "templateName", value = "模板名称")
    private String templateName;
}
