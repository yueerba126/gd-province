/**
 * @filename:GradedGrainDepotTemplateVo 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.vo;

import com.sydata.grade.vo.GradedGrainDepotStandardTreeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.grade.domain.GradedGrainDepotTemplate;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定模板Vo)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedGrainDepotTemplateVo对象", description = "等级粮库评定管理-等级粮库评定模板Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GradedGrainDepotTemplateVo extends GradedGrainDepotTemplate implements Serializable {

    private static final long serialVersionUID = 1684314836909L;

    @DataBindDict(sourceField = "#templateState", sourceFieldCombination = "grade_template_state")
    @ApiModelProperty(name = "templateStateName", value = "状态名称")
    private String templateStateName;

    @ApiModelProperty(name = "gradedGrainDepotStandardTreeVos", value = "等级标准树结构")
    private List<GradedGrainDepotStandardTreeVo> gradedGrainDepotStandardTreeVos;
}
