/**
 * @filename:GradedGrainDepotTemplateServiceImpl 2023年05月17日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2018 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.util.PoiMergeCellUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.framework.excel.ExcelExportStylerZtImpl;
import com.sydata.framework.excel.ZtExportUtil;
import com.sydata.grade.domain.GradedGrainDepotStandard;
import com.sydata.grade.dto.XmDto;
import com.sydata.grade.enums.GradeBcztEnum;
import com.sydata.grade.enums.GradeStateEnum;
import com.sydata.grade.http.GradedEnterpriseReviewFactoryService;
import com.sydata.grade.param.GradedGrainDepotStandardSaveParam;
import com.sydata.grade.service.IGradedGrainDepotStandardService;
import com.sydata.grade.vo.GradedGrainDepotStandardExcelVo;
import com.sydata.grade.vo.GradedGrainDepotStandardTreeVo;
import com.sydata.grade.vo.GradedGrainDepotStandardVo;
import io.netty.util.internal.StringUtil;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
import com.sydata.grade.domain.GradedGrainDepotTemplate;
import com.sydata.grade.mapper.GradedGrainDepotTemplateMapper;
import com.sydata.grade.service.IGradedGrainDepotTemplateService;
import com.sydata.grade.param.GradedGrainDepotTemplatePageParam;
import com.sydata.grade.param.GradedGrainDepotTemplateSaveParam;
import com.sydata.grade.vo.GradedGrainDepotTemplateVo;
import com.sydata.grade.annotation.DataBindGradedGrainDepotTemplate;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.DASH;
import static com.baomidou.mybatisplus.core.toolkit.StringPool.SLASH;
import static java.util.function.Function.identity;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * @Description:TODO(等级粮库评定管理-等级粮库评定模板服务实现)
 * @version: V1.0
 * @author: lzq
 */
@Service("gradedGrainDepotTemplateService")
public class GradedGrainDepotTemplateServiceImpl extends ServiceImpl<GradedGrainDepotTemplateMapper, GradedGrainDepotTemplate> implements IGradedGrainDepotTemplateService {

    final static String CACHE_NAME = "composite:gradedGrainDepotTemplate";

    @Resource
    private GradedGrainDepotTemplateMapper gradedGrainDepotTemplateMapper;

    @Resource
    private IGradedGrainDepotStandardService gradedGrainDepotStandardService;

    @Resource
    private IFileStorageService fileStorageService;

    @Resource
    private GradedEnterpriseReviewFactoryService gradedEnterpriseReviewFactoryService;

