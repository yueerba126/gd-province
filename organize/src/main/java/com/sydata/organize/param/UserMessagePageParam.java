package com.sydata.organize.param;

import com.sydata.common.param.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 用户消息分页参数
 * @date 2023/3/2 15:20
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "用户消息分页参数")
public class UserMessagePageParam extends PageQueryParam implements Serializable {

    @ApiModelProperty(value = "消息状态：0未读、1已读")
    private Boolean state;

    @ApiModelProperty(value = "消息类型：1系统消息、2业务消息")
    private Integer type;

    @ApiModelProperty(value = "目标类型：1部门、2角色、3用户")
    private Integer targetType;

    @ApiModelProperty(value = "功能模块")
    private String module;
}
