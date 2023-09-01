package com.sydata.dostrict.casetarget.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 执法督察--执法案件新增参数
 *
 * @author fuql
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "执法督察--执法案件新增参数")
public class ApparitorCaseSavaParam implements Serializable {

    @ApiModelProperty(value = "执法案件ID")
    private String id;

    @Excel(name = "案件名称")
    @ApiModelProperty(value = "案件名称")
    private String caseName;

    @Excel(name = "案件企业")
    @ApiModelProperty(value = "案件企业")
    private String caseEnterpriseId;

    @Excel(name = "法定代表人")
    @ApiModelProperty(value = "法定代表人")
    private String corporation;

    @Excel(name = "联系电话")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    @Excel(name = "联系人地址")
    @ApiModelProperty(value = "联系人地址")
    private String address;

    @Excel(name = "案件类型")
    @ApiModelProperty(value = "案件类型 字典：case_type")
    private String caseType;

    @Excel(name = "案发地点")
    @ApiModelProperty(value = "案发地点")
    private String place;

    @Excel(name = "案发时间")
    @ApiModelProperty(value = "案发时间")
    private LocalDate caseDate;

    @Excel(name = "案件来源")
    @ApiModelProperty(value = "案件来源 字典：case_source")
    private String caseSource;

    @Excel(name = "立案描述")
    @ApiModelProperty(value = "立案描述")
    private String caseDescribe;

    @Excel(name = "案件附件")
    @ApiModelProperty(value = "案件附件")
    private String fileAttachment;
}
