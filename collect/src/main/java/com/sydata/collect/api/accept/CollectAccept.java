package com.sydata.collect.api.accept;

import cn.hutool.extra.spring.SpringUtil;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.event.DataCollectEvent;
import com.sydata.collect.api.param.BaseApiParam;
import com.sydata.collect.api.register.DataRegister;
import com.sydata.collect.api.validated.group.CorrectCheck;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author lzq
 * @description 数据收集接收器
 * @date 2022/10/19 11:00
 */
@Validated
@Component
public class CollectAccept {

    @Resource
    private DataRegister dataRegister;

    @SneakyThrows(Throwable.class)
    @Transactional(rollbackFor = RuntimeException.class)
    @Validated(CorrectCheck.class)
    public String accept(LocalDateTime beginTime, DataApiEnum apiEnum, boolean isDataHandle, @Valid BaseApiParam param) {
        // 处理数据
        String dataId = dataRegister.getByDataApi(apiEnum).collect(param);

        // 发布数据收集事件
        if (isDataHandle) {
            DataCollectEvent dataCollectEvent = new DataCollectEvent()
                    .setBeginTime(beginTime)
                    .setApiCode(apiEnum.getApiCode())
                    .setParam(param)
                    .setDataId(dataId);
            SpringUtil.publishEvent(dataCollectEvent);
        }
        return dataId;
    }
}
