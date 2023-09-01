package com.sydata.dostrict.personnel.param;

import com.sydata.dostrict.personnel.domain.ApparitorTitleType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 行政管理-称号类别树查询参数
 *
 * @author fuql
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "行政管理-称号类别树查询参数")
public class ApparitorTitleTypeParam extends ApparitorTitleType implements Serializable {

    @ApiModelProperty(value = "是否显示数量")
    private Integer isShowNum;

    @ApiModelProperty(value = "是否显示（无）分类")
    private Integer isShowHave;
}
