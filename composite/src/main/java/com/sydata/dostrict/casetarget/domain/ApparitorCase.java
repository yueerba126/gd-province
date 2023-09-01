package com.sydata.dostrict.casetarget.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sydata.common.domain.BaseFiledEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 行政管理-执法案件对象 apparitor_case
 *
 * @author fuql
 * @date 2023-04-26
 */
@ApiModel(description = "行政管理-执法案件")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_case")
public class ApparitorCase extends BaseFiledEntity implements Serializable{

    @Excel(name = "执法案件ID")
    @ApiModelProperty(value = "执法案件ID")
    @TableId(value = "id", type = IdType.AUTO)
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

    @Excel(name = "案件处理-处理程序")
    @ApiModelProperty(value = "案件处理-处理程序")
    private String caseProcedure;

    @Excel(name = "案件处理-办理完结日期")
    @ApiModelProperty(value = "案件处理-办理完结日期")
    private LocalDate caseCompletion;

    @Excel(name = "案件处理-备注")
    @ApiModelProperty(value = "案件处理-备注")
    private String remark;

    @Excel(name = "案件处理-现场笔录")
    @ApiModelProperty(value = "案件处理-现场笔录")
    private String fileRecord;

    @Excel(name = "案件处理-现场检查记录表")
    @ApiModelProperty(value = "案件处理-现场检查记录表")
    private String fileSheet;

    @Excel(name = "案件处理-办理附件")
    @ApiModelProperty(value = "案件处理-办理附件")
    private String fileHandle;

}