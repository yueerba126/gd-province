package com.sydata.report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.report.domain.CommandRecord;
import com.sydata.report.param.CommandRecordPageParam;

/**
 * 数据上报-国家平台指令接收记录Service接口
 *
 * @author lzq
 * @date 2022-10-31
 */
public interface ICommandRecordService extends IService<CommandRecord> {

    /**
     * 分页列表
     *
     * @param pageParam 分页列表参数
     * @return 记录列表
     */
    Page<CommandRecord> pages(CommandRecordPageParam pageParam);

    /**
     * 接收指令
     *
     * @param commandRecord 国家平台指令接收记录
     * @return 指令ID
     */
    String receive(CommandRecord commandRecord);

    /**
     * 获取最新的指令ID
     *
     * @return 指令ID
     */
    String lastOrderId();
}