    @DataBindFieldConvert
    @Override
    public Page<GradedGrainDepotTemplateVo> pages(GradedGrainDepotTemplatePageParam pageParam) {
        Page<GradedGrainDepotTemplate> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedGrainDepotTemplate::getId, pageParam.getId())
                .like(ObjectUtils.isNotEmpty(pageParam.getTemplateName()), GradedGrainDepotTemplate::getTemplateName, pageParam.getTemplateName())
                .eq(ObjectUtils.isNotEmpty(pageParam.getTemplateNumber()), GradedGrainDepotTemplate::getTemplateNumber, pageParam.getTemplateNumber())
                .eq(ObjectUtils.isNotEmpty(pageParam.getTemplateState()), GradedGrainDepotTemplate::getTemplateState, pageParam.getTemplateState())
                .ge(ObjectUtils.isNotEmpty(pageParam.getBeginQysj()), GradedGrainDepotTemplate::getQysj, pageParam.getBeginQysj())
                .le(ObjectUtils.isNotEmpty(pageParam.getEndQysj()), GradedGrainDepotTemplate::getQysj, pageParam.getEndQysj())
                .ge(ObjectUtils.isNotEmpty(pageParam.getBeginUpdateTime()), GradedGrainDepotTemplate::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(ObjectUtils.isNotEmpty(pageParam.getEndUpdateTime()), GradedGrainDepotTemplate::getUpdateTime, pageParam.getEndUpdateTime())
                .ne(GradedGrainDepotTemplate::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedGrainDepotTemplate::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        List<GradedGrainDepotTemplate> pageRecords = page.getRecords();
        pageRecords.stream().map(e -> {
            return tranferState(e);
        }).collect(Collectors.toList());
        return BeanUtils.copyToPage(page, GradedGrainDepotTemplateVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GradedGrainDepotTemplateVo detail(String id) {
        IGradedGrainDepotTemplateService gradedGrainDepotTemplateService = SpringUtil.getBean(this.getClass());
        GradedGrainDepotTemplateVo vo = BeanUtils.copyByClass(tranferState(gradedGrainDepotTemplateService.getById(id)), GradedGrainDepotTemplateVo.class);
        List<GradedGrainDepotStandardTreeVo> treeVos = gradedGrainDepotStandardService.treeByTemplateId(id);
        vo.setGradedGrainDepotStandardTreeVos(treeVos);
        return vo;
    }

    @DataBindService(strategy = DataBindGradedGrainDepotTemplate.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gradedGrainDepotTemplateMapper);
    }

    
    @Override
    public String saveDataByFile(GradedGrainDepotTemplateSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        // 获取上传的文件信息
        InputStream inputStream = fileStorageService.fileStreamById(param.getFileId());
        GradedGrainDepotTemplate gradedGrainDepotTemplate = BeanUtils.copyByClass(param, GradedGrainDepotTemplate.class);
        gradedGrainDepotTemplate.setId(IdUtil.simpleUUID());
        gradedGrainDepotTemplate.setTemplateNumber(IdUtil.simpleUUID());
        gradedGrainDepotTemplate.setTemplateState(GradeStateEnum.SAVE.getState());
        gradedGrainDepotTemplate.setCreateBy(loginUser.getName());
        gradedGrainDepotTemplate.setUpdateBy(loginUser.getName());
        gradedGrainDepotTemplate.setUpdateTime(LocalDateTime.now());
        gradedGrainDepotTemplate.setCreateTime(LocalDateTime.now());
        gradedGrainDepotTemplate.setCzbz(CzBzEnum.I.getCzBz());

        GradedGrainDepotTemplate unx = getByUnxName(param.getNf());

        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在该年份的考核模板");

        gradedGrainDepotStandardService.excelImportParsing(inputStream, gradedGrainDepotTemplate.getId(), param.getFileName());

        super.save(gradedGrainDepotTemplate);
        param.setId(gradedGrainDepotTemplate.getId());
        return gradedGrainDepotTemplate.getId();
    }

    @Override
    public String saveData(GradedGrainDepotTemplateSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        // 获取上传的文件信息
        GradedGrainDepotTemplate gradedGrainDepotTemplate = BeanUtils.copyByClass(param, GradedGrainDepotTemplate.class);
        gradedGrainDepotTemplate.setId(IdUtil.simpleUUID());
        gradedGrainDepotTemplate.setTemplateNumber(IdUtil.simpleUUID());
        gradedGrainDepotTemplate.setTemplateState(GradeStateEnum.SAVE.getState());
        gradedGrainDepotTemplate.setCreateBy(loginUser.getName());
        gradedGrainDepotTemplate.setUpdateBy(loginUser.getName());
        gradedGrainDepotTemplate.setUpdateTime(LocalDateTime.now());
        gradedGrainDepotTemplate.setCreateTime(LocalDateTime.now());
        gradedGrainDepotTemplate.setCzbz(CzBzEnum.I.getCzBz());

        GradedGrainDepotTemplate unx = getByUnxName(param.getNf());

        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在该年份的考核模板");

        super.save(gradedGrainDepotTemplate);
        param.setId(gradedGrainDepotTemplate.getId());
        return gradedGrainDepotTemplate.getId();
    }
    
    @Override
    public String updateDataByFile(GradedGrainDepotTemplateSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        // 获取上传的文件信息
        InputStream inputStream = fileStorageService.fileStreamById(param.getFileId());
        GradedGrainDepotTemplate gradedGrainDepotTemplate = BeanUtils.copyByClass(param, GradedGrainDepotTemplate.class);
        gradedGrainDepotTemplate.setCzbz(CzBzEnum.U.getCzBz());
        gradedGrainDepotTemplate.setUpdateBy(loginUser.getName());
        gradedGrainDepotTemplate.setUpdateTime(LocalDateTime.now());

        Assert.notNull(gradedGrainDepotTemplate, "模板ID不存在");

        GradedGrainDepotTemplate unx = getByUnxName(param.getNf());

        Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在该年份的考核模板");

        gradedGrainDepotStandardService.excelImportParsing(inputStream, gradedGrainDepotTemplate.getId(), param.getFileName());

        super.updateById(gradedGrainDepotTemplate);
        param.setId(gradedGrainDepotTemplate.getId());
        return gradedGrainDepotTemplate.getId();
    }

    @Override
    public String updateData(GradedGrainDepotTemplateSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        // 获取上传的文件信息
        GradedGrainDepotTemplate gradedGrainDepotTemplate = BeanUtils.copyByClass(param, GradedGrainDepotTemplate.class);
        gradedGrainDepotTemplate.setCzbz(CzBzEnum.U.getCzBz());
        gradedGrainDepotTemplate.setUpdateBy(loginUser.getName());
        gradedGrainDepotTemplate.setUpdateTime(LocalDateTime.now());

        Assert.notNull(gradedGrainDepotTemplate, "模板ID不存在");

        GradedGrainDepotTemplate unx = getByUnxName(param.getNf());
        if(ObjectUtils.isNotEmpty(unx)){
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在该年份的考核模板");
        }

        super.updateById(gradedGrainDepotTemplate);
        param.setId(gradedGrainDepotTemplate.getId());
        return gradedGrainDepotTemplate.getId();
    }

    @Override
    public Boolean push(String id) {
        Boolean returnBoolean = true;
        LoginUser loginUser = UserSecurity.loginUser();
        GradedGrainDepotTemplate gradedGrainDepotTemplate = super.getById(id);
        Assert.state(Objects.nonNull(gradedGrainDepotTemplate), "未查询到相应的模板数据，请重新选择！");
        Assert.state(GradeStateEnum.SAVE.getState().equals(gradedGrainDepotTemplate.getTemplateState()), "不是拟稿状态不能发布！");
        List<GradedGrainDepotStandardVo> gradedGrainDepotStandardVos = gradedGrainDepotStandardService.listByTemplateId(id);
        Assert.state(CollectionUtil.isNotEmpty(gradedGrainDepotStandardVos), "未查询到相应的指标数据，请编辑指标数据！");
        List<GradedGrainDepotStandardVo> filters = gradedGrainDepotStandardVos.stream().
                filter(e->StringUtils.equals(e.getBczt(), GradeBcztEnum.TJ.getState())).collect(Collectors.toList());
        Assert.state(CollectionUtil.isNotEmpty(filters), "指标数据未提交，请编辑指标数据！");
        List<GradedGrainDepotStandardVo> zbFilters = filters.stream().filter(e->!StringUtil.isNullOrEmpty(e.getScoringMethod())).collect(Collectors.toList());
        BigDecimal projectScoreSum = zbFilters.stream().map(e->e.getProjectScore()).reduce(BigDecimal.ZERO, BigDecimal::add);
        Assert.state(projectScoreSum.compareTo(new BigDecimal(100))>=0, "模版指标分值小于100，不能发布，请编辑指标项！");

        returnBoolean = super.lambdaUpdate()
                .eq(GradedGrainDepotTemplate::getId, id)
                .set(GradedGrainDepotTemplate::getTemplateState, GradeStateEnum.PUSH.getState())
                .set(GradedGrainDepotTemplate::getCzbz, CzBzEnum.U.getCzBz())
                .set(GradedGrainDepotTemplate::getQysj, LocalDateTime.now())
                .set(GradedGrainDepotTemplate::getUpdateTime, LocalDateTime.now())
                .set(GradedGrainDepotTemplate::getUpdateBy, loginUser.getName())
                .update();

        // 信息下传至库软件
        distributeTemplateInfo(gradedGrainDepotTemplate, gradedGrainDepotStandardVos);

        return returnBoolean;
    }

    private void distributeTemplateInfo(GradedGrainDepotTemplate gradedGrainDepotTemplate,
                                        List<GradedGrainDepotStandardVo> gradedGrainDepotStandardVos) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedGrainDepotTemplateSaveParam param = new GradedGrainDepotTemplateSaveParam();
        List<GradedGrainDepotStandardSaveParam> gradedGrainDepotStandardSaveParams = Lists.newArrayList();
        param = BeanUtils.copyByClass(gradedGrainDepotTemplate,GradedGrainDepotTemplateSaveParam.class);
        gradedGrainDepotStandardSaveParams = BeanUtils.copyToList(gradedGrainDepotStandardVos,GradedGrainDepotStandardSaveParam.class);
        param.setGradedGrainDepotStandardSaveParamList(gradedGrainDepotStandardSaveParams);
        param.setTemplateState(GradeStateEnum.PUSH.getState());
        param.setQysj(LocalDateTime.now());
        param.setUpdateBy(loginUser.getName());
        param.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseReviewFactoryService.saveOrUpdateTemplate(param);
    }

    
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();

        gradedGrainDepotStandardService.removeDataByTemplateId(ids);

        // 信息下传至库软件
        gradedEnterpriseReviewFactoryService.removeTemplate(ids);

        return super.lambdaUpdate()
                .in(CollectionUtils.isNotEmpty(ids), GradedGrainDepotTemplate::getId, ids)
                .set(GradedGrainDepotTemplate::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedGrainDepotTemplate::getUpdateBy, loginUser.getName())
                .set(GradedGrainDepotTemplate::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @Override
    public GradedGrainDepotTemplate getByUnxName(String nf) {
        return super.lambdaQuery()
                .select(GradedGrainDepotTemplate::getId)
                .ne(GradedGrainDepotTemplate::getCzbz, CzBzEnum.D.getCzBz())
                .eq(GradedGrainDepotTemplate::getNf, nf)
                .oneOpt()
                .map(GradedGrainDepotTemplate::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public void export(String id) {
        // 获取考核模板指标列表并转换为单位考核导出明细VO模板
        List<GradedGrainDepotStandardExcelVo> excelVos = gradedGrainDepotStandardService.getGradedGrainDepotStandardExcelVos(id);
        excelVos.stream().map(e -> {
            e.setJfzb(e.getJfzbName());
            return e;
        }).collect(Collectors.toList());
        GradedGrainDepotTemplate gradedGrainDepotTemplate = this.getById(id);
        ExportParams params = new ExportParams();
        params.setTitle(gradedGrainDepotTemplate.getTemplateName() + DASH + gradedGrainDepotTemplate.getNf());
        params.setSheetName(gradedGrainDepotTemplate.getTemplateName() + DASH + gradedGrainDepotTemplate.getNf());
        params.setStyle(ExcelExportStylerZtImpl.class);
        Workbook workbook = ExcelExportUtil.exportExcel(params, GradedGrainDepotStandardExcelVo.class, excelVos);

        Map<Integer, int[]> mergeMap = MapUtil.newHashMap();
        int rows = 4;
        for (int i = 0; i < rows; i++) {
            mergeMap.put(i, i == 0 ? null : new int[]{i - 1});
        }
        PoiMergeCellUtil.mergeCells(workbook.getSheetAt(0), mergeMap, 2);
        ZtExportUtil.doWebExport(workbook, gradedGrainDepotTemplate.getTemplateName() + DASH + gradedGrainDepotTemplate.getNf(), true);
    }

    @Override
    public GradedGrainDepotTemplate getNowYearGradedGrainDepotTemplate(){
        int nowYear = DateUtil.year(new Date());
        GradedGrainDepotTemplate grainDepotTemplate = super.lambdaQuery()
                .eq(GradedGrainDepotTemplate::getNf, String.valueOf(nowYear))
                .ne(GradedGrainDepotTemplate::getCzbz, CzBzEnum.D.getCzBz())
                .eq(GradedGrainDepotTemplate::getTemplateState, GradeStateEnum.PUSH.getState())
                .oneOpt().orElse(null);
        if(ObjectUtils.isNotEmpty(grainDepotTemplate)){
            List<XmDto> xmDtoList = gradedGrainDepotStandardService.getGradedGrainDepotStandardDto(grainDepotTemplate.getId());
            grainDepotTemplate.setXmDtoList(xmDtoList);
            List<GradedGrainDepotStandardExcelVo> gradedGrainDepotStandardExcelVoList = gradedGrainDepotStandardService.getGradedGrainDepotStandardExcelVos(grainDepotTemplate.getId());
            grainDepotTemplate.setGradedGrainDepotStandardExcelVoList(gradedGrainDepotStandardExcelVoList);
        }
        return grainDepotTemplate;
    }

    @Override
    public GradedGrainDepotTemplate getGradedGrainDepotTemplateById(String templateId) {
        GradedGrainDepotTemplate grainDepotTemplate = super.lambdaQuery()
                .eq(GradedGrainDepotTemplate::getId, templateId)
                .oneOpt().orElse(null);
        if (ObjectUtils.isNotEmpty(grainDepotTemplate)) {
            List<XmDto> xmDtoList = gradedGrainDepotStandardService.getGradedGrainDepotStandardDtoWithHistory(grainDepotTemplate.getId());
            grainDepotTemplate.setXmDtoList(xmDtoList);
            List<GradedGrainDepotStandardExcelVo> gradedGrainDepotStandardExcelVoList =
                    gradedGrainDepotStandardService.getGradedGrainDepotStandardExcelVosWithHistory(grainDepotTemplate.getId());
            grainDepotTemplate.setGradedGrainDepotStandardExcelVoList(gradedGrainDepotStandardExcelVoList);
        }
        return grainDepotTemplate;
    }

    /**
     * @return
     * @Author lzq
     * @Description 拟稿，已发布 已完成
     * 保存时：拟稿
     * 发布后：已发布
     * 发布的2022年，到了2023年1月1日：已完成
     * @Param
     **/
    private GradedGrainDepotTemplate tranferState(GradedGrainDepotTemplate grainDepotTemplate) {
        Assert.state(Objects.nonNull(grainDepotTemplate), "未查询到相应的模板数据，请输入正确ID！");
        String templateState = grainDepotTemplate.getTemplateState();
        String nf = grainDepotTemplate.getNf();
        int nowYear = DateUtil.year(new Date());
        if (StringUtils.equals(templateState, GradeStateEnum.PUSH.getState()) && !StringUtils.isEmpty(nf)) {
            int year = Integer.valueOf(nf);
            if (nowYear - year >= 1) {
                grainDepotTemplate.setTemplateState(GradeStateEnum.COMPLETE.getState());
            }
        }
        return grainDepotTemplate;
    }
}