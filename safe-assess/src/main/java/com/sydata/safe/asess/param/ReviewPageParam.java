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
 * @description 考核评审分页参数
 * @date 2023/4/3 16:51
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核评审分页参数")
public class ReviewPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "考核模板ID")
    private String templateId;

    @ApiModelProperty(value = "考核模板名称")
    private String templateName;

    @ApiModelProperty(value = "考核模板年份")
    private String templateYear;

    @ApiModelProperty(value = "分配日期（开始）")
    private LocalDate allotDateBegin;

    @ApiModelProperty(value = "分配日期（结束）")
    private LocalDate allotDateEnd;

    @ApiModelProperty(value = "自动分配：1是 0否")
    private Boolean autoAllot;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "单位考核ID")
    private String orgAssessId;
}
