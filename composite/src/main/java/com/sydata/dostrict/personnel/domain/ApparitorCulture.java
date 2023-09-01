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
import java.time.LocalDateTime;

/**
 * 行政管理-人才培养计划对象 apparitor_culture
 *
 * @author fuql
 * @date 2023-04-25
 */
@ApiModel(description = "行政管理-人才培养计划")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_culture")
public class ApparitorCulture extends BaseFiledEntity implements Serializable {

    @Excel(name = "人才培养计划id")
    @ApiModelProperty(value = "人才培养计划id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("单位名称")
    private String dwmc;

    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String deptId;

    @Excel(name = "文件标题")
    @ApiModelProperty(value = "文件标题")
    private String fileName;

    @Excel(name = "文件描述")
    @ApiModelProperty(value = "文件描述")
    private String remark;

    @Excel(name = "审核人名称")
    @ApiModelProperty(value = "审核人名称")
    private String approvedName;

    @Excel(name = "审核时间")
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime approvedTime;

    @Excel(name = "状态1拟稿，2待审核，3发布")
    @ApiModelProperty(value = "状态1拟稿，2待审核，3发布")
    private String billStatus;

    @Excel(name = "附件记录")
    @ApiModelProperty(value = "附件记录")
    private String fileAttachment;
}