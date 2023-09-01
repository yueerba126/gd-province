package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.UserMessageCount;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import org.apache.ibatis.annotations.Param;

/**
 * 组织架构-用户消息数量Mapper接口
 *
 * @author system
 * @date 2023-03-02
 */
@DataPermissionExclude
public interface UserMessageCountMapper extends BaseMapper<UserMessageCount> {

    /**
     * 统计用户消息数量
     *
     * @param userId 用户ID
     * @return 统计结果
     */
    boolean statistics(@Param("userId") String userId);

    /**
     * 已读消息
     *
     * @param userId 用户ID
     * @param count  已读数量
     * @return 已读状态
     */
    boolean read(@Param("userId") String userId, @Param("count") Integer count);
}