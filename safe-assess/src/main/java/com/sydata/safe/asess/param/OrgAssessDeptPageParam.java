package com.sydata.safe.asess.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author lzq
 * @description 部门考核分页参数
 * @date 2023/2/13 15:12
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "部门考核分页参数")
public class OrgAssessDeptPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "配合部门填报截止日期（开始）")
    private LocalDate cooperateDeptEndDateBegin;

    @ApiModelProperty(value = "配合部门填报截止日期（结束）")
    private LocalDate cooperateDeptEndDateEnd;

    @ApiModelProperty(value = "模板编号")
    private String number;

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "单位考核ID")
    private String orgAssessId;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;
}
