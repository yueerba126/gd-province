package com.sydata.framework.core.util;

import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author lzq
 * @describe 树工具类
 * @date 2022-06-29 18:22
 */
public final class TreeUtils {


    /**
     * 列表转树
     *
     * @param list        列表
     * @param idFun       ID GET方法
     * @param parentIdFun 父ID GET方法
     * @param childSet    子节点 SET方法
     * @param rootId      根节点ID
     */
    public static <T, K> List<T> toTree(List<T> list,
                                        Function<T, K> idFun,
                                        Function<T, K> parentIdFun,
                                        BiConsumer<T, List<T>> childSet,
                                        K rootId) {
        return toParentIdMap(list, idFun, parentIdFun, childSet).get(rootId);
    }

    /**
     * 列表转父idMap
     *
     * @param list        列表
     * @param idFun       ID GET方法
     * @param parentIdFun 父ID GET方法
     * @param childSet    子节点 SET方法
     */
    public static <T, K> Map<K, List<T>> toParentIdMap(List<T> list,
                                                       Function<T, K> idFun,
                                                       Function<T, K> parentIdFun,
                                                       BiConsumer<T, List<T>> childSet) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        Map<K, List<T>> parentIdMap = StreamEx.of(list).groupingBy(parentIdFun);
        list.forEach(v -> {
            K id = idFun.apply(v);
            List<T> child = parentIdMap.getOrDefault(id, Collections.emptyList());
            childSet.accept(v, child);
        });
        return parentIdMap;
    }
}
