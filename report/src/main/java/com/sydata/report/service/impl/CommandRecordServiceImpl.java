package com.sydata.report.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.report.domain.CommandRecord;
import com.sydata.report.mapper.CommandRecordMapper;
import com.sydata.report.param.CommandRecordPageParam;
import com.sydata.report.service.ICommandRecordService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 数据上报-国家平台指令接收记录Service业务层处理
 *
 * @author lzq
 * @date 2022-10-31
 */
@CacheConfig(cacheNames = CommandRecordServiceImpl.CACHE_NAME)
@Service("commandRecordService")
public class CommandRecordServiceImpl extends ServiceImpl<CommandRecordMapper, CommandRecord>
        implements ICommandRecordService {

    final static String CACHE_NAME = "report:commandRecord";

    @Resource
    private CommandRecordMapper commandRecordMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Page<CommandRecord> pages(CommandRecordPageParam pageParam) {
        return super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getOrderid()), CommandRecord::getOrderid, pageParam.getOrderid())
                .eq(isNotEmpty(pageParam.getType()), CommandRecord::getType, pageParam.getType())
                .ge(isNotEmpty(pageParam.getBeginReceiveTime()), CommandRecord::getReceiveTime, pageParam.getBeginReceiveTime())
                .le(isNotEmpty(pageParam.getEndReceiveTime()), CommandRecord::getReceiveTime, pageParam.getEndReceiveTime())
                .orderByDesc(CommandRecord::getId)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
    }

    @CachePut(key = "'lastOrderId'", condition = "'1'.equals(#commandRecord.type)")
    @Override
    public String receive(CommandRecord commandRecord) {
        super.save(commandRecord.setReceiveTime(LocalDateTime.now()));
        return commandRecord.getOrderid();
    }

    @Cacheable(key = "'lastOrderId'")
    @Override
    public String lastOrderId() {
        return super.lambdaQuery()
                .select(CommandRecord::getOrderid)
                .eq(CommandRecord::getType, "1")
                .orderByDesc(CommandRecord::getReceiveTime)
                .page(new Page<>(1, 1))
                .getRecords()
                .stream()
                .findFirst()
                .map(CommandRecord::getOrderid)
                .orElse(null);
    }
}
