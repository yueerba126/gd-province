/**
 * @filename:GradedEnterpriseReviewVo 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description:TODO(等级粮库评定管理-专家管理Dto)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedExpertDto对象", description = "等级粮库评定管理-专家管理Dto")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GradedExpertDto implements Serializable {

    private static final long serialVersionUID = 1684750987869L;

    @Excel(name = "姓名")
    @ApiModelProperty(name = "expertName" , value = "姓名")
    private String expertName;
    @Excel(name = "性别(1男2女)")
    @ApiModelProperty(name = "expertSex" , value = "性别(1男2女)")
    private String expertSex;
    @Excel(name = "手机号")
    @ApiModelProperty(name = "phoneNum" , value = "手机号")
    private String phoneNum;
    @Excel(name = "职称")
    @ApiModelProperty(name = "expertTitle" , value = "职称")
    private String expertTitle;
    @Excel(name = "身份证号")
    @ApiModelProperty(name = "sfzh" , value = "身份证号")
    private String sfzh;
    @Excel(name = "银行卡号")
    @ApiModelProperty(name = "yhkh" , value = "银行卡号")
    private String yhkh;
    @Excel(name = "开户行支行名称")
    @ApiModelProperty(name = "khzh" , value = "开户行支行名称")
    private String khzh;
    @Excel(name = "工作单位")
    @ApiModelProperty(name = "gzdw" , value = "工作单位")
    private String gzdw;
    @Excel(name = "参与评定年限")
    @ApiModelProperty(name = "pdnx" , value = "参与评定年限")
    private String pdnx;
}
