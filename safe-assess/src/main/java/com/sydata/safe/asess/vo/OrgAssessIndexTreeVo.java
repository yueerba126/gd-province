package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.safe.asess.domain.OrgAssessIndex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

import static com.sydata.framework.databind.handle.value.bind.ValueBindStrategy.SEPARATED;

/**
 * @author lzq
 * @description 粮食安全考核-考核指标树形VO
 * @date 2023/2/13 16:14
 */
@ApiModel(description = "粮食安全考核-考核指标树形VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrgAssessIndexTreeVo extends OrgAssessIndex implements Serializable {

    @DataBindOrganize(sourceField = "#leadDeptId")
    @ApiModelProperty(value = "牵头部门名称")
    private String leadDeptName;

    @DataBindOrganize(sourceField = "#cooperateDeptIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "配合部门名称S")
    private String cooperateDeptNames;

    @DataBindOrganize(sourceField = "#templateLeadDeptId")
    @ApiModelProperty(value = "考核模板牵头部门名称")
    private String templateLeadDeptName;

    @DataBindOrganize(sourceField = "#templateCooperateDeptIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "考核模板配合部门IDS")
    private String templateCooperateDeptNames;

    @DataBindDept(sourceField = "#allotLeadDeptId")
    @ApiModelProperty(value = "考核评分分配牵头部门名称S")
    private String allotLeadDeptName;

    @DataBindDept(sourceField = "#allotCooperateDeptIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "考核评分分配配合部门名称S")
    private String allotCooperateDeptNames;

    @ApiModelProperty(value = "子节点")
    private List<OrgAssessIndexTreeVo> child;
}
