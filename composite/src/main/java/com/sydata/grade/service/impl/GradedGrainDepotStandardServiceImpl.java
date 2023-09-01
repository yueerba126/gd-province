/**
 * @filename:GradedGrainDepotStandardServiceImpl 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2018 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.framework.core.util.TreeUtils;
import com.sydata.framework.util.StringUtils;
import com.sydata.grade.domain.GradedEnterpriseSelfAssessment;
import com.sydata.grade.domain.GradedGrainDepotTemplate;
import com.sydata.grade.dto.DxDto;
import com.sydata.grade.dto.XmDto;
import com.sydata.grade.dto.XxDto;
import com.sydata.grade.enums.GradeBcztEnum;
import com.sydata.grade.enums.GradeJfzbEnum;
import com.sydata.grade.param.FileStorageUploadParam;
import com.sydata.grade.utils.ObjectCustomUtils;
import com.sydata.grade.vo.GradedEnterpriseSelfAssessmentVo;
import com.sydata.grade.vo.GradedGrainDepotStandardExcelVo;
import com.sydata.grade.vo.GradedGrainDepotStandardTreeVo;
import com.sydata.organize.domain.Menu;
import io.netty.util.internal.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.grade.domain.GradedGrainDepotStandard;
import com.sydata.grade.mapper.GradedGrainDepotStandardMapper;
import com.sydata.grade.service.IGradedGrainDepotStandardService;
import com.sydata.grade.param.GradedGrainDepotStandardPageParam;
import com.sydata.grade.param.GradedGrainDepotStandardSaveParam;
import com.sydata.grade.vo.GradedGrainDepotStandardVo;
import com.sydata.grade.annotation.DataBindGradedGrainDepotStandard;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;

import static com.sydata.organize.service.impl.OrganizeServiceImpl.ROOT_PARENT_ID;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定标准服务实现)
 * @version: V1.0
 * @author: lzq
 */
@Service("gradedGrainDepotStandardService")
public class GradedGrainDepotStandardServiceImpl extends ServiceImpl<GradedGrainDepotStandardMapper, GradedGrainDepotStandard> implements IGradedGrainDepotStandardService {

    final static String CACHE_NAME = "composite:gradedGrainDepotStandard";

    @Resource
    private GradedGrainDepotStandardMapper gradedGrainDepotStandardMapper;

    @Resource
    private IFileStorageService fileStorageService;

    @DataBindFieldConvert
    @Override
    public List<XmDto> uploadUse(FileStorageUploadParam fileStorageUploadParam) {
        // 只支持xlsx或者xls的文件格式
        String fileType = FileUtil.extName(fileStorageUploadParam.getFile().getOriginalFilename());
        boolean state = isState(fileType);
        Assert.state(state, "只支持xlsx或者xls的文件格式,系统检测该文件实际类型是" + fileType);
        try {
            excelImportParsing(fileStorageUploadParam.getFile().getInputStream(),fileStorageUploadParam.getTemplateId(),fileStorageUploadParam.getFile().getOriginalFilename());
        } catch (IOException e) {
            Assert.isNull(fileStorageUploadParam.getFile().getOriginalFilename(), "文件解析失败，原因为：" + e.getMessage());
        }
        fileStorageService.uploadUse(fileStorageUploadParam.getFile(), fileStorageUploadParam.getOrganizeId(), fileStorageUploadParam.getStockHouseId());
        return getGradedGrainDepotStandardDto(fileStorageUploadParam.getTemplateId());
    }

