/**
 * @filename:GradedEnterpriseReviewPageParam 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.param;

import io.swagger.annotations.ApiModelProperty;
import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核-分页参数)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@ApiModel(value = "GradedEnterpriseReviewPageParam对象", description = "等级粮库评定管理-企业申报审核-分页参数")
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GradedEnterpriseReviewPageParam extends PageQueryParam implements Serializable {

    private static final long serialVersionUID = 1684750987869L;

    @ApiModelProperty(name = "id", value = "id")
    private String id;
    @ApiModelProperty(name = "sbnf", value = "申报年份")
    private String sbnf;
    @ApiModelProperty(name = "kdmc", value = "库点名称")
    private String kdmc;
    @ApiModelProperty(name = "qymc", value = "企业名称")
    private String qymc;
    @ApiModelProperty(name = "xzqhdm", value = "所在区域")
    private String xzqhdm;
    @ApiModelProperty(name = "sbzt", value = "申报状态")
    private String sbzt;
    @ApiModelProperty(name = "checkSbdj", value = "申报等级数据隔离")
    private String checkSbdj;
    @ApiModelProperty(name = "sbdj", value = "申报等级")
    private String sbdj;
    @ApiModelProperty(name = "beginSbrq", value = "开始申报日期")
    private String beginSbrq;
    @ApiModelProperty(name = "endSbrq", value = "结束申报日期")
    private String endSbrq;
    @ApiModelProperty(name = "spnf", value = "授牌年份")
    private String spnf;
    @ApiModelProperty(name = "spzt", value = "授牌状态")
    private String spzt;
    @ApiModelProperty(name = "commonStatus", value = "状态")
    private String commonStatus;
    @ApiModelProperty(name = "ids", value = "id集合")
    private List<String> ids;
}
