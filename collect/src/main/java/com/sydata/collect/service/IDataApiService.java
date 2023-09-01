package com.sydata.collect.service;

import com.sydata.collect.vo.DataApiColumnsVo;

import java.util.List;

/**
 * @author lzq
 * @description 数据收集-数据API接口
 * @date 2023/4/27 16:26
 */
public interface IDataApiService {

    /**
     * 查询接口字段信息
     *
     * @param apiCode 接口编号
     * @return 数据API接口字段信息列表
     */
    List<DataApiColumnsVo> columns(String apiCode);
}
