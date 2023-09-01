/**
 * @filename:GradedGrainDepotStandardVo 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.vo;

import com.sydata.grade.param.GradedGrainDepotStandardSaveParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.grade.domain.GradedGrainDepotStandard;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定标准Vo)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedGrainDepotStandardVo对象", description = "等级粮库评定管理-等级粮库评定标准Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GradedGrainDepotStandardVo extends GradedGrainDepotStandard implements Serializable {

    private static final long serialVersionUID = 1684315566154L;

    @DataBindDict(sourceField = "#jfzb", sourceFieldCombination = "grade_jfzb")
    @ApiModelProperty(name = "jfzbName", value = "加分指标名称")
    private String jfzbName;

    @DataBindDict(sourceField = "#bczt", sourceFieldCombination = "grade_bczt")
    @ApiModelProperty(name = "bcztName", value = "保存状态(1:暂存，2:提交)")
    private String bcztName;
}
