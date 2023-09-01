package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.safe.asess.domain.OrgAssessDeptIndex;
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
 * @description 粮食安全考核-部门考核指标树形VO
 * @date 2023/2/13 16:14
 */
@ApiModel(description = "粮食安全考核-部门考核指标树形VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrgAssessDeptIndexTreeVo extends OrgAssessDeptIndex implements Serializable {

    @DataBindOrganize(sourceField = "#leadDeptId")
    @ApiModelProperty(value = "牵头部门名称")
    private String leadDeptName;

    @DataBindOrganize(sourceField = "#cooperateDeptIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "配合部门名称S")
    private String cooperateDeptNames;

    @DataBindDept
    @ApiModelProperty(value = "自评部门名称")
    private String deptName;

    @ApiModelProperty(value = "子节点")
    private List<OrgAssessDeptIndexTreeVo> child;
}
