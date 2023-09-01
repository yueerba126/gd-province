package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.safe.asess.domain.ReviewIndex;
import com.sydata.safe.asess.param.ReviewIndexAllotParam;
import com.sydata.safe.asess.param.ReviewIndexAssessParam;
import com.sydata.safe.asess.param.ScoreIndexAssessParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 粮食安全考核-考核评审指标Mapper接口
 *
 * @author system
 * @date 2023-04-03
 */
@DataPermissionExclude
public interface ReviewIndexMapper extends BaseMapper<ReviewIndex> {

    /**
     * 考核评审指标分配
     *
     * @param reviewIds 考核ID列表
     * @param list      考核评审指标分配参数列表
     * @return 分配结果
     */
    Boolean allot(@Param("reviewIds") List<String> reviewIds, @Param("list") List<ReviewIndexAllotParam> list);

    /**
     * 考核评分牵头指标提交
     *
     * @param leads 考核评分牵头指标列表
     * @return 操作状态
     */
    Boolean scoreLeadIndexSubmit(List<ScoreIndexAssessParam> leads);

    /**
     * 考核
     *
     * @param list 考核评审指标考核参数列表
     * @return 操作状态
     */
    Boolean assess(List<ReviewIndexAssessParam> list);
}