package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.safe.asess.domain.OrgAssessDept;
import org.apache.ibatis.annotations.Param;

/**
 * 粮食安全考核-部门考核Mapper接口
 *
 * @author system
 * @date 2023-02-18
 */
public interface OrgAssessDeptMapper extends BaseMapper<OrgAssessDept> {

    /**
     * 操作提交自评部门数
     *
     * @param id    部门考核ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationDeptSubmitCount(@Param("id") String id, @Param("count") int count);
}