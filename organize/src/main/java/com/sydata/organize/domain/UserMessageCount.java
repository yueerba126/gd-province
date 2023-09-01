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

/**
 * 组织架构-用户消息数量对象 org_user_message_count
 *
 * @author system
 * @date 2023-03-02
 */
@ApiModel(description = "组织架构-用户消息数量")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_user_message_count")
public class UserMessageCount implements Serializable {

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;

    @ApiModelProperty(value = "消息总数")
    private Integer totalCount;

    @ApiModelProperty(value = "已读总数")
    private Integer readCount;

    @ApiModelProperty(value = "未读总数")
    private Integer unreadCount;
}