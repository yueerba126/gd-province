package com.sydata.organize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.AppApi;
import com.sydata.organize.param.AppApiPageParam;
import com.sydata.organize.vo.AppApiVo;
import com.sydata.organize.vo.AppSecretGenerateVo;

/**
 * 组织架构-app对接应用Service接口
 *
 * @author lzq
 * @date 2022-10-21
 */
public interface IAppApiService extends IService<AppApi> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<AppApiVo> pages(AppApiPageParam pageParam);

    /**
     * 详情
     *
     * @param appid 主键ID
     * @return 详情
     */
    AppApiVo detail(String appid);

    /**
     * 生成应用秘钥信息
     *
     * @return 生成应用秘钥信息
     */
    AppSecretGenerateVo generate();

    /**
     * 修改应用状态
     *
     * @param state 状态
     * @param appid 应用ID
     * @return 操作标识
     */
    Boolean updateEod(Boolean state, String appid);
}