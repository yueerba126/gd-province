package com.sydata.generate.core.framework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: multiple
 * @description: 字段
 * @author: lzq
 * @create: 2023-06-17 22:55
 */
@Data
@ToString
@NoArgsConstructor
public class PropertyInfo implements Serializable {

    private Integer id;
    private String column;
    private String jdbcType;
    private String comment;
    private String property;
    private String javaType;
    private String columnAccuracy;
    private String isNullable;
    /**
     * @Description 参数查询方式(参考wapper的函数)
     **/
    private String paramSelectType;
    /**
     * @Description 要绑定的注解
     **/
    private String bindAnnotation;
    /**
     * @Description 要绑定的字典类型
     **/
    private String bindSourceFieldCombination;

    public PropertyInfo(String property, String bindAnnotation, String bindSourceFieldCombination) {
        this.property = property;
        this.bindAnnotation = bindAnnotation;
        this.bindSourceFieldCombination = bindSourceFieldCombination;
    }
    public PropertyInfo(Integer id, String column, String jdbcType, String comment, String property, String javaType, String columnAccuracy, String isNullable) {
        this.id = id;
        this.column = column;
        this.jdbcType = jdbcType;
        this.comment = comment;
        this.property = property;
        this.javaType = javaType;
        this.columnAccuracy = columnAccuracy;
        this.isNullable = isNullable;
    }
    public PropertyInfo(Integer id, String column, String jdbcType, String comment, String property, String javaType, String columnAccuracy, String isNullable, String paramSelectType, String bindAnnotation, String bindSourceFieldCombination) {
        this.id = id;
        this.column = column;
        this.jdbcType = jdbcType;
        this.comment = comment;
        this.property = property;
        this.javaType = javaType;
        this.columnAccuracy = columnAccuracy;
        this.isNullable = isNullable;
        this.paramSelectType = paramSelectType;
        this.bindAnnotation = bindAnnotation;
        this.bindSourceFieldCombination = bindSourceFieldCombination;
    }
}
