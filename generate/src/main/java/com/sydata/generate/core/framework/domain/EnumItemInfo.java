package com.sydata.generate.core.framework.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @program: multiple
 * @description: 自定义枚举的信息
 * @author: lzq
 * @create: 2023-05-12 16:11
 */
@Data
@ToString
@NoArgsConstructor
public class EnumItemInfo implements Serializable {
    /**
     * @Description 枚举名
     **/
    private String enumName;
    /**
     * @Description 枚举值
     **/
    private String enumValue;

    public EnumItemInfo(String enumName, String enumValue) {
        this.enumName = enumName;
        this.enumValue = enumValue;
    }
}
