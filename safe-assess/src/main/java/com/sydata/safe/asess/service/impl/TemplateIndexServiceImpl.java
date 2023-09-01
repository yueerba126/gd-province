package com.sydata.safe.asess.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.safe.asess.domain.TemplateIndex;
import com.sydata.safe.asess.mapper.TemplateIndexMapper;
import com.sydata.safe.asess.param.IndexRemoveParam;
import com.sydata.safe.asess.service.ITemplateIndexService;
import com.sydata.safe.asess.service.ITemplateService;
import com.sydata.safe.asess.vo.TemplateIndexTreeVo;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.sydata.organize.service.impl.OrganizeServiceImpl.ROOT_PARENT_ID;
import static java.lang.Boolean.TRUE;
import static java.util.function.Function.identity;

/**
 * 粮食安全考核-考核模板指标Service业务层处理
 *
 * @author system
 * @date 2023-02-13
 */
@CacheConfig(cacheNames = TemplateIndexServiceImpl.CACHE_NAME)
@Service("templateIndexService")
public class TemplateIndexServiceImpl extends ServiceImpl<TemplateIndexMapper, TemplateIndex> implements ITemplateIndexService {

    final static String CACHE_NAME = "safeAssess:templateIndex";

    @Resource
    private ITemplateService templateService;

    @CacheEvict(key = "'templateId='+#entity.templateId")
    @Override
    public boolean save(TemplateIndex entity) {
        return super.save(entity);
    }

    @CacheEvict(key = "'templateId='+#entity.templateId")
    @Override
    public boolean updateById(TemplateIndex entity) {
        // 操作权限判断
        Assert.state(templateService.operationAuth(entity.getTemplateId()), "非考核模板组织人员不可操作");

        return super.updateById(entity);
    }

    @Cacheable(key = "'templateId='+#templateId")
    @Override
    public List<TemplateIndex> listByTemplateId(String templateId) {
        return super.lambdaQuery().eq(TemplateIndex::getTemplateId, templateId).list();
    }

    @DataBindFieldConvert
    @Override
    public List<TemplateIndexTreeVo> treeByTemplateId(String templateId) {
        List<TemplateIndex> list = SpringUtil.getBean(this.getClass()).listByTemplateId(templateId);
        list.sort(Comparator.comparing(TemplateIndex::getId));
        List<TemplateIndexTreeVo> vos = BeanUtils.copyToList(list, TemplateIndexTreeVo.class);

        return TreeUtils.toTree(vos, TemplateIndexTreeVo::getId,
                TemplateIndexTreeVo::getParentId,
                TemplateIndexTreeVo::setChild, ROOT_PARENT_ID);
    }

    @CacheEvict(key = "'templateId='+#templateId")
    @Override
    public Boolean removeByTemplateId(String templateId) {
        return super.lambdaUpdate().eq(TemplateIndex::getTemplateId, templateId).remove();
    }

    @CacheEvict(key = "'templateId='+#removeParam.templateId")
    @Override
    public Boolean remove(IndexRemoveParam removeParam) {
        // 操作权限判断
        Assert.state(templateService.operationAuth(removeParam.getTemplateId()), "非考核模板组织人员不可操作");

        // 查找树节点,判断是否存在子节点
        List<TemplateIndexTreeVo> list = treeByTemplateId(removeParam.getTemplateId());
        TemplateIndexTreeVo node = StreamEx.of(list).toMap(TemplateIndex::getId, identity()).get(removeParam.getId());
        Boolean isNotChild = Optional.ofNullable(node)
                .map(TemplateIndexTreeVo::getChild)
                .map(CollectionUtils::isEmpty)
                .orElse(TRUE);
        Assert.state(isNotChild, "存在子节点不允许删除");

        return super.removeById(removeParam.getId());
    }
}