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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * @author lzq
 * @description 组织架构-app对接应用关联库区
 * @date 2023/5/30 10:48
 */
@ApiModel(description = "组织架构-app对接应用关联库区")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_app_api_stock_house")
public class AppApiStockHouse implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @NotBlank(message = "应用id必填")
    @ApiModelProperty(value = "应用id")
    private String appid;

    @NotBlank(message = "行政区域必填")
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

    @NotBlank(message = "单位代码必填")
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}$", message = "单位代码格式错误")
    @Size(max = 18, min = 18, message = "单位代码只能是18位")
    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @NotBlank(message = "库区代码必填")
    @Pattern(regexp = "^[A-Z0-9]{2}[0-9]{6}[A-Z0-9]{10}[0-9]{3}$", message = "库区代码格式错误")
    @Size(max = 21, min = 21, message = "库区代码只能是21位")
    @ApiModelProperty(value = "库区ID")
    private String stockHouseId;
}
