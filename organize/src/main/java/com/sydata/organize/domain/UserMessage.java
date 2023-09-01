package com.sydata.organize.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * 组织架构-用户消息对象 org_user_message
 *
 * @author system
 * @date 2023-03-02
 */
@ApiModel(description = "组织架构-用户消息")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_user_message")
public class UserMessage implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息状态：0未读、1已读")
    private Boolean state;

    @ApiModelProperty(value = "消息类型：1系统消息、2业务消息")
    private Integer type;

    @ApiModelProperty(value = "目标类型：1部门、2角色、3用户")
    private Integer targetType;

    @ApiModelProperty(value = "目标ID(由目标类型决定)")
    private String targetId;

    @ApiModelProperty(value = "目标名称")
    private String targetName;

    @ApiModelProperty(value = "功能模块")
    private String module;

    @ApiModelProperty(value = "路由地址")
    private String route;

    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "已读时间")
    private LocalDateTime readTime;
}