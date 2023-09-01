package com.sydata.organize.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.config.UserMessageConfig;
import com.sydata.organize.domain.User;
import com.sydata.organize.domain.UserMessage;
import com.sydata.organize.domain.UserMessageCount;
import com.sydata.organize.domain.UserRole;
import com.sydata.organize.enums.UserMessageTargetTypeEnum;
import com.sydata.organize.mapper.UserMessageMapper;
import com.sydata.organize.param.UserMessagePageParam;
import com.sydata.organize.param.UserMessageSendParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IUserMessageCountService;
import com.sydata.organize.service.IUserMessageService;
import com.sydata.organize.service.IUserRoleService;
import com.sydata.organize.service.IUserService;
import com.sydata.organize.vo.UserMessageVo;
import one.util.streamex.StreamEx;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.sydata.framework.cache.properties.CacheConfigProperties.KEY_SEGMENTATION;
import static com.sydata.organize.enums.UserMessageTargetTypeEnum.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 组织架构-用户消息Service业务层处理
 *
 * @author system
 * @date 2023-03-02
 */
@CacheConfig(cacheNames = UserMessageServiceImpl.CACHE_NAME)
@Service("userMessageService")
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements IUserMessageService {

    final static String CACHE_NAME = "organize:userMessage";

    private static final String UNREAD_QUEUE = CACHE_NAME + ":unReadQueue:userId=";

    private static final Map<UserMessageTargetTypeEnum, Function<String, List<String>>>
            TARGET_TYPE_FIND_USERS_MAP = new HashMap<>(UserMessageTargetTypeEnum.values().length);

    @Resource
    private UserMessageMapper userMessageMapper;

    @Resource
    private IUserService userService;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IUserMessageCountService userMessageCountService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserMessageConfig userMessageConfig;

    @PostConstruct
    public void init() {
        // 注册部门找用户ID的方法
        TARGET_TYPE_FIND_USERS_MAP.put(DEPT, deptId -> userService.idsByDeptId(Long.parseLong(deptId)));

        // 注册角色找用户ID的方法
        TARGET_TYPE_FIND_USERS_MAP.put(ROLE, roleId -> {
            List<UserRole> userRoles = userRoleService.listByRoleId(Long.parseLong(roleId));
            return StreamEx.of(userRoles).map(UserRole::getUserId).toList();
        });

        // 注册用户找用户ID的方法
        TARGET_TYPE_FIND_USERS_MAP.put(USER, Collections::singletonList);

        // 注册组织找用户ID的方法
        TARGET_TYPE_FIND_USERS_MAP.put(ORGANIZE, organizeId -> userService.idsByOrganizeId(organizeId));
    }

    @DataBindFieldConvert
    @Override
    public Page<UserMessageVo> pages(UserMessagePageParam pageParam) {
        Page<UserMessage> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getState()), UserMessage::getState, pageParam.getState())
                .eq(isNotEmpty(pageParam.getType()), UserMessage::getType, pageParam.getType())
                .eq(isNotEmpty(pageParam.getTargetType()), UserMessage::getTargetType, pageParam.getTargetType())
                .eq(isNotEmpty(pageParam.getModule()), UserMessage::getModule, pageParam.getModule())
                .eq(UserMessage::getUserId, UserSecurity.userId())
                .orderByDesc(UserMessage::getId)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, UserMessageVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean send(UserMessageSendParam sendParam) {
        UserMessageTargetTypeEnum targetType = UserMessageTargetTypeEnum.getByTargetType(sendParam.getTargetType());
        Assert.notNull(targetType, "目标类型不存在");

        // 根据目标类型和目标ID寻找用户ID列表
        Function<String, List<String>> userIdsFunction = TARGET_TYPE_FIND_USERS_MAP.get(targetType);
        Assert.notNull(userIdsFunction, "该目标类型并未设置寻找用户的方法");
        List<String> userIds = userIdsFunction.apply(sendParam.getTargetId());
        if (CollectionUtil.isEmpty(userIds)) {
            return FALSE;
        }

        // 根据用户ID查询用户列表后构建成用户消息进行批量新增
        String name = UserSecurity.userName();
        LocalDateTime now = LocalDateTime.now();
        List<UserMessage> userMessages = StreamEx.of(userService.listByIds(userIds))
                .filterBy(User::getOrganizeId, sendParam.getOrganizeId())
                .map(user -> {
                    UserMessage userMessage = BeanUtils.copyByClass(sendParam, UserMessage.class);
                    userMessage.setUserId(user.getId()).setOrganizeId(user.getOrganizeId()).setDeptId(user.getDeptId());
                    return userMessage.setCreateBy(name).setCreateTime(now);
                }).toList();
        boolean state = super.saveBatch(userMessages);

        // 统计用户消息数量
        userMessageCountService.statistics(userIds);

        // 插入到用户消息未读队列
        userMessages.forEach(messages -> {
            String key = buildUnreadQueueKey(messages.getUserId());

            // 控制每个用户最大未读消息数（防止队列消息长度过大）
            Long size = Optional.ofNullable(redisTemplate.opsForList().size(key)).orElse(0L);
            if (size >= userMessageConfig.getUnreadQueueMaxCount()) {
                long overflow = size - userMessageConfig.getUnreadQueueMaxCount();
                redisTemplate.opsForList().rightPop(key, overflow + 1);
            }

            // 插入消息并设置过期时间（防止队列消息永久堆积）
            redisTemplate.opsForList().leftPush(key, messages);
            redisTemplate.expire(key, userMessageConfig.getUnreadQueueOvertime(), TimeUnit.MINUTES);
        });
        return state;
    }

    @Override
    public List<UserMessage> pop(String userId, int count) {
        String key = buildUnreadQueueKey(userId);
        return redisTemplate.opsForList().rightPop(key, count);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public UserMessageCount read(List<String> ids) {
        String userId = UserSecurity.userId();

        // 将消息列表改为已读且返回修改行数
        AbstractWrapper wrapper = super.lambdaUpdate()
                .set(UserMessage::getState, TRUE)
                .set(UserMessage::getReadTime, LocalDateTime.now())
                .in(UserMessage::getId, ids)
                .eq(UserMessage::getUserId, userId)
                .eq(UserMessage::getState, FALSE)
                .getWrapper();
        int rows = userMessageMapper.update(null, wrapper);
        if (rows > 0) {
            userMessageCountService.read(userId, rows);
        }

        return userMessageCountService.getById(userId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public UserMessageCount allRead() {
        String userId = UserSecurity.userId();

        // 将用户所有未读消息改为已读且返回修改行数
        AbstractWrapper wrapper = super.lambdaUpdate()
                .set(UserMessage::getState, TRUE)
                .set(UserMessage::getReadTime, LocalDateTime.now())
                .eq(UserMessage::getUserId, userId)
                .eq(UserMessage::getState, FALSE)
                .getWrapper();
        int rows = userMessageMapper.update(null, wrapper);
        if (rows > 0) {
            userMessageCountService.read(userId, rows);
        }

        return userMessageCountService.getById(userId);
    }

    /**
     * 构建用户未读消息队列key(服务名:用户消息:unReadQueue:userId=用户ID)
     *
     * @param userId 用户ID
     * @return 用户未读消息队列key
     */
    private String buildUnreadQueueKey(String userId) {
        return SpringUtil.getApplicationName() + KEY_SEGMENTATION + UNREAD_QUEUE + userId;
    }
}