package com.sydata.dostrict.casetarget.param;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 执法督察--执法案件查询参数
 *
 * @author fuql
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "执法督察--执法案件查询参数")
public class ApparitorCasePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "案件企业")
    private String caseEnterpriseId;

    @Excel(name = "案件名称")
    @ApiModelProperty(value = "案件名称")
    private String caseName;

    @Excel(name = "案件类型")
    @ApiModelProperty(value = "案件类型 字典：case_type")
    private String caseType;

    @ApiModelProperty(value = "开始案发时间")
    private LocalDateTime beginCaseDate;

    @ApiModelProperty(value = "结束案发时间")
    private LocalDateTime endCaseDate;

    @Excel(name = "案件来源")
    @ApiModelProperty(value = "案件来源 字典：case_source")
    private String caseSource;

}
