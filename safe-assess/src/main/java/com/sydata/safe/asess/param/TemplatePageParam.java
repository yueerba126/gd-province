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
 * @description 考核模板分页参数
 * @date 2023/2/13 15:12
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "考核模板分页参数")
public class TemplatePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "发布日期（开始）")
    private LocalDate pushDateBegin;

    @ApiModelProperty(value = "发布日期（结束）")
    private LocalDate pushDateEnd;

    @ApiModelProperty(value = "模板编号")
    private String number;

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "行政区域ID")
    private String regionId;
}
