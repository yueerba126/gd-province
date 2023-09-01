package com.sydata.safe.asess.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.safe.asess.domain.OrgAssessIndex;
import com.sydata.safe.asess.domain.OrgAssessReviewIndex;
import com.sydata.safe.asess.mapper.OrgAssessReviewIndexMapper;
import com.sydata.safe.asess.param.OrgAssessReviewIndexSubmitParam;
import com.sydata.safe.asess.service.IOrgAssessReviewIndexService;
import com.sydata.safe.asess.vo.OrgAssessReviewIndexTreeVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

import static com.sydata.organize.service.impl.OrganizeServiceImpl.ROOT_PARENT_ID;
import static java.lang.Boolean.FALSE;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

/**
 * 粮食安全考核-部门自评指标Service业务层处理
 *
 * @author system
 * @date 2023-02-20
 */
@CacheConfig(cacheNames = OrgAssessReviewIndexServiceImpl.CACHE_NAME)
@Service("orgAssessReviewIndexService")
public class OrgAssessReviewIndexServiceImpl extends ServiceImpl<OrgAssessReviewIndexMapper, OrgAssessReviewIndex>
        implements IOrgAssessReviewIndexService {

    final static String CACHE_NAME = "safeAssess:orgAssessReviewIndex";

    @Resource
    private OrgAssessReviewIndexMapper orgAssessReviewIndexMapper;

    @Cacheable(key = "'orgAssessDeptReviewId='+#orgAssessDeptReviewId")
    @Override
    public List<OrgAssessReviewIndex> listByAssessDeptReviewId(String orgAssessDeptReviewId) {
        return super.lambdaQuery().eq(OrgAssessReviewIndex::getOrgAssessDeptReviewId, orgAssessDeptReviewId).list();
    }

    @DataBindFieldConvert
    @Override
    public List<OrgAssessReviewIndexTreeVo> treeByAssessDeptReviewId(String orgAssessDeptReviewId) {
        OrgAssessReviewIndexServiceImpl bean = SpringUtil.getBean(this.getClass());
        List<OrgAssessReviewIndex> list = bean.listByAssessDeptReviewId(orgAssessDeptReviewId);
        list.sort(Comparator.comparing(OrgAssessReviewIndex::getTemplateIndexId));
        List<OrgAssessReviewIndexTreeVo> vos = BeanUtils.copyToList(list, OrgAssessReviewIndexTreeVo.class);

        return TreeUtils.toTree(vos, OrgAssessReviewIndexTreeVo::getTemplateIndexId,
                OrgAssessReviewIndexTreeVo::getTemplateIndexParentId,
                OrgAssessReviewIndexTreeVo::setChild, ROOT_PARENT_ID);
    }

    @CacheEvict(key = "'orgAssessDeptReviewId='+#orgAssessDeptReviewId")
    @Override
    public Boolean submit(String orgAssessDeptReviewId, List<OrgAssessReviewIndexSubmitParam> list) {
        return isEmpty(list) ? FALSE : orgAssessReviewIndexMapper.submit(list);
    }

    @Override
    public void removeByOrgAssessDeptReviewIds(List<String> orgAssessDeptReviewIds) {
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, OrgAssessReviewIndex, OrgAssessReviewIndex>composeExecute()
                .fields(OrgAssessReviewIndex::getOrgAssessDeptReviewId)
                .params(orgAssessDeptReviewIds)
                .remove();
        super.lambdaUpdate().in(OrgAssessReviewIndex::getOrgAssessDeptReviewId, orgAssessDeptReviewIds).remove();
    }
}
