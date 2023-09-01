package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.UserMessage;
import com.sydata.organize.domain.UserMessageCount;
import com.sydata.organize.param.UserMessagePageParam;
import com.sydata.organize.param.UserMessageSendParam;
import com.sydata.organize.vo.UserMessageVo;

import java.util.List;

/**
 * 组织架构-用户消息Service接口
 *
 * @author system
 * @date 2023-03-02
 */
public interface IUserMessageService extends IService<UserMessage> {

    /**
     * 分页列表
     *
     * @param pageParam 用户消息分页参数
     * @return 用户消息VO
     */
    Page<UserMessageVo> pages(UserMessagePageParam pageParam);

    /**
     * 发送消息
     *
     * @param sendParam 用户消息发送参数
     * @return 发送结果
     */
    Boolean send(UserMessageSendParam sendParam);

    /**
     * 弹出用户未读消息列表
     *
     * @param userId 用户ID
     * @param count  数量
     * @return 用户未读消息列表
     */
    List<UserMessage> pop(String userId, int count);

    /**
     * 已读消息
     *
     * @param ids 用户消息ID列表
     * @return 用户消息数量
     */
    UserMessageCount read(List<String> ids);

    /**
     * 全部已读
     *
     * @return 用户消息数量
     */
    UserMessageCount allRead();
}
