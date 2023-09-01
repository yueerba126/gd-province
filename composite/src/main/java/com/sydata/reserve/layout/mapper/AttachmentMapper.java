package com.sydata.reserve.layout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.reserve.layout.domain.Attachment;

/**
 * 储备布局地理信息-附件文件 Mapper接口
 *
 * @author lzq
 * @date 2022-08-19
 */
@DataPermissionExclude
public interface AttachmentMapper extends BaseMapper<Attachment> {

}
