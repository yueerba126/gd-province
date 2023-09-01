package com.sydata.framework.core.global;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 公共全局的统一返回值
 *
 * @author lzq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "公共全局的统一返回值")
@Accessors(chain = true)
public class WebResult<T> implements Serializable {

    public static final WebResult SUCCESS = WebResult.success().setMsg("操作成功");

    @ApiModelProperty(value = "状态码", example = "200")
    private int code;

    @ApiModelProperty(value = "提示信息", example = "操作成功")
    private String msg;

    @ApiModelProperty(value = "返回内容")
    private T data;

    /**
     * 返回请求正常并且包含data
     *
     * @param data 返回内容
     * @return 公共全局的统一返回值
     */
    public static <T> WebResult<T> success(T data) {
        return new WebResult<>(HttpStatus.OK.value(), null, data);
    }

    /**
     * 返回请求正常并且不包含data一般用在编辑接口之类不需要返回data的情况
     *
     * @return 公共全局的统一返回值
     */
    public static WebResult success() {
        return success(null);
    }

    /**
     * 自定义错误码和错误信息
     *
     * @param code 错误状态码
     * @param msg  错误信息
     * @return 公共全局的统一返回值
     */
    public static WebResult<String> error(int code, String msg) {
        return new WebResult<>(code, msg, null);
    }

    /**
     * 自定义错误信息
     *
     * @param msg 错误信息
     * @return 公共全局的统一返回值
     */
    public static WebResult<String> error(String msg) {
        return new WebResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
    }
}
