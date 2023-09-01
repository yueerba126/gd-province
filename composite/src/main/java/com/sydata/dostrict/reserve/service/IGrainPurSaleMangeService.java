package com.sydata.dostrict.reserve.service;

import com.sydata.dostrict.reserve.param.GrainSelectParam;
import com.sydata.dostrict.reserve.param.GrainWheetSelectParam;
import com.sydata.dostrict.reserve.vo.CompanyDistributionVo;
import com.sydata.dostrict.reserve.vo.RealAndPlanningQuantityVo;
import com.sydata.dostrict.reserve.vo.RealAndPlanningWheelOutAndInVo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: gd-province-platform
 * @description: 粮食储备-粮油购销管理 Service接口
 * @author: lzq
 * @create: 2023-04-24 18:47
 */
public interface IGrainPurSaleMangeService {
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取储备计划量和实际库存量在省储市储县储的统计信息
     * @Date 16:14 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.RealAndPlanningQuantityVo>
     **/
    RealAndPlanningQuantityVo listRealAndPlanningQuantityVo(@RequestBody @Valid GrainSelectParam param);
    /**
     * @Author lzq
     * @Description 根据行政区划和库点代码获取不同年份的轮入轮出在省储市储县储的统计信息
     * @Date 16:15 2023/4/25
     * @Param [GrainSelectParam]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.RealAndPlanningWheelOutAndInVo>
     **/
    List<RealAndPlanningWheelOutAndInVo> listRealAndPlanningWheelOutAndInVos(@RequestBody @Valid GrainWheetSelectParam param);
    /**
     * @Author lzq
     * @Description 根据行政区划获取企业分布信息
     * @Date 14:59 2023/4/27
     * @Param [param]
     * @return java.util.List<com.sydata.dostrict.reserve.vo.CompanyDistributionVo>
     **/
    List<CompanyDistributionVo> listCompanyDistributionVos(@Valid GrainSelectParam param);
}
