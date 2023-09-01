package com.sydata.framework.util;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;

import java.util.Collection;
import java.util.List;

/**
 * @author lzq
 * @describe Bean操作
 * @date 2022-07-26 15:13
 */
@Slf4j
public final class BeanUtils extends org.springframework.beans.BeanUtils {


    /**
     * 根据class拷贝对象
     *
     * @param source           来源对象
     * @param classType        需要复制成什么对象
     * @param ignoreProperties 忽略的字段
     * @return classType 实例
     */
    public static <T> T copyByClass(Object source, Class<T> classType, String... ignoreProperties) {
        if (classType == null) {
            return null;
        }
        T newInstance = ReflectUtil.newInstanceIfPossible(classType);
        if (source != null) {
            copyProperties(source, newInstance, ignoreProperties);
        }
        return newInstance;
    }


    /**
     * 拷贝list
     *
     * @param sources          源列表
     * @param classType        目标类型
     * @param ignoreProperties 拷贝忽略
     * @return 目标列表
     */
    public static <T> List<T> copyToList(Collection<?> sources, Class<T> classType, String... ignoreProperties) {
        return StreamEx.of(sources)
                .map(t -> copyByClass(t, classType, ignoreProperties))
                .toList();
    }

    /**
     * 根据class拷贝对象Page
     *
     * @param page             page模型
     * @param classType        需要复制成什么对象
     * @param ignoreProperties 忽略的字段
     * @return Page 原page模型
     */
    public static <T> Page<T> copyToPage(Page page, Class<T> classType, String... ignoreProperties) {
        return page.setRecords(copyToList(page.getRecords(), classType, ignoreProperties));
    }
}
