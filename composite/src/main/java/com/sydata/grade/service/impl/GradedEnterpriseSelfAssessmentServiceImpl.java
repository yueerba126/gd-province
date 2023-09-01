/**
 * @filename:GradedEnterpriseSelfAssessmentServiceImpl 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2018 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.sydata.grade.domain.GradedEnterpriseProcess;
import com.sydata.grade.domain.GradedGrainDepotTemplate;
import com.sydata.grade.dto.XmDto;
import com.sydata.grade.service.IGradedGrainDepotStandardService;
import com.sydata.grade.service.IGradedGrainDepotTemplateService;
import com.sydata.grade.vo.GradedEnterpriseProcessVo;
import com.sydata.grade.vo.GradedGrainDepotStandardExcelVo;
import com.sydata.grade.vo.GradedReturnSelfAssessmentVo;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.grade.domain.GradedEnterpriseSelfAssessment;
import com.sydata.grade.mapper.GradedEnterpriseSelfAssessmentMapper;
import com.sydata.grade.service.IGradedEnterpriseSelfAssessmentService;
import com.sydata.grade.param.GradedEnterpriseSelfAssessmentPageParam;
import com.sydata.grade.param.GradedEnterpriseSelfAssessmentSaveParam;
import com.sydata.grade.vo.GradedEnterpriseSelfAssessmentVo;
import com.sydata.grade.annotation.DataBindGradedEnterpriseSelfAssessment;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;

/**
 * @Description:TODO(等级粮库评定管理-企业申报自评表服务实现)
 * @version: V1.0
 * @author: lzq
 */
@Service("gradedEnterpriseSelfAssessmentService")
public class GradedEnterpriseSelfAssessmentServiceImpl extends ServiceImpl<GradedEnterpriseSelfAssessmentMapper, GradedEnterpriseSelfAssessment> implements IGradedEnterpriseSelfAssessmentService {

    final static String CACHE_NAME = "composite:gradedEnterpriseSelfAssessment";

    @Resource
    private IGradedGrainDepotTemplateService gradedGrainDepotTemplateService;

    @Resource
    private IGradedGrainDepotStandardService grainDepotStandardService;

    @Resource
    private GradedEnterpriseSelfAssessmentMapper gradedEnterpriseSelfAssessmentMapper;

