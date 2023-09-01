package com.sydata.common.file.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * 文件存储对象 file_storage
 *
 * @author lzq
 * @date 2022-06-23
 */
@ApiModel(description = "公共文件存储")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("common_file_storage")
public class FileStorage implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = ASSIGN_ID)
    private String id;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "存储桶")
    private String bucket;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "存储名称")
    private String storageName;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "文件大小(字节数)")
    private Long fileSize;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "状态 1未使用 2使用中 3已弃用")
    private String state;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "使用时间")
    private LocalDateTime useTime;

    @TableField(updateStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "弃用时间")
    private LocalDateTime discardTime;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "国ID")
    private String countryId;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "区ID")
    private String areaId;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;
}