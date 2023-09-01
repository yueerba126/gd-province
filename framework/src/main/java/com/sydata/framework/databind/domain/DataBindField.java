package com.sydata.framework.databind.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 查询对象字段
 *
 * @author zhoucl
 * @date 2021/6/25 10:51
 */
@Data
@Accessors(chain = true)
public class DataBindField implements Serializable {
    /**
     * 属性名；放到el表达式里面作为字段
     */
    private String propertyName;

    /**
     * 对象字段名
     */
    private String fieldName;
    /**
     * 对应数据库列名
     */
    private String columnName;
}
