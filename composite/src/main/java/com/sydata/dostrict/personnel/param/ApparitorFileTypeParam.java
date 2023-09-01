package com.sydata.dostrict.personnel.param;

import com.sydata.dostrict.personnel.domain.ApparitorFileType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 行政管理模块--企业人员树查询参数
 *
 * @author fuql
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "行政管理模块--企业人员树查询参数")
public class ApparitorFileTypeParam extends ApparitorFileType implements Serializable {

    @ApiModelProperty(value = "是否显示数量")
    private Integer isShowNum;

    @ApiModelProperty(value = "是否显示（无）分类")
    private Integer isShowHave;
}