    @DataBindFieldConvert
    @Override
    public List<XmDto> getGradedGrainDepotStandardDto (String id) {
        List<GradedGrainDepotStandardVo> vos = listByTemplateId(id);
        List<GradedGrainDepotStandardVo> xms = vos.stream().filter(e -> StringUtils.equals(e.getParentId(),"0")).collect(Collectors.toList());
        List<GradedGrainDepotStandardVo> dms = vos.stream().filter(e -> !StringUtils.equals(e.getParentId(),"0")&&StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<GradedGrainDepotStandardVo> xxms = vos.stream().filter(e -> !StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<XmDto> xmDtos = Lists.newArrayList();
        for (int i = 0; i < xms.size(); i++) {
            List<DxDto> dxDtos = Lists.newArrayList();
            GradedGrainDepotStandardVo xmsItem = xms.get(i);
            XmDto xm = new XmDto();
            xm.setXmmc(xmsItem.getProjectName());
            xm.setTemplateId(xmsItem.getTemplateId());
            for (int j = 0; j < dms.size(); j++) {
                List<XxDto> xxDtos = Lists.newArrayList();
                GradedGrainDepotStandardVo dmsItem = dms.get(j);
                if(StringUtils.equals(xmsItem.getId(),dmsItem.getParentId())){
                    DxDto dx = new DxDto();
                    dx.setDxtm(dmsItem.getProjectName());
                    dx.setDxfz(dmsItem.getProjectScore());
                    dx.setPdnr(dmsItem.getAssessmentContent());
                    dx.setTemplateId(xmsItem.getTemplateId());
                    dxDtos.add(dx);
                    for (int k = 0; k < xxms.size(); k++) {
                        GradedGrainDepotStandardVo xxmsItem = xxms.get(k);
                        XxDto xxDto = new XxDto();
                        if(StringUtils.equals(dmsItem.getId(),xxmsItem.getParentId())){
                            xxDto.setScoringMethodId(xxmsItem.getId());
                            xxDto.setProjectSort(xxmsItem.getProjectSort());
                            xxDto.setJfzb(xxmsItem.getJfzb());
                            xxDto.setJfzbName(xxmsItem.getJfzbName());
                            xxDto.setJfff(xxmsItem.getScoringMethod());
                            xxDto.setXxfz(xxmsItem.getProjectScore());
                            xxDto.setDf(xxmsItem.getScore());
                            xxDto.setTemplateId(xmsItem.getTemplateId());
                            xxDtos.add(xxDto);
                        }
                    }
                    dx.setXxDtos(xxDtos);
                }
            }
            xm.setDxInfos(dxDtos);
            xmDtos.add(xm);
        }
        return xmDtos;
    }

    @DataBindFieldConvert
    @Override
    public List<XmDto> getGradedGrainDepotStandardDtoWithHistory (String id) {
        List<GradedGrainDepotStandardVo> vos = listByTemplateIdWithHistory(id);
        List<GradedGrainDepotStandardVo> xms = vos.stream().filter(e -> org.apache.commons.lang3.StringUtils.equals(e.getParentId(),"0")).collect(Collectors.toList());
        List<GradedGrainDepotStandardVo> dms = vos.stream().filter(e -> !org.apache.commons.lang3.StringUtils.equals(e.getParentId(),"0")&& org.apache.commons.lang3.StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<GradedGrainDepotStandardVo> xxms = vos.stream().filter(e -> !org.apache.commons.lang3.StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<XmDto> xmDtos = Lists.newArrayList();
        for (int i = 0; i < xms.size(); i++) {
            List<DxDto> dxDtos = Lists.newArrayList();
            GradedGrainDepotStandardVo xmsItem = xms.get(i);
            XmDto xm = new XmDto();
            xm.setXmmc(xmsItem.getProjectName());
            xm.setTemplateId(xmsItem.getTemplateId());
            for (int j = 0; j < dms.size(); j++) {
                List<XxDto> xxDtos = Lists.newArrayList();
                GradedGrainDepotStandardVo dmsItem = dms.get(j);
                if(org.apache.commons.lang3.StringUtils.equals(xmsItem.getId(),dmsItem.getParentId())){
                    DxDto dx = new DxDto();
                    dx.setDxtm(dmsItem.getProjectName());
                    dx.setDxfz(dmsItem.getProjectScore());
                    dx.setPdnr(dmsItem.getAssessmentContent());
                    dx.setTemplateId(xmsItem.getTemplateId());
                    dxDtos.add(dx);
                    for (int k = 0; k < xxms.size(); k++) {
                        GradedGrainDepotStandardVo xxmsItem = xxms.get(k);
                        XxDto xxDto = new XxDto();
                        if(org.apache.commons.lang3.StringUtils.equals(dmsItem.getId(),xxmsItem.getParentId())){
                            xxDto.setScoringMethodId(xxmsItem.getId());
                            xxDto.setProjectSort(xxmsItem.getProjectSort());
                            xxDto.setJfzb(xxmsItem.getJfzb());
                            xxDto.setJfzbName(xxmsItem.getJfzbName());
                            xxDto.setJfff(xxmsItem.getScoringMethod());
                            xxDto.setXxfz(xxmsItem.getProjectScore());
                            xxDto.setDf(xxmsItem.getScore());
                            xxDto.setTemplateId(xmsItem.getTemplateId());
                            xxDtos.add(xxDto);
                        }
                    }
                    dx.setXxDtos(xxDtos);
                }
            }
            xm.setDxInfos(dxDtos);
            xmDtos.add(xm);
        }
        return xmDtos;
    }

    @DataBindFieldConvert
    @Override
    public List<XmDto> getGradedGrainDepotStandardDto (String id,List<GradedEnterpriseSelfAssessmentVo> gradedEnterpriseSelfAssessmentVoList) {
        List<GradedGrainDepotStandardVo> vos = listByTemplateIdWithHistory(id);
        List<GradedGrainDepotStandardVo> xms = vos.stream().filter(e -> StringUtils.equals(e.getParentId(),"0")).collect(Collectors.toList());
        List<GradedGrainDepotStandardVo> dms = vos.stream().filter(e -> !StringUtils.equals(e.getParentId(),"0")&&StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<GradedGrainDepotStandardVo> xxms = vos.stream().filter(e -> !StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<XmDto> xmDtos = Lists.newArrayList();
        for (int i = 0; i < xms.size(); i++) {
            List<DxDto> dxDtos = Lists.newArrayList();
            GradedGrainDepotStandardVo xmsItem = xms.get(i);
            XmDto xm = new XmDto();
            xm.setXmmc(xmsItem.getProjectName());
            xm.setTemplateId(xmsItem.getTemplateId());
            for (int j = 0; j < dms.size(); j++) {
                List<XxDto> xxDtos = Lists.newArrayList();
                GradedGrainDepotStandardVo dmsItem = dms.get(j);
                if(StringUtils.equals(xmsItem.getId(),dmsItem.getParentId())){
                    DxDto dx = new DxDto();
                    dx.setDxtm(dmsItem.getProjectName());
                    dx.setDxfz(dmsItem.getProjectScore());
                    dx.setPdnr(dmsItem.getAssessmentContent());
                    dx.setTemplateId(xmsItem.getTemplateId());
                    dxDtos.add(dx);
                    for (int k = 0; k < xxms.size(); k++) {
                        GradedGrainDepotStandardVo xxmsItem = xxms.get(k);
                        XxDto xxDto = new XxDto();
                        if(StringUtils.equals(dmsItem.getId(),xxmsItem.getParentId())){
                            GradedEnterpriseSelfAssessmentVo filter = gradedEnterpriseSelfAssessmentVoList.stream().filter(e->StringUtils.equals(e.getScoringMethodId(),xxmsItem.getId())).findFirst().orElse(null);
                            if(ObjectUtils.isEmpty(filter)){
                                continue;
                            }
                            xxDto.setId(filter.getId());
                            xxDto.setScoringMethodId(filter.getScoringMethodId());
                            xxDto.setProjectSort(xxmsItem.getProjectSort());
                            xxDto.setJfzb(xxmsItem.getJfzb());
                            xxDto.setJfzbName(xxmsItem.getJfzbName());
                            xxDto.setJfff(xxmsItem.getScoringMethod());
                            xxDto.setXxfz(xxmsItem.getProjectScore());
                            xxDto.setDf(xxmsItem.getScore());
                            xxDto.setTemplateId(xmsItem.getTemplateId());
                            xxDto.setProvinceSddf(filter.getProvinceSddf());
                            xxDto.setProvinceKfyy(filter.getProvinceKfyy());
                            xxDto.setCitySddf(filter.getCitySddf());
                            xxDto.setCityKfyy(filter.getCityKfyy());
                            xxDto.setSjzpf(filter.getSjzpf());
                            xxDto.setZpf(filter.getZpf());
                            xxDtos.add(xxDto);
                        }
                    }
                    dx.setXxDtos(xxDtos);
                }
            }
            xm.setDxInfos(dxDtos);
            xmDtos.add(xm);
        }
        return xmDtos;
    }

    @DataBindFieldConvert
    @Override
    public List<XmDto> getGradedGrainDepotStandardDtoWithHistory (String id,List<GradedEnterpriseSelfAssessmentVo> gradedEnterpriseSelfAssessmentVoList) {
        List<GradedGrainDepotStandardVo> vos = listByTemplateIdWithHistory(id);
        List<GradedGrainDepotStandardVo> xms = vos.stream().filter(e -> StringUtils.equals(e.getParentId(),"0")).collect(Collectors.toList());
        List<GradedGrainDepotStandardVo> dms = vos.stream().filter(e -> !StringUtils.equals(e.getParentId(),"0")&&StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<GradedGrainDepotStandardVo> xxms = vos.stream().filter(e -> !StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<XmDto> xmDtos = Lists.newArrayList();
        for (int i = 0; i < xms.size(); i++) {
            List<DxDto> dxDtos = Lists.newArrayList();
            GradedGrainDepotStandardVo xmsItem = xms.get(i);
            XmDto xm = new XmDto();
            xm.setXmmc(xmsItem.getProjectName());
            xm.setTemplateId(xmsItem.getTemplateId());
            for (int j = 0; j < dms.size(); j++) {
                List<XxDto> xxDtos = Lists.newArrayList();
                GradedGrainDepotStandardVo dmsItem = dms.get(j);
                if(StringUtils.equals(xmsItem.getId(),dmsItem.getParentId())){
                    DxDto dx = new DxDto();
                    dx.setDxtm(dmsItem.getProjectName());
                    dx.setDxfz(dmsItem.getProjectScore());
                    dx.setPdnr(dmsItem.getAssessmentContent());
                    dx.setTemplateId(xmsItem.getTemplateId());
                    dxDtos.add(dx);
                    for (int k = 0; k < xxms.size(); k++) {
                        GradedGrainDepotStandardVo xxmsItem = xxms.get(k);
                        XxDto xxDto = new XxDto();
                        if(StringUtils.equals(dmsItem.getId(),xxmsItem.getParentId())){
                            GradedEnterpriseSelfAssessmentVo filter = gradedEnterpriseSelfAssessmentVoList.stream().filter(e->StringUtils.equals(e.getScoringMethodId(),xxmsItem.getId())).findFirst().orElse(null);
                            if(ObjectUtils.isEmpty(filter)){
                                continue;
                            }
                            xxDto.setId(filter.getId());
                            xxDto.setScoringMethodId(filter.getScoringMethodId());
                            xxDto.setProjectSort(xxmsItem.getProjectSort());
                            xxDto.setJfzb(xxmsItem.getJfzb());
                            xxDto.setJfzbName(xxmsItem.getJfzbName());
                            xxDto.setJfff(xxmsItem.getScoringMethod());
                            xxDto.setXxfz(xxmsItem.getProjectScore());
                            xxDto.setDf(xxmsItem.getScore());
                            xxDto.setTemplateId(xmsItem.getTemplateId());
                            xxDto.setProvinceSddf(filter.getProvinceSddf());
                            xxDto.setProvinceKfyy(filter.getProvinceKfyy());
                            xxDto.setCitySddf(filter.getCitySddf());
                            xxDto.setCityKfyy(filter.getCityKfyy());
                            xxDto.setSjzpf(filter.getSjzpf());
                            xxDto.setZpf(filter.getZpf());
                            xxDtos.add(xxDto);
                        }
                    }
                    dx.setXxDtos(xxDtos);
                }
            }
            xm.setDxInfos(dxDtos);
            xmDtos.add(xm);
        }
        return xmDtos;
    }

    @DataBindFieldConvert
    @Override
    public List<GradedGrainDepotStandardExcelVo> getGradedGrainDepotStandardExcelVos(String id) {
        List<GradedGrainDepotStandardVo> vos = listByTemplateId(id);
        List<GradedGrainDepotStandard> details = vos.stream().filter(e -> !StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<GradedGrainDepotStandardExcelVo> excelVos = Lists.newArrayList();
        for (int i = 0; i < details.size(); i++) {
            GradedGrainDepotStandard item = details.get(i);
            GradedGrainDepotStandardExcelVo vo = new GradedGrainDepotStandardExcelVo();
            vo.setScoringMethodId(item.getId());
            vo.setProjectSort(item.getProjectSort());
            vo.setJfzb(item.getJfzb());
            vo.setMethod(item.getScoringMethod());
            vo.setMinScore(item.getProjectScore());
            vo.setScore(item.getScore());
            GradedGrainDepotStandard parentVo = vos.stream().filter(e -> StringUtils.equals(e.getId(), item.getParentId())).findFirst().orElse(new GradedGrainDepotStandardVo());
            vo.setSubject(parentVo.getProjectName());
            vo.setMaxScore(parentVo.getProjectScore());
            vo.setContent(parentVo.getAssessmentContent());
            GradedGrainDepotStandard oldParentVo = vos.stream().filter(e -> StringUtils.equals(e.getId(), parentVo.getParentId())).findFirst().orElse(new GradedGrainDepotStandardVo());
            vo.setProject(oldParentVo.getProjectName());
            excelVos.add(vo);
        }
        return excelVos;
    }

    @DataBindFieldConvert
    @Override
    public List<GradedGrainDepotStandardExcelVo> getGradedGrainDepotStandardExcelVosWithHistory(String id) {
        List<GradedGrainDepotStandardVo> vos = listByTemplateIdWithHistory(id);
        List<GradedGrainDepotStandard> details = vos.stream().filter(e -> !org.apache.commons.lang3.StringUtils.isEmpty(e.getScoringMethod())).collect(Collectors.toList());
        List<GradedGrainDepotStandardExcelVo> excelVos = Lists.newArrayList();
        for (int i = 0; i < details.size(); i++) {
            GradedGrainDepotStandard item = details.get(i);
            GradedGrainDepotStandardExcelVo vo = new GradedGrainDepotStandardExcelVo();
            vo.setScoringMethodId(item.getId());
            vo.setProjectSort(item.getProjectSort());
            vo.setJfzb(item.getJfzb());
            vo.setMethod(item.getScoringMethod());
            vo.setMinScore(item.getProjectScore());
            vo.setScore(item.getScore());
            GradedGrainDepotStandard parentVo = vos.stream().filter(e -> org.apache.commons.lang3.StringUtils.equals(e.getId(), item.getParentId())).findFirst().orElse(new GradedGrainDepotStandardVo());
            vo.setSubject(parentVo.getProjectName());
            vo.setMaxScore(parentVo.getProjectScore());
            vo.setContent(parentVo.getAssessmentContent());
            GradedGrainDepotStandard oldParentVo = vos.stream().filter(e -> org.apache.commons.lang3.StringUtils.equals(e.getId(), parentVo.getParentId())).findFirst().orElse(new GradedGrainDepotStandardVo());
            vo.setProject(oldParentVo.getProjectName());
            excelVos.add(vo);
        }
        return excelVos;
    }

    @Override
    public void excelImportParsing(InputStream inputStream, String templateId, String fileName) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<GradedGrainDepotStandardVo> exists = listByTemplateId(templateId);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        List<GradedGrainDepotStandardExcelVo> list = Lists.newArrayList();
        List<GradedGrainDepotStandardSaveParam> grainDepotStandardSaveParams = Lists.newArrayList();
        try {
            list = ExcelImportUtil.importExcel(inputStream, GradedGrainDepotStandardExcelVo.class, params);
            if (CollectionUtil.isNotEmpty(list)) {
                list = list.stream().filter(e -> ObjectUtils.isNotEmpty(e) && !ObjectCustomUtils.allfieldIsNUll(e)).collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(list) && list.size() > 0) {
                    // 将空字段按规律填满
                    fillNullData(list);
                    // 组装id和parentId
                    AssembleTree(templateId, loginUser, list, grainDepotStandardSaveParams);
                    // 保存或是更新标准数据
                    if (CollectionUtil.isNotEmpty(grainDepotStandardSaveParams)) {
                        List<GradedGrainDepotStandard> gradedGrainDepotStandards = BeanUtils.copyToList(grainDepotStandardSaveParams, GradedGrainDepotStandard.class);
                        if (!CollectionUtil.isEmpty(exists)) {
                            QueryWrapper<GradedGrainDepotStandard> gradedGrainDepotStandardQueryWrapper = new QueryWrapper();
                            gradedGrainDepotStandardQueryWrapper.lambda().eq(StringUtils.isNotEmpty(templateId), GradedGrainDepotStandard::getTemplateId, templateId);
                            super.remove(gradedGrainDepotStandardQueryWrapper);
                        }
                        super.saveBatch(gradedGrainDepotStandards);
                    }
                } else {
                    Assert.notNull(list, "文件内容不规范，请检查格式或内容：" + fileName);
                }
            } else {
                Assert.notNull(list, "文件内容不规范，请检查格式或内容：" + fileName);
            }
        } catch (Exception e) {
            Assert.notNull(inputStream, "文件解析失败，请检查格式：" + fileName);
        }
    }

    private void AssembleTree(String templateId, LoginUser loginUser, List<GradedGrainDepotStandardExcelVo> list, List<GradedGrainDepotStandardSaveParam> grainDepotStandardSaveParams) {
        for (int i = 0; i < list.size(); i++) {
            GradedGrainDepotStandardExcelVo excelVo = list.get(i);
            GradedGrainDepotStandardSaveParam projectVo = grainDepotStandardSaveParams.stream().filter(x -> StringUtils.equals(x.getProjectName(), excelVo.getProject())).findFirst().orElse(null);
            GradedGrainDepotStandardSaveParam subjectVo = grainDepotStandardSaveParams.stream().filter(x -> StringUtils.equals(x.getProjectName(), excelVo.getSubject())).findFirst().orElse(null);
            GradedGrainDepotStandardSaveParam methodVo = grainDepotStandardSaveParams.stream().filter(x -> StringUtils.equals(x.getScoringMethod(), excelVo.getMethod())).findFirst().orElse(null);

            if (StringUtils.isNotEmpty(excelVo.getProject()) && projectVo == null) {
                GradedGrainDepotStandardSaveParam grainDepotStandardSaveParam = new GradedGrainDepotStandardSaveParam();
                if (excelVo.getProject().contains("加分")) {
                    grainDepotStandardSaveParam.setJfzb(GradeJfzbEnum.YES_CODE.getState());
                } else {
                    grainDepotStandardSaveParam.setJfzb(GradeJfzbEnum.NO_CODE.getState());
                }
                grainDepotStandardSaveParam.setTemplateId(templateId);
                grainDepotStandardSaveParam.setId(IdUtil.simpleUUID());
                grainDepotStandardSaveParam.setParentId("0");
                grainDepotStandardSaveParam.setProjectSort(i * 100 + 1);
                grainDepotStandardSaveParam.setProjectName(excelVo.getProject());
                grainDepotStandardSaveParam.setCreateBy(loginUser.getName());
                grainDepotStandardSaveParam.setUpdateBy(loginUser.getName());
                grainDepotStandardSaveParam.setUpdateTime(LocalDateTime.now());
                grainDepotStandardSaveParam.setCreateTime(LocalDateTime.now());
                grainDepotStandardSaveParam.setCzbz(CzBzEnum.I.getCzBz());
                grainDepotStandardSaveParam.setBczt(GradeBcztEnum.TJ.getState());
                grainDepotStandardSaveParams.add(grainDepotStandardSaveParam);
            }
            projectVo = grainDepotStandardSaveParams.stream().filter(x -> StringUtils.equals(x.getProjectName(), excelVo.getProject())).findFirst().orElse(null);
            if (StringUtils.isNotEmpty(excelVo.getSubject()) && subjectVo == null) {
                GradedGrainDepotStandardSaveParam grainDepotStandardSaveParam = new GradedGrainDepotStandardSaveParam();
                if (StringUtils.equals(projectVo.getJfzb(),GradeJfzbEnum.YES_CODE.getState())) {
                    grainDepotStandardSaveParam.setJfzb(GradeJfzbEnum.YES_CODE.getState());
                } else {
                    grainDepotStandardSaveParam.setJfzb(GradeJfzbEnum.NO_CODE.getState());
                }
                grainDepotStandardSaveParam.setTemplateId(templateId);
                grainDepotStandardSaveParam.setId(IdUtil.simpleUUID());
                grainDepotStandardSaveParam.setParentId(projectVo.getId());
                grainDepotStandardSaveParam.setProjectSort(i * 100 + 2);
                grainDepotStandardSaveParam.setProjectName(excelVo.getSubject());
                grainDepotStandardSaveParam.setProjectScore(excelVo.getMaxScore());
                grainDepotStandardSaveParam.setAssessmentContent(excelVo.getContent());
                grainDepotStandardSaveParam.setCreateBy(loginUser.getName());
                grainDepotStandardSaveParam.setUpdateBy(loginUser.getName());
                grainDepotStandardSaveParam.setUpdateTime(LocalDateTime.now());
                grainDepotStandardSaveParam.setCreateTime(LocalDateTime.now());
                grainDepotStandardSaveParam.setCzbz(CzBzEnum.I.getCzBz());
                grainDepotStandardSaveParam.setBczt(GradeBcztEnum.TJ.getState());
                // 匹配第一个
                grainDepotStandardSaveParams.add(grainDepotStandardSaveParam);
            }
            subjectVo = grainDepotStandardSaveParams.stream().filter(x -> StringUtils.equals(x.getProjectName(), excelVo.getSubject())).findFirst().orElse(null);
            if (StringUtils.isNotEmpty(excelVo.getMethod()) && methodVo == null) {
                GradedGrainDepotStandardSaveParam grainDepotStandardSaveParam = new GradedGrainDepotStandardSaveParam();
                if (StringUtils.equals(subjectVo.getJfzb(),GradeJfzbEnum.YES_CODE.getState())) {
                    grainDepotStandardSaveParam.setJfzb(GradeJfzbEnum.YES_CODE.getState());
                } else {
                    grainDepotStandardSaveParam.setJfzb(GradeJfzbEnum.NO_CODE.getState());
                }
                grainDepotStandardSaveParam.setTemplateId(templateId);
                grainDepotStandardSaveParam.setId(IdUtil.simpleUUID());
                grainDepotStandardSaveParam.setParentId(subjectVo.getId());
                grainDepotStandardSaveParam.setProjectSort(i * 100 + 3);
                grainDepotStandardSaveParam.setScoringMethod(excelVo.getMethod());
                grainDepotStandardSaveParam.setProjectScore(excelVo.getMinScore());
                grainDepotStandardSaveParam.setScore(excelVo.getScore());
                grainDepotStandardSaveParam.setCreateBy(loginUser.getName());
                grainDepotStandardSaveParam.setUpdateBy(loginUser.getName());
                grainDepotStandardSaveParam.setUpdateTime(LocalDateTime.now());
                grainDepotStandardSaveParam.setCreateTime(LocalDateTime.now());
                grainDepotStandardSaveParam.setCzbz(CzBzEnum.I.getCzBz());
                grainDepotStandardSaveParam.setBczt(GradeBcztEnum.TJ.getState());
                grainDepotStandardSaveParams.add(grainDepotStandardSaveParam);
            }
            methodVo = grainDepotStandardSaveParams.stream().filter(x -> StringUtils.equals(x.getScoringMethod(), excelVo.getMethod())).findFirst().orElse(null);
        }
    }

    private void fillNullData(List<GradedGrainDepotStandardExcelVo> list) {
        GradedGrainDepotStandardExcelVo tmp = new GradedGrainDepotStandardExcelVo();
        for (int i = 0; i < list.size(); i++) {
            GradedGrainDepotStandardExcelVo excelVo = list.get(i);
            if (StringUtils.isNotEmpty(excelVo.getProject())) {
                tmp.setProject(excelVo.getProject());
            }
            if (StringUtils.isNotEmpty(excelVo.getSubject())) {
                tmp.setSubject(excelVo.getSubject());
            }
            if (StringUtils.isNotEmpty(excelVo.getContent())) {
                tmp.setContent(excelVo.getContent());
            }
            if (ObjectUtils.isNotEmpty(excelVo.getMaxScore())) {
                tmp.setMaxScore(excelVo.getMaxScore());
            }
            if (StringUtils.isEmpty(excelVo.getProject())) {
                excelVo.setProject(tmp.getProject());
            }
            if (StringUtils.isEmpty(excelVo.getSubject())) {
                excelVo.setSubject(tmp.getSubject());
            }
            if (StringUtils.isEmpty(excelVo.getContent())) {
                excelVo.setContent(tmp.getContent());
            }
            if (ObjectUtils.isEmpty(excelVo.getMaxScore())) {
                excelVo.setMaxScore(tmp.getMaxScore());
            }
        }
    }

    private boolean isState(String fileType) {
        Set<String> fileTypes = new HashSet();
        fileTypes.add("xlsx");
        fileTypes.add("xls");
        return fileTypes.contains(fileType);
    }

    @DataBindFieldConvert
    @Override
    public List<GradedGrainDepotStandardTreeVo> treeByTemplateId(String templateId) {
        List<GradedGrainDepotStandardVo> list = SpringUtil.getBean(this.getClass()).listByTemplateId(templateId);
        List<GradedGrainDepotStandardTreeVo> vos = BeanUtils.copyToList(list, GradedGrainDepotStandardTreeVo.class);

        return TreeUtils.toTree(vos, GradedGrainDepotStandardTreeVo::getId,
                GradedGrainDepotStandardTreeVo::getParentId,
                GradedGrainDepotStandardTreeVo::setChild, ROOT_PARENT_ID);
    }

    @DataBindFieldConvert
    @Override
    public List<GradedGrainDepotStandardVo> listByTemplateId(String templateId) {
        List<GradedGrainDepotStandard> list = super.lambdaQuery().
                ne(GradedGrainDepotStandard::getCzbz, CzBzEnum.D.getCzBz()).
                eq(GradedGrainDepotStandard::getTemplateId, templateId).
                orderByAsc(GradedGrainDepotStandard::getProjectSort).list();
        return BeanUtils.copyToList(list, GradedGrainDepotStandardVo.class);
    }

    @DataBindFieldConvert
    @Override
    public List<GradedGrainDepotStandardVo> listByTemplateIdWithHistory(String templateId) {
        List<GradedGrainDepotStandard> list = super.lambdaQuery()
                .eq(GradedGrainDepotStandard::getTemplateId, templateId).
                        orderByAsc(GradedGrainDepotStandard::getProjectSort).list();
        return BeanUtils.copyToList(list, GradedGrainDepotStandardVo.class);
    }

    @DataBindFieldConvert
    @Override
    public Page<GradedGrainDepotStandardVo> pages(GradedGrainDepotStandardPageParam pageParam) {
        Page<GradedGrainDepotStandard> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedGrainDepotStandard::getId, pageParam.getId())
                .eq(ObjectUtils.isNotEmpty(pageParam.getTemplateId()), GradedGrainDepotStandard::getTemplateId, pageParam.getTemplateId())
                .eq(ObjectUtils.isNotEmpty(pageParam.getParentId()), GradedGrainDepotStandard::getParentId, pageParam.getParentId())
                .eq(ObjectUtils.isNotEmpty(pageParam.getJfzb()), GradedGrainDepotStandard::getJfzb, pageParam.getJfzb())
                .ge(ObjectUtils.isNotEmpty(pageParam.getBeginUpdateTime()), GradedGrainDepotStandard::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(ObjectUtils.isNotEmpty(pageParam.getEndUpdateTime()), GradedGrainDepotStandard::getUpdateTime, pageParam.getEndUpdateTime())
                .ne(GradedGrainDepotStandard::getCzbz, CzBzEnum.D.getCzBz())
                .orderByAsc(GradedGrainDepotStandard::getProjectSort)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GradedGrainDepotStandardVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GradedGrainDepotStandardVo detail(String id) {
        IGradedGrainDepotStandardService gradedGrainDepotStandardService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(gradedGrainDepotStandardService.getById(id), GradedGrainDepotStandardVo.class);
    }

    @DataBindService(strategy = DataBindGradedGrainDepotStandard.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gradedGrainDepotStandardMapper);
    }

    @Override
    public String saveData(GradedGrainDepotStandardSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedGrainDepotStandard gradedGrainDepotStandard = BeanUtils.copyByClass(param, GradedGrainDepotStandard.class);
        gradedGrainDepotStandard.setId(IdUtil.simpleUUID());
        gradedGrainDepotStandard.setCreateBy(loginUser.getName());
        gradedGrainDepotStandard.setUpdateBy(loginUser.getName());
        gradedGrainDepotStandard.setUpdateTime(LocalDateTime.now());
        gradedGrainDepotStandard.setCreateTime(LocalDateTime.now());
        gradedGrainDepotStandard.setCzbz(CzBzEnum.I.getCzBz());
        gradedGrainDepotStandard.setJfzb(getRootParentIsJfzb(param));
        gradedGrainDepotStandard.setBczt(GradeBcztEnum.ZC.getState());

        if(!StringUtils.equals(param.getParentId(),"0")){
            Assert.isTrue(param.getProjectScore().compareTo(BigDecimal.ZERO)>0, "指标分值必须大于零");
        }

        if(!StringUtil.isNullOrEmpty(param.getScoringMethod())){
            GradedGrainDepotStandard grainDepotStandard = detail(param.getParentId());
            BigDecimal projectScore = grainDepotStandard.getProjectScore();
            List<GradedGrainDepotStandard> gradedGrainDepotStandardList = listByParentId(param.getParentId());
            BigDecimal sum = gradedGrainDepotStandardList.stream().filter(e->StringUtils.isNotEmpty(e.getScoringMethod())).
                    map(GradedGrainDepotStandard::getProjectScore).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            int status = sum.add(param.getProjectScore()).compareTo(projectScore);
            Assert.isTrue(status<=0, "三级指标的分数之和不能大于其父集指标");
        }

        List<GradedGrainDepotStandardVo> gradedGrainDepotStandardVos = listByTemplateId(param.getTemplateId());
        if(CollectionUtil.isNotEmpty(gradedGrainDepotStandardVos)){
            Integer maxSort = gradedGrainDepotStandardVos.stream().map(e->e.getProjectSort()).collect(Collectors.maxBy(Integer::compare)).orElse(0);
            gradedGrainDepotStandard.setProjectSort(maxSort+1);
        }else{
            gradedGrainDepotStandard.setProjectSort(0);
        }

        super.save(gradedGrainDepotStandard);
        param.setId(gradedGrainDepotStandard.getId());
        return gradedGrainDepotStandard.getId();
    }

    
    @Override
    public Boolean submitData(GradedGrainDepotStandardSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();

        Assert.isTrue(StringUtils.isNotEmpty(param.getTemplateId()), "模板id不能为空");
        List<GradedGrainDepotStandardVo> gradedGrainDepotStandardList = listByTemplateId(param.getTemplateId());
        Assert.isTrue(CollectionUtil.isNotEmpty(gradedGrainDepotStandardList), "该模板下没有添加指标");
        BigDecimal sum = gradedGrainDepotStandardList.stream().filter(e->StringUtils.isNotEmpty(e.getScoringMethod())).
                map(GradedGrainDepotStandard::getProjectScore).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        int status = sum.compareTo(new BigDecimal(100));
        Assert.isTrue(status>=0, "提交状态下的模板，计分指标分数之和必须为100分或以上！");

        return super.lambdaUpdate()
                .in(GradedGrainDepotStandard::getTemplateId, param.getTemplateId())
                .set(GradedGrainDepotStandard::getCzbz, CzBzEnum.U.getCzBz())
                .set(GradedGrainDepotStandard::getBczt, GradeBcztEnum.TJ.getState())
                .set(GradedGrainDepotStandard::getUpdateBy, loginUser.getName())
                .set(GradedGrainDepotStandard::getUpdateTime, LocalDateTime.now())
                .update();
    }

    
    @Override
    public String updateData(GradedGrainDepotStandardSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedGrainDepotStandard gradedGrainDepotStandard = BeanUtils.copyByClass(param, GradedGrainDepotStandard.class);
        gradedGrainDepotStandard.setCzbz(CzBzEnum.U.getCzBz());
        gradedGrainDepotStandard.setBczt(GradeBcztEnum.ZC.getState());
        gradedGrainDepotStandard.setUpdateBy(loginUser.getName());
        gradedGrainDepotStandard.setUpdateTime(LocalDateTime.now());

        if(!StringUtils.equals(param.getParentId(),"0")){
            Assert.isTrue(param.getProjectScore().compareTo(BigDecimal.ZERO)>0, "指标分值必须大于零");
        }

        if(!StringUtil.isNullOrEmpty(param.getScoringMethod())){
            GradedGrainDepotStandard grainDepotStandard = detail(param.getParentId());
            BigDecimal projectScore = grainDepotStandard.getProjectScore();
            List<GradedGrainDepotStandard> gradedGrainDepotStandardList = listByParentId(param.getParentId())
                    .stream().filter(e->!StringUtils.equals(e.getId(),param.getId())).collect(Collectors.toList());
            BigDecimal sum = gradedGrainDepotStandardList.stream().filter(e->StringUtils.isNotEmpty(e.getScoringMethod())).
                    map(GradedGrainDepotStandard::getProjectScore).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            int status = sum.add(param.getProjectScore()).compareTo(projectScore);
            Assert.isTrue(status<=0, "三级指标的分数之和不能大于其父集指标");
        }

        super.updateById(gradedGrainDepotStandard);
        param.setId(gradedGrainDepotStandard.getId());
        return gradedGrainDepotStandard.getId();
    }


    private String getRootParentIsJfzb(GradedGrainDepotStandardSaveParam param) {
        String jfzbCode = null;
        if(StringUtils.equals(param.getParentId(),"0")){
            if(param.getProjectName().contains("加分")){
                jfzbCode = GradeJfzbEnum.YES_CODE.getState();
            }else{
                jfzbCode =  GradeJfzbEnum.NO_CODE.getState();
            }
        }else{
            List<GradedGrainDepotStandardVo> all = listByTemplateId(param.getTemplateId());
            if(CollectionUtil.isNotEmpty(all)){
                GradedGrainDepotStandardVo parent = all.stream().filter(e->
                        StringUtils.equals(param.getParentId(),e.getId())).findFirst().orElse(null);
                if(ObjectUtils.isNotEmpty(parent)){
                    if(StringUtils.equals(parent.getParentId(),"0")){
                        if(parent.getProjectName().contains("加分")){
                            jfzbCode =  GradeJfzbEnum.YES_CODE.getState();
                        }else{
                            jfzbCode =  GradeJfzbEnum.NO_CODE.getState();
                        }
                    }else{
                        GradedGrainDepotStandardVo oldParent = all.stream().filter(e->
                                StringUtils.equals(parent.getParentId(),e.getId())).findFirst().orElse(null);
                        if(ObjectUtils.isNotEmpty(oldParent)){
                            if(StringUtils.equals(oldParent.getParentId(),"0")){
                                if(oldParent.getProjectName().contains("加分")){
                                    jfzbCode =  GradeJfzbEnum.YES_CODE.getState();
                                }else{
                                    jfzbCode =  GradeJfzbEnum.NO_CODE.getState();
                                }
                            }
                        }
                    }
                }
            }
        }
        return jfzbCode;
    }

    
    @Override
    public Boolean saveOrUpdateBatchData(List<GradedGrainDepotStandardSaveParam> params,String templateId) {
        Boolean status = false;
        LoginUser loginUser = UserSecurity.loginUser();
        List<GradedGrainDepotStandardVo> exists = listByTemplateId(templateId);
        List<GradedGrainDepotStandard> gradedGrainDepotStandards = BeanUtils.copyToList(params, GradedGrainDepotStandard.class);
        for (int i = 0; i < gradedGrainDepotStandards.size(); i++) {
            GradedGrainDepotStandard gradedGrainDepotStandard = gradedGrainDepotStandards.get(i);
            List<GradedGrainDepotStandard> filters = exists.stream().filter(e->StringUtils.equals(gradedGrainDepotStandard.getId(),e.getId())).collect(Collectors.toList());
            if(CollectionUtil.isNotEmpty(filters)){
                gradedGrainDepotStandard.setTemplateId(templateId);
                gradedGrainDepotStandard.setCzbz(CzBzEnum.U.getCzBz());
                gradedGrainDepotStandard.setUpdateBy(loginUser.getName());
                gradedGrainDepotStandard.setUpdateTime(LocalDateTime.now());
                status = super.updateById(gradedGrainDepotStandard);
            }else{
                gradedGrainDepotStandard.setTemplateId(templateId);
                gradedGrainDepotStandard.setCreateBy(loginUser.getName());
                gradedGrainDepotStandard.setUpdateBy(loginUser.getName());
                gradedGrainDepotStandard.setUpdateTime(LocalDateTime.now());
                gradedGrainDepotStandard.setCreateTime(LocalDateTime.now());
                gradedGrainDepotStandard.setCzbz(CzBzEnum.I.getCzBz());
                status = super.save(gradedGrainDepotStandard);
            }
        }
        return status;
    }

    
    @Override
    public Boolean removeData(String id) {
        LoginUser loginUser = UserSecurity.loginUser();
        List<GradedGrainDepotStandard> gradedGrainDepotStandards = SpringUtil.getBean(this.getClass()).listByParentId(id);
        Assert.state(CollectionUtils.isEmpty(gradedGrainDepotStandards), "存在下级，不能删除");
        return super.lambdaUpdate()
                .eq(GradedGrainDepotStandard::getId, id)
                .set(GradedGrainDepotStandard::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedGrainDepotStandard::getUpdateBy, loginUser.getName())
                .set(GradedGrainDepotStandard::getUpdateTime, LocalDateTime.now())
                .update();
    }

    
    @Override
    public Boolean removeDataByTemplateId(List<String> templateIds) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(CollectionUtils.isNotEmpty(templateIds), GradedGrainDepotStandard::getTemplateId, templateIds)
                .set(GradedGrainDepotStandard::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedGrainDepotStandard::getUpdateBy, loginUser.getName())
                .set(GradedGrainDepotStandard::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @Override
    public List<GradedGrainDepotStandard> listByParentId(String parentId) {
        return super.lambdaQuery()
                .eq(GradedGrainDepotStandard::getParentId, parentId)
                .ne(GradedGrainDepotStandard::getCzbz, CzBzEnum.D.getCzBz())
                .list();
    }
}