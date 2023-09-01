package com.sydata.organize.interfaces.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lzq
 * @description 广东省统一身份登录账号详细信息VO
 * @date 2023/5/23 14:31
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(description = "广东省统一身份登录账号详细信息VO")
public class UserInfoDetailVo implements Serializable {

    @ApiModelProperty(value = "账号唯一标识")
    private String userId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "证件类型")
    private int certificateType;

    @ApiModelProperty(value = "证件号码")
    private String certificateNumber;

    @ApiModelProperty(value = "头像:Base64 的格式")
    private String image;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "重点用户所属行政区划代码")
    private String specialUserRegionCode;
}
