package com.sydata.reserve.http;

import lombok.Data;

import java.io.Serializable;


/**
 * 库软件接口统一接收对象
 *
 * @author fuql
 * @date 2021/7/23 17:15
 */
@Data
public class DistributionResult<T> implements Serializable {

    private String msg;

    private T data;

    private int code;
}
