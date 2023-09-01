/**
 * @filename:ApparitorSecureTypeBeanDao 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2020 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.storage.domain.ApparitorSecureType;
import com.sydata.dostrict.storage.param.ApparitorSecureTypeSelectParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**   
 * @Description:TODO(安全仓储-安全生产-制度类别数据访问层)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
public interface ApparitorSecureTypeMapper extends BaseMapper<ApparitorSecureType> {
    /**
     * 行政管理-文件类别树形结构
     *
     * @param param 查询参数
     * @return 树形节点
     */
    List<KuNode> selectFileTypeTreeList(@Param("param") ApparitorSecureTypeSelectParam param);
}
