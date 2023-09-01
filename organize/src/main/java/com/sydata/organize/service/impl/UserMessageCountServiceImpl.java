package com.sydata.organize.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.cache.MultiCacheBatchHelp;
import com.sydata.framework.orm.FrameworkSqlHelper;
import com.sydata.organize.domain.UserMessageCount;
import com.sydata.organize.mapper.UserMessageCountMapper;
import com.sydata.organize.service.IUserMessageCountService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 * 组织架构-用户消息数量Service业务层处理
 *
 * @author system
 * @date 2023-03-02
 */
@CacheConfig(cacheNames = UserMessageCountServiceImpl.CACHE_NAME)
@Service("userMessageCountService")
public class UserMessageCountServiceImpl extends ServiceImpl<UserMessageCountMapper, UserMessageCount>
        implements IUserMessageCountService {

    final static String CACHE_NAME = "organize:userMessageCount";

    @Resource
    private UserMessageCountMapper userMessageCountMapper;

    @Cacheable(key = "'userId='+#id")
    @Override
    public UserMessageCount getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean statistics(List<String> userIds) {
        // 批量统计 不存在则插入[消息总数=1,未读总数=1]，存在则更新[消息总数+1,未读总数+1]
        FrameworkSqlHelper.executeBatchUpdate(this, userIds, userMessageCountMapper::statistics);

        // 删除缓存
        MultiCacheBatchHelp.apply(CACHE_NAME).<String, UserMessageCount, UserMessageCount>composeExecute()
                .fields(UserMessageCount::getUserId)
                .params(userIds)
                .remove();
        return TRUE;
    }

    @CacheEvict(key = "'userId='+#userId")
    @Override
    public boolean read(String userId, Integer count) {
        return userMessageCountMapper.read(userId, count);
    }
}