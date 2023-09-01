package com.sydata.generate.core.framework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: multiple
 * @description: 自定义方法的信息
 * @author: lzq
 * @create: 2023-05-16 19:24
 */
@Data
@ToString
@NoArgsConstructor
public class MethodInfo implements Serializable {

    /**
     * @Description 方法名称
     **/
    private String methodName;
    /**
     * @Description 方法的返回类型
     **/
    private String returnType;
    /**
     * @Description 方法的描述
     **/
    private String comment;
    /**
     * @Description 要绑定的注解
     **/
    private String bindAnnotation;

    public MethodInfo(String methodName, String returnType, String comment, String bindAnnotation) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.comment = comment;
        this.bindAnnotation = bindAnnotation;
    }
}
