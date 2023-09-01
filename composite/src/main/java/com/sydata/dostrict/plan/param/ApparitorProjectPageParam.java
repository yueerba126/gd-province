package com.sydata.dostrict.plan.param;

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
 * @author lzq
 * @describe 行政管理模块--企业人员分页参数
 * @date 2022-06-30 10:40
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "规划建设-企业人员-分页参数")
public class ApparitorProjectPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    @ApiModelProperty(value = "库区代码")
    private String kqdm;

    @ApiModelProperty(value = "行政区划代码")
    private String xzqhdm;

    @ApiModelProperty(value = "操作标志")
    private String czbz;

    @ApiModelProperty(value = "开始申报日期")
    private LocalDate beginSbrq;

    @ApiModelProperty(value = "结束申报日期")
    private LocalDate endSbrq;
}
