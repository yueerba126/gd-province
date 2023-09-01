package com.sydata.data.quality.job.dto;

import cn.hutool.extra.spring.SpringUtil;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import one.util.streamex.StreamEx;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.UNDERSCORE;


/**
 * @author lzq
 * @description 数据质量统计DTO
 * @date 2023/4/23 18:13
 */
@Data
@Accessors(chain = true)
public class QualityReportStatisticsDto implements Serializable {

    private static final String HASH_KEY = "dataQualityReport:statisticsHash";

    @ApiModelProperty(value = "数据总条数")
    private int dataTotalCount;

    @ApiModelProperty(value = "数据合格条数")
    private int dataGoodCount;

    @ApiModelProperty(value = "数据问题条数")
    private int dataIssueCount;

    @ApiModelProperty(value = "问题总数(一条数据可能存在多个问题)")
    private int issueTotalCount;

    @ApiModelProperty(value = "字段总数")
    private int fieldTotalCount;

    @ApiModelProperty(value = "字段上传数(不为空的字段数)")
    private int fieldValidCount;

    /**
     * 更新缓存
     *
     * @param apiEnum       数据API枚举
     * @param stockHouseId  库区ID
     * @param redisTemplate redis操作模板
     */
    public void updateCache(DataApiEnum apiEnum, String stockHouseId, RedisTemplate redisTemplate) {
        HashOperations hash = redisTemplate.opsForHash();
        String hashKey = hashKey(apiEnum);

        Map<String, Field> fieldMap = ClassFieldMapUtil.mapByClass(this.getClass());
        fieldMap.forEach((name, field) -> {
            int fieldVal = (int) ClassFieldMapUtil.getFieldVal(this, field);
            hash.increment(hashKey, hashFieldKey(stockHouseId, field), fieldVal);
        });
    }


    /**
     * 获取缓存统计值
     *
     * @param apiEnum       数据API枚举
     * @param stockHouseId  库区ID
     * @param redisTemplate redis操作模板
     * @return
     */
    public static QualityReportStatisticsDto getCache(DataApiEnum apiEnum, String stockHouseId, RedisTemplate redisTemplate) {
        HashOperations hash = redisTemplate.opsForHash();
        String hashKey = hashKey(apiEnum);

        QualityReportStatisticsDto statistics = new QualityReportStatisticsDto();
        Map<String, Field> fieldMap = ClassFieldMapUtil.mapByClass(QualityReportStatisticsDto.class);
        fieldMap.forEach((name, field) -> {
            Object fieldVal = hash.get(hashKey, hashFieldKey(stockHouseId, field));
            if (fieldVal != null) {
                ClassFieldMapUtil.setFieldVal(statistics, field, fieldVal);
            }
        });

        return statistics;
    }

    /**
     * 删除
     *
     * @param apiEnum       数据API枚举
     * @param stockHouseIds 库区ID列表
     * @param redisTemplate redis操作模板
     */
    public static void delete(DataApiEnum apiEnum, List<String> stockHouseIds, RedisTemplate redisTemplate) {
        Map<String, Field> fieldMap = ClassFieldMapUtil.mapByClass(QualityReportStatisticsDto.class);
        List<String> hashKeys = new ArrayList<>();
        fieldMap.forEach((name, field) -> stockHouseIds.forEach(stockHouseId -> {
            String hashKey = hashFieldKey(stockHouseId, field);
            hashKeys.add(hashKey);
        }));
        redisTemplate.opsForHash().delete(hashKey(apiEnum), hashKeys.toArray());
    }

    /**
     * 清理
     *
     * @param redisTemplate redis操作模板
     */
    public static void clear(RedisTemplate redisTemplate) {
        StreamEx.of(DataApiEnum.values()).map(QualityReportStatisticsDto::hashKey).toListAndThen(redisTemplate::delete);
    }


    /**
     * 获取hashKey
     *
     * @param stockHouseId 库区ID
     * @param field        字段属性
     * @return 哈希键
     */
    private static String hashFieldKey(String stockHouseId, Field field) {
        return stockHouseId + UNDERSCORE + field.getName();
    }

    /**
     * 完整哈希key
     *
     * @param apiEnum 数据API枚举
     * @return 哈希key
     */
    private static String hashKey(DataApiEnum apiEnum) {
        return SpringUtil.getApplicationName() + COLON + HASH_KEY + COLON + apiEnum.getApiCode();
    }
}
