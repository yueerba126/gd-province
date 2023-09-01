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

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;
import static com.baomidou.mybatisplus.annotation.IdType.INPUT;

/**
 * 组织架构-组织对象 org_organize
 *
 * @author lzq
 * @date 2022-06-28
 */
@ApiModel(description = "组织架构-组织")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_organize")
public class Organize implements Serializable {

    @ApiModelProperty(value = "（主键ID）组织代码:行政=行政区域+4位数自增号 企业=18位统一社会信用代码")
    @TableId(value = "id", type = INPUT)
    private String id;

    @ApiModelProperty(value = "组织类型：行政组织、企业组织")
    private String kind;

    @ApiModelProperty(value = "组织业务类型：粮食监管单位")
    private String busType;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父组织ID")
    private String parentId;

    @ApiModelProperty(value = "父组织ID集合,分隔")
    private String parentIds;

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

    @ApiModelProperty(value = "行政区域ID")
    private String regionId;

    @ApiModelProperty(value = "国ID")
    private String countryId;

    @ApiModelProperty(value = "省ID")
    private String provinceId;

    @ApiModelProperty(value = "市ID")
    private String cityId;

    @ApiModelProperty(value = "区ID")
    private String areaId;

    @ApiModelProperty(value = "菜单系统ID")
    private String menuSystemId;
}