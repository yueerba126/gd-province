package com.sydata.basis.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.basis.domain.Company;
import com.sydata.basis.domain.CompanyRelation;
import com.sydata.basis.mapper.CompanyRelationMapper;
import com.sydata.basis.service.ICompanyRelationService;
import com.sydata.basis.service.ICompanyService;
import com.sydata.basis.vo.CompanyTreeVo;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.permission.extend.ICompanyExtend;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.COMMA;
import static com.sydata.framework.core.util.TreeUtils.toParentIdMap;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 单位信息关系Service业务层处理
 *
 * @author lzq
 * @date 2022-12-06
 */
@CacheConfig(cacheNames = CompanyRelationServiceImpl.CACHE_NAME)
@Service("companyRelationService")
public class CompanyRelationServiceImpl extends ServiceImpl<CompanyRelationMapper, CompanyRelation>
        implements ICompanyRelationService, ICompanyExtend {

    final static String CACHE_NAME = "basis:companyRelation";

    @Resource
    private CompanyRelationMapper companyRelationMapper;

    @Resource
    private ICompanyService companyService;

    @Cacheable(key = "'id='+#id")
    @Override
    public CompanyRelation getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public List<String> sonIds(String companyId) {
        CompanyRelation companyRelation = SpringUtil.getBean(this.getClass()).getById(companyId);
        if (isEmpty(companyRelation) || isEmpty(companyRelation.getSonIds())) {
            return Collections.emptyList();
        }
        return StreamEx.of(companyRelation.getSonIds().split(COMMA)).toList();
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, CompanyRelation, CompanyRelation>composeExecute()
                .fields(CompanyRelation::getId)
                .params((Collection<String>) list)
                .remove();
        return super.removeByIds(list);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @XxlJob("companyRelationHandle")
    public void companyRelationHandle() {
        // 全量查询单位信息表
        List<Company> list = companyService.lambdaQuery().select(Company::getId, Company::getSjdwdm).list();

        // 将上级单位代码为null或等于本单位代码的过滤掉避免空指针或死循环,转单位父idMap
        Map<String, List<CompanyTreeVo>> parentIdMap = StreamEx.of(list)
                .filter(v -> isNotEmpty(v.getSjdwdm()) && !Objects.equals(v.getId(), v.getSjdwdm()))
                .map(v -> BeanUtils.copyByClass(v, CompanyTreeVo.class))
                .toListAndThen(vos -> {
                    BiConsumer<CompanyTreeVo, List<CompanyTreeVo>> childSet = CompanyTreeVo::setChild;
                    return toParentIdMap(vos, CompanyTreeVo::getId, CompanyTreeVo::getSjdwdm, childSet);
                });

        // 转单位idMap
        Map<String, Company> idMap = StreamEx.of(list).toMap(Company::getId, Function.identity());

        // 组装单位上下级关联关系
        List<CompanyRelation> relations = new ArrayList<>();
        list.forEach(v -> {
            CompanyRelation relation = new CompanyRelation().setId(v.getId())
                    .setSonIds(findSonIds(v.getId(), parentIdMap))
                    .setParentIds(findParentIds(v.getId(), idMap));
            relations.add(relation);
        });

        // 清空单位关系表
        List<String> ids = super.lambdaQuery()
                .select(CompanyRelation::getId)
                .list()
                .stream()
                .map(CompanyRelation::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ids)) {
            this.removeByIds(ids);
        }

        // 批量新增
        super.saveBatch(relations);
    }

    /**
     * 查找所有子单位ID
     *
     * @param id          主键ID
     * @param parentIdMap 单位父idMap
     * @return 子单位ID
     */
    private String findSonIds(String id, Map<String, List<CompanyTreeVo>> parentIdMap) {
        List<CompanyTreeVo> sons = parentIdMap.get(id);
        if (isEmpty(sons)) {
            return null;
        }

        List<CompanyTreeVo> list = new ArrayList<>(sons);

        List<CompanyTreeVo> temps = new ArrayList<>(sons);
        while (isNotEmpty(temps)) {
            // 寻找下级
            List<CompanyTreeVo> next = new ArrayList<>();
            temps.forEach(temp -> {
                List<CompanyTreeVo> grandson = parentIdMap.getOrDefault(temp.getId(), Collections.emptyList());
                next.addAll(grandson);
            });

            // 级别下推
            temps.clear();
            temps.addAll(next);

            // 累加后代
            list.addAll(next);

            // 校验如果出现重复儿子则返回空,循环依赖
            if (list.size() != StreamEx.of(list).map(Company::getId).toSet().size()) {
                XxlJobHelper.log("单位代码{}寻找子节点时发现循环依赖{}", id, list);
                return null;
            }
        }
        return StreamEx.of(list).map(Company::getId).joining(COMMA);
    }

    /**
     * 查找所有父单位ID
     *
     * @param id    主键ID
     * @param idMap 单位idMap
     * @return 父单位ID
     */
    private String findParentIds(String id, Map<String, Company> idMap) {
        Company company = idMap.get(id);
        if (isEmpty(company) || isEmpty(company.getSjdwdm())) {
            return null;
        }

        Set<String> parentIds = new HashSet<>();

        String parentId = company.getSjdwdm();
        while (isNotEmpty(parentId)) {
            // 校验如果出现重复父亲则返回空,循环依赖
            boolean state = parentIds.add(parentId);
            if (!state) {
                XxlJobHelper.log("单位代码{}寻找父节点时发现循环依赖{}", id, parentIds);
                return null;
            }
            Company parent = idMap.get(parentId);
            parentId = parent == null ? null : parent.getSjdwdm();
        }
        return StreamEx.of(parentIds).joining(COMMA);
    }
}