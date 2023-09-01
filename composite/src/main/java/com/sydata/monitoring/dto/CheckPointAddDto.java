package com.sydata.monitoring.dto;

import com.sydata.common.param.PageQueryParam;
import com.sydata.monitoring.entity.CheckPoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author zhangcy
 * @since 2023/4/24 11:36
 */
@ApiModel(value = "监测点查询参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class CheckPointAddDto extends CheckPoint {

    @NotBlank(message = "监测点账号不能空")
    @ApiModelProperty(value = "监测点账号")
    private String account;

    @NotBlank(message = "监测点账号密码不能空")
    @ApiModelProperty(value = "监测点密码")
    private String password;

}
