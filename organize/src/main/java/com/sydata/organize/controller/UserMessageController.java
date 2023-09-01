package com.sydata.organize.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.organize.domain.UserMessage;
import com.sydata.organize.domain.UserMessageCount;
import com.sydata.organize.param.UserMessagePageParam;
import com.sydata.organize.param.UserMessageSendParam;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IUserMessageCountService;
import com.sydata.organize.service.IUserMessageService;
import com.sydata.organize.vo.UserMessageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author lzq
 * @description 组织架构-用户消息API
 * @date 2023/3/2 15:19
 */
@Api(tags = "组织架构-用户消息API")
@Validated
@RestController
@RequestMapping("/org/user/message")
public class UserMessageController {

    @Resource
    private IUserMessageService userMessageService;

    @Resource
    private IUserMessageCountService userMessageCountService;

    @ApiOperation("分页列表")
    @PostMapping("/page")
    public Page<UserMessageVo> page(@RequestBody @Valid UserMessagePageParam pageParam) {
        return userMessageService.pages(pageParam);
    }

    @ApiOperation("发送消息")
    @PostMapping("/send")
    public Boolean send(@RequestBody @Valid UserMessageSendParam sendParam) {
        return userMessageService.send(sendParam);
    }

    @ApiOperation("获取当前用户消息数量")
    @PostMapping("/count")
    public UserMessageCount count() {
        return userMessageCountService.getById(UserSecurity.userId());
    }

    @ApiOperation("弹出用户未读消息列表")
    @PostMapping("/pop")
    public List<UserMessage> pop(@RequestParam("count") int count) {
        return userMessageService.pop(UserSecurity.userId(), count);
    }

    @ApiOperation("消息已读")
    @PostMapping("/read")
    public UserMessageCount read(@RequestBody @Valid @NotEmpty(message = "消息ID必传") List<String> ids) {
        return userMessageService.read(ids);
    }

    @ApiOperation("全部已读")
    @PostMapping("/all/read")
    public UserMessageCount allRead() {
        return userMessageService.allRead();
    }
}
