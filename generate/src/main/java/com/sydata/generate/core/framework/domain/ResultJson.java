package com.sydata.generate.core.framework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: multiple
 * @description:
 * @author: lzq
 * @create: 2023-06-17 23:10
 */
@Data
@ToString
@NoArgsConstructor
public class ResultJson implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public ResultJson(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
