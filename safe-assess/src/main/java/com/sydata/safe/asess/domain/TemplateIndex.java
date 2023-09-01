package com.sydata.safe.asess.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
import java.math.BigDecimal;

/**
 * 粮食安全考核-考核指标对象 safe_assess_index
 *
 * @author system
 * @date 2023-02-13
 */
@ApiModel(description = "粮食安全考核-考核模板指标表")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_template_index")
public class TemplateIndex implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "考核模板ID")
    private String templateId;

    @ApiModelProperty(value = "父ID")
    private String parentId;

    @ApiModelProperty(value = "指标名称")
    private String name;

    @ApiModelProperty(value = "是否是题目 0是 1否")
    private Boolean topic;

    @ApiModelProperty(value = "分数")
    private BigDecimal score;

    @ApiModelProperty(value = "评分标准")
    private String standard;

    @ApiModelProperty(value = "评分说明")
    private String illustrate;

    @ApiModelProperty(value = "牵头部门ID")
    private Long leadDeptId;

    @ApiModelProperty(value = "配合部门IDS")
    private String cooperateDeptIds;

    @ApiModelProperty(value = "缺项行政区域IDS")
    private String lackRegionIds;
}