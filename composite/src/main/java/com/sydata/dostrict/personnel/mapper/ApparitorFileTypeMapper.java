package com.sydata.dostrict.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.personnel.domain.ApparitorFileType;
import com.sydata.dostrict.personnel.param.ApparitorFileTypeParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 行政管理-文件类别管理Mapper接口
 *
 * @author fuql
 * @date 2023-04-24
 */
public interface ApparitorFileTypeMapper extends BaseMapper<ApparitorFileType> {

    /**
     * 行政管理-文件类别树形结构
     *
     * @param param 查询参数
     * @return 树形节点
     */
    List<KuNode> selectFileTypeTreeList(@Param("param")ApparitorFileTypeParam param);
}
