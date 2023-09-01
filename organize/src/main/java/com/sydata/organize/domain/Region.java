package com.sydata.organize.domain;

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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 组织架构-行政区域对象 org_region
 *
 * @author lzq
 * @date 2022-06-28
 */
@ApiModel(description = "组织架构-行政区域")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_region")
public class Region implements Serializable {

    @NotBlank(message = "行政区域代码不能为空")
    @ApiModelProperty(value = "行政区域代码（主键ID）")
    @TableId(value = "id")
    private String id;

    @NotBlank(message = "行政区域名称不能为空")
    @ApiModelProperty(value = "行政区域名称")
    private String name;

    @NotBlank(message = "行政区域类型不能为空")
    @ApiModelProperty(value = "行政区域类型：国、省、县")
    private String type;

    @NotBlank(message = "父级行政区域代码不能为空")
    @ApiModelProperty(value = "父级行政区域代码")
    private String parentId;

    @ApiModelProperty(value = "国代码")
    private String countryId;

    @ApiModelProperty(value = "省代码")
    private String provinceId;

    @ApiModelProperty(value = "市代码")
    private String cityId;

    @ApiModelProperty(value = "区代码")
    private String areaId;

    @NotNull(message = "经度不能为空")
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @NotNull(message = "纬度不能为空")
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新者")
    private String updateBy;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "海康区域ID")
    private String hkRegionId;

    @ApiModelProperty(value = "行政区划排序")
    private Long regionSort;
}