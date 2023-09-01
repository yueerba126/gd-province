package com.sydata.safe.asess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sydata.safe.asess.domain.OrgAssess;
import com.sydata.safe.asess.domain.OrgAssessDept;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 粮食安全考核-单位考核Mapper接口
 *
 * @author system
 * @date 2023-02-16
 */
public interface OrgAssessMapper extends BaseMapper<OrgAssess> {

    /**
     * 部门考核提交
     *
     * @param orgAssessDept    部门考核
     * @param leadAlreadyScore 已自评牵头总分
     * @return 操作状态
     */
    Boolean deptAssessSubmit(@Param("orgAssessDept") OrgAssessDept orgAssessDept,
                             @Param("leadAlreadyScore") BigDecimal leadAlreadyScore);

    /**
     * 部门考核退回
     *
     * @param orgAssessDept 部门考核
     * @return 操作状态
     */
    Boolean deptAssessReset(OrgAssessDept orgAssessDept);


    /**
     * 考核评审考核
     *
     * @param orgAssessId        单位考核ID
     * @param deptLeadCount      考核评分牵头考核指标数
     * @param deptCooperateCount 考核评分配合考核指标数
     * @param assessScore        考核评分牵头指标总分
     * @return 处理状态
     */
    Boolean assess(@Param("orgAssessId") String orgAssessId,
                   @Param("deptLeadCount") int deptLeadCount,
                   @Param("deptCooperateCount") int deptCooperateCount,
                   @Param("assessScore") BigDecimal assessScore);


    /**
     * 考核评审退回
     *
     * @param orgAssessId        单位考核ID
     * @param deptLeadCount      考核评分牵头考核指标数
     * @param deptCooperateCount 考核评分配合考核指标数
     * @param assessScore        考核评分牵头指标总分
     * @return 处理状态
     */
    Boolean assessReset(@Param("orgAssessId") String orgAssessId,
                        @Param("deptLeadCount") int deptLeadCount,
                        @Param("deptCooperateCount") int deptCooperateCount,
                        @Param("assessScore") BigDecimal assessScore);

    /**
     * 抽查
     *
     * @param orgAssessId     单位考核ID
     * @param checkAwaitCount 待抽查指标数
     * @param checkScore      抽查总分
     * @return 处理状态
     */
    Boolean check(@Param("orgAssessId") String orgAssessId,
                  @Param("checkAwaitCount") int checkAwaitCount,
                  @Param("checkScore") BigDecimal checkScore,
                  @Param("assessScoreProportion") BigDecimal assessScoreProportion,
                  @Param("checkScoreProportion") BigDecimal checkScoreProportion);
}