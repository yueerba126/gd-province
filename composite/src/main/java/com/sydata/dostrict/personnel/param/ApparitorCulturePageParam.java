package com.sydata.dostrict.personnel.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author fuql
 * @describe 行政管理-人才培养计划分页参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "行政管理-人才培养计划分页参数")
public class ApparitorCulturePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "部门")
    private String deptId;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "文件标题")
    private String fileName;

    @ApiModelProperty(value = "开始创建时间")
    private LocalDate beginCreateTime;

    @ApiModelProperty(value = "结束创建时间")
    private LocalDate endCreateTime;

    @ApiModelProperty(value = "开始审核时间")
    private LocalDate beginApprovedTime;

    @ApiModelProperty(value = "结束审核时间")
    private LocalDate endApprovedTime;

}
