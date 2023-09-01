package com.sydata.framework.orm;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.google.common.primitives.Ints;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.logging.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author lzq
 * @describe SQL 辅助类
 * @date 2022-10-27 14:10
 */
public final class FrameworkSqlHelper {

    private final static int BATCH_SIZE = 1000;

    /**
     * 执行批量操作
     *
     * @param service      实现类
     * @param list         数据集合
     * @param mapperMethod 执行的mapper方法
     * @return 操作结果
     */
    public static <E> List<BatchResult> executeBatchUpdate(IService service,
                                                           List<E> list,
                                                           MethodConsumer<E> mapperMethod) {
        return executeBatchUpdate(service, list, mapperMethod, BATCH_SIZE);
    }

    /**
     * 执行批量操作
     *
     * @param service       实现类
     * @param list          数据集合
     * @param mapperMethod  执行的mapper方法
     * @param failsConsumer 回调执行失败数据集合
     */
    public static <E> void executeBatchUpdate(IService service,
                                              List<E> list,
                                              MethodConsumer<E> mapperMethod,
                                              Consumer<List<E>> failsConsumer) {
        executeBatchUpdate(service, list, mapperMethod, failsConsumer, BATCH_SIZE);
    }

    /**
     * 执行批量操作
     *
     * @param service      实现类
     * @param list         数据集合
     * @param mapperMethod 执行的mapper方法
     * @return 操作结果
     */
    public static <E> List<BatchResult> executeBatchUpdate(IService service,
                                                           List<E> list,
                                                           MethodConsumer<E> mapperMethod,
                                                           int batchSize) {
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        Log log = (Log) ClassFieldMapUtil.getFieldVal(service, "log");
        Class mapperClass = (Class) ClassFieldMapUtil.getFieldVal(service, "mapperClass");
        String sqlStatement = mapperClass.getName() + StringPool.DOT + mapperMethod.getMethodName();

        List<BatchResult> batchResults = new ArrayList<>();
        SqlHelper.executeBatch(service.getEntityClass(), log, sqlSession -> {
            int size = list.size();
            int i = 1;
            for (E element : list) {
                sqlSession.update(sqlStatement, element);
                if ((i % batchSize == 0) || i == size) {
                    batchResults.addAll(sqlSession.flushStatements());
                }
                i++;
            }
        });
        return batchResults;
    }

    /**
     * 执行批量操作
     *
     * @param service       实现类
     * @param list          数据集合
     * @param mapperMethod  执行的mapper方法
     * @param failsConsumer 回调执行失败数据集合
     */
    public static <E> void executeBatchUpdate(IService service,
                                              List<E> list,
                                              MethodConsumer<E> mapperMethod,
                                              Consumer<List<E>> failsConsumer,
                                              int batchSize) {
        List<BatchResult> batchResults = executeBatchUpdate(service, list, mapperMethod, batchSize);
        if (CollectionUtils.isEmpty(batchResults)) {
            return;
        }

        List<Integer> executeRows = StreamEx.of(batchResults)
                .map(BatchResult::getUpdateCounts)
                .map(Ints::asList)
                .flatMap(Collection::stream)
                .toList();

        List<E> fails = new ArrayList<>();
        for (int i = 0; i < executeRows.size(); i++) {
            if (executeRows.get(i) <= 0) {
                fails.add(list.get(i));
            }
        }

        if (CollectionUtils.isNotEmpty(fails)) {
            failsConsumer.accept(fails);
        }
    }
}
