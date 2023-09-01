package com.sydata.organize.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE;
import static com.baomidou.mybatisplus.annotation.FieldStrategy.NOT_EMPTY;

/**
 * 组织架构-用户对象 org_user
 *
 * @author lzq
 * @date 2022-06-28
 */
@ApiModel(description = "组织架构-用户")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("org_user")
public class User implements Serializable {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @NotBlank(message = "登录账号不能为空")
    @ApiModelProperty(value = "登录账号")
    private String account;

    @TableField(updateStrategy = NOT_EMPTY)
    @NotBlank(message = "登录密码不能为空")
    @Size(min = 172, max = 172, message = "密码格式不正确")
    @ApiModelProperty(value = "登录密码")
    private String password;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @TableField(updateStrategy = NOT_EMPTY, fill = INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新者")
    private String updateBy;

    @TableField(fill = INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新时间")
    private LocalDateTime updateTime;

    @NotBlank(message = "组织不能为空")
    @ApiModelProperty(value = "组织ID")
    private String organizeId;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @TableField(updateStrategy = NOT_EMPTY, insertStrategy = NOT_EMPTY)
    @ApiModelProperty(value = "广东省统一身份认证用户ID")
    private String gdsUnifiedIdentityUserId;
}