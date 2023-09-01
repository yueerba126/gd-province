package com.sydata.safe.asess.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.safe.asess.domain.OrgAssessIndex;
import com.sydata.safe.asess.mapper.OrgAssessIndexMapper;
import com.sydata.safe.asess.param.CheckPlanIndexCheckParam;
import com.sydata.safe.asess.param.OrgAssessDeptIndexSubmitParam;
import com.sydata.safe.asess.param.OrgAssessIndexDistributionParam;
import com.sydata.safe.asess.param.ReviewIndexAssessParam;
import com.sydata.safe.asess.service.IOrgAssessIndexService;
import com.sydata.safe.asess.vo.OrgAssessIndexTreeVo;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sydata.organize.service.impl.OrganizeServiceImpl.ROOT_PARENT_ID;
import static java.lang.Boolean.FALSE;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

/**
 * 粮食安全考核-单位考核指标Service业务层处理
 *
 * @author system
 * @date 2023-02-16
 */
@CacheConfig(cacheNames = OrgAssessIndexServiceImpl.CACHE_NAME)
@Service("orgAssessIndexService")
public class OrgAssessIndexServiceImpl extends ServiceImpl<OrgAssessIndexMapper, OrgAssessIndex>
        implements IOrgAssessIndexService {

    final static String CACHE_NAME = "safeAssess:orgAssessIndex";

    @Resource
    private OrgAssessIndexMapper orgAssessIndexMapper;

    @Cacheable(key = "'orgAssessId='+#orgAssessId")
    @Override
    public List<OrgAssessIndex> listByOrgAssessId(String orgAssessId) {
        return super.lambdaQuery().eq(OrgAssessIndex::getOrgAssessId, orgAssessId).list();
    }

    @Override
    public List<OrgAssessIndex> listByOrgAssessIds(List<String> orgAssessIds) {
        return MultiCacheBatchHelp.apply(CACHE_NAME)
                .<String, OrgAssessIndex, List<OrgAssessIndex>>composeExecute()
                .fields(OrgAssessIndex::getOrgAssessId)
                .params(orgAssessIds)
                .targets(ids -> super.lambdaQuery().in(OrgAssessIndex::getOrgAssessId, ids).list())
                .group(targets -> StreamEx.of(targets).groupingBy(OrgAssessIndex::getOrgAssessId))
                .get()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @DataBindFieldConvert
    @Override
    public List<OrgAssessIndexTreeVo> treeByOrgAssessId(String orgAssessId) {
        List<OrgAssessIndex> list = SpringUtil.getBean(this.getClass()).listByOrgAssessId(orgAssessId);
        List<OrgAssessIndexTreeVo> vos = BeanUtils.copyToList(list, OrgAssessIndexTreeVo.class);

        return TreeUtils.toTree(vos, OrgAssessIndexTreeVo::getTemplateIndexId,
                OrgAssessIndexTreeVo::getTemplateIndexParentId,
                OrgAssessIndexTreeVo::setChild, ROOT_PARENT_ID);
    }

    @DataBindFieldConvert
    @Override
    public Map<String, List<OrgAssessIndexTreeVo>> treeMapByOrgAssessIds(List<String> orgAssessIds) {
        List<OrgAssessIndex> list = SpringUtil.getBean(this.getClass()).listByOrgAssessIds(orgAssessIds);
        list.sort(Comparator.comparing(OrgAssessIndex::getTemplateIndexId));

        return StreamEx.of(list)
                .groupingBy(OrgAssessIndex::getOrgAssessId, collectingAndThen(toList(), lists -> {
                    List<OrgAssessIndexTreeVo> vos = BeanUtils.copyToList(lists, OrgAssessIndexTreeVo.class);
                    return TreeUtils.toTree(vos, OrgAssessIndexTreeVo::getTemplateIndexId,
                            OrgAssessIndexTreeVo::getTemplateIndexParentId,
                            OrgAssessIndexTreeVo::setChild, ROOT_PARENT_ID);
                }));
    }

    @Override
    public Map<String, OrgAssessIndex> mapByOrgAssessId(String orgAssessId) {
        List<OrgAssessIndex> list = SpringUtil.getBean(this.getClass()).listByOrgAssessId(orgAssessId);
        return StreamEx.of(list).toMap(OrgAssessIndex::getId, Function.identity());
    }

    @Override
    public Map<String, OrgAssessIndex> templateIndexMapByOrgAssessId(String orgAssessId) {
        List<OrgAssessIndex> list = SpringUtil.getBean(this.getClass()).listByOrgAssessId(orgAssessId);
        return StreamEx.of(list).toMap(OrgAssessIndex::getTemplateIndexId, Function.identity());
    }

    @CacheEvict(key = "'orgAssessId='+#orgAssessId")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean allot(String orgAssessId, List<OrgAssessIndexDistributionParam> list) {
        return isEmpty(list) ? FALSE : orgAssessIndexMapper.allot(list);
    }

    @CacheEvict(key = "'orgAssessId='+#orgAssessId")
    @Override
    public Boolean deptAssessSubmit(String orgAssessId, List<OrgAssessDeptIndexSubmitParam> list) {
        return isEmpty(list) ? FALSE : orgAssessIndexMapper.deptAssessSubmit(list);
    }

    @CacheEvict(key = "'orgAssessId='+#orgAssessId")
    @Override
    public Boolean assess(String orgAssessId, List<ReviewIndexAssessParam> list) {
        return isEmpty(list) ? FALSE : orgAssessIndexMapper.assess(list);
    }

    @CacheEvict(key = "'orgAssessId='+#orgAssessId")
    @Override
    public Boolean check(String orgAssessId, List<CheckPlanIndexCheckParam> list) {
        return isEmpty(list) ? FALSE : orgAssessIndexMapper.check(list);
    }

    @Override
    public void removeByOrgAssessIds(List<String> orgAssessIds) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, OrgAssessIndex, List<OrgAssessIndex>>composeExecute()
                .fields(OrgAssessIndex::getOrgAssessId)
                .params(orgAssessIds)
                .remove();
        super.lambdaUpdate().in(OrgAssessIndex::getOrgAssessId, orgAssessIds).remove();
    }
}
