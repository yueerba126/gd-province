package com.sydata.framework.databind.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据源对象包裹
 *
 * @author zhoucl
 * @date 2021/8/31 16:36
 */
@Getter
@Setter
@AllArgsConstructor
public class DataBindSourceData implements Serializable {

    /**
     * 原始数据
     */
    private Object data;

    /**
     * 数据对应唯一编号
     */
    private String unKey;
}
