package com.sydata.organize.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.organize.annotation.DataBindOrganize;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.framework.util.GenerateNoUtil;
import com.sydata.framework.util.StringUtils;
import com.sydata.organize.domain.Organize;
import com.sydata.organize.domain.Region;
import com.sydata.organize.enums.OrganizeBusTypeEnum;
import com.sydata.organize.mapper.OrganizeMapper;
import com.sydata.organize.param.*;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IOrganizeService;
import com.sydata.organize.service.IRegionService;
import com.sydata.organize.vo.OrganizeTreeVo;
import com.sydata.organize.vo.OrganizeVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;
import static com.sydata.organize.enums.OrganizeKindEnum.ADMIN;
import static com.sydata.organize.enums.OrganizeKindEnum.ENTERPRISE;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * 组织架构-组织Service业务层处理
 *
 * @author lzq
 * @date 2022-06-28
 */
@Service("organizeService")
@CacheConfig(cacheNames = OrganizeServiceImpl.CACHE_NAME)
public class OrganizeServiceImpl extends ServiceImpl<OrganizeMapper, Organize> implements IOrganizeService {


    final static String CACHE_NAME = "organize:organize";

    /**
     * 行政组织ID生成KEY
     */
    private final static String ADMIN_ID_KEY = "orgAdmin";


    public final static String ROOT_PARENT_ID = "0";

    @Resource
    private OrganizeMapper organizeMapper;

    @Resource
    private IRegionService regionService;

    @Override
    public List<Organize> listByIds(Collection<? extends Serializable> idList) {
        return MultiCacheBatchHelp
                .apply(CACHE_NAME).<String, Organize, Organize>composeExecute()
                .fields(Organize::getId)
                .params((Collection<String>) idList)
                .targets(super::listByIds)
                .group(targets -> StreamEx.of(targets).toMap(Organize::getId, Function.identity()))
                .get()
                .stream()
                .collect(Collectors.toList());
    }

