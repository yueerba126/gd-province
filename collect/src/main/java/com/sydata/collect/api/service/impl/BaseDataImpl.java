package com.sydata.collect.api.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.service.IData;
import com.sydata.collect.quality.dto.DataIssueDto;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.common.domain.BaseFiledEntity;
import com.sydata.framework.cache.util.ClassFieldMapUtil;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COLON;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.sydata.common.api.enums.CzBzEnum.*;
import static com.sydata.organize.handler.OrganizeMetaObjectHandler.CZBZ;
import static com.sydata.organize.handler.OrganizeMetaObjectHandler.ID;

/**
 * @author lzq
 * @description 数据接口公共实现类
 * @date 2022/10/19 9:59
 */
public abstract class BaseDataImpl<P extends BaseApiParam, M extends BaseMapper<T>, T extends BaseFiledEntity, R>
        extends ServiceImpl<M, T> implements IData<P, T, R> {

    private static final ThreadLocal OLD_ENTITY_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public String collect(P param) {
        // 获取操作标志
        String czBz = (String) ClassFieldMapUtil.getFieldVal(param, CZBZ);
        CzBzEnum czBzEnum = CzBzEnum.getBy(czBz);
        Assert.notNull(czBzEnum, "操作标志不存在");

        // 自定义校验
        DataIssueDto dataIssueDto = new DataIssueDto().setDtls(new CopyOnWriteArrayList<>());
        validated(dataIssueDto, param);
        Assert.state(CollectionUtils.isEmpty(dataIssueDto.getDtls()), () -> {
            String errs = StreamEx.of(dataIssueDto.getDtls())
                    .map(dtl -> dtl.getFieldName() + COLON + dtl.getIssueRemark())
                    .collect(Collectors.joining(COMMA));
            return errs;
        });

        // 获取ID查询旧数据,如果发现是空缓存直接置空
        String id = param.getBuildId();
        IService<T> service = SpringUtil.getBean(this.getClass());
        T oldEntity = service.getById(id);

        // 将参数转换为实体--并设置ID值
        T newEntity = toEntity(param);
        ClassFieldMapUtil.setFieldVal(newEntity, ID, id);

        try {
            OLD_ENTITY_THREAD_LOCAL.set(oldEntity);

            // 数据收集实时落库
            boolean result = Boolean.TRUE;
            if (I.equals(czBzEnum) || U.equals(czBzEnum)) {
                result = oldEntity == null ? service.save(newEntity) : service.updateById(newEntity);
            } else if (D.equals(czBzEnum) && oldEntity != null) {
                result = service.removeById(newEntity);
            }
            Assert.isTrue(result, "数据操作失败");

            // 收集落库后处理
            if (!D.equals(czBzEnum) || oldEntity != null) {
                collectAfter(param, newEntity);
            }
        } finally {
            OLD_ENTITY_THREAD_LOCAL.remove();
        }

        return id;
    }


    /**
     * 获取旧实体
     *
     * @return 旧实体
     */
    protected T oldEntity() {
        return (T) OLD_ENTITY_THREAD_LOCAL.get();
    }


    /**
     * 收集后处理
     *
     * @param p      API参数
     * @param entity 实体
     */
    protected void collectAfter(P p, T entity) {

    }
}
