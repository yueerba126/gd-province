package com.sydata.framework.cache.listener;

import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.sydata.framework.cache.listener.annotation.RedisKeyspaceConfig;
import com.sydata.framework.cache.listener.annotation.RedisKeyspaceEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.sydata.framework.cache.properties.CacheConfigProperties.KEY_SEGMENTATION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * @author lzq
 * @describe redis key 事件监听器
 * @date 2022-03-02 01:45
 */
@Component
@Slf4j
public class RedisKeyspaceEventRegister implements MessageListener, InstantiationAwareBeanPostProcessor, CommandLineRunner {

    private final static String KEA = "KEA";
    private static String keyspace = "__keyspace@%s__:";

    private final Map<String, Map<RedisKeyEventEnum, List<CallbackInvoke>>> topicMap = MapUtil.newConcurrentHashMap();

    @Resource
    private RedisMessageListenerContainer redisMessageListenerContainer;

    public RedisKeyspaceEventRegister(@Value("${spring.redis.database:0}") int database) {
        keyspace = String.format(keyspace, database);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        RedisKeyspaceConfig config = targetClass.getAnnotation(RedisKeyspaceConfig.class);
        if (config == null) {
            return bean;
        }

        Method[] methods = targetClass.getDeclaredMethods();
        for (Method method : methods) {
            RedisKeyspaceEvent event = findAnnotation(method, RedisKeyspaceEvent.class);
            if (event == null) {
                continue;
            }

            // 校验方法参数
            Parameter[] parameters = method.getParameters();
            Assert.state(parameters.length >= 1 && parameters.length <= 2, "键空间监听方法必须声明一个或两个参数");
            Assert.state(parameters[0].getParameterizedType() == String.class, "键空间监听方法param[0]必须=String");
            Assert.state(parameters.length == 1 || parameters[1].getParameterizedType() == RedisKeyEventEnum.class,
                    "键空间监听方法param[1]必须=RedisKeyEventEnum】");

            // 校验注解参数
            String keyPrefix = StringUtils.hasLength(event.keyPrefix()) ? event.keyPrefix() : config.keyPrefix();
            Assert.hasLength(keyPrefix, "请配置好需要监听的keyPrefix");
            RedisKeyEventEnum[] eventEnums = event.event();
            Assert.notEmpty(eventEnums, "请配置好需要监听的事件RedisKeyEventEnum");
            boolean keyIncludePrefix = event.keyIncludePrefix();
            Assert.notNull(keyIncludePrefix, "请配置好回调时的key是否需要包含前缀");

            method.setAccessible(Boolean.TRUE);

            // 将订阅的topic事件注册到topicMap
            String topic = SpringUtil.getApplicationName() + KEY_SEGMENTATION + keyPrefix + "*";
            CallbackInvoke invoke = CallbackInvoke.of(bean, method, keyIncludePrefix ? 0 : topic.length() - 1);
            for (RedisKeyEventEnum eventEnum : eventEnums) {
                topicMap.computeIfAbsent(topic, key -> MapUtil.newConcurrentHashMap())
                        .computeIfAbsent(eventEnum, key -> new CopyOnWriteArrayList<>())
                        .add(invoke);
            }
        }
        return bean;
    }


    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        if (ObjectUtils.isEmpty(message.getChannel()) || ObjectUtils.isEmpty(message.getBody())) {
            return;
        }
        LocalDateTime beginTime = LocalDateTime.now();

        // 截取__keyspace@*__:长度获得发生变动的topic
        String topic = new String(pattern, UTF_8).substring(keyspace.length());
        Map<RedisKeyEventEnum, List<CallbackInvoke>> eventsMap = topicMap.get(topic);
        if (CollectionUtils.isEmpty(eventsMap)) {
            return;
        }

        // 截取__keyspace@*__:长度获得发生变动的key后匹配key前缀
        String keyspace = new String(message.getChannel(), UTF_8);
        String event = new String(message.getBody(), UTF_8);
        String cacheKey = keyspace.substring(RedisKeyspaceEventRegister.keyspace.length());


        RedisKeyEventEnum eventEnum = RedisKeyEventEnum.getByEvent(event);
        List<CallbackInvoke> callbackInvokes;
        if (eventEnum == null || CollectionUtils.isEmpty(callbackInvokes = eventsMap.get(eventEnum))) {
            log.trace("cacheKey={},event={}不处理该事件", cacheKey, event);
            return;
        }

        callbackInvokes.forEach(callbackInvoke -> callbackInvoke.doInvoke(cacheKey, eventEnum));

        LocalDateTime endTime = LocalDateTime.now();
        long handleTime = Duration.between(beginTime, endTime).toMillis();
        StringJoiner sj = new StringJoiner("\r\n", "\r\n", "\r\n");
        sj.add("===============redis keyspace 事件===========================");
        sj.add("topic : " + topic);
        sj.add("cacheKey : " + cacheKey);
        sj.add("event : " + event);
        sj.add("startTime : " + beginTime);
        sj.add("endTime : " + endTime);
        sj.add("handleTime : " + handleTime + "MS");
        sj.add("=============================================================");
        log.trace(sj.toString());
    }


    @Override
    public void run(String... args) {
        initNotifyKeyspaceEvents();
        doRegister();
    }

    /**
     * 初始化设置redis key空间事件
     */
    protected void initNotifyKeyspaceEvents() {
        RedisConnection connection = redisMessageListenerContainer.getConnectionFactory().getConnection();
        try {
            String notify = "notify-keyspace-events";
            Properties config = connection.getConfig(notify);
            if (!KEA.equals(config.getProperty(notify))) {
                connection.setConfig(notify, KEA);
            }
        } finally {
            connection.close();
        }
    }

    /**
     * 注册topicMap中需要监听的事件
     */
    protected void doRegister() {
        List<PatternTopic> topics = new ArrayList<>();
        for (String topic : topicMap.keySet()) {
            topics.add(PatternTopic.of(keyspace + topic));
        }
        log.debug("redisKey空间监听事件{}", topics);
        redisMessageListenerContainer.addMessageListener(this, topics);
    }

    /**
     * @author lzq
     * @describe redis key 事件监听回调器
     * @date 2022-03-02 01:45
     */
    private static class CallbackInvoke {
        private Object bean;
        private Method method;
        private int keyBeginIndex;


        public static CallbackInvoke of(Object bean, Method method, int keyBeginIndex) {
            CallbackInvoke instance = new CallbackInvoke();
            instance.bean = bean;
            instance.method = method;
            instance.keyBeginIndex = keyBeginIndex;
            return instance;
        }

        @SneakyThrows(Throwable.class)
        public void doInvoke(String cacheKey, RedisKeyEventEnum eventEnum) {
            String callbackKey = cacheKey.substring(keyBeginIndex);

            Parameter[] parameters = method.getParameters();
            if (parameters.length == 1) {
                method.invoke(bean, callbackKey);
            } else {
                method.invoke(bean, callbackKey, eventEnum);
            }
        }
    }
}