    @DataBindFieldConvert
    @Override
    public Page<GradedEnterpriseSelfAssessmentVo> pages(GradedEnterpriseSelfAssessmentPageParam pageParam) {
        Page<GradedEnterpriseSelfAssessment> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseSelfAssessment::getId, pageParam.getId())
                .eq(ObjectUtils.isNotEmpty(pageParam.getQyid()), GradedEnterpriseSelfAssessment::getQyid, pageParam.getQyid())
                .eq(ObjectUtils.isNotEmpty(pageParam.getTemplateId()), GradedEnterpriseSelfAssessment::getTemplateId, pageParam.getTemplateId())
                .eq(ObjectUtils.isNotEmpty(pageParam.getScoringMethodId()), GradedEnterpriseSelfAssessment::getScoringMethodId, pageParam.getScoringMethodId())
                .ne(GradedEnterpriseSelfAssessment::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseSelfAssessment::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GradedEnterpriseSelfAssessmentVo.class);
    }

    @DataBindFieldConvert
    @Override
    public List<GradedEnterpriseSelfAssessmentVo> list(String qyid) {
        List<GradedEnterpriseSelfAssessment> gradedEnterpriseSelfAssessmentList = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseSelfAssessment::getQyid, qyid)
                .ne(GradedEnterpriseSelfAssessment::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseSelfAssessment::getUpdateTime).list();
        List<GradedEnterpriseSelfAssessmentVo> gradedEnterpriseSelfAssessmentVoList = BeanUtils.copyToList(gradedEnterpriseSelfAssessmentList, GradedEnterpriseSelfAssessmentVo.class);
        GradedGrainDepotTemplate gradedGrainDepotTemplate = gradedGrainDepotTemplateService.getNowYearGradedGrainDepotTemplate();
        Assert.state(Objects.nonNull(gradedGrainDepotTemplate), "本年度暂未开启等级粮库评定！");
        List<GradedGrainDepotStandardExcelVo> gradedGrainDepotStandardExcelVos = grainDepotStandardService.getGradedGrainDepotStandardExcelVos(gradedGrainDepotTemplate.getId());
        for (int i = 0; i < gradedEnterpriseSelfAssessmentVoList.size(); i++) {
            GradedEnterpriseSelfAssessmentVo gradedEnterpriseSelfAssessmentVo = gradedEnterpriseSelfAssessmentVoList.get(i);
            for (int j = 0; j < gradedGrainDepotStandardExcelVos.size(); j++) {
                GradedGrainDepotStandardExcelVo grainDepotStandardExcelVo = gradedGrainDepotStandardExcelVos.get(j);
                if(StringUtils.equals(gradedEnterpriseSelfAssessmentVo.getScoringMethodId(),grainDepotStandardExcelVo.getScoringMethodId())){
                    gradedEnterpriseSelfAssessmentVo.setProject(grainDepotStandardExcelVo.getProject());
                    gradedEnterpriseSelfAssessmentVo.setSubject(grainDepotStandardExcelVo.getSubject());
                    gradedEnterpriseSelfAssessmentVo.setMaxScore(grainDepotStandardExcelVo.getMaxScore());
                    gradedEnterpriseSelfAssessmentVo.setContent(grainDepotStandardExcelVo.getContent());
                    gradedEnterpriseSelfAssessmentVo.setJfzb(grainDepotStandardExcelVo.getJfzb());
                    gradedEnterpriseSelfAssessmentVo.setMinScore(grainDepotStandardExcelVo.getMinScore());
                    gradedEnterpriseSelfAssessmentVo.setMethod(grainDepotStandardExcelVo.getMethod());
                    gradedEnterpriseSelfAssessmentVo.setProjectSort(grainDepotStandardExcelVo.getProjectSort());
                    gradedEnterpriseSelfAssessmentVo.setScoringMethodId(grainDepotStandardExcelVo.getScoringMethodId());
                }
            }
        }
        gradedEnterpriseSelfAssessmentVoList = gradedEnterpriseSelfAssessmentVoList.stream().sorted(Comparator.comparing(GradedEnterpriseSelfAssessmentVo::getProjectSort)).collect(Collectors.toList());
        return gradedEnterpriseSelfAssessmentVoList;
    }

    @DataBindFieldConvert
    @Override
    public List<XmDto> listDto(String qyid) {
        List<GradedEnterpriseSelfAssessment> gradedEnterpriseSelfAssessmentList = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseSelfAssessment::getQyid, qyid)
                .ne(GradedEnterpriseSelfAssessment::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseSelfAssessment::getUpdateTime).list();
        List<GradedEnterpriseSelfAssessmentVo> gradedEnterpriseSelfAssessmentVoList = BeanUtils.copyToList(gradedEnterpriseSelfAssessmentList, GradedEnterpriseSelfAssessmentVo.class);
        GradedGrainDepotTemplate gradedGrainDepotTemplate = gradedGrainDepotTemplateService.
                getGradedGrainDepotTemplateById(gradedEnterpriseSelfAssessmentVoList.get(0).getTemplateId());
        Assert.state(Objects.nonNull(gradedGrainDepotTemplate), "本年度暂未开启等级粮库评定！");
        List<XmDto> xmDtoList =  grainDepotStandardService.getGradedGrainDepotStandardDto(gradedGrainDepotTemplate.getId(),gradedEnterpriseSelfAssessmentVoList);
        return xmDtoList;
    }

    @DataBindFieldConvert
    @Override
    public List<GradedReturnSelfAssessmentVo> getGradedReturnSelfAssessmentList(String qyid,Boolean sdpf) {
        GradedGrainDepotTemplate gradedGrainDepotTemplate = gradedGrainDepotTemplateService.getNowYearGradedGrainDepotTemplate();
        Assert.state(Objects.nonNull(gradedGrainDepotTemplate), "本年度暂未开启等级粮库评定！");
        List<GradedGrainDepotStandardExcelVo> gradedGrainDepotStandardExcelVos = grainDepotStandardService.getGradedGrainDepotStandardExcelVos(gradedGrainDepotTemplate.getId());
        List<GradedReturnSelfAssessmentVo> gradedReturnSelfAssessmentVos = Lists.newArrayList();
        for (int j = 0; j < gradedGrainDepotStandardExcelVos.size(); j++) {
            GradedGrainDepotStandardExcelVo grainDepotStandardExcelVo = gradedGrainDepotStandardExcelVos.get(j);
            GradedReturnSelfAssessmentVo gradedReturnSelfAssessmentVo = new GradedReturnSelfAssessmentVo();
            gradedReturnSelfAssessmentVo.setQyid(qyid);
            gradedReturnSelfAssessmentVo.setTemplateId(gradedGrainDepotTemplate.getId());
            gradedReturnSelfAssessmentVo.setScoringMethodId(grainDepotStandardExcelVo.getScoringMethodId());
            gradedReturnSelfAssessmentVo.setZpf(grainDepotStandardExcelVo.getMinScore());
            gradedReturnSelfAssessmentVo.setSjzpf(grainDepotStandardExcelVo.getMinScore());
            if(sdpf){
                gradedReturnSelfAssessmentVo.setSdjcdf(grainDepotStandardExcelVo.getMinScore());
                gradedReturnSelfAssessmentVo.setKfyy(null);
            }else{
                gradedReturnSelfAssessmentVo.setSdjcdf(null);
                gradedReturnSelfAssessmentVo.setKfyy(null);
            }
            gradedReturnSelfAssessmentVo.setProjectSort(grainDepotStandardExcelVo.getProjectSort());
            gradedReturnSelfAssessmentVos.add(gradedReturnSelfAssessmentVo);
        }

        gradedReturnSelfAssessmentVos = gradedReturnSelfAssessmentVos.stream().sorted(Comparator.comparing(GradedReturnSelfAssessmentVo::getProjectSort)).collect(Collectors.toList());
        return gradedReturnSelfAssessmentVos;
    }

    @DataBindFieldConvert
    @Override
    public GradedEnterpriseSelfAssessmentVo detail(String id) {
        IGradedEnterpriseSelfAssessmentService gradedEnterpriseSelfAssessmentService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(gradedEnterpriseSelfAssessmentService.getById(id), GradedEnterpriseSelfAssessmentVo.class);
    }

    @DataBindService(strategy = DataBindGradedEnterpriseSelfAssessment.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gradedEnterpriseSelfAssessmentMapper);
    }

    
    @Override
    public String saveData(GradedEnterpriseSelfAssessmentSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseSelfAssessment gradedEnterpriseSelfAssessment = BeanUtils.copyByClass(param, GradedEnterpriseSelfAssessment.class);
        gradedEnterpriseSelfAssessment.setId(IdUtil.simpleUUID());
        gradedEnterpriseSelfAssessment.setCreateBy(loginUser.getName());
        gradedEnterpriseSelfAssessment.setUpdateBy(loginUser.getName());
        gradedEnterpriseSelfAssessment.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseSelfAssessment.setCreateTime(LocalDateTime.now());
        gradedEnterpriseSelfAssessment.setCzbz(CzBzEnum.I.getCzBz());
        super.save(gradedEnterpriseSelfAssessment);
        param.setId(gradedEnterpriseSelfAssessment.getId());
        return gradedEnterpriseSelfAssessment.getId();
    }

    
    @Override
    public String updateData(GradedEnterpriseSelfAssessmentSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseSelfAssessment gradedEnterpriseSelfAssessment = BeanUtils.copyByClass(param, GradedEnterpriseSelfAssessment.class);
        gradedEnterpriseSelfAssessment.setCzbz(CzBzEnum.U.getCzBz());
        gradedEnterpriseSelfAssessment.setUpdateBy(loginUser.getName());
        gradedEnterpriseSelfAssessment.setUpdateTime(LocalDateTime.now());
        super.updateById(gradedEnterpriseSelfAssessment);
        param.setId(gradedEnterpriseSelfAssessment.getId());
        return gradedEnterpriseSelfAssessment.getId();
    }

    
    @Override
    public Boolean saveOrUpdateBatchData(List<GradedEnterpriseSelfAssessmentSaveParam> params) {
        Boolean returnStatus = true;
        LoginUser loginUser = UserSecurity.loginUser();
        List<GradedEnterpriseSelfAssessment> gradedEnterpriseSelfAssessments = BeanUtils.copyToList(params, GradedEnterpriseSelfAssessment.class);
        if(CollectionUtil.isNotEmpty(gradedEnterpriseSelfAssessments)){
            for (int i = 0; i < gradedEnterpriseSelfAssessments.size(); i++) {
                GradedEnterpriseSelfAssessment gradedEnterpriseSelfAssessment = gradedEnterpriseSelfAssessments.get(i);
                gradedEnterpriseSelfAssessment.setCreateBy(loginUser.getName());
                gradedEnterpriseSelfAssessment.setUpdateBy(loginUser.getName());
                gradedEnterpriseSelfAssessment.setUpdateTime(LocalDateTime.now());
                gradedEnterpriseSelfAssessment.setCreateTime(LocalDateTime.now());
                gradedEnterpriseSelfAssessment.setCzbz(CzBzEnum.I.getCzBz());
                returnStatus = super.saveOrUpdate(gradedEnterpriseSelfAssessment);
            }
        }
        return returnStatus;
    }

    
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(GradedEnterpriseSelfAssessment::getId, ids)
                .set(GradedEnterpriseSelfAssessment::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedEnterpriseSelfAssessment::getUpdateBy, loginUser.getName())
                .set(GradedEnterpriseSelfAssessment::getUpdateTime, LocalDateTime.now())
                .update();
    }

}