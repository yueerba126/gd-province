/**
 * @filename:ApparitorSecureSystemBeanDao 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.file.domain.FileStorage;
import com.sydata.dostrict.storage.domain.ApparitorSecureSystem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**   
 * @Description:TODO(安全仓储-安全生产-生产制度数据访问层)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface ApparitorSecureSystemMapper extends BaseMapper<ApparitorSecureSystem> {
    /**
     * 无权限查询附件
     *
     * @param fileId fileId
     * @return 附件
     */
    FileStorage queryFileStorage(@Param("fileId") String fileId);
}
