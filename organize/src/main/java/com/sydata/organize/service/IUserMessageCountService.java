package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.UserMessageCount;

import java.util.List;

/**
 * 组织架构-用户消息数量Service接口
 *
 * @author system
 * @date 2023-03-02
 */
public interface IUserMessageCountService extends IService<UserMessageCount> {

    /**
     * 统计用户消息数量
     *
     * @param userIds 用户ID列表
     * @return 统计结果
     */
    boolean statistics(List<String> userIds);

    /**
     * 已读消息
     *
     * @param userId 用户ID
     * @param count  已读数量
     * @return 已读状态
     */
    boolean read(String userId, Integer count);
}
