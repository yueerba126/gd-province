/**
 * @filename:GradedEnterpriseReviewServiceImpl 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2018 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.sydata.collect.api.enums.DataApiEnum;
import com.sydata.collect.api.param.grade.GradeApiParam;
import com.sydata.collect.api.service.impl.BaseDataImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import com.sydata.framework.util.BeanUtils;
import com.sydata.grade.annotation.DataBindGradedEnterpriseReview;
import com.sydata.grade.domain.GradedEnterpriseReview;
import com.sydata.grade.domain.GradedEnterpriseStock;
import com.sydata.grade.dto.GradedEnterpriseReviewDto;
import com.sydata.grade.enums.*;
import com.sydata.grade.http.GradedEnterpriseReviewFactoryService;
import com.sydata.grade.mapper.GradedEnterpriseReviewMapper;
import com.sydata.grade.param.*;
import com.sydata.grade.service.*;
import com.sydata.grade.vo.*;
import com.sydata.organize.domain.Region;
import com.sydata.organize.enums.RegionTypeEnum;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.organize.service.IRegionService;
import jodd.util.StringUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.sydata.collect.api.enums.DataApiEnum.GRADE_REVIEW;

/**
 * @Description:TODO(等级粮库评定管理-企业申报审核服务实现)
 * @version: V1.0
 * @author: lzq
 */
