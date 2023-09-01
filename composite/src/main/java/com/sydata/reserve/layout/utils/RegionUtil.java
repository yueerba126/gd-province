package com.sydata.reserve.layout.utils;

/**
 * 行政区划代码处理
 */
public class RegionUtil {

    /**
     * 处理市或区县查询条件
     */
    public  static String regionQuery(String code){
        if (code == null){
            return null;
        }
        if (code.endsWith("00")){
            //市
            return code.substring(0,4);
        }else {
            return code;
        }
    }
}
