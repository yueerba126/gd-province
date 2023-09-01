package com.sydata.safe.asess.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sydata.organize.domain.Dept;
import com.sydata.safe.asess.domain.*;
import com.sydata.safe.asess.param.ReviewAllotParam;
import com.sydata.safe.asess.param.ReviewAssessParam;
import com.sydata.safe.asess.param.ReviewPageParam;
import com.sydata.safe.asess.vo.ReviewGroupVo;
import com.sydata.safe.asess.vo.ReviewVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 粮食安全考核-考核评审Service接口
 *
 * @author system
 * @date 2023-04-03
 */
public interface IReviewService extends IService<Review> {

    /**
     * 分组分页列表
     *
     * @param pageParam 分页参数
     * @return 分组分页列表
     */
    Page<ReviewGroupVo> groupPage(ReviewPageParam pageParam);

    /**
     * 分页列表
     *
     * @param pageParam 分页参数
     * @return 分页列表
     */
    Page<ReviewVo> page(ReviewPageParam pageParam);

    /**
     * 操作权限
     *
     * @param deptId 部门ID
     * @return 权限标识
     */
    Boolean operationAuth(Long deptId);

    /**
     * 考核模板分配
     *
     * @param template     考核模板
     * @param list         考核模板指标列表
     * @param allotDate    分配日期
     * @param autoAllotMap 自动分配map
     * @return 分配结果
     */
    Boolean allot(Template template, List<TemplateIndex> list, LocalDate allotDate, Map<String, Dept> autoAllotMap);

    /**
     * 考核评审分配
     *
     * @param allotParam 考核评审分配参数
     * @return 分配结果
     */
    Boolean allot(ReviewAllotParam allotParam);

    /**
     * 单位考核绑定
     *
     * @param templateId         考核模板ID
     * @param assesses           单位考核列表
     * @param orgAssessIndexList 单位考核指标列表
     * @return 绑定结果
     */
    Boolean regionBind(String templateId, List<OrgAssess> assesses, List<OrgAssessIndex> orgAssessIndexList);

    /**
     * 单位考核提交
     *
     * @param orgAssessId 单位考核ID
     * @return 提交
     */
    boolean regionSubmit(String orgAssessId);

    /**
     * 单位考核退回
     *
     * @param orgAssessId 单位考核ID
     * @return 退回结果
     */
    boolean regionReset(String orgAssessId);

    /**
     * 操作部门提交总数
     *
     * @param id    考核评审ID
     * @param count 操作数量
     * @return 操作状态
     */
    Boolean operationDeptSubmitCount(String id, int count);

    /**
     * 评审考核
     *
     * @param assessParam 考核评审考核参数
     * @return 考核结果
     */
    Boolean assess(ReviewAssessParam assessParam);


    /**
     * 评审退回
     *
     * @param id 考核评审ID
     * @return 考核结果
     */
    Boolean reset(String id);


    /**
     * 根据考核模板ID查询考核评审指标映射
     *
     * @param templateId      考核模板ID
     * @param assessRegionIds 考核区域ID列表
     * @return 考核评审指标映射map<地区ID, map < 部门ID, map < 考核模板指标ID, 考核评审指标>>>
     */
    Map<String, Map<Long, Map<String, ReviewIndex>>> indexMaps(String templateId, List<String> assessRegionIds);

    /**
     * 考核模板撤回
     *
     * @param templateId 考核模板ID
     */
    void templateRevoke(String templateId);
}