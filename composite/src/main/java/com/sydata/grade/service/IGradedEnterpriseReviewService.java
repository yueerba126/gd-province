/**
 * @filename:GradedEnterpriseReviewService 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2020 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.grade.domain.GradedEnterpriseReview;
import com.sydata.grade.param.GradedEnterpriseReviewApproveParam;
import com.sydata.grade.param.GradedEnterpriseReviewPageParam;
import com.sydata.grade.param.GradedEnterpriseReviewSaveParam;
import com.sydata.grade.param.GradedEnterpriseStockExportParam;
import com.sydata.grade.vo.GradedEnterpriseReviewVo;

import java.util.Collection;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核服务层)
 * @version: V1.0
 * @author: lzq
 *
 */
public interface IGradedEnterpriseReviewService extends IService<GradedEnterpriseReview> {

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedEnterpriseReviewVo> pages(GradedEnterpriseReviewPageParam pageParam);

    /**
     * 实地评审分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<GradedEnterpriseReviewVo> sdPages(GradedEnterpriseReviewPageParam pageParam);

    /**
     * 导出
     *
     * @param pageParam 参数
     */
    void exportData(GradedEnterpriseReviewPageParam pageParam);

    /**
     * 导出
     *
     * @param pageParam 参数
     */
    void exportDataSd(GradedEnterpriseReviewPageParam pageParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GradedEnterpriseReviewVo detail(String id);

    /**
     * 翻译
     *
     * @param list
     * @return list
     */
    List<GradedEnterpriseReviewVo> translate(List<GradedEnterpriseReviewVo> list);

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
    String saveData(GradedEnterpriseReviewSaveParam param);

    /**
     * 审批数据
     *
     * @param param 参数
     * @return 主键ID
     */
    Boolean approveData(GradedEnterpriseReviewApproveParam param);

    /**
     * 批量审批数据
     *
     * @param params 参数
     * @return 主键ID
     */
    Boolean batchApproveData(List<GradedEnterpriseReviewApproveParam> params);

    /**
     * 授牌
     *
     * @param param 参数
     * @return 主键ID
     */
    Boolean awardingData(GradedEnterpriseReviewApproveParam param);

    /**
     * 实地确认
     *
     * @param param 参数
     * @return 主键ID
     */
    Boolean indeedData(GradedEnterpriseReviewApproveParam param);

    /**
     * 修改
     *
     * @param param 参数
     * @return 主键ID
     */
    String updateData(GradedEnterpriseReviewSaveParam param);

    /**
     * 所在区域必须填写，并且一定要精确到县, 并获取对应的行政区划
     *
     * @param xzqhdm 参数
     * @return 主键ID
     */
    List<String> assertXzqhdm(String xzqhdm);

    /**
     * 所在区域必须填写，并且一定要精确到县, 并获取对应的行政区划
     *
     * @param xzqhdm 参数
     * @return 主键ID
     */
    List<String> getXzqhdms(String xzqhdm);

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
    GradedEnterpriseReview getByUnx(String qydm,String kqdm,String sbnf);

    /**
     * 唯一性
     *
     * @param qydm,kqdm 参数
     * @return 主键ID
     */
    GradedEnterpriseReview getByUnx(String qydm,String kqdm);

    /**
     * 验证可审核性
     *
     * @param id 参数
     * @return 主键ID
     */
    GradedEnterpriseReview verifyPassReview(String id);
    /**
     * 验证可申报性
     *
     * @param qydm,kqdm 参数
     * @return 主键ID
     */
    GradedEnterpriseReview verifyIndeedReview(String qydm, String kqdm);
    GradedEnterpriseReview verifyAwardingReview(String qydm, String kqdm);
    GradedEnterpriseReview verifyReduceReview(String qydm, String kqdm);
    GradedEnterpriseReview verifyPickReview(String qydm, String kqdm);


    /**
     * 唯一性
     *
     * @param gradedEnterpriseReviewSaveParam 参数
     * @return 主键ID
     */
    Boolean gradedEnterpriseHttpServiceSave(GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam);

    /**
     * 详情
     *
     * @param id 主键ID
     * @return 详情
     */
    GradedEnterpriseReviewSaveParam getGradedEnterpriseReviewSaveParam(String id);
}