    @Cacheable(key = "'id='+#id")
    @Override
    public Organize getById(Serializable id) {
        return super.getById(id);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'name='+#entity.name"),
    })
    @Override
    public boolean save(Organize entity) {
        OrganizeServiceImpl organizeService = SpringUtil.getBean(this.getClass());
        Assert.isNull(organizeService.getByName(entity.getName()), String.format("组织名称%s已存在", entity.getName()));
        Assert.isNull(organizeService.getById(entity.getId()), String.format("组织代码%s已存在", entity.getId()));

        Region region = regionService.getById(entity.getRegionId());
        entity.setCountryId(region.getCountryId()).setProvinceId(region.getProvinceId())
                .setCityId(region.getCityId())
                .setAreaId(region.getAreaId());

        // 设置组织父信息
        parentSet(entity);

        return super.save(entity);
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#entity.id"),
            @CacheEvict(key = "'name='+#entity.name"),
    })
    @Override
    public boolean updateById(Organize entity) {
        OrganizeServiceImpl organizeService = SpringUtil.getBean(this.getClass());

        Organize nameOrganize = organizeService.getByName(entity.getName());
        boolean nameState = nameOrganize == null || nameOrganize.getId().equals(entity.getId());
        Assert.state(nameState, String.format("组织名称%s已存在", entity.getName()));

        Region region = regionService.getById(entity.getRegionId());
        entity.setCountryId(region.getCountryId()).setProvinceId(region.getProvinceId())
                .setCityId(region.getCityId())
                .setAreaId(region.getAreaId());

        Organize oldOrganize = organizeService.getById(entity.getId());
        if (oldOrganize.getParentId().equals(entity.getParentId())) {
            // 设置为原父组织ID集合
            entity.setParentIds(oldOrganize.getParentIds());
        } else {
            // 设置组织父信息
            parentSet(entity);

            // 子组织迁移
            sonTransfer(entity, oldOrganize);
        }

        // 清除原父组织ID集合缓存
        this.clearParentIdsCache(oldOrganize.getParentIds());

        return super.updateById(entity);
    }

    @DataBindFieldConvert
    @Override
    public Page<OrganizeVo> pages(OrganizePageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();

        Page<Organize> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getId()), Organize::getId, pageParam.getId())
                .eq(isNotEmpty(pageParam.getKind()), Organize::getKind, pageParam.getKind())
                .eq(isNotEmpty(pageParam.getBusType()), Organize::getBusType, pageParam.getBusType())
                .likeRight(isNotEmpty(pageParam.getName()), Organize::getName, pageParam.getName())
                .eq(isNotEmpty(pageParam.getRegionId()), Organize::getRegionId, pageParam.getRegionId())
                .eq(isNotEmpty(pageParam.getMenuSystemId()), Organize::getMenuSystemId, pageParam.getMenuSystemId())
                .eq(isNotEmpty(loginUser.getCountryId()), Organize::getCountryId, loginUser.getCountryId())
                .eq(isNotEmpty(loginUser.getProvinceId()), Organize::getProvinceId, loginUser.getProvinceId())
                .eq(isNotEmpty(loginUser.getCityId()), Organize::getCityId, loginUser.getCityId())
                .eq(isNotEmpty(loginUser.getAreaId()), Organize::getAreaId, loginUser.getAreaId())
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, OrganizeVo.class);
    }

    @Cacheable(key = "'parentIds='+#parentIds")
    @Override
    public List<Organize> listByParentIds(String parentIds) {
        return super.lambdaQuery().likeRight(Organize::getParentIds, parentIds).list();
    }

    @DataBindFieldConvert
    @Override
    public List<OrganizeTreeVo> treeByParentIds() {
        LoginUser user = UserSecurity.loginUser();
        List<Organize> organizes = SpringUtil.getBean(this.getClass()).listByParentIds(user.getOrganizeParentIds());

        List<OrganizeTreeVo> vos = BeanUtils.copyToList(organizes, OrganizeTreeVo.class);
        vos.sort(Comparator.comparing(OrganizeTreeVo::getRegionId));

        return TreeUtils.toTree(vos, OrganizeTreeVo::getId, OrganizeTreeVo::getParentId,
                OrganizeTreeVo::setChild, user.getOrganizeParentId());
    }

    @Cacheable(key = "'name='+#name")
    @Override
    public Organize getByName(String name) {
        return super.lambdaQuery().eq(Organize::getName, name).one();
    }

    @Override
    public Boolean save(OrganizeOperateParam operateParam) {
        // 校验组织业务类型
        if (isNotEmpty(operateParam.getBusType())) {
            OrganizeBusTypeEnum busTypeEnum = OrganizeBusTypeEnum.getByBusType(operateParam.getBusType());
            Assert.notNull(busTypeEnum, "业务类型不存在");
        }

        if (ENTERPRISE.getKind().equals(operateParam.getKind())) {
            Assert.state(StringUtils.length(operateParam.getId()) == 18, "企业信用代码只能为18位");
            Assert.state(isEmpty(operateParam.getBusType()), "企业组织无法设置业务类型");
        } else if (ADMIN.getKind().equals(operateParam.getKind())) {
            // 根据行政区域编码-生成组织ID
            String key = ADMIN_ID_KEY + LocalDate.now().toString().replace(DASH, EMPTY) + operateParam.getRegionId();
            String id = GenerateNoUtil.generate(key, 4, 1).get(0).substring(ADMIN_ID_KEY.length());
            operateParam.setId(id);
        }

        return SpringUtil.getBean(this.getClass()).save(BeanUtils.copyByClass(operateParam, Organize.class));
    }

    @Override
    public Boolean update(OrganizeOperateParam operateParam) {
        // 校验组织业务类型
        Assert.hasLength(operateParam.getId(), "ID不能为空");
        if (isNotEmpty(operateParam.getBusType())) {
            OrganizeBusTypeEnum busTypeEnum = OrganizeBusTypeEnum.getByBusType(operateParam.getBusType());
            Assert.notNull(busTypeEnum, "业务类型不存在");
        }
        return SpringUtil.getBean(this.getClass()).updateById(BeanUtils.copyByClass(operateParam, Organize.class));
    }

    @Caching(evict = {
            @CacheEvict(key = "'id='+#removeParam.id"),
            @CacheEvict(key = "'name='+#removeParam.name"),
    })
    @Override
    public Boolean remove(OrganizeRemoveParam removeParam) {
        List<Organize> list = SpringUtil.getBean(this.getClass()).listByParentIds(removeParam.getParentIds());
        Assert.state(CollectionUtils.size(list) < 2, "该组织底下存在子组织,无法删除");

        // 根据父组织ID集合清理缓存
        this.clearParentIdsCache(removeParam.getParentIds());

        return super.removeById(removeParam.getId());
    }

    @Override
    public Boolean setSystem(OrganizeSetSystemParam setSystemParam) {
        // 设置组织系统ID
        boolean update = super.lambdaUpdate()
                .set(Organize::getMenuSystemId, setSystemParam.getMenuSystemId())
                .in(Organize::getId, setSystemParam.getIds())
                .update();

        // 清除修改过的组织列表的所有缓存
        List<Organize> organizes = SpringUtil.getBean(this.getClass()).listByIds(setSystemParam.getIds());
        clearCache(organizes);
        return update;
    }

    @Override
    public Boolean setBusType(OrganizeSetBusTypeParam busTypeParam) {
        // 设置组织业务类型
        boolean update = super.lambdaUpdate()
                .set(Organize::getBusType, busTypeParam.getBusType())
                .in(Organize::getId, busTypeParam.getIds())
                .update();

        // 清除修改过的组织列表的所有缓存
        List<Organize> organizes = SpringUtil.getBean(this.getClass()).listByIds(busTypeParam.getIds());
        clearCache(organizes);
        return update;
    }

    @DataBindService(strategy = DataBindOrganize.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, organizeMapper);
    }


    /**
     * 设置组织父信息
     *
     * @param entity 组织实体
     */
    private void parentSet(Organize entity) {
        String parentIds;
        if (ROOT_PARENT_ID.equals(entity.getParentId())) {
            parentIds = entity.getId();
        } else {
            Organize parent = SpringUtil.getBean(this.getClass()).getById(entity.getParentId());
            Assert.notNull(parent, "父组织不存在");
            parentIds = parent.getParentIds() + COMMA + entity.getId();
        }

        // 根据父组织ID集合清理缓存
        this.clearParentIdsCache(parentIds);

        entity.setParentIds(parentIds);
    }

    /**
     * 父组织发生改变，子组织跟随迁移
     *
     * @param entity  修改后组织
     * @param oldDept 修改前组织
     */
    private void sonTransfer(Organize entity, Organize oldDept) {
        // 根据父部门IDS查询子组织列表，并移除本级组织
        List<Organize> trees = SpringUtil.getBean(this.getClass()).listByParentIds(oldDept.getParentIds());
        Map<String, Organize> idMap = StreamEx.of(trees).toMap(Organize::getId, Function.identity());
        idMap.remove(entity.getId());
        if (MapUtils.isEmpty(idMap)) {
            return;
        }

        // 清除子组织列表的所有缓存
        Collection<Organize> sons = idMap.values();
        clearCache(sons);


        // 将修改前组织的parentIds替换成修改后组织parentIds
        sons.forEach(son -> {
            String parentIds = son.getParentIds().replace(oldDept.getParentIds(), entity.getParentIds());
            son.setParentIds(parentIds);
        });
        super.updateBatchById(sons);
    }

    /**
     * 根据父组织ID集合清理缓存
     *
     * @param parentIds 父组织ID集合(逗号分隔)
     */
    private void clearParentIdsCache(String parentIds) {
        List<String> allParentIds = new ArrayList<>();

        List<String> list = StreamEx.of(parentIds.split(COMMA)).toList();
        while (isNotEmpty(list)) {
            allParentIds.add(StreamEx.of(list).joining(COMMA));
            list.remove(list.size() - 1);
        }

        MultiCacheBatchHelp.apply(CACHE_NAME).<String, Organize, Organize>composeExecute()
                .fields(Organize::getParentIds)
                .params(allParentIds)
                .remove();
    }

    /**
     * 清除指定组织列表的所有缓存
     *
     * @param list 组织列表
     */
    private void clearCache(Collection<Organize> list) {
        // 清除组织:ID、name、parentIds的缓存
        MultiCacheBatchHelp.apply(CACHE_NAME).<Organize, Organize, Organize>composeExecute()
                .fields(Organize::getId)
                .inline()
                .params(list)
                .remove();

        MultiCacheBatchHelp.apply(CACHE_NAME).<Organize, Organize, Organize>composeExecute()
                .fields(Organize::getName)
                .inline()
                .params(list)
                .remove();

        MultiCacheBatchHelp.apply(CACHE_NAME).<Organize, Organize, Organize>composeExecute()
                .fields(Organize::getParentIds)
                .inline()
                .params(list)
                .remove();
    }
}

