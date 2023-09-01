package com.sydata.framework.databind.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import com.sydata.framework.databind.annotation.DataBindFieldConfig;
import com.sydata.framework.databind.config.DataBindConfig;
import com.sydata.framework.databind.config.DataBindConstants;
import com.sydata.framework.databind.domain.DataBindItem;
import com.sydata.framework.databind.domain.DataBindObject;
import com.sydata.framework.databind.service.DataBindParamInterceptor;
import com.sydata.framework.databind.service.IDataBindService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotationSelectors;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 数据绑定解析类
 *
 * @author zhoucl
 * @date 2021/4/21 14:11
 */
@Slf4j
@Component
public class DataBindHandleBootstrap {
    /**
     * 那些包名需要做转换
     */
    private static String[] CONVERT_PACKAGE = new String[]{"com.sydata.", "com.baomidou.mybatisplus."};

    /**
     * 用于springEL表达式的解析
     */
    private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * 解析过的数据转换类
     */
    private static final Map<Class<?>, List<DataBindItem>> GLOBAL_DATA_BIND_CONVERT_MAP = new HashMap<>();

    /**
     * 类的那些字段需要继续遍历
     */
    private static final Map<Class<?>, List<Field>> GLOBAL_ITERATOR_MAP = new HashMap<>();

    public static List<DataBindParamInterceptor> dataBindParamInterceptors;

    /**
     * 存储不需要数据转换Class
     */
    private static final Set<Class<?>> GLOBAL_EXCLUDE = new HashSet<>();

    private static IDataBindService dataBindService;

    /**
     * 缓存本次处理数据
     */
    public final static ThreadLocal<Map<String, Object>> THREAD_LOCAL_CACHE = ThreadLocal.withInitial(() -> {
        // 初始化缓存对象,并设置登录组织id
        Map<String, Object> cacheMap = new HashMap<>(64);
        for (DataBindParamInterceptor dataBindParamInterceptor : dataBindParamInterceptors) {
            dataBindParamInterceptor.apply(cacheMap);
        }
        return cacheMap;
    });

    /**
     * 类解析缓存--标识哪些类被解析过（解决循环引用问题）
     */
    private static final ThreadLocal<Set<Integer>> SCANNING_OBJECT_THREAD_LOCAL = ThreadLocal.withInitial(HashSet::new);

    public DataBindHandleBootstrap(IDataBindService dataBindService,
                                   DataBindConfig dataBindConfig,
//                                   ITokenService tokenService,
                                   List<DataBindParamInterceptor> dataBindParamInterceptors) {
        DataBindHandleBootstrap.dataBindService = dataBindService;
        CONVERT_PACKAGE = dataBindConfig.getConvert().getConvertPackage();
        DataBindHandleBootstrap.dataBindParamInterceptors = dataBindParamInterceptors;
    }

    /**
     * 注解是否支持数据绑定
     *
     * @param annotation 注解
     * @return 注解是否支持数据绑定
     */
    public static boolean supportDataBindAnnotation(Annotation annotation) {
        return dataBindService.supportDataBindAnnotation(annotation.annotationType());
    }

    /**
     * 是否在没有具体实现方案扩展注解里面
     *
     * @param annotation 注解
     * @return 注解是否支持数据绑定
     */
    public static boolean supportExtendStrategyAnnotations(Annotation annotation) {
        return dataBindService.supportExtendStrategyAnnotations(annotation.annotationType());
    }

    public static void dataHandConvert(Object object) {
        dataHandConvert(object, 1);
    }

    public static void dataHandConvert(Object... object) {
        doDataHandConvert(Arrays.asList(object), 1);
    }

    public static void dataHandConvert(Object object, int convertNumber) {
        doDataHandConvert(Collections.singletonList(object), convertNumber);
    }

