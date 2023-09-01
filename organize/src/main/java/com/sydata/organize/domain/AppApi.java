package com.sydata.organize.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 组织架构-app对接应用对象 org_app_api
 *
 * @author lzq
 * @date 2022-10-21
 */
@ApiModel(description = "组织架构-app对接应用")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_app_api")
public class AppApi implements Serializable {

    @NotBlank(message = "应用ID必填")
    @Size(max = 16, message = "应用ID最大长度16")
    @ApiModelProperty(value = "应用ID")
    @TableId(value = "id", type = IdType.INPUT)
    private String appid;

    @NotBlank(message = "应用名称必填")
    @Size(max = 30, message = "应用名称最大长度30")
    @ApiModelProperty(value = "应用名称")
    private String appName;

    @NotBlank(message = "应用key必填")
    @Size(max = 128, message = "应用key最大长度128")
    @ApiModelProperty(value = "应用key")
    private String appKey;

    @NotBlank(message = "应用秘钥必填")
    @Size(min = 172, max = 172, message = "应用秘钥格式不正确")
    @ApiModelProperty(value = "应用秘钥")
    private String appSecret;

    @NotBlank(message = "组织ID必填")
    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @NotNull(message = "应用状态必填")
    @ApiModelProperty(value = "应用状态:1启用 0禁用")
    private Boolean state;

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

    @NotEmpty(message = "行政区域必填")
    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @TableField(updateStrategy = NOT_EMPTY, insertStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "国ID")
    private String countryId;

    @TableField(updateStrategy = NOT_EMPTY, insertStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @TableField(updateStrategy = NOT_EMPTY, insertStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "市ID")
    private String cityId;

    @TableField(updateStrategy = NOT_EMPTY, insertStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "区ID")
    private String areaId;

    @TableField(updateStrategy = NOT_EMPTY, insertStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;
}