package com.sydata.manage.param;

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
import java.time.LocalDateTime;

/**
 * @author lzq
 * @describe 通风作业分页参数
 * @date 2022-07-25 10:01
 */
@ApiModel(description = "粮库管理-通风作业分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VentilationPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id-通风作业单号")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "开始通风日期")
    private LocalDate beginTfrq;

    @ApiModelProperty(value = "结束通风日期")
    private LocalDate endTfrq;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
