package com.sydata.framework.cache.util;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.SneakyThrows;
import one.util.streamex.StreamEx;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author pangbohuan
 * @date 2020/9/1 0001 11:44
 * @description 获取某个class字段map工具类
 */
public final class ClassFieldMapUtil {

    /**
     * 使用caffeine同步加载缓存将需要转换的目标类字段缓存起来,避免多次实例化对象
     * <p>
     * caffeine内存实现:
     * 使用弱引用存储键,当key没有其他引用时,缓存项可以被垃圾回收
     * 不必担心这部分内存会无限膨胀
     * 另外还有时间驱逐,容量驱逐两个回收策略
     */
    private static LoadingCache<Class, Map<String, Field>> classFieldMapCache = Caffeine.newBuilder()
            // 基于时间失效,写入之后开始计时失效,现在设置的是1小时
            .expireAfterWrite(1, TimeUnit.HOURS)
            // 缓存容量
            .maximumSize(500)
            // 同步加载-当get的时候没有值,就调用该方法获取值加入缓存再返回
            .build(k -> {
                List<Field> fieldsAll = new ArrayList<>();

                Class superClass = k;
                while (superClass != Object.class) {
                    // 获取这个类的所有字段转成list
                    List<Field> fields = new ArrayList<>(Arrays.asList(superClass.getDeclaredFields()));
                    // 排除静态字段
                    fields.removeIf(field -> Modifier.isStatic(field.getModifiers()));
                    // 这个类的所有字段都设置可见
                    fields.forEach(field -> field.setAccessible(Boolean.TRUE));
                    // 将这个类的字段组装起来
                    fieldsAll.addAll(fields);
                    // 转换成父类class
                    superClass = superClass.getSuperclass();
                }
                // 将字段list转成map,字段名作为key
                return StreamEx.of(fieldsAll).toMap(Field::getName, Function.identity());
            });

    private ClassFieldMapUtil() {
    }

    /**
     * 获取一个class的字段map
     *
     * @param classes 类对象
     * @return 这个类字段map<字段名, 字段对象>
     */
    public static Map<String, Field> mapByClass(Class classes) {
        return classFieldMapCache.get(classes);
    }

    /**
     * 获取字段属性的值
     *
     * @param obj   目标对象
     * @param field 属性
     * @return 属性的值
     */
    @SneakyThrows(Throwable.class)
    public static Object getFieldVal(Object obj, Field field) {
        return field.get(obj);
    }

    /**
     * 获取字段属性的值
     *
     * @param obj       目标对象
     * @param fieldName 属性名
     * @return 属性的值
     */
    @SneakyThrows(Throwable.class)
    public static Object getFieldVal(Object obj, String fieldName) {
        return getField(obj.getClass(), fieldName).get(obj);
    }

    /**
     * 设置字段属性的值
     *
     * @param obj       目标对象
     * @param fieldName 属性名
     * @param fileVal   属性值
     */
    @SneakyThrows(Throwable.class)
    public static void setFieldVal(Object obj, String fieldName, Object fileVal) {
        getField(obj.getClass(), fieldName).set(obj, fileVal);
    }

    /**
     * 设置字段属性的值
     *
     * @param obj     目标对象
     * @param field   属性字段
     * @param fileVal 属性值
     */
    @SneakyThrows(Throwable.class)
    public static void setFieldVal(Object obj, Field field, Object fileVal) {
        field.set(obj, fileVal);
    }


    /**
     * 获取字段属性
     *
     * @param cla       目标cla
     * @param fieldName 属性名
     * @return Field
     */
    @SneakyThrows(Throwable.class)
    public static Field getField(Class cla, String fieldName) {
        Field field = getFieldNotCheck(cla, fieldName);
        Assert.notNull(field, cla + "不存在该属性" + fieldName);
        return field;
    }

    /**
     * 获取字段属性
     *
     * @param cla      目标cla
     * @param fileName 属性名
     * @return Field
     */
    @SneakyThrows(Throwable.class)
    public static Field getFieldNotCheck(Class cla, String fileName) {
        return mapByClass(cla).get(fileName);
    }
}
