package com.sydata.organize.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.organize.domain.UserMessage;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;

/**
 * 组织架构-用户消息Mapper接口
 *
 * @author system
 * @date 2023-03-02
 */
@DataPermissionExclude
public interface UserMessageMapper extends BaseMapper<UserMessage> {

}