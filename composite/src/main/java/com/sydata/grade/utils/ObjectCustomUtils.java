package com.sydata.grade.utils;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.lang.reflect.Field;

/**
 * @program: gd-province-platform
 * @description:
 * @author: lzq
 * @create: 2023-05-18 12:22
 */
public class ObjectCustomUtils {
    /**
     * @return boolean
     * @Description 判断一个对象的所有元素是否都是空
     * @Date 14:15 2023/5/18
     * @Param [o]
     **/
    public static boolean allfieldIsNUll(Object o) {
        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                //把私有属性公有化
                field.setAccessible(true);
                Object object = field.get(o);
                if (object instanceof CharSequence) {
                    if (!ObjectUtils.isEmpty(object)) {
                        return false;
                    }
                } else {
                    if (!ObjectUtils.isNull(object)) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
