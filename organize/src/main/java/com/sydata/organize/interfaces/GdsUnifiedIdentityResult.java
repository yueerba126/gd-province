package com.sydata.organize.interfaces;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @author lzq
 * @description 广东省统一身份认证返回结果集
 * @date 2023/5/23 11:31
 */
@Data
public class GdsUnifiedIdentityResult<T> implements Serializable {

    @ApiModelProperty(value = "错误信息")
    private String message;

    @ApiModelProperty(value = "状态码 0:成功，非 0:失败")
    private int status;

    @ApiModelProperty(value = "响应体")
    private T data;


    /**
     * 检查并获取响应体
     *
     * @return 响应体
     */
    public T checkData() {
        Assert.state(status == 0, "广东省统一身份认证响应的错误信息:" + message);
        return data;
    }
}
