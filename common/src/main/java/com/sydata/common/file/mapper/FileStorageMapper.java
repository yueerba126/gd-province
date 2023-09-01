package com.sydata.common.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.common.file.domain.FileStorage;

/**
 * 文件存储Mapper接口
 *
 * @author lzq
 * @date 2022-06-23
 */
@DataPermissionExclude
public interface FileStorageMapper extends BaseMapper<FileStorage> {
}