    /**
     * 开始数据转换
     *
     * @param objects       转换对象
     * @param convertNumber 转换次数
     */
    public static void doDataHandConvert(List<Object> objects, int convertNumber) {
        if (CollectionUtils.isEmpty(objects)) {
            return;
        }

        try {
            // 递归队列
            Queue<Object> myQueue = new LinkedList<>();
            // 过滤掉不符合条件的对象
            StreamEx.of(objects)
                    .filter(object -> Objects.nonNull(object) && !isPrimitive(object.getClass()))
                    .forEach(myQueue::add);
            // 存储有数据转换Class value:Class对应哪些字段有配置转换规则
            Map<Class<?>, DataBindObject> dataBindObjectMap = new HashMap<>(16);
            while (!myQueue.isEmpty()) {
                Object item = myQueue.poll();

                if (isSupportConvert(item)) {
                    int hashCode = System.identityHashCode(item);
                    Set<Integer> hashCodes = SCANNING_OBJECT_THREAD_LOCAL.get();
                    if (hashCodes.contains(hashCode)) {
                        continue;
                    }

                    Class<?> itemClass = item.getClass();
                    DataBindObject dataBindObject = null;
                    // 获取class缓存的方案
                    List<DataBindItem> dataBindItems = GLOBAL_DATA_BIND_CONVERT_MAP.get(itemClass);
                    // 缓存已经方案直接使用,不需要再次解析
                    if (dataBindItems != null) {
                        dataBindObject = dataBindObjectMap.get(itemClass);
                        if (dataBindObject == null) {
                            dataBindObject = new DataBindObject().setItems(dataBindItems);
                            dataBindObjectMap.put(itemClass, dataBindObject);
                        }
                    } else if (!GLOBAL_EXCLUDE.contains(itemClass)) {
                        // 没有已有方案，做解析
                        // 遍历解析字段配置
                        Collection<Field> fields = ClassFieldMapUtil.mapByClass(itemClass).values();
                        for (Field field : fields) {
                            // 解析字段是否有数据绑定注解
                            List<DataBindItem> fieldDataBindItems = findDataBindAnnotation(field, itemClass);
                            if (CollectionUtils.isNotEmpty(fieldDataBindItems)) {
                                // 还没有配置则初始化
                                if (dataBindObject == null) {
                                    dataBindObject = new DataBindObject();
                                    dataBindObjectMap.put(itemClass, dataBindObject);
                                }
                                // 字段添加映射规则
                                dataBindObject.addDataBindItems(fieldDataBindItems);
                            }
                        }
                        // 读取到，加入全局缓存
                        if (dataBindObject != null) {
                            GLOBAL_DATA_BIND_CONVERT_MAP.put(itemClass, dataBindObject.getItems());
                        } else {
                            // 全部字段读取完没有方案加入排除列表
                            GLOBAL_EXCLUDE.add(itemClass);
                        }
                    }

                    // 对象字段遍历找出需要加入队列的字段
                    forFieldAddQueue(myQueue, item);

                    // 有解析规则加入到查询对象
                    if (dataBindObject != null) {
                        dataBindObject.addData(item);
                    }
                    hashCodes.add(hashCode);
                } else if (item instanceof Collection) {
                    // 集合直接加入到解析队列
                    myQueue.addAll(((Collection<?>) item));
                } else if (item instanceof Map) {
                    Map<?, ?> map = (Map<?, ?>) item;
                    if (!map.isEmpty()) {
                        map.forEach((key, value) -> {
                            // 排除基本对象
                            if (Objects.nonNull(key) && !isPrimitive(key.getClass())) {
                                myQueue.add(key);
                            }
                            if (Objects.nonNull(value) && !isPrimitive(value.getClass())) {
                                myQueue.add(value);
                            }
                        });
                    }
                }
            }
            dataBindService.dataBind(dataBindObjectMap.values(), convertNumber);
        } finally {
            THREAD_LOCAL_CACHE.remove();
            SCANNING_OBJECT_THREAD_LOCAL.remove();
        }
    }

