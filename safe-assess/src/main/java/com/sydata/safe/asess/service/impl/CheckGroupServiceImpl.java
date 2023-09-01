package com.sydata.safe.asess.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.UserSecurity;
import com.sydata.safe.asess.annotation.DataBindCheckGroup;
import com.sydata.safe.asess.domain.CheckGroup;
import com.sydata.safe.asess.domain.CheckGroupPersonnel;
import com.sydata.safe.asess.mapper.CheckGroupMapper;
import com.sydata.safe.asess.param.CheckGroupPageParam;
import com.sydata.safe.asess.param.CheckGroupSaveParam;
import com.sydata.safe.asess.param.CheckGroupUpdateParam;
import com.sydata.safe.asess.service.ICheckGroupPersonnelService;
import com.sydata.safe.asess.service.ICheckGroupService;
import com.sydata.safe.asess.vo.CheckGroupVo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static com.sydata.framework.util.StringUtils.isNotEmpty;

/**
 * 粮安-实地抽查小组管理Service业务层处理
 *
 * @author system
 * @date 2023-02-10
 */
@CacheConfig(cacheNames = CheckGroupServiceImpl.CACHE_NAME)
@Service("checkGroupService")
public class CheckGroupServiceImpl extends ServiceImpl<CheckGroupMapper, CheckGroup> implements ICheckGroupService {

    final static String CACHE_NAME = "safeAssess:checkGroup";

    @Resource
    private CheckGroupMapper checkGroupMapper;

    @Resource
    private ICheckGroupPersonnelService groupPersonnelService;

    @Cacheable(key = "'id='+#id")
    @Override
    public CheckGroup getById(Serializable id) {
        return super.getById(id);
    }


    @CacheEvict(key = "'id='+#id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean removeById(Serializable id) {
        // 操作权限判断
        Assert.state(this.operationAuth((String) id), "非抽查小组所属组织人员不可操作");

        CheckGroup checkGroup = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.notNull(checkGroup, "小组不存在");

        // 删除小组成员
        groupPersonnelService.removeByCheckGroupId(checkGroup.getId());

        // 删除小组
        return super.removeById(id);
    }

    @DataBindFieldConvert
    @Override
    public Page<CheckGroupVo> pages(CheckGroupPageParam pageParam) {
        Page<CheckGroup> page = super.lambdaQuery()
                .likeRight(isNotEmpty(pageParam.getGroupName()), CheckGroup::getGroupName, pageParam.getGroupName())
                .eq(isNotEmpty(pageParam.getLeaderId()), CheckGroup::getLeaderId, pageParam.getLeaderId())
                .eq(isNotEmpty(pageParam.getLiaisonManId()), CheckGroup::getLiaisonManId, pageParam.getLiaisonManId())
                .eq(isNotEmpty(pageParam.getOrganizeId()), CheckGroup::getOrganizeId, pageParam.getOrganizeId())
                .orderByDesc(CheckGroup::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, CheckGroupVo.class);
    }

    @DataBindFieldConvert
    @Override
    public CheckGroupVo detail(String id) {
        CheckGroup checkGroup = SpringUtil.getBean(this.getClass()).getById(id);
        List<CheckGroupPersonnel> groupPersonnelList = groupPersonnelService.listByCheckGroupId(id);

        return BeanUtils.copyByClass(checkGroup, CheckGroupVo.class).setGroupPersonnelList(groupPersonnelList);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean save(CheckGroupSaveParam checkGroupSaveParam) {
        // 设置小组成员
        String id = IdWorker.getIdStr();
        groupPersonnelService.setUpByCheckGroupId(id, checkGroupSaveParam.getGroupPersonnelList());

        return super.save(BeanUtils.copyByClass(checkGroupSaveParam, CheckGroup.class).setId(id));
    }

    @CacheEvict(key = "'id='+#checkGroupUpdateParam.id")
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean update(CheckGroupUpdateParam checkGroupUpdateParam) {
        // 操作权限判断
        Assert.state(this.operationAuth(checkGroupUpdateParam.getId()), "非抽查小组所属组织人员不可操作");

        String id = checkGroupUpdateParam.getId();
        CheckGroup checkGroup = SpringUtil.getBean(this.getClass()).getById(id);
        Assert.notNull(checkGroup, "小组不存在");

        // 设置小组成员
        groupPersonnelService.setUpByCheckGroupId(id, checkGroupUpdateParam.getGroupPersonnelList());

        // 修改小组信息
        return super.updateById(BeanUtils.copyByClass(checkGroupUpdateParam, CheckGroup.class));
    }

    @Override
    public Long checkUserRelationCount(String checkUserId) {
        Long leaderCount = super.lambdaQuery().eq(CheckGroup::getLeaderId, checkUserId).count();
        Long liaisonCount = super.lambdaQuery().eq(CheckGroup::getLiaisonManId, checkUserId).count();
        return leaderCount + liaisonCount;
    }

    @Override
    public Boolean operationAuth(String id) {
        CheckGroup checkGroup = SpringUtil.getBean(this.getClass()).getById(id);
        return UserSecurity.organizeId().equals(checkGroup.getOrganizeId());
    }


    @DataBindService(strategy = DataBindCheckGroup.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, checkGroupMapper);
    }
}