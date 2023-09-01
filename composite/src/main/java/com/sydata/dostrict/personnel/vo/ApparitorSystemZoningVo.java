package com.sydata.dostrict.personnel.vo;

import com.sydata.dostrict.personnel.domain.ApparitorSystemZoning;
import com.sydata.organize.annotation.DataBindRegion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author fuql
 * @describe 基础信息-人员制度管理管理行政区划VOVO
 * @date 2022-08-19
 */
@ApiModel(description = "基础信息-人员制度管理管理行政区划VO")
@Data
@ToString
@Accessors(chain = true)
public class ApparitorSystemZoningVo extends ApparitorSystemZoning implements Serializable {

    @DataBindRegion
    @ApiModelProperty(value = "行政区域名称")
    private String regionName;

}