    /**
     * 获取当前线程组织id
     *
     * @return 组织id
     */
    public static Long getCurrentThreadTenantId() {
        Map<String, Object> cacheMap = THREAD_LOCAL_CACHE.get();
        if (cacheMap != null) {
            Object tenantIdObj = cacheMap.get(DataBindConstants.BILL_CENTER_TENANT_ID_COLUMN);
            if (Objects.nonNull(tenantIdObj)) {
                return (Long) tenantIdObj;
            }
        }
        return null;
    }

    /**
     * 对象字段遍历找出需要加入队列的字段
     *
     * @param myQueue 遍历队列
     * @param item    源对象
     */
    @SneakyThrows(Throwable.class)
    private static void forFieldAddQueue(Queue<Object> myQueue, Object item) {
        if (Objects.nonNull(item)) {
            Collection<Field> fields = GLOBAL_ITERATOR_MAP.get(item.getClass());
            List<Field> cacheFields = null;
            if (fields == null) {
                // 没有缓存过该类需要获取全部字段
                cacheFields = new ArrayList<>();
                fields = ClassFieldMapUtil.mapByClass(item.getClass()).values();
            }
            // 字段如果不是基本类型,加入递归队列
            if (CollectionUtils.isNotEmpty(fields)) {
                for (Field field : fields) {
                    // 不是基本类才做处理
                    if (!isPrimitive(field.getType())) {
                        // 加入缓存列表
                        if (cacheFields != null) {
                            cacheFields.add(field);
                        }

                        // 对象字段遍历找出需要加入队列的字段
                        Object fieldValue = field.get(item);
                        int hashCode = System.identityHashCode(fieldValue);
                        if (Objects.nonNull(fieldValue) && !SCANNING_OBJECT_THREAD_LOCAL.get().contains(hashCode)) {
                            myQueue.add(fieldValue);
                        }
                    }
                }
            }
            // 不为空则缓存
            if (cacheFields != null) {
                GLOBAL_ITERATOR_MAP.put(item.getClass(), cacheFields);
            }
        }
    }

    /**
     * 通过el表达式获取对象属性值
     *
     * @param o       源对象
     * @param unKey   对象唯一key
     * @param fieldEl el表达式
     * @return el表达式结果
     */
    public static Object getFieldValue(Object o, String unKey, String fieldEl) {
        return getFieldValue(o, unKey, fieldEl, null);
    }

    /**
     * 通过el表达式获取对象属性值
     *
     * @param o        源对象
     * @param unKey    对象缓存唯一key
     * @param fieldEl  el表达式
     * @param consumer el表达式赋值变量对象回调
     * @return el表达式结果
     */
    public static Object getFieldValue(Object o, String unKey, String fieldEl, Consumer<EvaluationContext> consumer) {
        if (Objects.nonNull(o)) {
            // 同一个对象缓存EvaluationContext  经测试不缓存在同一对象很多次取值时会有60毫秒左右消耗
            Map<String, Object> cacheMap = THREAD_LOCAL_CACHE.get();
            Object ec = cacheMap.get(getUnKey(o, unKey));
            EvaluationContext evaluationContext;
            if (ec == null) {
                evaluationContext = new StandardEvaluationContext();
                ClassFieldMapUtil.mapByClass(o.getClass()).forEach((fieldName, field) -> {
                    try {
                        evaluationContext.setVariable(fieldName, field.get(o));
                    } catch (Exception e) {
                        log.error(String.format("值异常；字段：%s，数据源：%s"
                                , fieldName
                                , JSON.toJSONString(o)));
                    }
                });
                evaluationContext.setVariable(DataBindConstants.DATA_THIS, o);
                cacheMap.put(unKey, evaluationContext);
            } else {
                evaluationContext = (EvaluationContext) ec;
            }
            if (consumer != null) {
                consumer.accept(evaluationContext);
            }
            try {
                return SPEL_EXPRESSION_PARSER.parseExpression(fieldEl)
                        .getValue(evaluationContext);
            } catch (Exception e) {
                log.error(String.format("值异常；表达式：%s，数据源：%s"
                        , fieldEl
                        , JSON.toJSONString(o)));
                return null;
            }
        }
        return null;
    }

