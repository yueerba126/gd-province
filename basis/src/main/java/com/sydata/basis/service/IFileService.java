package com.sydata.basis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.basis.domain.File;
import com.sydata.basis.param.FilePageParam;
import com.sydata.basis.vo.FileVo;
import com.sydata.framework.databind.domain.DataBindQuery;

import java.util.Collection;

/**
 * @Description 基础信息-文件信息接口
 * @Author lzq
 * @Date 2022/10/11 16:23
 */
public interface IFileService extends IService<File> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<FileVo> pages(FilePageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    FileVo detail(String id);

    /**
     * 下载库区鸟瞰图
     *
     * @param stockHouseId 库区ID
     */
    void downloadPmt(String stockHouseId);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);
}
