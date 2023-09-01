package com.sydata.trade.param;

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
 * @describe 粮油购销-客户分页参数
 * @date 2022-07-22 19:19
 */
@ApiModel(description = "粮油购销-客户分页参数")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CustomerPageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "主键ID（由组织ID+客户统一社会信用代码组成）")
    private String id;

    @ApiModelProperty(value = "企业ID")
    private String enterpriseId;

    @ApiModelProperty(value = "客户名称")
    private String khmc;

    @ApiModelProperty(value = "客户统一信用代码")
    private String khtyshxydmhsfzh;

    @ApiModelProperty(value = "开始更新时间")
    private LocalDateTime beginUpdateTime;

    @ApiModelProperty(value = "结束更新时间")
    private LocalDateTime endUpdateTime;

}
