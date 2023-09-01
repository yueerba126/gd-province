package com.sydata.safe.asess.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.util.BeanUtils;
import com.sydata.safe.asess.domain.CheckGroupPersonnel;
import com.sydata.safe.asess.mapper.CheckGroupPersonnelMapper;
import com.sydata.safe.asess.param.CheckGroupPersonnelSaveParam;
import com.sydata.safe.asess.service.ICheckGroupPersonnelService;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Boolean.FALSE;

/**
 * 实地抽查人员Service业务层处理
 *
 * @author system
 * @date 2023-02-13
 */
@CacheConfig(cacheNames = CheckGroupPersonnelServiceImpl.CACHE_NAME)
@Service("checkGroupPersonnelService")
public class CheckGroupPersonnelServiceImpl extends ServiceImpl<CheckGroupPersonnelMapper, CheckGroupPersonnel>
        implements ICheckGroupPersonnelService {

    final static String CACHE_NAME = "safeAssess:checkGroupPersonnel";

    @Cacheable(key = "'checkGroupId='+#checkGroupId")
    @Override
    public List<CheckGroupPersonnel> listByCheckGroupId(String checkGroupId) {
        return super.lambdaQuery().eq(CheckGroupPersonnel::getCheckGroupId, checkGroupId).list();
    }

    @CacheEvict(key = "'checkGroupId='+#checkGroupId")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean setUpByCheckGroupId(String checkGroupId, List<CheckGroupPersonnelSaveParam> groupPersonnelList) {
        if (CollectionUtils.isEmpty(groupPersonnelList)) {
            return FALSE;
        }
        this.removeByCheckGroupId(checkGroupId);

        return StreamEx.of(groupPersonnelList)
                .map(groupPersonnel -> BeanUtils.copyByClass(groupPersonnel, CheckGroupPersonnel.class))
                .peek(personnel -> personnel.setCheckGroupId(checkGroupId))
                .toListAndThen(super::saveBatch);
    }

    @CacheEvict(key = "'checkGroupId='+#checkGroupId")
    @Override
    public boolean removeByCheckGroupId(String checkGroupId) {
        return super.lambdaUpdate().eq(CheckGroupPersonnel::getCheckGroupId, checkGroupId).remove();
    }
}