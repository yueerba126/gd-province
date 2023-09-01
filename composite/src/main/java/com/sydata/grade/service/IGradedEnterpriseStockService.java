/**
 * @filename:GradedEnterpriseStockService 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.grade.domain.GradedEnterpriseReview;
import com.sydata.grade.domain.GradedEnterpriseStock;
import com.sydata.grade.param.GradedEnterpriseStockExportParam;
import com.sydata.grade.param.GradedEnterpriseStockPageParam;
import com.sydata.grade.param.GradedEnterpriseStockParam;
import com.sydata.grade.param.GradedEnterpriseStockSaveParam;
import com.sydata.grade.vo.GradedEnterpriseStockVo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-企业等级库点服务层)
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IGradedEnterpriseStockService extends IService<GradedEnterpriseStock> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedEnterpriseStockVo> pages(GradedEnterpriseStockPageParam pageParam);

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedEnterpriseStockVo> pagesTwo(GradedEnterpriseStockPageParam pageParam);

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedEnterpriseStockVo> pagesThree(GradedEnterpriseStockPageParam pageParam);
    /**
     * 导出
     *
     * @param pageParam 参数
     */
    void exportDataAll(GradedEnterpriseStockPageParam pageParam);

    /**
     * 导出
     *
     * @param pageParam 参数
     */
    void exportData2A(GradedEnterpriseStockPageParam pageParam);

    /**
     * 导出
     *
     * @param pageParam 参数
     */
    void exportData3A(GradedEnterpriseStockPageParam pageParam);

    /**
     * 翻译
     *
     * @param  list
     */
    List<GradedEnterpriseStockVo> translate(List<GradedEnterpriseStock> list);


    /**
     * 摘除
     *
     * @param param 参数
     * @return 主键ID
     */
    Boolean pickRankData(GradedEnterpriseStockParam param);

    /**
     * 降级
     *
     * @param param 参数
     * @return 主键ID
     */
    Boolean reduceRankData(GradedEnterpriseStockParam param);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GradedEnterpriseStockVo detail(String id);

    /**
     * 数据绑定
     *
     * @param dataBindQuerys 数据查询列表
     */
    void dataBind(Collection<DataBindQuery> dataBindQuerys);

    /**
     * 新增
     *
     * @param param 参数
     * @return 主键ID
     */
    String saveData(GradedEnterpriseStockSaveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(GradedEnterpriseStockSaveParam param);

    /**
     * 删除
     *
     * @param ids 参数
     * @return 主键ID
     */
    Boolean removeData(List<String> ids);

    /**
     * 唯一性
     *
     * @param qydm,kqdm 参数
     * @return 主键ID
     */
    GradedEnterpriseStock getByUnx(String qydm, String kqdm);

    /**
     * 唯一性
     *
     * @param qydm,kqdm 参数
     * @return 主键ID
     */
    GradedEnterpriseStock getReduceByUnx(String qydm, String kqdm);

    /**
     * 唯一性
     *
     * @param qydm,kqdm 参数
     * @return 主键ID
     */
    GradedEnterpriseStock getPickByUnx(String qydm, String kqdm);

}