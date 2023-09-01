package com.sydata.organize.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lzq
 * @description 用户消息发送参数
 * @date 2023/3/2 15:20
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "用户消息发送参数")
public class UserMessageSendParam implements Serializable {

    @NotBlank(message = "消息内容必填")
    @ApiModelProperty(value = "消息内容")
    private String content;

    @NotNull(message = "消息类型不能为空")
    @ApiModelProperty(value = "消息类型：1系统消息、2业务消息")
    private Integer type;

    @NotNull(message = "目标类型不能为空")
    @ApiModelProperty(value = "目标类型：1部门、2角色、3用户")
    private Integer targetType;

    @NotEmpty(message = "目标ID不能为空")
    @ApiModelProperty(value = "目标ID(由目标类型决定)")
    private String targetId;

    @NotEmpty(message = "组织ID不能为空")
    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @NotEmpty(message = "目标名称不能为空")
    @ApiModelProperty(value = "目标名称")
    private String targetName;

    @NotEmpty(message = "功能模块不能为空")
    @ApiModelProperty(value = "功能模块")
    private String module;

    @ApiModelProperty(value = "路由地址")
    private String route;
}
