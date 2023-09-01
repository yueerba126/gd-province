package com.sydata.grade.http;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @Author lzq
 * @Description 库软件接口统一接收对象
 * @Date 16:37 2023/5/29
 * @Param
 * @return
 **/
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class GradeResult<T> implements Serializable {

    @ApiModelProperty(value = "是否成功")
    private int code;

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "返回结果")
    private T data;
}
