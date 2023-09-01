package com.sydata.safe.asess.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.safe.asess.domain.OrgAssessDeptIndex;
import com.sydata.safe.asess.mapper.OrgAssessDeptIndexMapper;
import com.sydata.safe.asess.param.OrgAssessDeptIndexDistributionParam;
import com.sydata.safe.asess.param.OrgAssessDeptIndexSubmitParam;
import com.sydata.safe.asess.param.OrgAssessReviewIndexSubmitParam;
import com.sydata.safe.asess.service.IOrgAssessDeptIndexService;
import com.sydata.safe.asess.vo.OrgAssessDeptIndexTreeVo;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.sydata.organize.service.impl.OrganizeServiceImpl.ROOT_PARENT_ID;
import static java.lang.Boolean.FALSE;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

/**
 * 粮食安全考核-部门考核指标Service业务层处理
 *
 * @author system
 * @date 2023-02-18
 */
@CacheConfig(cacheNames = OrgAssessDeptIndexServiceImpl.CACHE_NAME)
@Service("orgAssessDeptIndexService")
public class OrgAssessDeptIndexServiceImpl extends ServiceImpl<OrgAssessDeptIndexMapper, OrgAssessDeptIndex>
        implements IOrgAssessDeptIndexService {

    final static String CACHE_NAME = "safeAssess:orgAssessDeptIndex";


    @Resource
    private OrgAssessDeptIndexMapper orgAssessDeptIndexMapper;

    @Cacheable(key = "'orgAssessDeptId='+#orgAssessDeptId")
    @Override
    public List<OrgAssessDeptIndex> listByOrgAssessDeptId(String orgAssessDeptId) {
        return super.lambdaQuery().eq(OrgAssessDeptIndex::getOrgAssessDeptId, orgAssessDeptId).list();
    }

    @Override
    public List<OrgAssessDeptIndex> listByOrgAssessDeptIds(List<String> orgAssessDeptIds) {
        return MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, OrgAssessDeptIndex, List<OrgAssessDeptIndex>>composeExecute()
                .fields(OrgAssessDeptIndex::getOrgAssessDeptId)
                .params(orgAssessDeptIds)
                .targets(ids -> super.lambdaQuery().in(OrgAssessDeptIndex::getOrgAssessDeptId, ids).list())
                .group(targets -> StreamEx.of(targets).groupingBy(OrgAssessDeptIndex::getOrgAssessDeptId))
                .get()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @DataBindFieldConvert
    @Override
    public List<OrgAssessDeptIndexTreeVo> treeByAssessDeptId(String orgAssessId) {
        List<OrgAssessDeptIndex> list = SpringUtil.getBean(this.getClass()).listByOrgAssessDeptId(orgAssessId);
        list.sort(Comparator.comparing(OrgAssessDeptIndex::getTemplateIndexId));
        List<OrgAssessDeptIndexTreeVo> vos = BeanUtils.copyToList(list, OrgAssessDeptIndexTreeVo.class);

        return TreeUtils.toTree(vos, OrgAssessDeptIndexTreeVo::getTemplateIndexId,
                OrgAssessDeptIndexTreeVo::getTemplateIndexParentId,
                OrgAssessDeptIndexTreeVo::setChild, ROOT_PARENT_ID);
    }

    @CacheEvict(key = "'orgAssessDeptId='+#orgAssessDeptId")
    @Override
    public Boolean distribution(String orgAssessDeptId, List<OrgAssessDeptIndexDistributionParam> list) {
        return isEmpty(list) ? FALSE : orgAssessDeptIndexMapper.distribution(list);
    }

    @CacheEvict(key = "'orgAssessDeptId='+#orgAssessDeptId")
    @Override
    public Boolean reviewSubmit(String orgAssessDeptId, List<OrgAssessReviewIndexSubmitParam> list) {
        return isEmpty(list) ? FALSE : orgAssessDeptIndexMapper.reviewSubmit(list);
    }

    @CacheEvict(key = "'orgAssessDeptId='+#orgAssessDeptId")
    @Override
    public Boolean submit(String orgAssessDeptId, List<OrgAssessDeptIndexSubmitParam> list) {
        return isEmpty(list) ? FALSE : orgAssessDeptIndexMapper.submit(list);
    }

    @Override
    public void removeByOrgAssessDeptIds(List<String> orgAssessDeptIds) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, OrgAssessDeptIndex, OrgAssessDeptIndex>composeExecute()
                .fields(OrgAssessDeptIndex::getOrgAssessDeptId)
                .params(orgAssessDeptIds)
                .remove();
        super.lambdaUpdate().in(OrgAssessDeptIndex::getOrgAssessIndexId, orgAssessDeptIds).remove();
    }
}