@Service("gradedEnterpriseReviewService")
public class GradedEnterpriseReviewServiceImpl
        extends BaseDataImpl<GradeApiParam, GradedEnterpriseReviewMapper, GradedEnterpriseReview, Object>
        implements IGradedEnterpriseReviewService {

    final static String CACHE_NAME = "composite:gradedEnterpriseReview";

    @Resource
    private GradedEnterpriseReviewMapper gradedEnterpriseReviewMapper;

    @Resource
    private IGradedEnterpriseProcessService gradedEnterpriseProcessService;

    @Resource
    private IGradedEnterpriseFlowService gradedEnterpriseFlowService;

    @Resource
    private IGradedEnterpriseStockService gradedEnterpriseStockService;

    @Resource
    private IGradedEnterpriseSelfAssessmentService gradedEnterpriseSelfAssessmentService;

    @Resource
    private IGradedEnterpriseMaterialService gradedEnterpriseMaterialService;

    @Resource
    private IRegionService regionService;

    @Resource
    private GradedEnterpriseReviewFactoryService gradedEnterpriseReviewFactoryService;

    @Override
    public boolean updateById(GradedEnterpriseReview param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseReview gradedEnterpriseReview = BeanUtils.copyByClass(param, GradedEnterpriseReview.class);
        gradedEnterpriseReview.setCreateBy(loginUser.getName());
        gradedEnterpriseReview.setUpdateBy(loginUser.getName());
        gradedEnterpriseReview.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseReview.setCreateTime(LocalDateTime.now());
        gradedEnterpriseReview.setSpzt(null);
        gradedEnterpriseReview.setCitySdzt(null);
        gradedEnterpriseReview.setProvinceSdzt(null);
        gradedEnterpriseReview.setCzbz(CzBzEnum.I.getCzBz());
        gradedEnterpriseReview.setSbnf(String.valueOf(param.getSbrq().getYear()));

        // 验证是否有资格申报粮库等级
        verifyStock(param.getQydm(), param.getKqdm(), param.getSbnf());

        // 所在区域必须填写，并且一定要精确到县
        assertXzqhdm(param.getXzqhdm());

        GradedEnterpriseReview unx = getByUnx(param.getQydm(), param.getKqdm(), param.getSbnf());
        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在企业和库点以及申报年份相同的申报数据");
        }

        // 等级粮库验证
        verifyLevels(param.getQydm(), param.getKqdm(), param.getSbdj(), param.getLkcr());

        super.updateById(gradedEnterpriseReview);
        param.setId(gradedEnterpriseReview.getId());
        return true;
    }

    private void verifyStock(String qydm, String kqdm, String sbnf) {
        GradedEnterpriseReview gradedEnterpriseReview = getByUnx(qydm, kqdm, sbnf);
        if (ObjectUtils.isNotEmpty(gradedEnterpriseReview)) {
            List<GradedEnterpriseFlowVo> gradedEnterpriseFlowVos = gradedEnterpriseFlowService.verifyNotPass(gradedEnterpriseReview.getId());
            if (CollectionUtil.isNotEmpty(gradedEnterpriseFlowVos)) {
                GradedEnterpriseFlowVo gradedEnterpriseFlowVo = gradedEnterpriseFlowVos.get(0);
                if (ObjectUtils.isNotEmpty(gradedEnterpriseFlowVo)) {
                    if (ObjectUtils.isNotEmpty(gradedEnterpriseFlowVo)) {
                        if (StringUtils.equals(gradedEnterpriseFlowVo.getFlowCode(), GradeFlowNodeEnum.D.getState())) {
                            Assert.isTrue(LocalDateTime.now().isAfter(gradedEnterpriseReview.getUpdateTime().plusYears(1)),
                                    "在" + LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime(),"yyyy-MM-dd HH:mm:ss") + "市级实地核查不通过，只能" +
                                            LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime().plusYears(1),"yyyy-MM-dd HH:mm:ss") + "才能再次申报");
                        } else if (StringUtils.equals(gradedEnterpriseFlowVo.getFlowCode(), GradeFlowNodeEnum.F.getState())) {
                            Assert.isTrue(LocalDateTime.now().isAfter(gradedEnterpriseReview.getUpdateTime().plusYears(1)),
                                    "在" + LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime(),"yyyy-MM-dd HH:mm:ss") + "省级级实地核查不通过，只能" +
                                            LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime().plusYears(1),"yyyy-MM-dd HH:mm:ss") + "才能再次申报");
                        } else if (StringUtils.equals(gradedEnterpriseFlowVo.getFlowCode(), GradeFlowNodeEnum.G.getState())) {
                            GradedEnterpriseStock reduced = gradedEnterpriseStockService.getReduceByUnx(qydm, kqdm);
                            GradedEnterpriseStock picked = gradedEnterpriseStockService.getPickByUnx(qydm, kqdm);
                            if (ObjectUtils.isNotEmpty(picked)) {
                                Assert.isTrue(LocalDateTime.now().isAfter(gradedEnterpriseReview.getUpdateTime().plusYears(2)),
                                        "在" + LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime(),"yyyy-MM-dd HH:mm:ss") + "被摘除，只能" +
                                                LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime().plusYears(2),"yyyy-MM-dd HH:mm:ss") + "才能再次申报");
                            } else if (ObjectUtils.isNotEmpty(reduced)) {
                                Assert.isTrue(LocalDateTime.now().isAfter(gradedEnterpriseReview.getUpdateTime().plusYears(1)),
                                        "在" + LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime(),"yyyy-MM-dd HH:mm:ss") + "被降级，只能" +
                                                LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime().plusYears(1),"yyyy-MM-dd HH:mm:ss") + "才能再次申报");
                            } else {
                                Assert.isTrue(LocalDateTime.now().isAfter(gradedEnterpriseReview.getUpdateTime().plusYears(1)),
                                        "在" + LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime(),"yyyy-MM-dd HH:mm:ss") + "授牌不通过，只能" +
                                                LocalDateTimeUtil.format(gradedEnterpriseReview.getUpdateTime().plusYears(1),"yyyy-MM-dd HH:mm:ss") + "才能再次申报");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean removeById(GradedEnterpriseReview entity) {
        Assert.isTrue(false, "不允许删除");
        return super.removeById(entity);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean save(GradedEnterpriseReview param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseReview gradedEnterpriseReview = BeanUtils.copyByClass(param, GradedEnterpriseReview.class);
        gradedEnterpriseReview.setCreateBy(loginUser.getName());
        gradedEnterpriseReview.setUpdateBy(loginUser.getName());
        gradedEnterpriseReview.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseReview.setCreateTime(LocalDateTime.now());
        gradedEnterpriseReview.setSpzt(null);
        gradedEnterpriseReview.setCitySdzt(null);
        gradedEnterpriseReview.setProvinceSdzt(null);
        gradedEnterpriseReview.setCzbz(CzBzEnum.I.getCzBz());
        gradedEnterpriseReview.setSbnf(String.valueOf(param.getSbrq().getYear()));

        // 验证是否有资格申报粮库等级
        verifyStock(param.getQydm(), param.getKqdm(), param.getSbnf());

        // 所在区域必须填写，并且一定要精确到县
        assertXzqhdm(param.getXzqhdm());

        // 等级粮库验证
        verifyLevels(param.getQydm(), param.getKqdm(), param.getSbdj(), param.getLkcr());

        super.save(gradedEnterpriseReview);
        param.setId(gradedEnterpriseReview.getId());
        return true;
    }

    @Override
    protected void collectAfter(GradeApiParam param, GradedEnterpriseReview gradedEnterpriseReview) {
        // 初始化流程
        List<GradedEnterpriseFlowSaveParam> gradedEnterpriseFlowSaveParams = BeanUtils.copyToList(param.getGradedEnterpriseFlowSaveParams(),
                GradedEnterpriseFlowSaveParam.class);
        gradedEnterpriseFlowService.saveOrUpdateBatchData(gradedEnterpriseFlowSaveParams);

        // 初始化审核详情
        List<GradedEnterpriseProcessSaveParam> gradedEnterpriseProcessSaveParams = BeanUtils.copyToList(param.getGradedEnterpriseProcessSaveParams(),
                GradedEnterpriseProcessSaveParam.class);
        gradedEnterpriseProcessService.saveOrUpdateBatchData(gradedEnterpriseProcessSaveParams);

        // 初始化证明材料
        List<GradedEnterpriseMaterialSaveParam> gradedEnterpriseMaterialSaveParams = BeanUtils.copyToList(param.getGradedEnterpriseMaterialSaveParams(),
                GradedEnterpriseMaterialSaveParam.class);
        gradedEnterpriseMaterialService.saveOrUpdateBatchData(gradedEnterpriseMaterialSaveParams);

        // 初始化实地信息
        List<GradedEnterpriseSelfAssessmentSaveParam> gradedEnterpriseSelfAssessmentSaveParams = BeanUtils.copyToList(param.getGradedEnterpriseSelfAssessmentSaveParams(),
                GradedEnterpriseSelfAssessmentSaveParam.class);
        gradedEnterpriseSelfAssessmentService.saveOrUpdateBatchData(gradedEnterpriseSelfAssessmentSaveParams);
    }

    @DataBindFieldConvert
    @Override
    public Page<GradedEnterpriseReviewVo> pages(GradedEnterpriseReviewPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        String regionId = loginUser.getRegionId();
        pageParam.setXzqhdm(regionId);
        Region region = regionService.getById(regionId);
        // 如果是省的话只让看到AAA级以上
        if (StringUtil.equals(RegionTypeEnum.PROVINCE.getType(), region.getType())) {
            pageParam.setCheckSbdj("1");
        } else {
            pageParam.setCheckSbdj("2");
        }
        Page<GradedEnterpriseReviewVo> reviewVos = gradedEnterpriseReviewMapper.getGradedEnterpriseReviewList(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()), pageParam);
        switchState(reviewVos.getRecords(), regionId);
        List<GradedEnterpriseReviewVo> gradedEnterpriseReviewVos = reviewVos.getRecords().stream().
                filter(e -> StringUtils.equals(e.getLastZsSbzt(), GradeFlowStatusEnum.PASS.getState())&&
                        !StringUtils.equals(e.getLastZsSbzt(), GradeFlowStatusEnum.NO_PASS.getState())&&
                                    !StringUtils.equals(e.getCitySdzt(), GradeSdztEnum.NO_PASS.getState())&&
                                        !StringUtils.equals(e.getProvinceSdzt(), GradeSdztEnum.NO_PASS.getState())&&
                                                !StringUtils.equals(e.getSpzt(), GradeAwardingEnum.NO_PASS.getState())&&
                                                !StringUtils.equals(e.getSpzt(), GradeAwardingEnum.PASS.getState())
                ).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(gradedEnterpriseReviewVos)) {
            for (int i = 0; i < gradedEnterpriseReviewVos.size(); i++) {
                GradedEnterpriseReviewVo gradedEnterpriseReviewVo = gradedEnterpriseReviewVos.get(i);
                String czzt = gradedEnterpriseReviewVo.getCzzt();
                if (StringUtil.equals(RegionTypeEnum.PROVINCE.getType(), region.getType())) {
                    if (StringUtil.equals(GradeFlowNodeEnum.F.getState(), czzt) ||
                            StringUtil.equals(GradeFlowNodeEnum.G.getState(), czzt) ||
                            StringUtil.equals(GradeFlowNodeEnum.H.getState(), czzt)) {
                    } else {
                    }
                } else if (StringUtil.equals(RegionTypeEnum.CITY.getType(), region.getType())) {
                    if (StringUtil.equals(GradeFlowNodeEnum.D.getState(), czzt) ||
                            StringUtil.equals(GradeFlowNodeEnum.E.getState(), czzt)||
                            StringUtil.equals(GradeFlowNodeEnum.H.getState(), czzt)) {
                    } else {
                    }
                } else {
                    if (StringUtil.equals(GradeFlowNodeEnum.C.getState(), czzt)) {
                    } else {
                    }
                }
            }
        }
        reviewVos.setRecords(gradedEnterpriseReviewVos);
        reviewVos.setTotal(gradedEnterpriseReviewVos.size());
        return reviewVos;
    }

    private void switchState(List<GradedEnterpriseReviewVo> reviewVos, String regionId) {
        if (CollectionUtil.isNotEmpty(reviewVos)) {
            for (int i = 0; i < reviewVos.size(); i++) {
                GradedEnterpriseReviewVo gradedEnterpriseReviewVo = reviewVos.get(i);
                String[] flowCodes = gradedEnterpriseReviewVo.getFlowCode().split(",");
                String[] flowStatuss = gradedEnterpriseReviewVo.getFlowStatus().split(",");
                String[] flowXzqhdms = gradedEnterpriseReviewVo.getFlowXzqhdm().split(",");
                for (int j = 0; j < flowCodes.length; j++) {
                    if (!StringUtils.equals(flowXzqhdms[j], regionId)) {
                        continue;
                    }
                    // 县粮食局审核
                    if (StringUtils.equals(flowCodes[j], GradeFlowNodeEnum.C.getState())) {
                        if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.PASS.getState())) {
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setButtonStr("详情");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.D.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(null);
                            continue;
                        } else if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.NO_PASS.getState())) {
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setButtonStr("详情,审核");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.C.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(null);
                            break;
                        } else {
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setButtonStr("详情,审核");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.C.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(null);
                            break;
                        }
                    }
                    // 市粮食局审核
                    else if (StringUtils.equals(flowCodes[j], GradeFlowNodeEnum.D.getState())) {
                        if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.PASS.getState())) {
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j + 1]);
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setButtonStr("详情,市实地");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.E.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DPF.getState());
                            continue;
                        } else if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.NO_PASS.getState())) {
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setButtonStr("详情");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.D.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(null);
                            break;
                        } else {
                            if (j > 0) {
                                if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.WAIT.getState())) {
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setButtonStr("详情");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.C.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                } else if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.PASS.getState())) {
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setButtonStr("详情,审核");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.D.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                } else {
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setButtonStr("详情");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.C.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                }
                            } else {
                                gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                gradedEnterpriseReviewVo.setButtonStr("详情,审核");
                                gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.D.getState());
                                gradedEnterpriseReviewVo.setCommonStatus(null);
                            }
                            break;
                        }
                    }
                    // 市级实地核查
                    else if (StringUtils.equals(flowCodes[j], GradeFlowNodeEnum.E.getState())) {
                        if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.PASS.getState())) {
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setButtonStr("详情");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.F.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(null);
                            continue;
                        } else if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.NO_PASS.getState())) {
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setButtonStr("详情,市实地");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.E.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DPF.getState());
                            break;
                        } else {
                            if (j > 0) {
                                if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.WAIT.getState())) {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.D.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                } else if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.PASS.getState())) {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.PASS.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情,市实地");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.E.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DPF.getState());
                                } else {
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.D.getState());
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情");
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                }
                            } else {
                                gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                gradedEnterpriseReviewVo.setButtonStr("详情,市实地");
                                gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.E.getState());
                                gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DPF.getState());
                            }
                            break;
                        }
                    }
                    // 省粮食局审核
                    else if (StringUtils.equals(flowCodes[j], GradeFlowNodeEnum.F.getState())) {
                        if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.PASS.getState())) {
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j-1]);
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setButtonStr("详情,省实地");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.G.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DPF.getState());
                            continue;
                        } else if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.NO_PASS.getState())) {
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setButtonStr("详情");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.F.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(null);
                            break;
                        } else {
                            if (j > 0) {
                                if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.WAIT.getState())) {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.E.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                } else if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.PASS.getState())) {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.F.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情,审核");
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                } else {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.E.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情");
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                }
                            } else {
                                gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.F.getState());
                                gradedEnterpriseReviewVo.setButtonStr("详情,审核");
                                gradedEnterpriseReviewVo.setCommonStatus(null);
                            }
                            break;
                        }
                    }
                    // 省级实地核查
                    else if (StringUtils.equals(flowCodes[j], GradeFlowNodeEnum.G.getState())) {
                        if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.PASS.getState())) {
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j + 1]);
                            gradedEnterpriseReviewVo.setButtonStr("详情,授牌");
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.H.getState());
                            gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DSP.getState());
                            continue;
                        } else if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.NO_PASS.getState())) {
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.G.getState());
                            gradedEnterpriseReviewVo.setButtonStr("详情,省实地");
                            gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DPF.getState());
                            break;
                        } else {
                            if (j > 0) {
                                if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.WAIT.getState())) {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.F.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                } else if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.PASS.getState())) {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.PASS.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情,省实地");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.G.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DPF.getState());
                                } else {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.F.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                }
                            } else {
                                gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                gradedEnterpriseReviewVo.setButtonStr("详情,省实地");
                                gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.G.getState());
                                gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DPF.getState());
                            }
                            break;
                        }
                    }
                    // 通过授牌
                    else if (StringUtils.equals(flowCodes[j], GradeFlowNodeEnum.H.getState())) {
                        if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.PASS.getState())) {
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.H.getState());
                            gradedEnterpriseReviewVo.setButtonStr("详情");
                            gradedEnterpriseReviewVo.setCommonStatus(null);
                            continue;
                        } else if (StringUtils.equals(flowStatuss[j], GradeFlowStatusEnum.NO_PASS.getState())) {
                            gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                            gradedEnterpriseReviewVo.setZsSbzt(flowStatuss[j]);
                            gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.H.getState());
                            gradedEnterpriseReviewVo.setButtonStr("详情");
                            gradedEnterpriseReviewVo.setCommonStatus(null);
                            break;
                        } else {
                            if (j > 0) {
                                if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.WAIT.getState())) {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情,省实地");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.G.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DPF.getState());
                                } else if (StringUtils.equals(flowStatuss[j - 1], GradeFlowStatusEnum.PASS.getState())) {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情,授牌");
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.H.getState());
                                    gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DSP.getState());
                                } else {
                                    gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                    gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.G.getState());
                                    gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                    gradedEnterpriseReviewVo.setButtonStr("详情");
                                    gradedEnterpriseReviewVo.setCommonStatus(null);
                                }
                            } else {
                                gradedEnterpriseReviewVo.setLastZsSbzt(flowStatuss[j - 1]);
                                gradedEnterpriseReviewVo.setZsSbzt(GradeFlowStatusEnum.WAIT.getState());
                                gradedEnterpriseReviewVo.setButtonStr("详情,授牌");
                                gradedEnterpriseReviewVo.setCzzt(GradeFlowNodeEnum.H.getState());
                                gradedEnterpriseReviewVo.setCommonStatus(GradeCommonEnum.DSP.getState());
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @DataBindFieldConvert
    @Override
    public Page<GradedEnterpriseReviewVo> sdPages(GradedEnterpriseReviewPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        String regionId = loginUser.getRegionId();
        pageParam.setXzqhdm(regionId);
        Region region = regionService.getById(regionId);
        // 如果是县的话不让看到实地列表
        if (StringUtil.equals(RegionTypeEnum.AREA.getType(), region.getType())) {
            pageParam.setXzqhdm(IdUtil.simpleUUID());
        }
        // 如果是省的话只让看到AAA级以上
        if (StringUtil.equals(RegionTypeEnum.PROVINCE.getType(), region.getType())) {
            pageParam.setCheckSbdj("1");
        } else {
            pageParam.setCheckSbdj("2");
        }
        Page<GradedEnterpriseReviewVo> reviewVos = gradedEnterpriseReviewMapper.
                getSdGradedEnterpriseReviewList(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()), pageParam);
        switchState(reviewVos.getRecords(), regionId);
        List<GradedEnterpriseReviewVo> gradedEnterpriseReviewVos = reviewVos.getRecords().stream().
                filter(e -> StringUtils.equals(e.getLastZsSbzt(), GradeFlowStatusEnum.PASS.getState()) &&
                        !StringUtils.equals(e.getLastZsSbzt(), GradeFlowStatusEnum.NO_PASS.getState()) &&
                                !StringUtils.equals(e.getCitySdzt(), GradeSdztEnum.NO_PASS.getState())&&
                                !StringUtils.equals(e.getProvinceSdzt(), GradeSdztEnum.NO_PASS.getState())&&
                        !StringUtils.equals(e.getSpzt(), GradeAwardingEnum.NO_PASS.getState())&&
                        !StringUtils.equals(e.getSpzt(), GradeAwardingEnum.PASS.getState())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(gradedEnterpriseReviewVos)) {
            for (int i = 0; i < gradedEnterpriseReviewVos.size(); i++) {
                GradedEnterpriseReviewVo gradedEnterpriseReviewVo = gradedEnterpriseReviewVos.get(i);
                String czzt = gradedEnterpriseReviewVo.getCzzt();
                if (StringUtil.equals(RegionTypeEnum.PROVINCE.getType(), region.getType())) {
                    if (StringUtil.equals(GradeFlowNodeEnum.G.getState(), czzt) ||
                            StringUtil.equals(GradeFlowNodeEnum.H.getState(), czzt)) {
                    } else {
                        gradedEnterpriseReviewVos.remove(i);
                        i--;
                    }
                } else if (StringUtil.equals(RegionTypeEnum.CITY.getType(), region.getType())) {
                    if (StringUtil.equals(GradeFlowNodeEnum.E.getState(), czzt)||
                            StringUtil.equals(GradeFlowNodeEnum.H.getState(), czzt)) {
                    } else {
                        gradedEnterpriseReviewVos.remove(i);
                        i--;
                    }
                } else {
                    gradedEnterpriseReviewVos.remove(i);
                    i--;
                }
            }
        }
        if(!StringUtils.isEmpty(pageParam.getCommonStatus())){
            gradedEnterpriseReviewVos = gradedEnterpriseReviewVos.stream().
                    filter(e->StringUtils.equals(e.getCommonStatus(),pageParam.getCommonStatus())).collect(Collectors.toList());
        }
        reviewVos.setRecords(gradedEnterpriseReviewVos);
        reviewVos.setTotal(gradedEnterpriseReviewVos.size());
        return reviewVos;
    }

    @DataBindFieldConvert
    @Override
    public void exportData(GradedEnterpriseReviewPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        String regionId = loginUser.getRegionId();
        pageParam.setXzqhdm(regionId);
        Region region = regionService.getById(regionId);
        // 如果是省的话只让看到AAA级以上
        if (StringUtil.equals(RegionTypeEnum.PROVINCE.getType(), region.getType())) {
            pageParam.setCheckSbdj("1");
        } else {
            pageParam.setCheckSbdj("2");
        }
        List<GradedEnterpriseReviewVo> reviewVos = gradedEnterpriseReviewMapper.getGradedEnterpriseReviewListByIds(pageParam);
        switchState(reviewVos, regionId);
        List<GradedEnterpriseReviewVo> gradedEnterpriseReviewVos = reviewVos.stream().
                filter(e -> StringUtils.equals(e.getLastZsSbzt(), GradeFlowStatusEnum.PASS.getState())&&
                        !StringUtils.equals(e.getLastZsSbzt(), GradeFlowStatusEnum.NO_PASS.getState())&&
                        !StringUtils.equals(e.getCitySdzt(), GradeSdztEnum.NO_PASS.getState())&&
                        !StringUtils.equals(e.getProvinceSdzt(), GradeSdztEnum.NO_PASS.getState())&&
                        !StringUtils.equals(e.getSpzt(), GradeAwardingEnum.NO_PASS.getState())&&
                        !StringUtils.equals(e.getSpzt(), GradeAwardingEnum.PASS.getState())
                ).collect(Collectors.toList());
        DataBindHandleBootstrap.dataHandConvert(gradedEnterpriseReviewVos, 2);
        List<GradedEnterpriseReviewDto> gradedEnterpriseReviewDtos = BeanUtils.copyToList(gradedEnterpriseReviewVos, GradedEnterpriseReviewDto.class);
        ZtExcelBuildUtil.buildExcelExport(GradedEnterpriseReviewDto.class, "粮库申报信息").setData(gradedEnterpriseReviewDtos).doWebExport();
    }

    @DataBindFieldConvert
    @Override
    public void exportDataSd(GradedEnterpriseReviewPageParam pageParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        String regionId = loginUser.getRegionId();
        pageParam.setXzqhdm(regionId);
        Region region = regionService.getById(regionId);
        // 如果是县的话不让看到实地列表
        if (StringUtil.equals(RegionTypeEnum.AREA.getType(), region.getType())) {
            pageParam.setXzqhdm(IdUtil.simpleUUID());
        }
        // 如果是省的话只让看到AAA级以上
        if (StringUtil.equals(RegionTypeEnum.PROVINCE.getType(), region.getType())) {
            pageParam.setCheckSbdj("1");
        } else {
            pageParam.setCheckSbdj("2");
        }
        List<GradedEnterpriseReviewVo> reviewVos = gradedEnterpriseReviewMapper.getSdGradedEnterpriseReviewListByIds(pageParam);
        switchState(reviewVos, regionId);
        List<GradedEnterpriseReviewVo> gradedEnterpriseReviewVos = reviewVos.stream().
                filter(e -> StringUtils.equals(e.getLastZsSbzt(), GradeFlowStatusEnum.PASS.getState()) &&
                        !StringUtils.equals(e.getLastZsSbzt(), GradeFlowStatusEnum.NO_PASS.getState()) &&
                        !StringUtils.equals(e.getCitySdzt(), GradeSdztEnum.NO_PASS.getState())&&
                        !StringUtils.equals(e.getProvinceSdzt(), GradeSdztEnum.NO_PASS.getState())&&
                        !StringUtils.equals(e.getSpzt(), GradeAwardingEnum.NO_PASS.getState())&&
                        !StringUtils.equals(e.getSpzt(), GradeAwardingEnum.PASS.getState())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(gradedEnterpriseReviewVos)) {
            for (int i = 0; i < gradedEnterpriseReviewVos.size(); i++) {
                GradedEnterpriseReviewVo gradedEnterpriseReviewVo = gradedEnterpriseReviewVos.get(i);
                String czzt = gradedEnterpriseReviewVo.getCzzt();
                if (StringUtil.equals(RegionTypeEnum.PROVINCE.getType(), region.getType())) {
                    if (StringUtil.equals(GradeFlowNodeEnum.G.getState(), czzt) ||
                            StringUtil.equals(GradeFlowNodeEnum.H.getState(), czzt)) {
                    } else {
                        gradedEnterpriseReviewVos.remove(i);
                        i--;
                    }
                } else if (StringUtil.equals(RegionTypeEnum.CITY.getType(), region.getType())) {
                    if (StringUtil.equals(GradeFlowNodeEnum.E.getState(), czzt)||
                            StringUtil.equals(GradeFlowNodeEnum.H.getState(), czzt)) {
                    } else {
                        gradedEnterpriseReviewVos.remove(i);
                        i--;
                    }
                } else {
                    gradedEnterpriseReviewVos.remove(i);
                    i--;
                }
            }
        }
        DataBindHandleBootstrap.dataHandConvert(gradedEnterpriseReviewVos, 2);
        List<GradedEnterpriseReviewDto> gradedEnterpriseReviewDtos = BeanUtils.copyToList(gradedEnterpriseReviewVos, GradedEnterpriseReviewDto.class);
        ZtExcelBuildUtil.buildExcelExport(GradedEnterpriseReviewDto.class, "粮库申报信息").setData(gradedEnterpriseReviewDtos).doWebExport();
    }

    @DataBindFieldConvert
    @Override
    public List<GradedEnterpriseReviewVo> translate(List<GradedEnterpriseReviewVo> list) {
        return BeanUtils.copyToList(list, GradedEnterpriseReviewVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GradedEnterpriseReviewVo detail(String id) {
        IGradedEnterpriseReviewService gradedEnterpriseReviewService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(gradedEnterpriseReviewService.getById(id), GradedEnterpriseReviewVo.class);
    }

    @DataBindService(strategy = DataBindGradedEnterpriseReview.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gradedEnterpriseReviewMapper);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    @Override
    public String saveData(GradedEnterpriseReviewSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseReview gradedEnterpriseReview = BeanUtils.copyByClass(param, GradedEnterpriseReview.class);
        gradedEnterpriseReview.setCreateBy(loginUser.getName());
        gradedEnterpriseReview.setUpdateBy(loginUser.getName());
        gradedEnterpriseReview.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseReview.setCreateTime(LocalDateTime.now());
        gradedEnterpriseReview.setCzbz(CzBzEnum.I.getCzBz());
        gradedEnterpriseReview.setSbnf(String.valueOf(param.getSbrq().getYear()));

        // 验证是否有资格申报粮库等级
        verifyStock(param.getQydm(), param.getKqdm(), param.getSbnf());

        // 所在区域必须填写，并且一定要精确到县
        assertXzqhdm(param.getXzqhdm());

        GradedEnterpriseReview unx = getByUnx(param.getQydm(), param.getKqdm(), param.getSbnf());
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在企业和库点以及申报年份相同的申报数据");

        // 等级粮库验证
        verifyLevels(param.getQydm(), param.getKqdm(), param.getSbdj(), param.getLkcr());

        // 初始化流程
        gradedEnterpriseFlowService.saveOrUpdateBatchData(param.getGradedEnterpriseFlowSaveParams());

        // 初始化审核详情
        gradedEnterpriseProcessService.saveOrUpdateBatchData(param.getGradedEnterpriseProcessSaveParams());

        // 初始化证明材料
        gradedEnterpriseMaterialService.saveOrUpdateBatchData(param.getGradedEnterpriseMaterialSaveParams());

        // 初始化实地信息
        gradedEnterpriseSelfAssessmentService.saveOrUpdateBatchData(param.getGradedEnterpriseSelfAssessmentSaveParams());

        super.save(gradedEnterpriseReview);
        param.setId(gradedEnterpriseReview.getId());
        return gradedEnterpriseReview.getId();
    }

    private void verifyLevels(String qydm, String kqdm, String sbdj2, BigDecimal lkcr2) {
        GradedEnterpriseStock gradedEnterpriseStock = gradedEnterpriseStockService.getByUnx(qydm, kqdm);
        int sbdj = Integer.valueOf(sbdj2);
        BigDecimal lkcr = lkcr2;
        int nowYear = LocalDateTime.now().getYear();
        verifyLevels(gradedEnterpriseStock, sbdj, lkcr, nowYear);
    }

    /**
     * @return void
     * @Author lzq
     * @Description 等级粮库验证
     * @Date 10:41 2023/5/26
     * @Param [gradedEnterpriseStock, sbdj, lkcr, nowYear]
     **/
    private void verifyLevels(GradedEnterpriseStock gradedEnterpriseStock, int sbdj, BigDecimal lkcr, int nowYear) {
        if (ObjectUtils.isNotEmpty(gradedEnterpriseStock)) {
            int spdj = Integer.valueOf(gradedEnterpriseStock.getSpdj());
            int spnf = Integer.valueOf(gradedEnterpriseStock.getSpnf());
            int difference = nowYear - spnf;

            switch (spdj) {
                case 1:
                    switch (sbdj) {
                        case 1:
                            Assert.isTrue(false, "已经是A级粮库了，无需重复申报");
                        case 2:
                            return;
                        case 3:
                            return;
                        case 4:
                            Assert.isTrue(false, "请先申报AAA级粮库授牌且满两年");
                            return;
                        case 5:
                            Assert.isTrue(false, "请先申报AAAA级粮库授牌且满两年");
                        default:
                            return;
                    }
                case 2:
                    switch (sbdj) {
                        case 1:
                            Assert.isTrue(false, "已经是AA级粮库了，不需要申报A级粮库");
                            return;
                        case 2:
                            Assert.isTrue(false, "已经是AA级粮库了，无需重复申报");
                            return;
                        case 3:
                            return;
                        case 4:
                            Assert.isTrue(false, "请先申报AAA级粮库授牌且满两年");
                            return;
                        case 5:
                            Assert.isTrue(false, "请先申报AAAA级粮库授牌且满两年");
                        default:
                            return;
                    }
                case 3:
                    switch (sbdj) {
                        case 1:
                            Assert.isTrue(false, "已经是AAA级粮库了，不需要申报A级粮库");
                            return;
                        case 2:
                            Assert.isTrue(false, "已经是AAA级粮库了，不需要申报AA级粮库");
                            return;
                        case 3:
                            Assert.isTrue(false, "已经是AAA级粮库了，无需重复申报");
                            return;
                        case 4:
                            Assert.isTrue(difference >= 2 && (lkcr.compareTo(new BigDecimal(100000)) >= 0),
                                    "AAA级粮库申报AAAA级粮库，需要AAA级粮库授牌满2年,且库区仓容不低于10万吨");
                            return;
                        case 5:
                            Assert.isTrue(false, "AAA级粮库需要先申报AAAA级粮库，不能跳级申报");
                        default:
                            return;
                    }
                case 4:
                    switch (sbdj) {
                        case 1:
                            Assert.isTrue(false, "已经是AAAA级粮库了，不需要申报A级粮库");
                        case 2:
                            Assert.isTrue(false, "已经是AAAA级粮库了，不需要申报AA级粮库");
                        case 3:
                            Assert.isTrue(false, "已经是AAAA级粮库了，不需要申报AAA级粮库");
                        case 4:
                            Assert.isTrue(false, "已经是AAAA级粮库了，无需重复申报");
                        case 5:
                            Assert.isTrue(difference >= 2 && (lkcr.compareTo(new BigDecimal(300000)) >= 0),
                                    "AAAA级粮库申报AAAAA级粮库，需要AAA级粮库授牌满2年,且库区仓容不低于30万吨");
                            return;
                        default:
                            return;
                    }
                case 5:
                    Assert.isTrue(false, "已经是AAAAA级粮库了，无需重复申报");
                default:
                    return;
            }
        } else {
            switch (sbdj) {
                case 1:
                case 2:
                case 3:
                    return;
                default:
                    Assert.isTrue(false, "请先申报AAA级粮库");
            }
        }
    }

    @Override
    public Boolean batchApproveData(List<GradedEnterpriseReviewApproveParam> params) {
        Boolean returnBoolean = true;
        if (CollectionUtil.isNotEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                GradedEnterpriseReviewApproveParam param = params.get(i);
                returnBoolean = approveData(param);
            }
        }
        return returnBoolean;
    }

    @Override
    public Boolean approveData(GradedEnterpriseReviewApproveParam param) {
        Boolean returnBoolean = true;
        // 获取申报信息
        GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam = getGradedEnterpriseReviewSaveParam(param.getQyid());
        // 验证是否有资格申报粮库等级
        verifyStock(gradedEnterpriseReviewSaveParam.getQydm(), gradedEnterpriseReviewSaveParam.getKqdm(), gradedEnterpriseReviewSaveParam.getSbnf());
        // 获取当前企业的当前流程信息
        updateFlowInfo(param, GradeOpertionEnum.S, gradedEnterpriseReviewSaveParam);
        // 保存审核信息
        param.getGradedEnterpriseProcessSaveParam().setQyid(param.getQyid());
        gradedEnterpriseProcessService.saveData(param.getGradedEnterpriseProcessSaveParam());
        // 下发库软件
        gradedEnterpriseHttpServiceSave(gradedEnterpriseReviewSaveParam);
        return returnBoolean;
    }

    @Override
    public GradedEnterpriseReviewSaveParam getGradedEnterpriseReviewSaveParam(String id) {
        GradedEnterpriseReview gradedEnterpriseReview = super.getById(id);
        return BeanUtils.copyByClass(gradedEnterpriseReview, GradedEnterpriseReviewSaveParam.class);
    }

    private void assertStock(GradedEnterpriseReviewApproveParam param) {
        List<GradedEnterpriseReview> gradedEnterpriseReviews = super.lambdaQuery()
                .eq(GradedEnterpriseReview::getId, param.getQyid())
                .eq(GradedEnterpriseReview::getSbzt, GradeSbztEnum.NO_PASS.getState())
                .ne(GradedEnterpriseReview::getCzbz, CzBzEnum.D.getCzBz()).list();
        Assert.isTrue(CollectionUtil.isEmpty(gradedEnterpriseReviews), "审批不通过不能授牌！");
    }

    @Override
    public Boolean awardingData(GradedEnterpriseReviewApproveParam param) {
        Boolean returnBoolean = true;
        if (StringUtils.equals(param.getSpCheck(), GradeSpCheck.YES.getState())) {
            // 查询是否有未通过的审核信息
            assertStock(param);
            // 授牌评分校验
            assertAwarding(param);
        }
        // 更新流程信息
        GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam = getGradedEnterpriseReviewSaveParam(param.getQyid());
        updateFlowInfo(param, GradeOpertionEnum.SP, gradedEnterpriseReviewSaveParam);
        // 保存审核信息
        if (StringUtils.equals(param.getSpzt(), GradeAwardingEnum.PASS.getState())) {
            GradedEnterpriseProcessSaveParam gradedEnterpriseProcessSaveParam = new GradedEnterpriseProcessSaveParam();
            gradedEnterpriseProcessSaveParam.setShjg(GradeShjgEnum.YES.getState());
            gradedEnterpriseProcessSaveParam.setShyj("授牌通过");
            gradedEnterpriseProcessSaveParam.setQyid(param.getQyid());
            gradedEnterpriseProcessService.saveData(gradedEnterpriseProcessSaveParam);
        } else {
            GradedEnterpriseProcessSaveParam gradedEnterpriseProcessSaveParam = new GradedEnterpriseProcessSaveParam();
            gradedEnterpriseProcessSaveParam.setShjg(GradeShjgEnum.NO.getState());
            gradedEnterpriseProcessSaveParam.setShyj("授牌不通过");
            gradedEnterpriseProcessSaveParam.setQyid(param.getQyid());
            gradedEnterpriseProcessService.saveData(gradedEnterpriseProcessSaveParam);
        }
        // 下发库软件
        gradedEnterpriseHttpServiceSave(gradedEnterpriseReviewSaveParam);
        return returnBoolean;
    }

    private GradedEnterpriseReviewVo putGradedEnterpriseStockIn(String qyid) {
        GradedEnterpriseReviewVo gradedEnterpriseReviewVo = detail(qyid);
        GradedEnterpriseStock gradedEnterpriseStock = gradedEnterpriseStockService.getByUnx(gradedEnterpriseReviewVo.getQydm(), gradedEnterpriseReviewVo.getKqdm());
        GradedEnterpriseStockSaveParam gradedEnterpriseStockSaveParam = new GradedEnterpriseStockSaveParam();
        gradedEnterpriseStockSaveParam.setQyid(qyid);
        BeanUtils.copyProperties(gradedEnterpriseReviewVo, gradedEnterpriseStockSaveParam);
        if (ObjectUtils.isNotEmpty(gradedEnterpriseStock)) {
            int sbdj = Integer.valueOf(gradedEnterpriseReviewVo.getSbdj());
            int spdj = Integer.valueOf(gradedEnterpriseStock.getSpdj());
            Assert.isTrue(sbdj > spdj, "已存在对应的等级粮库资格");
            if (sbdj > spdj) {
                gradedEnterpriseStockSaveParam.setSpdj(gradedEnterpriseReviewVo.getSbdj());
                gradedEnterpriseStockSaveParam.setSpzt(GradeAwardingEnum.PASS.getState());
                gradedEnterpriseStockSaveParam.setSpnf(String.valueOf(LocalDateTime.now().getYear()));
                gradedEnterpriseStockSaveParam.setId(gradedEnterpriseStock.getId());
                gradedEnterpriseStockService.updateData(gradedEnterpriseStockSaveParam);
                gradedEnterpriseReviewFactoryService.saveOrUpdateStock(gradedEnterpriseStockSaveParam);
            }
        } else {
            gradedEnterpriseStockSaveParam.setId(IdUtil.simpleUUID());
            gradedEnterpriseStockSaveParam.setSpdj(gradedEnterpriseReviewVo.getSbdj());
            gradedEnterpriseStockSaveParam.setSpzt(GradeAwardingEnum.PASS.getState());
            gradedEnterpriseStockSaveParam.setSpnf(String.valueOf(LocalDateTime.now().getYear()));
            gradedEnterpriseStockService.saveData(gradedEnterpriseStockSaveParam);
            gradedEnterpriseReviewFactoryService.saveOrUpdateStock(gradedEnterpriseStockSaveParam);
        }
        return gradedEnterpriseReviewVo;
    }

    private GradedEnterpriseReviewVo putIndeedGradedEnterpriseStockIn(String qyid) {
        GradedEnterpriseReviewVo gradedEnterpriseReviewVo = detail(qyid);
        GradedEnterpriseStock gradedEnterpriseStock = gradedEnterpriseStockService.getByUnx(gradedEnterpriseReviewVo.getQydm(), gradedEnterpriseReviewVo.getKqdm());
        GradedEnterpriseStockSaveParam gradedEnterpriseStockSaveParam = new GradedEnterpriseStockSaveParam();
        gradedEnterpriseStockSaveParam.setQyid(qyid);
        BeanUtils.copyProperties(gradedEnterpriseReviewVo, gradedEnterpriseStockSaveParam);
        if (ObjectUtils.isNotEmpty(gradedEnterpriseStock)) {
            gradedEnterpriseStockSaveParam.setId(gradedEnterpriseStock.getId());
            gradedEnterpriseStockSaveParam.setSpzt(GradeAwardingEnum.NO_PASS.getState());
            gradedEnterpriseStockService.updateData(gradedEnterpriseStockSaveParam);
            gradedEnterpriseReviewFactoryService.saveOrUpdateStock(gradedEnterpriseStockSaveParam);
        } else {
            gradedEnterpriseStockSaveParam.setId(IdUtil.simpleUUID());
            gradedEnterpriseStockSaveParam.setSpzt(GradeAwardingEnum.NO_PASS.getState());
            gradedEnterpriseStockService.saveData(gradedEnterpriseStockSaveParam);
            gradedEnterpriseReviewFactoryService.saveOrUpdateStock(gradedEnterpriseStockSaveParam);
        }
        return gradedEnterpriseReviewVo;
    }

    private void assertAwarding(GradedEnterpriseReviewApproveParam param) {
        List<GradedEnterpriseSelfAssessmentVo> list = gradedEnterpriseSelfAssessmentService.list(param.getQyid()).stream().filter(e -> ObjectUtils.isNotEmpty(e.getProvinceSddf())).collect(Collectors.toList());
        Assert.isTrue(CollectionUtil.isNotEmpty(list), "请先完成实地评分，在进行授牌");
        BigDecimal sdjcdfSum = list.stream().map(GradedEnterpriseSelfAssessmentVo::getProvinceSddf).reduce(BigDecimal.ZERO, BigDecimal::add);
        GradedEnterpriseReviewVo gradedEnterpriseReviewVo = detail(param.getQyid());
        int sbdj = Integer.valueOf(gradedEnterpriseReviewVo.getSbdj());
        if (sbdj == 1) {
            Assert.isTrue(sdjcdfSum.compareTo(new BigDecimal(70)) >= 0, "A级：符合参评条件，且综合得分≥70分");
        } else if (sbdj == 2) {
            Assert.isTrue(sdjcdfSum.compareTo(new BigDecimal(75)) >= 0, "AA级：符合参评条件，且综合得分≥75分");
        } else if (sbdj == 3) {
            Assert.isTrue(sdjcdfSum.compareTo(new BigDecimal(85)) >= 0, "AAA级：符合参评条件，且综合得分≥85分");
        } else if (sbdj == 4) {
            Assert.isTrue(sdjcdfSum.compareTo(new BigDecimal(95)) >= 0, "AAAA级：符合参评条件，且综合得分≥95分");
        } else {
            Assert.isTrue(sdjcdfSum.compareTo(new BigDecimal(100)) >= 0, "AAAAA级：符合参评条件，且综合得分≥100分");
        }
    }

    @Override
    public Boolean indeedData(GradedEnterpriseReviewApproveParam param) {
        Boolean returnBoolean = true;
        // 获取当前申报信息
        GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam = getGradedEnterpriseReviewSaveParam(param.getQyid());
        // 获取自评分信息
        List<GradedEnterpriseSelfAssessmentVo> gradedEnterpriseSelfAssessmentVos
                = gradedEnterpriseSelfAssessmentService.list(param.getQyid());
        List<GradedEnterpriseSelfAssessmentSaveParam> gradedEnterpriseSelfAssessmentSaveParams
                = BeanUtils.copyToList(gradedEnterpriseSelfAssessmentVos, GradedEnterpriseSelfAssessmentSaveParam.class);
        param.setGradedEnterpriseSelfAssessmentSaveParams(gradedEnterpriseSelfAssessmentSaveParams);
        // 更新流程信息
        updateFlowInfo(param, GradeOpertionEnum.SD, gradedEnterpriseReviewSaveParam);
        if (StringUtils.equals(param.getGradedEnterpriseProcessSaveParam().getShjg(), GradeShjgEnum.YES.getState())) {
            // 保存审核信息
            GradedEnterpriseProcessSaveParam gradedEnterpriseProcessSaveParam = new GradedEnterpriseProcessSaveParam();
            gradedEnterpriseProcessSaveParam.setShjg(GradeShjgEnum.YES.getState());
            gradedEnterpriseProcessSaveParam.setShyj(param.getGradedEnterpriseProcessSaveParam().getShyj());
            gradedEnterpriseProcessSaveParam.setQyid(param.getQyid());
            gradedEnterpriseProcessService.saveData(gradedEnterpriseProcessSaveParam);
        } else if (StringUtils.equals(param.getGradedEnterpriseProcessSaveParam().getShjg(), GradeShjgEnum.NO.getState())) {
            // 保存审核信息
            GradedEnterpriseProcessSaveParam gradedEnterpriseProcessSaveParam = new GradedEnterpriseProcessSaveParam();
            gradedEnterpriseProcessSaveParam.setShjg(GradeShjgEnum.NO.getState());
            gradedEnterpriseProcessSaveParam.setShyj(param.getGradedEnterpriseProcessSaveParam().getShyj());
            gradedEnterpriseProcessSaveParam.setQyid(param.getQyid());
            gradedEnterpriseProcessService.saveData(gradedEnterpriseProcessSaveParam);
            // 写入等级粮库记录
            putIndeedGradedEnterpriseStockIn(param.getQyid());
        }

        // 保存自评分信息
        // gradedEnterpriseSelfAssessmentService.saveOrUpdateBatchData(param.getGradedEnterpriseSelfAssessmentSaveParams());

        // 下发库软件
        gradedEnterpriseReviewSaveParam.setGradedEnterpriseSelfAssessmentSaveParams(param.getGradedEnterpriseSelfAssessmentSaveParams());
        gradedEnterpriseHttpServiceSave(gradedEnterpriseReviewSaveParam);
        return returnBoolean;
    }

    private void updateFlowInfo(GradedEnterpriseReviewApproveParam param,
                                GradeOpertionEnum gradeOpertionEnum,
                                GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<String> xzqhdmIds = assertXzqhdm(param.getXzqhdm());
        List<String> regionIds = Lists.newArrayList();
        regionIds.add(xzqhdmIds.get(0));
        List<String> cityIds = Lists.newArrayList();
        cityIds.add(xzqhdmIds.get(1));
        List<String> provinceIds = Lists.newArrayList();
        provinceIds.add(xzqhdmIds.get(2));

        // 待审核的节点
        GradedEnterpriseFlowSaveParam waitParam = gradedEnterpriseFlowService.listByQyid(param.getQyid()).stream().
                filter(x -> StringUtils.equals(x.getFlowStatus(), GradeFlowStatusEnum.WAIT.getState())
                        ||StringUtils.equals(x.getFlowStatus(), GradeFlowStatusEnum.NO_PASS.getState())).
                sorted(Comparator.comparing(GradedEnterpriseFlowSaveParam::getFlowSort)).findFirst().orElse(null);

        // 上一个审核通过的节点
        GradedEnterpriseFlowSaveParam passParam = gradedEnterpriseFlowService.listByQyid(param.getQyid()).stream().
                filter(x -> StringUtils.equals(x.getFlowStatus(), GradeFlowStatusEnum.PASS.getState())).
                sorted(Comparator.comparing(GradedEnterpriseFlowSaveParam::getFlowSort).reversed()).findFirst().orElse(null);

        Assert.isTrue(ObjectUtils.isNotEmpty(waitParam), "当前企业审批操作已完成，请进行下一步操作！");

        if (StringUtils.equals(waitParam.getFlowCode(), GradeFlowNodeEnum.C.getState())) {
            if (getNextPassStatus(loginUser, regionIds)) {
                Assert.isTrue(StringUtils.equals(gradeOpertionEnum.getState(), GradeOpertionEnum.S.getState()), "请前往审批");
                runFlow(param, waitParam, passParam);
                if (StringUtils.equals(param.getGradedEnterpriseProcessSaveParam().getShjg(), GradeShjgEnum.YES.getState())) {
                    updateApproveState(param, gradedEnterpriseReviewSaveParam, loginUser);
                } else {
                    gradedEnterpriseReviewSaveParam.setSbzt(GradeSbztEnum.NO_PASS.getState());
                    updateApproveState(param, gradedEnterpriseReviewSaveParam, loginUser);
                }
            } else {
                Assert.isTrue(false, "当前节点审核人必须是县级");
            }
        } else if (StringUtils.equals(waitParam.getFlowCode(), GradeFlowNodeEnum.D.getState())) {
            if (getNextPassStatus(loginUser, cityIds)) {
                Assert.isTrue(StringUtils.equals(gradeOpertionEnum.getState(), GradeOpertionEnum.S.getState()), "请前往审批");
                runFlow(param, waitParam, passParam);
                changeCityStockStatus(param, gradedEnterpriseReviewSaveParam, loginUser);
            } else {
                Assert.isTrue(false, "当前节点审核人必须是市级");
            }
        } else if (StringUtils.equals(waitParam.getFlowCode(), GradeFlowNodeEnum.E.getState())) {
            if (getNextPassStatus(loginUser, cityIds)) {
                Assert.isTrue(StringUtils.equals(gradeOpertionEnum.getState(), GradeOpertionEnum.SD.getState()), "请前往市级实地考核确认");
                runFlow(param, waitParam, passParam);
                changeCitySdzt(param, gradedEnterpriseReviewSaveParam, loginUser);
            } else {
                Assert.isTrue(false, "当前节点审核人必须是市级");
            }
        } else if (StringUtils.equals(waitParam.getFlowCode(), GradeFlowNodeEnum.F.getState())) {
            if (getNextPassStatus(loginUser, provinceIds)) {
                Assert.isTrue(StringUtils.equals(gradeOpertionEnum.getState(), GradeOpertionEnum.S.getState()), "请前往审批");
                runFlow(param, waitParam, passParam);
                changeProvinceStockStatus(param, gradedEnterpriseReviewSaveParam, loginUser);
            } else {
                Assert.isTrue(false, "当前节点审核人必须是省级");
            }
        } else if (StringUtils.equals(waitParam.getFlowCode(), GradeFlowNodeEnum.G.getState())) {
            if (getNextPassStatus(loginUser, provinceIds)) {
                Assert.isTrue(StringUtils.equals(gradeOpertionEnum.getState(), GradeOpertionEnum.SD.getState()), "请前往省级实地考核确认");
                runFlow(param, waitParam, passParam);
                changeProvinceSdzt(param, gradedEnterpriseReviewSaveParam, loginUser);
            } else {
                Assert.isTrue(false, "当前节点审核人必须是省级");
            }
        } else if (StringUtils.equals(waitParam.getFlowCode(), GradeFlowNodeEnum.H.getState())) {
            if (StringUtils.equals(waitParam.getXzqhdm(), GradeRegionEnum.GD.getState())) {
                if (getNextPassStatus(loginUser, provinceIds)) {
                    Assert.isTrue(StringUtils.equals(gradeOpertionEnum.getState(), GradeOpertionEnum.SP.getState()), "请前往授牌");
                    runFlow(param, waitParam, passParam);
                    // 更新审核状态
                    changeAwardingStatus(param, gradedEnterpriseReviewSaveParam, loginUser);
                } else {
                    Assert.isTrue(false, "当前节点审核人必须是省级");
                }
            } else {
                if (getNextPassStatus(loginUser, cityIds)) {
                    Assert.isTrue(StringUtils.equals(gradeOpertionEnum.getState(), GradeOpertionEnum.SP.getState()), "请前往授牌");
                    runFlow(param, waitParam, passParam);
                    // 更新审核状态
                    changeAwardingStatus(param, gradedEnterpriseReviewSaveParam, loginUser);
                } else {
                    Assert.isTrue(false, "当前节点审核人必须是市级");
                }
            }
        }
    }

    private void runFlow(GradedEnterpriseReviewApproveParam param, GradedEnterpriseFlowSaveParam waitParam, GradedEnterpriseFlowSaveParam passParam) {
        // 更新节点信息
        if (StringUtils.equals(param.getGradedEnterpriseProcessSaveParam().getShjg(), GradeShjgEnum.YES.getState())) {
            // 确认的是当前节点
            if (ObjectUtils.isNotEmpty(waitParam)) {
                waitParam.setFlowStatus(GradeFlowStatusEnum.PASS.getState());
                gradedEnterpriseFlowService.updateData(waitParam);
            }
        } else {
            // 否定的是上一个节点
            if (ObjectUtils.isNotEmpty(passParam)) {
                passParam.setFlowStatus(GradeFlowStatusEnum.NO_PASS.getState());
                gradedEnterpriseFlowService.updateData(passParam);

                waitParam.setFlowStatus(GradeFlowStatusEnum.WAIT.getState());
                gradedEnterpriseFlowService.updateData(waitParam);
            }
        }
    }

    private void changeAwardingStatus(GradedEnterpriseReviewApproveParam param,
                                      GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam,
                                      LoginUser loginUser) {
        if (StringUtils.equals(param.getSpzt(), GradeAwardingEnum.PASS.getState())) {
            GradedEnterpriseReviewVo gradedEnterpriseReviewVo = putGradedEnterpriseStockIn(param.getQyid());
            gradedEnterpriseReviewSaveParam.setSpdj(gradedEnterpriseReviewVo.getSbdj());
            gradedEnterpriseReviewSaveParam.setSpzt(GradeAwardingEnum.PASS.getState());
            gradedEnterpriseReviewSaveParam.setSpnf(String.valueOf(DateUtil.year(new Date())));
            gradedEnterpriseReviewSaveParam.setSbzt(GradeSbztEnum.PASS.getState());
            super.lambdaUpdate()
                    .in(GradedEnterpriseReview::getId, param.getQyid())
                    .set(GradedEnterpriseReview::getSpdj, gradedEnterpriseReviewVo.getSbdj())
                    .set(GradedEnterpriseReview::getSpzt, GradeAwardingEnum.PASS.getState())
                    .set(GradedEnterpriseReview::getSpnf, String.valueOf(DateUtil.year(new Date())))
                    .set(GradedEnterpriseReview::getSbzt, GradeSbztEnum.PASS.getState())
                    .set(GradedEnterpriseReview::getUpdateBy, loginUser.getName())
                    .set(GradedEnterpriseReview::getUpdateTime, LocalDateTime.now())
                    .update();
        } else {
            gradedEnterpriseReviewSaveParam.setSpzt(GradeAwardingEnum.NO_PASS.getState());
            gradedEnterpriseReviewSaveParam.setSbzt(GradeSbztEnum.REPORT.getState());
            super.lambdaUpdate()
                    .in(GradedEnterpriseReview::getId, param.getQyid())
                    .set(GradedEnterpriseReview::getSpzt, GradeAwardingEnum.NO_PASS.getState())
                    .set(GradedEnterpriseReview::getSbzt, GradeSbztEnum.REPORT.getState())
                    .set(GradedEnterpriseReview::getUpdateBy, loginUser.getName())
                    .set(GradedEnterpriseReview::getUpdateTime, LocalDateTime.now())
                    .update();
        }
    }

    private void changeCitySdzt(GradedEnterpriseReviewApproveParam param,
                                GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam,
                                LoginUser loginUser) {
        BigDecimal sdjcdfSum = param.getGradedEnterpriseSelfAssessmentSaveParams()
                .stream().map(GradedEnterpriseSelfAssessmentSaveParam::getCitySddf).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (StringUtils.equals(param.getGradedEnterpriseProcessSaveParam().getShjg(), GradeShjgEnum.YES.getState())) {
            gradedEnterpriseReviewSaveParam.setCitySdpf(sdjcdfSum);
            gradedEnterpriseReviewSaveParam.setCitySdzt(GradeSdztEnum.PASS.getState());
            super.lambdaUpdate()
                    .in(GradedEnterpriseReview::getId, param.getQyid())
                    .set(GradedEnterpriseReview::getCitySdpf, sdjcdfSum)
                    .set(GradedEnterpriseReview::getCitySdzt, GradeSdztEnum.PASS.getState())
                    .set(GradedEnterpriseReview::getUpdateBy, loginUser.getName())
                    .set(GradedEnterpriseReview::getUpdateTime, LocalDateTime.now())
                    .update();
        } else {
            gradedEnterpriseReviewSaveParam.setCitySdpf(sdjcdfSum);
            gradedEnterpriseReviewSaveParam.setCitySdzt(GradeSdztEnum.NO_PASS.getState());
            gradedEnterpriseReviewSaveParam.setSbzt(GradeSbztEnum.REPORT.getState());
            super.lambdaUpdate()
                    .in(GradedEnterpriseReview::getId, param.getQyid())
                    .set(GradedEnterpriseReview::getCitySdpf, sdjcdfSum)
                    .set(GradedEnterpriseReview::getCitySdzt, GradeSdztEnum.NO_PASS.getState())
                    .set(GradedEnterpriseReview::getSbzt, GradeSbztEnum.REPORT.getState())
                    .set(GradedEnterpriseReview::getUpdateBy, loginUser.getName())
                    .set(GradedEnterpriseReview::getUpdateTime, LocalDateTime.now())
                    .update();
        }
    }

    private void changeProvinceSdzt(GradedEnterpriseReviewApproveParam param,
                                    GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam,
                                    LoginUser loginUser) {
        BigDecimal sdjcdfSum = param.getGradedEnterpriseSelfAssessmentSaveParams()
                .stream().map(GradedEnterpriseSelfAssessmentSaveParam::getProvinceSddf).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (StringUtils.equals(param.getGradedEnterpriseProcessSaveParam().getShjg(), GradeShjgEnum.YES.getState())) {
            gradedEnterpriseReviewSaveParam.setProvinceSdpf(sdjcdfSum);
            gradedEnterpriseReviewSaveParam.setProvinceSdzt(GradeSdztEnum.PASS.getState());
            super.lambdaUpdate()
                    .in(GradedEnterpriseReview::getId, param.getQyid())
                    .set(GradedEnterpriseReview::getProvinceSdpf, sdjcdfSum)
                    .set(GradedEnterpriseReview::getProvinceSdzt, GradeSdztEnum.PASS.getState())
                    .set(GradedEnterpriseReview::getUpdateBy, loginUser.getName())
                    .set(GradedEnterpriseReview::getUpdateTime, LocalDateTime.now())
                    .update();
        } else {
            gradedEnterpriseReviewSaveParam.setProvinceSdpf(sdjcdfSum);
            gradedEnterpriseReviewSaveParam.setProvinceSdzt(GradeSdztEnum.NO_PASS.getState());
            gradedEnterpriseReviewSaveParam.setSbzt(GradeSbztEnum.REPORT.getState());
            super.lambdaUpdate()
                    .in(GradedEnterpriseReview::getId, param.getQyid())
                    .set(GradedEnterpriseReview::getProvinceSdpf, sdjcdfSum)
                    .set(GradedEnterpriseReview::getProvinceSdzt, GradeSdztEnum.NO_PASS.getState())
                    .set(GradedEnterpriseReview::getSbzt, GradeSbztEnum.REPORT.getState())
                    .set(GradedEnterpriseReview::getUpdateBy, loginUser.getName())
                    .set(GradedEnterpriseReview::getUpdateTime, LocalDateTime.now())
                    .update();
        }
    }

    private void changeCityStockStatus(GradedEnterpriseReviewApproveParam param,
                                       GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam,
                                       LoginUser loginUser) {
        gradedEnterpriseReviewSaveParam.setUpdateBy(loginUser.getName());
        if (StringUtils.equals(param.getGradedEnterpriseProcessSaveParam().getShjg(), GradeShjgEnum.YES.getState())) {
            gradedEnterpriseReviewSaveParam.setUpdateTime(LocalDateTime.now());
            gradedEnterpriseReviewSaveParam.setCitySdzt(GradeSdztEnum.WAIT.getState());
            updateApproveState(param, gradedEnterpriseReviewSaveParam, loginUser);
        } else {
            gradedEnterpriseReviewSaveParam.setUpdateTime(LocalDateTime.now());
            gradedEnterpriseReviewSaveParam.setCitySdzt(null);
            updateApproveState(param, gradedEnterpriseReviewSaveParam, loginUser);
        }
    }

    private void changeProvinceStockStatus(GradedEnterpriseReviewApproveParam param,
                                           GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam,
                                           LoginUser loginUser) {
        gradedEnterpriseReviewSaveParam.setUpdateBy(loginUser.getName());
        if (StringUtils.equals(param.getGradedEnterpriseProcessSaveParam().getShjg(), GradeShjgEnum.YES.getState())) {
            gradedEnterpriseReviewSaveParam.setUpdateTime(LocalDateTime.now());
            gradedEnterpriseReviewSaveParam.setProvinceSdzt(GradeSdztEnum.WAIT.getState());
            updateApproveState(param, gradedEnterpriseReviewSaveParam, loginUser);
        } else {
            gradedEnterpriseReviewSaveParam.setUpdateTime(LocalDateTime.now());
            gradedEnterpriseReviewSaveParam.setProvinceSdzt(null);
            updateApproveState(param, gradedEnterpriseReviewSaveParam, loginUser);
        }
    }

    private void updateApproveState(GradedEnterpriseReviewApproveParam param,
                                    GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam,
                                    LoginUser loginUser) {
        super.lambdaUpdate()
                .in(GradedEnterpriseReview::getId, param.getQyid())
                .set(ObjectUtils.isNotEmpty(gradedEnterpriseReviewSaveParam.getSbzt()),
                        GradedEnterpriseReview::getSbzt, gradedEnterpriseReviewSaveParam.getSbzt())
                .set(ObjectUtils.isNotEmpty(gradedEnterpriseReviewSaveParam.getCitySdzt()),
                        GradedEnterpriseReview::getCitySdzt, gradedEnterpriseReviewSaveParam.getCitySdzt())
                .set(ObjectUtils.isNotEmpty(gradedEnterpriseReviewSaveParam.getProvinceSdzt()),
                        GradedEnterpriseReview::getProvinceSdzt, gradedEnterpriseReviewSaveParam.getProvinceSdzt())
                .set(GradedEnterpriseReview::getUpdateBy, loginUser.getName())
                .set(GradedEnterpriseReview::getUpdateTime, LocalDateTime.now())
                .update();
    }

    /**
     * @return void
     * @Author lzq
     * @Description 校验组织信息
     * @Date 18:12 2023/5/25
     * @Param [loginUser, regionIds]
     **/
    private Boolean getNextPassStatus(LoginUser loginUser, List<String> regionIds) {
        String regionId = loginUser.getRegionId();
        return regionIds.contains(regionId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(GradedEnterpriseReviewSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseReview gradedEnterpriseReview = BeanUtils.copyByClass(param, GradedEnterpriseReview.class);
        gradedEnterpriseReview.setCzbz(CzBzEnum.U.getCzBz());
        gradedEnterpriseReview.setUpdateBy(loginUser.getName());
        gradedEnterpriseReview.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseReview.setSbnf(String.valueOf(param.getSbrq().getYear()));

        // 所在区域必须填写，并且一定要精确到县
        assertXzqhdm(param.getXzqhdm());

        GradedEnterpriseReview unx = getByUnx(param.getQydm(), param.getKqdm(), param.getSbnf());

        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在企业和库点以及申报年份相同的申报数据");
        }

        super.updateById(gradedEnterpriseReview);
        param.setId(gradedEnterpriseReview.getId());
        return gradedEnterpriseReview.getId();
    }

    /**
     * @return void
     * @Author lzq
     * @Description 所在区域必须填写，并且一定要精确到县, 并获取对应的行政区划
     * @Date 17:42 2023/5/25
     * @Param [param]
     **/
    @Override
    public List<String> assertXzqhdm(String xzqhdm) {
        List<String> regionList = Lists.newArrayList();
        Assert.isTrue(ObjectUtils.isNotEmpty(xzqhdm), "所在区域必须填写，并且一定要精确到县");
        Region xjRegion = regionService.getById(xzqhdm);
        Assert.isTrue(ObjectUtils.isNotEmpty(xjRegion), "所在区域填写格式有问题");
        regionList.add(xjRegion.getId());
        Region cjRegion = regionService.getById(xjRegion.getParentId());
        Assert.isTrue(ObjectUtils.isNotEmpty(cjRegion), "所在区域没有精确到县");
        regionList.add(cjRegion.getId());
        Region pjRegion = regionService.getById(cjRegion.getParentId());
        Assert.isTrue(ObjectUtils.isNotEmpty(pjRegion), "所在区域没有精确到县");
        Assert.isTrue(!StringUtils.equals(xjRegion.getId(), "000156"), "所在区域没有精确到县");
        regionList.add(pjRegion.getId());
        return regionList;
    }

    @Override
    public List<String> getXzqhdms(String xzqhdm) {
        List<String> regionList = Lists.newArrayList();
        List<Region> regions = regionService.sonsAll(xzqhdm);
        if (CollectionUtil.isNotEmpty(regions)) {
            for (int i = 0; i < regions.size(); i++) {
                Region region = regions.get(i);
                regionList.add(region.getId());
            }
        }
        regionList.add(xzqhdm);
        return regionList;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(GradedEnterpriseReview::getId, ids)
                .set(GradedEnterpriseReview::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedEnterpriseReview::getUpdateBy, loginUser.getName())
                .set(GradedEnterpriseReview::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @Override
    public GradedEnterpriseReview getByUnx(String qydm, String kqdm, String sbnf) {
        return super.lambdaQuery()
                .select(GradedEnterpriseReview::getId)
                .eq(GradedEnterpriseReview::getQydm, qydm)
                .eq(GradedEnterpriseReview::getKqdm, kqdm)
                .eq(GradedEnterpriseReview::getSbnf, sbnf)
                .ne(GradedEnterpriseReview::getCzbz, CzBzEnum.D.getCzBz())
                .oneOpt()
                .map(GradedEnterpriseReview::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public GradedEnterpriseReview getByUnx(String qydm, String kqdm) {
        return super.lambdaQuery()
                .select(GradedEnterpriseReview::getId)
                .eq(GradedEnterpriseReview::getQydm, qydm)
                .eq(GradedEnterpriseReview::getKqdm, kqdm)
                .ne(GradedEnterpriseReview::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseReview::getUpdateTime)
                .oneOpt()
                .map(GradedEnterpriseReview::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public GradedEnterpriseReview verifyPassReview(String id) {
        return super.lambdaQuery()
                .select(GradedEnterpriseReview::getId)
                .eq(GradedEnterpriseReview::getId, id)
                .ne(GradedEnterpriseReview::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseReview::getUpdateTime)
                .oneOpt()
                .map(GradedEnterpriseReview::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public GradedEnterpriseReview verifyIndeedReview(String qydm, String kqdm) {
        return super.lambdaQuery()
                .select(GradedEnterpriseReview::getId)
                .eq(GradedEnterpriseReview::getQydm, qydm)
                .eq(GradedEnterpriseReview::getKqdm, kqdm)
                .ge(GradedEnterpriseReview::getSbrq, LocalDate.now().plusYears(-1))
                .ne(GradedEnterpriseReview::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseReview::getUpdateTime)
                .oneOpt()
                .map(GradedEnterpriseReview::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public GradedEnterpriseReview verifyAwardingReview(String qydm, String kqdm) {
        return super.lambdaQuery()
                .select(GradedEnterpriseReview::getId)
                .eq(GradedEnterpriseReview::getQydm, qydm)
                .eq(GradedEnterpriseReview::getKqdm, kqdm)
                .ge(GradedEnterpriseReview::getSbrq, LocalDate.now().plusYears(-1))
                .ne(GradedEnterpriseReview::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseReview::getUpdateTime)
                .oneOpt()
                .map(GradedEnterpriseReview::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public GradedEnterpriseReview verifyReduceReview(String qydm, String kqdm) {
        return super.lambdaQuery()
                .select(GradedEnterpriseReview::getId)
                .eq(GradedEnterpriseReview::getQydm, qydm)
                .eq(GradedEnterpriseReview::getKqdm, kqdm)
                .ge(GradedEnterpriseReview::getSbrq, LocalDate.now().plusYears(-1))
                .ne(GradedEnterpriseReview::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseReview::getUpdateTime)
                .oneOpt()
                .map(GradedEnterpriseReview::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public GradedEnterpriseReview verifyPickReview(String qydm, String kqdm) {
        return super.lambdaQuery()
                .select(GradedEnterpriseReview::getId)
                .eq(GradedEnterpriseReview::getQydm, qydm)
                .eq(GradedEnterpriseReview::getKqdm, kqdm)
                .ge(GradedEnterpriseReview::getSbrq, LocalDate.now().plusYears(-2))
                .ne(GradedEnterpriseReview::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseReview::getUpdateTime)
                .oneOpt()
                .map(GradedEnterpriseReview::getId)
                .map(this::getById)
                .orElse(null);
    }

    @SneakyThrows
    @Override
    public Boolean gradedEnterpriseHttpServiceSave(GradedEnterpriseReviewSaveParam gradedEnterpriseReviewSaveParam) {
        String id = gradedEnterpriseReviewSaveParam.getId();

        // 流程信息
        List<GradedEnterpriseFlowVo> flowVos = gradedEnterpriseFlowService.list(id);
        List<GradedEnterpriseFlowSaveParam> gradedEnterpriseFlowSaveParams =
                BeanUtils.copyToList(flowVos, GradedEnterpriseFlowSaveParam.class);
        gradedEnterpriseReviewSaveParam.setGradedEnterpriseFlowSaveParams(gradedEnterpriseFlowSaveParams);

        // 审批信息
        List<GradedEnterpriseProcessVo> gradedEnterpriseProcessVos = gradedEnterpriseProcessService.list(id);
        List<GradedEnterpriseProcessSaveParam> gradedEnterpriseProcessSaveParams =
                BeanUtils.copyToList(gradedEnterpriseProcessVos, GradedEnterpriseProcessSaveParam.class);
        gradedEnterpriseReviewSaveParam.setGradedEnterpriseProcessSaveParams(gradedEnterpriseProcessSaveParams);

        // 自评分信息
        List<GradedEnterpriseSelfAssessmentVo> gradedEnterpriseSelfAssessmentVos = gradedEnterpriseSelfAssessmentService.list(id);
        List<GradedEnterpriseSelfAssessmentSaveParam> gradedEnterpriseSelfAssessmentSaveParams =
                BeanUtils.copyToList(gradedEnterpriseSelfAssessmentVos, GradedEnterpriseSelfAssessmentSaveParam.class);
        gradedEnterpriseReviewSaveParam.setGradedEnterpriseSelfAssessmentSaveParams(gradedEnterpriseSelfAssessmentSaveParams);

        // 证明材料信息
        List<GradedEnterpriseMaterialVo> gradedEnterpriseMaterialVos = gradedEnterpriseMaterialService.list(id);
        List<GradedEnterpriseMaterialSaveParam> gradedEnterpriseMaterialSaveParams =
                BeanUtils.copyToList(gradedEnterpriseMaterialVos, GradedEnterpriseMaterialSaveParam.class);
        gradedEnterpriseReviewSaveParam.setGradedEnterpriseMaterialSaveParams(gradedEnterpriseMaterialSaveParams);
        Boolean sptResult = gradedEnterpriseReviewFactoryService.doSptReturnInfo(gradedEnterpriseReviewSaveParam);
        return sptResult;
    }

    @Override
    public DataApiEnum api() {
        return GRADE_REVIEW;
    }
}