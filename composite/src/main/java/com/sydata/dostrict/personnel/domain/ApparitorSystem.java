package com.sydata.dostrict.personnel.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * 行政管理-人员制度管理对象 apparitor_system
 *
 * @author fuql
 * @date 2023-04-24
 */
@ApiModel(description = "行政管理-人员制度管理")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("apparitor_system")
public class ApparitorSystem extends BaseFiledEntity implements Serializable {


    @Excel(name = "人员制度管理id")
    @ApiModelProperty(value = "人员制度管理id")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty("单位代码")
    private String dwdm;

    @ApiModelProperty("单位名称")
    private String dwmc;

    @Excel(name = "租户ID")
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @Excel(name = "部门")
    @ApiModelProperty(value = "部门")
    private String deptId;

    @Excel(name = "文件类别id")
    @ApiModelProperty(value = "文件类别id")
    private Long fileTypeId;

    @Excel(name = "文件标题")
    @ApiModelProperty(value = "文件标题")
    private String fileName;

    @Excel(name = "发文号")
    @ApiModelProperty(value = "发文号")
    private String number;

    @Excel(name = "文件描述")
    @ApiModelProperty(value = "文件描述")
    private String remark;

    @ApiModelProperty(value = "发布人")
    private String releaseId;

    @ApiModelProperty(value = "状态")
    private String billStatus;

    @ApiModelProperty(value = "附件")
    private String fileAttachment;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime releaseTime;

}