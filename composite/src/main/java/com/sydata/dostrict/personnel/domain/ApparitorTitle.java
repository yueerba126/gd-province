package com.sydata.dostrict.personnel.domain;

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

/**
 * 行政管理-荣誉称号管理对象 apparitor_title
 *
 * @author fuql
 * @date 2023-04-25
 */
@ApiModel(description = "行政管理-荣誉称号管理")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_title")
public class ApparitorTitle extends BaseFiledEntity implements Serializable {

    @Excel(name = "荣誉称号管理id")
    @ApiModelProperty(value = "荣誉称号管理id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("单位名称")
    private String dwmc;

    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String deptId;

    @Excel(name = "称号类别id")
    @ApiModelProperty(value = "称号类别id")
    private Long titleTypeId;

    @Excel(name = "人员ID")
    @ApiModelProperty(value = "人员ID")
    private String personnelId;

    @ApiModelProperty("操作标志")
    private String czbz;
}