    /**
     * 给对象字段赋值
     *
     * @param o          源对象
     * @param unKey      对象缓存唯一key
     * @param value      本次值
     * @param valueField 赋值字段
     */
    public static void setValueField(Object o, String unKey, Object value, Field valueField) {
        try {
            if (o == null || value == null || valueField == null) {
                return;
            }
            valueField.set(o, TypeUtils.cast(value, valueField.getType(), ParserConfig.getGlobalInstance()));
            // 赋值后把新值设置到el对象
            Map<String, Object> cacheMap = THREAD_LOCAL_CACHE.get();
            Object ec = cacheMap.get(getUnKey(o, unKey));
            if (ec != null) {
                ((EvaluationContext) ec).setVariable(valueField.getName(), value);
            }
        } catch (Exception e) {
            log.error(String.format("基础数据转换赋值异常；字段：%s，数据源：%s"
                    , valueField.getName()
                    , value));
        }
    }

    private static String getUnKey(Object o, String unKey) {
        return StringUtils.isNotEmpty(unKey) ? unKey : String.format("%s-%s", o.getClass().getName(), o.hashCode());
    }

    /**
     * 判断是否需要转换；不是基本类型并且是com.sydata.包下的才做判断
     *
     * @param obj 判断对象
     * @return 是否需要转换
     */
    private static boolean isSupportConvert(Object obj) {
        return obj != null
                && !isPrimitive(obj.getClass())
                && StringUtils.startsWithAny(obj.getClass().getName(), CONVERT_PACKAGE);
    }

    /**
     * 找到字段配置的注解是否支持数据绑定
     *
     * @param field 字段
     */
    public static List<DataBindItem> findDataBindAnnotation(Field field, Class<?> itemClass) {
        Annotation[] annotations = field.getAnnotations();
        if (ArrayUtils.isEmpty(annotations)) {
            return null;
        }
        // 字段本来的注解需要实现DataBindFieldConfig才支持数据绑定
        return Arrays.stream(annotations)
                .filter(DataBindHandleBootstrap::supportDataBindAnnotation)
                .map(annotation -> {
                    DataBindItem dataBindItem = new DataBindItem();
                    if (supportExtendStrategyAnnotations(annotation)) {
                        dataBindItem.setDataBindFieldConfig(annotation);
                    } else {
                        dataBindItem.setDataBindFieldConfig(getMergedAnnotation(annotation, DataBindFieldConfig.class));
                    }
                    dataBindItem.setDataBindStrategy(annotation.annotationType())
                            .setBindField(field);

                    return dataBindItem;
                })
                .collect(Collectors.toList());
    }

    private static <A extends Annotation> A getMergedAnnotation(Annotation annotation, Class<A> annotationType) {
        return MergedAnnotations.from(annotation)
                .get(annotationType, null, MergedAnnotationSelectors.firstDirectlyDeclared())
                .synthesize(MergedAnnotation::isPresent)
                .orElse(null);
    }

    /**
     * 是否是基本类型
     *
     * @param clazz 判断对象Class
     * @return 是否是基本类型
     */
    private static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive()
                || clazz == Boolean.class
                || clazz == Character.class
                || clazz == Byte.class
                || clazz == Short.class
                || clazz == Integer.class
                || clazz == Long.class
                || clazz == Float.class
                || clazz == Double.class
                || clazz == BigInteger.class
                || clazz == BigDecimal.class
                || clazz == String.class
                || clazz == Date.class
                || clazz == java.sql.Date.class
                || clazz == java.sql.Time.class
                || clazz == java.sql.Timestamp.class
                || clazz.isEnum()
                || clazz == java.time.LocalDateTime.class
                || clazz == java.time.LocalDate.class
                || clazz == java.time.LocalTime.class
                ;
    }
}
