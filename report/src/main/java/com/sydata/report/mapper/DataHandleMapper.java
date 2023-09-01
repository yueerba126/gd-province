package com.sydata.report.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.report.domain.DataHandle;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据收集-数据处理Mapper接口
 *
 * @author lzq
 * @date 2022-10-21
 */
public interface DataHandleMapper extends BaseMapper<DataHandle> {

    /**
     * 根据指定的时间查询未处理ID列表
     *
     * @param apiCode 接口编号
     * @param endTime 请求时间的结束区间
     * @return 未处理ID列表
     */
    List<String> notHandleIdsByEndTime(@Param("apiCode") String apiCode, @Param("endTime") LocalDateTime endTime);
}