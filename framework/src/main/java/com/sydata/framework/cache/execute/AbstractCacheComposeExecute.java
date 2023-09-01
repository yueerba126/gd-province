package com.sydata.framework.cache.execute;

import com.sydata.framework.cache.batch.IMultiCacheBatch;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import com.sydata.framework.cache.util.FieldNameFunction;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Supplier;

/**
 * @author lzq
 * @describe 多级缓存组合操作执行器-抽象基类
 * @date 2022-06-10 09:38
 */
public abstract class AbstractCacheComposeExecute<P, M, V> {

    protected String eq = "=";

    protected String join = "&";

    protected boolean inline;

    protected IMultiCacheBatch multiCacheBatch;

    protected FieldNameFunction<M, Object>[] fields;

    protected Collection<P> params;

    protected Object[] paddings;

    public AbstractCacheComposeExecute<P, M, V> multiCacheBatch(IMultiCacheBatch multiCacheBatch) {
        this.multiCacheBatch = multiCacheBatch;
        return this;
    }

    public AbstractCacheComposeExecute<P, M, V> inline() {
        this.inline = Boolean.TRUE;
        return this;
    }

    public AbstractCacheComposeExecute<P, M, V> fields(FieldNameFunction<M, Object>... fields) {
        this.fields = fields;
        return this;
    }

    public AbstractCacheComposeExecute<P, M, V> params(Collection<P> params) {
        this.params = params;
        return this;
    }

    public AbstractCacheComposeExecute<P, M, V> paddings(Object... paddings) {
        this.paddings = paddings;
        return this;
    }


    /**
     * 查询缓存
     *
     * @return 缓存列表
     */
    public abstract Collection<V> get();

    /**
     * 设置缓存
     *
     * @param kvSupplier 缓存键值对
     * @return 缓存键值对
     */
    public abstract Map<String, V> put(Supplier<Map<P, V>> kvSupplier);

    /**
     * 删除
     */
    public abstract void remove();


    /**
     * 获取字段名数组
     *
     * @return 字段值数组
     */
    protected String[] fieldNames() {
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getFieldName();
            fieldNames[i] = fieldName;
        }
        return fieldNames;
    }

    /**
     * 获取字段值数组
     *
     * @param param      对象
     * @param fieldNames 字段名数组
     * @return 字段值数组
     */
    protected Object[] fieldValues(P param, String[] fieldNames) {
        Object[] fieldValues = new Object[fieldNames.length];
        int length = fieldNames.length - (paddings != null ? paddings.length : 0);

        // 判断是否是内联对象，说白了就是需要去GET某个属性才能拿到值的对象
        if (inline) {
            Map<String, Field> fieldMap = ClassFieldMapUtil.mapByClass(param.getClass());
            for (int i = 0; i < length; i++) {
                String fieldName = fieldNames[i];
                Field field = fieldMap.get(fieldName);
                Object fieldValue = ReflectionUtils.getField(field, param);
                fieldValues[i] = fieldValue;
            }
        } else {
            fieldValues[0] = param;
        }

        // 字段值补位
        if (paddings != null) {
            for (int i = 0; i < paddings.length; i++) {
                fieldValues[length + i] = paddings[i];
            }
        }
        return fieldValues;
    }

    /**
     * 拼接key
     *
     * @param fieldNames  字段名数组
     * @param fieldValues 字段值数组
     * @return key
     */
    protected String joinKey(String[] fieldNames, Object[] fieldValues) {
        StringJoiner key = new StringJoiner(join);
        int fieldSize = fieldNames.length;
        for (int i = 0; i < fieldSize; i++) {
            String fieldName = fieldNames[i];
            Object fieldValue = fieldValues[i];
            key.add(fieldName + eq + fieldValue);
        }
        return key.toString();
    }
}
