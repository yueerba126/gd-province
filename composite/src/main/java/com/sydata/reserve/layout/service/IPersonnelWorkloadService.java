package com.sydata.reserve.layout.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sydata.reserve.layout.param.MonthlyReportPageParam;
import com.sydata.reserve.layout.param.PersonnelWorkloadPageParam;
import com.sydata.reserve.layout.vo.MonthlyReportVo;
import com.sydata.reserve.layout.vo.PersonnelWorkloadVo;

/**
 * 粮食质量-质检人员工作量统计Service接口
 *
 * @author lzq
 * @date 2022-07-08
 */
public interface IPersonnelWorkloadService {


    /**
     * 粮食质量-质检人员工作量统计
     *
     * @param pageParam pageParam
     * @return 质检人员工作量统计
     */
    Page<PersonnelWorkloadVo> pageWorkload(PersonnelWorkloadPageParam pageParam);

    /**
     * 粮食质量-质检人员工作量统计
     *
     * @param pageParam pageParam
     * @return 质检人员工作量统计
     */
    Page<MonthlyReportVo> monthlyReport(MonthlyReportPageParam pageParam);
}