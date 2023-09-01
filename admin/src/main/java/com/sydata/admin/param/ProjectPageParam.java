package com.sydata.admin.param;

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
 * @describe 行政管理-项目信息分页参数
 * @date 2022-07-25 16:49
 */
@ApiModel(description = "行政管理-项目信息分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProjectPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "id(项目代码)")
    private String id;

    @ApiModelProperty(value = "法定代表人证照号码")
    private String fddbrzzhm;

    @ApiModelProperty(value = "建设状态 1已立项未开工 2建设中 3已验收 4已取消")
    private String jszt;

    @ApiModelProperty(value = "年份如2022")
    private String nf;

    @ApiModelProperty(value = "拟开工时间")
    private LocalDate nkgsj;

    @ApiModelProperty(value = "拟建成时间")
    private LocalDate njcsj;

    @ApiModelProperty(value = "项目名称")
    private String xmmc;

    @ApiModelProperty(value = "项目代码")
    private String xmdm;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;
}
