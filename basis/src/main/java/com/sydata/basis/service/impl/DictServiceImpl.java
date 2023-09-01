package com.sydata.basis.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.basis.domain.Dict;
import com.sydata.basis.mapper.DictMapper;
import com.sydata.basis.param.DictPageParam;
import com.sydata.basis.param.DictRemoveParam;
import com.sydata.basis.service.IDictService;
import com.sydata.basis.vo.DictTreeVo;
import com.sydata.common.basis.annotation.DataBindDict;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.util.BeanUtils;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.EMPTY;
import static com.sydata.common.basis.annotation.DataBindDict.DICT_KEY;
import static com.sydata.common.basis.annotation.DataBindDict.DICT_VALUE;
import static com.sydata.framework.cache.properties.CacheConfigProperties.KEY_SEGMENTATION;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * 基础信息-字典Service业务层处理
 *
 * @author lzq
 * @date 2022-07-26
 */
@CacheConfig(cacheNames = DictServiceImpl.CACHE_NAME)
@Service("dictService")
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {
    final static String CACHE_NAME = "basis:dict";

    @Resource
    private DictMapper dictMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @CacheEvict(key = "'dictType='+#entity.dictType")
    @Override
    public boolean save(Dict entity) {
        String dictTypeHashKey = buildHashKey(entity.getDictType());
        boolean exist = redisTemplate.opsForHash().hasKey(dictTypeHashKey, entity.getDictKey());
        Assert.state(!exist, "字典值已存在，请勿重复新增");
        Dict exitEntity = super.lambdaQuery()
                .eq(Dict::getDictType, entity.getDictType())
                .eq(Dict::getDictKey, entity.getDictKey())
                .one();
        Assert.isNull(exitEntity, "字典值已存在，请勿重复新增");

        boolean state = super.save(entity);
        if (state) {
            redisTemplate.opsForHash().put(dictTypeHashKey, entity.getDictKey(), entity);
            redisTemplate.opsForHash().put(dictTypeHashKey, entity.getDictValue(), entity);
        }
        return state;
    }

    @CacheEvict(key = "'dictType='+#entity.dictType")
    @Override
    public boolean updateById(Dict entity) {
        boolean state = super.updateById(entity);
        if (state) {
            String dictTypeHashKey = buildHashKey(entity.getDictType());
            redisTemplate.opsForHash().put(dictTypeHashKey, entity.getDictKey(), entity);
            redisTemplate.opsForHash().put(dictTypeHashKey, entity.getDictValue(), entity);
        }
        return state;
    }

    @Override
    public Page<Dict> pages(DictPageParam pageParam) {
        Page<Dict> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getDictType()), Dict::getDictType, pageParam.getDictType())
                .likeRight(isNotEmpty(pageParam.getDictKey()), Dict::getDictKey, pageParam.getDictKey())
                .likeRight(isNotEmpty(pageParam.getDictValue()), Dict::getDictValue, pageParam.getDictValue())
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return page;
    }

    @Cacheable(key = "'dictType='+#dictType")
    @Override
    public List<Dict> listByDictType(String dictType) {
        return super.lambdaQuery().eq(Dict::getDictType, dictType).list();
    }

    @Override
    public List<DictTreeVo> treeByDictType(String dictType) {
        List<Dict> dictList = SpringUtil.getBean(this.getClass()).listByDictType(dictType);

        // 转换为树结构
        List<DictTreeVo> vos = BeanUtils.copyToList(dictList, DictTreeVo.class);
        return TreeUtils.toTree(vos, DictTreeVo::getDictKey, DictTreeVo::getDictParentKey, DictTreeVo::setChild, EMPTY);
    }

    @Override
    @CacheEvict(key = "'dictType='+#removeParam.dictType")
    public boolean remove(DictRemoveParam removeParam) {
        boolean state = super.lambdaUpdate()
                .eq(Dict::getDictType, removeParam.getDictType())
                .in(Dict::getDictKey, removeParam.getDictKeys())
                .remove();
        if (state) {
            removeParam.getDictKeys().addAll(removeParam.getDictValues());
            redisTemplate.opsForHash().delete(buildHashKey(removeParam.getDictType()), removeParam.getDictKeys());
        }
        return state;
    }

    @DataBindService(strategy = DataBindDict.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        // 根据字典类型分组
        Map<String, List<DataBindQuery>> dictTypeMap = StreamEx.of(dataBindQuerys)
                .groupingBy(d -> d.getSourceFieldCombination().toString());

        dictTypeMap.forEach((dictType, list) -> {
            // 查询缓存
            Map<String, DataBindQuery> keyMap = StreamEx.of(list).toMap(d -> d.getQueryKey().toString(), identity());
            String dictTypeHashKey = buildHashKey(dictType);
            buildCacheData(dictTypeHashKey, keyMap);


            // 过滤未设置数据的查询来源，批量查询数据库设置数据并加入缓存
            Map<String, List<DataBindQuery>> columnMap = StreamEx.of(list)
                    .filter(dataBindQuery -> dataBindQuery.getData() == null)
                    .groupingBy(d -> d.getQueryColumn().toString());

            if (MapUtil.isNotEmpty(columnMap)) {
                Map<String, Dict> queryKeyMap = MapUtil.newHashMap();

                List<DataBindQuery> dictKeys = columnMap.get(DICT_KEY);
                queryKeyMap.putAll(buildDbData(dictType, Dict::getDictKey, Dict::setDictKey, dictKeys));

                List<DataBindQuery> dictValues = columnMap.get(DICT_VALUE);
                queryKeyMap.putAll(buildDbData(dictType, Dict::getDictValue, Dict::setDictValue, dictValues));

                if (MapUtil.isNotEmpty(queryKeyMap)) {
                    redisTemplate.opsForHash().putAll(dictTypeHashKey, queryKeyMap);
                }
            }
        });

    }


    /**
     * 构建缓存数据
     *
     * @param dictTypeHashKey 缓存key
     * @param keyMap          查询keyMap
     */
    private void buildCacheData(String dictTypeHashKey, Map<String, DataBindQuery> keyMap) {
        List<Dict> dictList = redisTemplate.opsForHash().multiGet(dictTypeHashKey, keyMap.keySet());

        StreamEx.of(dictList)
                .nonNull()
                .peek(dict -> {
                    List<Function<Dict, String>> fields = Arrays.asList(Dict::getDictKey, Dict::getDictValue);
                    StreamEx.of(fields)
                            .map(fun -> fun.apply(dict))
                            .filter(StringUtils::isNotEmpty)
                            .map(keyMap::get)
                            .nonNull()
                            .findFirst()
                            .ifPresent(bindQuery -> bindQuery.setData(dict));
                }).toList();
    }

    /**
     * 构建数据库数据
     *
     * @param dictType    字典类型
     * @param field       查询字段
     * @param setField    赋值字段
     * @param bindQueries 查询内容列表
     */
    private Map<String, Dict> buildDbData(String dictType,
                                          SFunction<Dict, String> field,
                                          BiConsumer<Dict, String> setField,
                                          List<DataBindQuery> bindQueries) {
        if (CollectionUtils.isEmpty(bindQueries)) {
            return Collections.emptyMap();
        }

        Map<String, Dict> dictMap = super.lambdaQuery()
                .eq(Dict::getDictType, dictType)
                .in(field, StreamEx.of(bindQueries).map(DataBindQuery::getQueryKey).toSet())
                .list()
                .stream()
                .collect(groupingBy(field, collectingAndThen(toList(), t -> t.get(0))));

        Map<String, DataBindQuery> keyMap = StreamEx.of(bindQueries).toMap(d -> d.getQueryKey().toString(), identity());
        keyMap.forEach((dictKey, bindQuery) -> {
            // 如果为null设置空实例并且赋值(目的是防止缓存穿透)
            Dict dict = dictMap.get(dictKey);
            if (dict == null) {
                dict = ReflectUtil.newInstance(Dict.class);
                setField.accept(dict, dictKey);
                dictMap.put(dictKey, dict);
            }
            bindQuery.setData(dict);
        });

        return dictMap;
    }

    /**
     * 构建HashKey(服务名:基础数据字典:字典类型)
     *
     * @param dictType 字典类型
     * @return HashKey
     */
    private String buildHashKey(String dictType) {
        return SpringUtil.getApplicationName() + KEY_SEGMENTATION + CACHE_NAME + KEY_SEGMENTATION + dictType;
    }
}