package com.sydata.safe.asess.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.organize.annotation.DataBindRegion;
import com.sydata.safe.asess.domain.ReviewIndex;
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
 * @description 粮食安全考核-考核评审指标树形VO
 * @date 2023/4/3 16:36
 */
@ApiModel(description = "粮食安全考核-考核评审指标树形VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReviewIndexTreeVo extends ReviewIndex implements Serializable {

    @DataBindOrganize(sourceField = "#leadDeptId")
    @ApiModelProperty(value = "牵头部门名称")
    private String leadDeptName;

    @DataBindOrganize(sourceField = "#cooperateDeptIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "配合部门名称S")
    private String cooperateDeptNames;

    @DataBindDept(sourceField = "#allotLeadDeptId")
    @ApiModelProperty(value = "分配牵头部门名称")
    private String allotLeadDeptName;

    @DataBindDept(sourceField = "#allotCooperateDeptIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "分配配合部门名称S")
    private String allotCooperateDeptNames;

    @DataBindRegion(sourceField = "#lackRegionIds", valueBindStrategy = SEPARATED)
    @ApiModelProperty(value = "缺项行政区域名称S")
    private String lackRegionNames;

    @ApiModelProperty(value = "子节点")
    private List<ReviewIndexTreeVo> child;
}
