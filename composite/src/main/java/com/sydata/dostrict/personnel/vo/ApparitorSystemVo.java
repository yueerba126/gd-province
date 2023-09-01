package com.sydata.dostrict.personnel.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author fuql
 * @describe 行政管理-人员制度管理主VO
 * @date 2022-08-19
 */
@ApiModel(description = "行政管理-人员制度管理主VO")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSystemVo extends ApparitorSystemMainVo implements Serializable {

    @ApiModelProperty(value = "人员制度行政区划数据")
    private List<ApparitorSystemZoningVo> zoningList;
}
