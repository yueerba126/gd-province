
package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 粮食安全考核-考核评分分组VO
 * @date 2023/4/3 16:54
 */
@ApiModel(description = "粮食安全考核-考核评分分组VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ScoreGroupVo implements Serializable {

    @ApiModelProperty(value = "考核模板ID")
    private String templateId;

    @ApiModelProperty(value = "考核模板名称")
    private String templateName;

    @ApiModelProperty(value = "考核模板年份")
    private String templateYear;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @ApiModelProperty(value = "考核区域总数")
    private Integer count;

    @ApiModelProperty(value = "待提交数量")
    private Integer awaitSubmitCount;

    @ApiModelProperty(value = "待评分数量")
    private Integer awaitScoreCount;

    @ApiModelProperty(value = "已评分数量")
    private Integer scoreCount;

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @DataBindDept
    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
