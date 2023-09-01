package com.sydata.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_NULL;

/**
 * @author lzq
 * @description 基础字段
 * @date 2022/10/9 15:01
 */
@Data
@Accessors(chain = true)
public abstract class BaseFiledEntity implements Serializable {

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(updateStrategy = NOT_NULL, fill = INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "国ID")
    private String countryId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "区ID")
    private String areaId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;

    /**
     * 获取数据ID
     *
     * @return 数据ID
     */
    public abstract String getId();
}
