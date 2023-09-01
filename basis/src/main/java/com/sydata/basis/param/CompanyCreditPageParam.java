package com.sydata.basis.param;

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
 * @description 基础信息-企业信用信息分页参数
 * @date 2022/10/11 17:52
 */
@ApiModel(description = "基础信息-企业信用信息分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CompanyCreditPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID(单位代码)")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "信用等级")
    private String xydj;

    @ApiModelProperty(value = "评定年份")
    private String pdnf;

    @ApiModelProperty(value = "评定单位/机构名称")
    private String pddw;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
