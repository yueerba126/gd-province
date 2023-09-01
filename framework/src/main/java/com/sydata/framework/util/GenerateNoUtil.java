package com.sydata.framework.util;

import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author pangbohuan
 * @describe 编码生成器
 * @date 2022-02-11 17:14
 */
@Component
public class GenerateNoUtil {

    private static RedisTemplate redisTemplate;

    public GenerateNoUtil(RedisTemplate redisTemplate) {
        GenerateNoUtil.redisTemplate = redisTemplate;
    }

    /**
     * 批量生成
     *
     * @param key   唯一标识
     * @param size  补位长度 如size=4，本次自增=1，后续则为0001
     * @param delta 自增量 如本次需要申请100个编码
     * @return 有序编码集合
     */
    public static List<String> generate(String key, int size, int delta) {

        String redisKey = String.format("%s:generate:%s", SpringUtil.getApplicationName(), key);

        // 原子性给新key设置初始值、过期时间
        redisTemplate.opsForValue().setIfAbsent(redisKey, 0, 1, TimeUnit.DAYS);
        // 原子性自增
        Long end = redisTemplate.opsForValue().increment(redisKey, delta);


        // 反推本次生成编码的起始点
        long start = end - delta + 1;

        List<String> list = new LinkedList<>();
        for (long serial = start; serial <= end; serial++) {
            // 补位 如size=4，serial=1，supplement则为0001
            String supplement = StringUtils.leftPad(String.valueOf(serial), size, "0");
            String no = key + supplement;
            list.add(no);
        }

        return list;
    }

    /**
     * 生成编码
     *
     * @param key  唯一标识
     * @param size 补位长度 如size=4，本次自增=1，后续则为0001
     * @return 唯一编码
     */
    public static String generate(String key, int size) {
        return generate(key, size, 1).get(0);
    }

    /**
     * 生成编码
     *
     * @param key 唯一标识
     * @return 唯一编码
     */
    public static String generate(String key) {
        return generate(key, 5, 1).get(0);
    }
}
