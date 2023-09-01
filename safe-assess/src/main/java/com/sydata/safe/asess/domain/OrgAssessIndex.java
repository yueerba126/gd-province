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
 * 粮食安全考核-单位考核指标对象 safe_assess_org_assess_index
 *
 * @author system
 * @date 2023-02-16
 */
@ApiModel(description = "粮食安全考核-单位考核指标")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("safe_assess_org_assess_index")
public class OrgAssessIndex implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "单位考核ID")
    private String orgAssessId;

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

    @ApiModelProperty(value = "缺项: 1启用 0禁用")
    private Boolean enable;

    @ApiModelProperty(value = "考核模板牵头部门ID")
    private Long templateLeadDeptId;

    @ApiModelProperty(value = "考核模板配合部门IDS")
    private String templateCooperateDeptIds;

    @ApiModelProperty(value = "考核评审分配牵头部门ID")
    private Long allotLeadDeptId;

    @ApiModelProperty(value = "考核评审分配配合部门IDS")
    private String allotCooperateDeptIds;

    @ApiModelProperty(value = "牵头部门ID")
    private Long leadDeptId;

    @ApiModelProperty(value = "配合部门IDS")
    private String cooperateDeptIds;

    @ApiModelProperty(value = "自评分数")
    private BigDecimal orgScore;

    @ApiModelProperty(value = "自评说明")
    private String orgIllustrate;

    @ApiModelProperty(value = "减分原因")
    private String minusCause;

    @ApiModelProperty(value = "整改措施")
    private String measure;

    @ApiModelProperty(value = "佐证材料文件IDS")
    private String fileIds;

    @ApiModelProperty(value = "佐证材料文件名称S")
    private String fileNames;

    @ApiModelProperty(value = "考核分数")
    private BigDecimal assessScore;

    @ApiModelProperty(value = "考核减分原因")
    private String assessMinusCause;

    @ApiModelProperty(value = "抽查分数")
    private BigDecimal checkScore;

    @ApiModelProperty(value = "抽查说明")
    private String checkRemark;

    @ApiModelProperty(value = "抽查文件IDS")
    private String checkFileIds;

    @ApiModelProperty(value = "抽查文件名称S")
    private String checkFileNames;
}