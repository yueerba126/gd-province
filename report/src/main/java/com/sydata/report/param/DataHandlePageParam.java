package com.sydata.report.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 数据收集-数据处理分页param
 * @date 2022/10/11 17:49
 */
@ApiModel(description = "数据收集-数据处理分页param")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DataHandlePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "接口编号")
    private String apiCode;

    @ApiModelProperty(value = "创建时间（开始）")
    private LocalDateTime createTimeByBegin;

    @ApiModelProperty(value = "创建时间（结束）")
    private LocalDateTime createTimeByEnd;

    @ApiModelProperty(value = "处理状态 1已处理 0未处理")
    private Boolean handleState;

    @ApiModelProperty(value = "操作标志（i：新增(默认)，u：更新，d：删除）")
    private String czbz;

    @ApiModelProperty(value = "数据ID")
    private String dataId;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;
}
