package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.common.com.sydata.organize.permission.annotation.DataPermissionExclude;
import com.sydata.safe.asess.domain.ScoreIndex;
import com.sydata.safe.asess.param.ScoreIndexAssessParam;

import java.util.List;

/**
 * 粮食安全考核-考核评分指标Mapper接口
 *
 * @author system
 * @date 2023-04-03
 */
@DataPermissionExclude
public interface ScoreIndexMapper extends BaseMapper<ScoreIndex> {

    /**
     * 考核
     *
     * @param list 考核评分指标考核参数列表
     * @return 操作结果
     */
    boolean assess(List<ScoreIndexAssessParam> list);
}