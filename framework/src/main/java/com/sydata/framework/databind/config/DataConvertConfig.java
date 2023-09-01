package com.sydata.framework.databind.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhoucl
 * @date 2021/6/28 18:27
 */
@Getter
@Setter
public class DataConvertConfig {

    /**
     * 需要处理的包名前缀
     */
    private String[] convertPackage;
}
