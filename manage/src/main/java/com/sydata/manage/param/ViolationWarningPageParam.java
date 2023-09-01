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
import java.time.LocalDateTime;

/**
 * @author lzq
 * @describe 粮库管理-违规预警信息分页参数
 * @date 2022-07-25 14:30
 */
@ApiModel(description = "粮库管理-违规预警信息分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ViolationWarningPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID（库区代码+日期8位年月日+顺序码3位）")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "开始预警发布时间")
    private LocalDateTime beginFbsj;

    @ApiModelProperty(value = "结束预警发布时间")
    private LocalDateTime endFbsj;

    @ApiModelProperty(value = "违规单位代码（统一社会信用代码）")
    private String qydm;

    @ApiModelProperty(value = "违规类型")
    private String wglx;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
