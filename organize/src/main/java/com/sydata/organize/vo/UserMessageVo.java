package com.sydata.organize.vo;

import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.organize.annotation.DataBindUser;
import com.sydata.organize.domain.UserMessage;
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
 * @description 组织架构-用户消息VO
 * @date 2023/3/2 15:22
 */
@ApiModel(description = "组织架构-用户消息VO")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserMessageVo extends UserMessage implements Serializable {

    @DataBindUser
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @DataBindOrganize
    @ApiModelProperty(value = "组织名称")
    private String organizeName;

    @DataBindDept
    @ApiModelProperty(value = "部门名称")
    private String deptName;
}
