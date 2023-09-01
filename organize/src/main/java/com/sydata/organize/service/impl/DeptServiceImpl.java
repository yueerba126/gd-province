package com.sydata.organize.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.annotation.DataBindDept;
import com.sydata.organize.domain.Dept;
import com.sydata.organize.mapper.DeptMapper;
import com.sydata.organize.param.DeptRemoveParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IDeptService;
import com.sydata.organize.vo.DeptTreeVo;
import com.sydata.organize.vo.DeptVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;

/**
 * 组织架构-部门Service业务层处理
 *
 * @author lzq
 * @date 2022-11-16
 */
@Service("deptService")
@CacheConfig(cacheNames = DeptServiceImpl.CACHE_NAME)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {
    final static String CACHE_NAME = "organize:dept";

    @Resource
    private DeptMapper deptMapper;

    @Cacheable(key = "'id='+#id")
    @Override
    public Dept getById(Serializable id) {
        return super.getById(id);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'organizeId='+#entity.organizeId"),
            @CacheEvict(key = "'organizeId='+#entity.organizeId+'&name='+#entity.name"),
    })
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean save(Dept entity) {
        DeptServiceImpl deptService = SpringUtil.getBean(this.getClass());
        String message = String.format("部门名%s已存在", entity.getName());
        Assert.isNull(deptService.getByName(entity.getOrganizeId(), entity.getName()), message);

        // 设置部门父信息
        parentSet(entity.setId(IdUtil.getSnowflakeNextId()));
        return super.save(entity);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'organizeId='+#entity.organizeId"),
            @CacheEvict(key = "'organizeId='+#entity.organizeId+'&name='+#entity.name"),
    })
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean updateById(Dept entity) {
        DeptServiceImpl deptService = SpringUtil.getBean(this.getClass());
        Dept nameDept = deptService.getByName(entity.getOrganizeId(), entity.getName());
        boolean nameState = nameDept == null || nameDept.getId().equals(entity.getId());
        Assert.state(nameState, String.format("部门名%s已存在", entity.getName()));


        Dept oldDept = deptService.getById(entity.getId());
        if (!oldDept.getParentId().equals(entity.getParentId())) {
            // 设置部门父信息
            parentSet(entity);

            // 子部门迁移
            sonTransfer(entity, oldDept);

            // 清除原父部门ID集合缓存
            this.clearParentIdsCache(oldDept.getParentIds());
        }

        // 清除原组织ID缓存
        if (!oldDept.getOrganizeId().equals(entity.getOrganizeId())) {
            MultiCacheBatchHelp.apply(CACHE_NAME).<String, Dept, List<Dept>>composeExecute()
                    .fields(Dept::getOrganizeId)
                    .params(Collections.singletonList(oldDept.getOrganizeId()))
                    .remove();
        }
        return super.updateById(entity);
    }

    @DataBindFieldConvert
    @Override
    public List<DeptTreeVo> treeByOrganizeId(String organizeId) {
        List<Dept> deptList = SpringUtil.getBean(this.getClass()).listByOrganizeIds(Collections.singletonList(organizeId));
        List<DeptTreeVo> vos = BeanUtils.copyToList(deptList, DeptTreeVo.class);
        return TreeUtils.toTree(vos, DeptTreeVo::getId, DeptTreeVo::getParentId, DeptTreeVo::setChild, 0L);
    }

    @DataBindFieldConvert
    @Override
    public List<DeptTreeVo> treeByParentIds() {
        Dept dept = UserSecurity.loginUser().getDept();
        if (dept == null) {
            return Collections.emptyList();
        }

        List<Dept> deptList = SpringUtil.getBean(this.getClass()).listByParentIds(dept.getParentIds());
        List<DeptTreeVo> vos = BeanUtils.copyToList(deptList, DeptTreeVo.class);
        return TreeUtils.toTree(vos, DeptTreeVo::getId, DeptTreeVo::getParentId, DeptTreeVo::setChild, dept.getParentId());
    }

    @DataBindFieldConvert
    @Override
    public DeptVo details(Long id) {
        Dept dept = SpringUtil.getBean(this.getClass()).getById(id);
        return BeanUtils.copyByClass(dept, DeptVo.class);
    }

    @Cacheable(key = "'organizeId='+#organizeId+'&name='+#name")
    @Override
    public Dept getByName(String organizeId, String name) {
        return super.lambdaQuery().eq(Dept::getOrganizeId, organizeId).eq(Dept::getName, name).one();
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#removeParam.id"),
            @CacheEvict(key = "'organizeId='+#removeParam.organizeId"),
            @CacheEvict(key = "'organizeId='+#removeParam.organizeId+'&name='+#removeParam.name"),
    })
    @Override
    public Boolean remove(DeptRemoveParam removeParam) {
        List<Dept> deptList = SpringUtil.getBean(this.getClass()).listByParentIds(removeParam.getParentIds());
        Assert.state(CollectionUtils.size(deptList) < 2, "该部门底下存在子部门,无法删除");

        // 根据父部门ID集合清理缓存
        this.clearParentIdsCache(removeParam.getParentIds());

        return super.removeById(removeParam.getId());
    }

    @Override
    public List<Dept> listByOrganizeIds(Collection<String> organizeIds) {
        return MultiCacheBatchHelp.apply(CACHE_NAME).<String, Dept, List<Dept>>composeExecute()
                .fields(Dept::getOrganizeId)
                .params(organizeIds)
                .targets(ids -> super.lambdaQuery().in(Dept::getOrganizeId, ids).list())
                .group(list -> StreamEx.of(list).groupingBy(Dept::getOrganizeId))
                .get()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "'parentIds='+#parentIds")
    @Override
    public List<Dept> listByParentIds(String parentIds) {
        return super.lambdaQuery().likeRight(Dept::getParentIds, parentIds).list();
    }

    @DataBindService(strategy = DataBindDept.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, deptMapper);
    }

    /**
     * 根据父部门ID集合清理缓存
     *
     * @param parentIds 父组织ID集合(逗号分隔)
     */
    private void clearParentIdsCache(String parentIds) {
        List<String> allParentIds = new ArrayList<>();

        List<String> list = StreamEx.of(parentIds.split(COMMA)).toList();
        while (ObjectUtil.isNotEmpty(list)) {
            allParentIds.add(StreamEx.of(list).joining(COMMA));
            list.remove(list.size() - 1);
        }

        MultiCacheBatchHelp.apply(CACHE_NAME).<String, Dept, List<Dept>>composeExecute()
                .fields(Dept::getParentIds)
                .params(allParentIds)
                .remove();
    }

    /**
     * 设置部门父信息
     *
     * @param entity 部门实体
     */
    private void parentSet(Dept entity) {
        String parentIds;
        if (entity.getParentId() == 0) {
            parentIds = entity.getId().toString();
        } else {
            Dept parent = SpringUtil.getBean(this.getClass()).getById(entity.getParentId());
            Assert.notNull(parent, "父部门不存在");
            Assert.state(parent.getOrganizeId().equals(entity.getOrganizeId()), "该部门与父部门不属于同一个组织");
            parentIds = parent.getParentIds() + COMMA + entity.getId();
        }

        // 根据父部门ID集合清理缓存
        this.clearParentIdsCache(parentIds);

        entity.setParentIds(parentIds);
    }

    /**
     * 父部门发生改变，子部门跟随迁移
     *
     * @param entity  修改后部门
     * @param oldDept 修改前部门
     */
    private void sonTransfer(Dept entity, Dept oldDept) {
        // 根据父部门IDS查询子部门列表，并移除本级部门
        List<Dept> trees = SpringUtil.getBean(this.getClass()).listByParentIds(oldDept.getParentIds());
        Map<Long, Dept> deptMap = StreamEx.of(trees).toMap(Dept::getId, Function.identity());
        deptMap.remove(entity.getId());
        if (MapUtils.isEmpty(deptMap)) {
            return;
        }
        Collection<Dept> sons = deptMap.values();

        // 清除部门:ID、name、parentIds
        MultiCacheBatchHelp.apply(CACHE_NAME).<Dept, Dept, Dept>composeExecute()
                .fields(Dept::getId)
                .inline()
                .params(sons)
                .remove();

        MultiCacheBatchHelp.apply(CACHE_NAME).<Dept, Dept, Dept>composeExecute()
                .fields(Dept::getOrganizeId, Dept::getName)
                .inline()
                .params(sons)
                .remove();

        MultiCacheBatchHelp.apply(CACHE_NAME).<Dept, Dept, Dept>composeExecute()
                .fields(Dept::getParentIds)
                .inline()
                .params(sons)
                .remove();

        // 将修改前部门的parentIds替换成修改后部门parentIds
        sons.forEach(son -> {
            String parentIds = son.getParentIds().replace(oldDept.getParentIds(), entity.getParentIds());
            son.setParentIds(parentIds);
        });
        super.updateBatchById(sons);
    }
}