package com.sydata.dostrict.personnel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.domain.KuNode;
import com.sydata.dostrict.personnel.domain.ApparitorTitleType;
import com.sydata.dostrict.personnel.param.ApparitorTitleTypeParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 行政管理-称号类别管理Mapper接口
 *
 * @author fuql
 * @date 2023-04-25
 */
public interface ApparitorTitleTypeMapper extends BaseMapper<ApparitorTitleType> {

    /**
     * 行政管理-称号类别树形结构
     *
     * @param param 查询参数
     * @return 树形节点
     */
    List<KuNode> selectFileTypeTreeList(@Param("param") ApparitorTitleTypeParam param);
}