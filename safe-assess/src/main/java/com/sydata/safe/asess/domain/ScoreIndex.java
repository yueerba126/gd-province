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
 * 粮食安全考核-考核评分指标对象 safe_assess_score_index
 *
 * @author system
 * @date 2023-04-03
 */
@ApiModel(description = "粮食安全考核-考核评分指标")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_score_index")
public class ScoreIndex implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "考核评分ID")
    private String scoreId;

    @ApiModelProperty(value = "考核模板指标ID")
    private String templateIndexId;

    @ApiModelProperty(value = "考核模板指标父ID")
    private String templateIndexParentId;

    @ApiModelProperty(value = "指标名称")
    private String name;

    @ApiModelProperty(value = "是否是题目 1是 0否")
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

    @ApiModelProperty(value = "缺项: 1启用 0禁用")
    private Boolean enable;

    @ApiModelProperty(value = "指标类型：1牵头 0配合")
    private Boolean type;

    @ApiModelProperty(value = "考核评审指标ID")
    private String reviewIndexId;

    @ApiModelProperty(value = "分配牵头部门ID")
    private Long allotLeadDeptId;

    @ApiModelProperty(value = "分配配合部门IDS")
    private String allotCooperateDeptIds;

    @ApiModelProperty(value = "分配指标类型：1牵头 0配合")
    private Boolean allotType;

    @ApiModelProperty(value = "考核分数")
    private BigDecimal assessScore;

    @ApiModelProperty(value = "考核减分原因")
    private String assessMinusCause;
}