/**
 * @filename:GradedEnterpriseMaterialVo 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.vo;

import com.sydata.grade.annotation.DataBindGradedEnterpriseReview;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.sydata.grade.domain.GradedEnterpriseMaterial;
import com.sydata.common.basis.annotation.*;
import com.sydata.organize.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:TODO(等级粮库评定管理-企业申报证明材料Vo)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseMaterialVo对象", description = "等级粮库评定管理-企业申报证明材料Vo")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GradedEnterpriseMaterialVo extends GradedEnterpriseMaterial implements Serializable {

    private static final long serialVersionUID = 1684747364536L;

    @DataBindGradedEnterpriseReview(sourceField = "#qyid")
    @ApiModelProperty(name = "qymc", value = "企业名称")
    private String qymc;
}
