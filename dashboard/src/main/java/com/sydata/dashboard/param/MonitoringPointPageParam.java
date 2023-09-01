package com.sydata.dashboard.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author lzq
 * @describe 公共模块-分页查询参数基类
 * @date 2022-07-09 16:11
 */
@ApiModel(description = "公共模块-分页查询参数基类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitoringPointPageParam implements Serializable {

    @NotNull(message = "货位代码")
    @ApiModelProperty(value = "货位代码")
    private String hwdm;

    @ApiModelProperty(value = "当前页", example = "1")
    private int pageNum = 1;

    @ApiModelProperty(value = "每页数量", example = "10")
    private int pageSize = 10;
}
