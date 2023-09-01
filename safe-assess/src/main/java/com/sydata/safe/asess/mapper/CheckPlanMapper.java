package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.safe.asess.domain.CheckPlan;
import org.apache.ibatis.annotations.Param;

/**
 * 粮食安全考核-抽查计划Mapper接口
 *
 * @author system
 * @date 2023-02-13
 */
public interface CheckPlanMapper extends BaseMapper<CheckPlan> {

    /**
     * 检查提交
     *
     * @param id    计划ID
     * @param count 操作数量
     * @return 提交结果
     */
    boolean checkSubmit(@Param("id") String id, @Param("count") int count);
}
