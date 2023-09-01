package com.sydata.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.report.domain.CommandRecord;

/**
 * 数据上报-国家平台指令接收记录Mapper接口
 *
 * @author lzq
 * @date 2022-10-31
 */
@DataPermissionExclude
public interface CommandRecordMapper extends BaseMapper<CommandRecord> {

}