/**
 * @filename:GradedExpertManageServiceImpl 2023年05月25日
 * @project multiple  V1.0
 * Copyright(c) 2018 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.sydata.framework.databind.handle.DataBindHandleBootstrap;
import com.sydata.framework.excel.ZtExcelBuildUtil;
import com.sydata.grade.domain.GradedEnterpriseProcess;
import com.sydata.grade.domain.GradedMergeExpertManage;
import com.sydata.grade.dto.GradedEnterpriseStockDto;
import com.sydata.grade.dto.GradedExpertDto;
import com.sydata.grade.param.GradedExpertManageExportParam;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
import com.sydata.grade.domain.GradedExpertManage;
import com.sydata.grade.mapper.GradedExpertManageMapper;
import com.sydata.grade.service.IGradedExpertManageService;
import com.sydata.grade.param.GradedExpertManagePageParam;
import com.sydata.grade.param.GradedExpertManageSaveParam;
import com.sydata.grade.vo.GradedExpertManageVo;
import com.sydata.grade.annotation.DataBindGradedExpertManage;
import com.sydata.common.api.enums.CzBzEnum;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:TODO(等级粮库评定管理-专家管理服务实现)
 * @version: V1.0
 * @author: lzq
 */
@Service("gradedExpertManageService")
public class GradedExpertManageServiceImpl extends ServiceImpl<GradedExpertManageMapper, GradedExpertManage> implements IGradedExpertManageService {

    final static String CACHE_NAME = "composite:gradedExpertManage";

    @Resource
    private GradedExpertManageMapper gradedExpertManageMapper;

    @DataBindFieldConvert
    @Override
    public Page<GradedExpertManageVo> pages(GradedExpertManagePageParam pageParam) {
        Page<GradedExpertManage> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedExpertManage::getId, pageParam.getId())
                .like(ObjectUtils.isNotEmpty(pageParam.getExpertName()), GradedExpertManage::getExpertName, pageParam.getExpertName())
                .like(ObjectUtils.isNotEmpty(pageParam.getExpertTitle()), GradedExpertManage::getExpertTitle, pageParam.getExpertTitle())
                .eq(ObjectUtils.isNotEmpty(pageParam.getPdnx()), GradedExpertManage::getPdnx, pageParam.getPdnx())
                .ne(GradedExpertManage::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedExpertManage::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GradedExpertManageVo.class);
    }

    @DataBindFieldConvert
    @Override
    public Page<GradedExpertManageVo> pagesMerge(GradedExpertManagePageParam pageParam) {
        List<GradedExpertManage> allList = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedExpertManage::getId, pageParam.getId())
                .in(ObjectUtils.isNotEmpty(pageParam.getPdnxs()), GradedExpertManage::getPdnx, pageParam.getPdnxs())
                .like(ObjectUtils.isNotEmpty(pageParam.getExpertName()), GradedExpertManage::getExpertName, pageParam.getExpertName())
                .like(ObjectUtils.isNotEmpty(pageParam.getExpertTitle()), GradedExpertManage::getExpertTitle, pageParam.getExpertTitle())
                .eq(ObjectUtils.isNotEmpty(pageParam.getPdnx()), GradedExpertManage::getPdnx, pageParam.getPdnx())
                .ne(GradedExpertManage::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedExpertManage::getUpdateTime).list();
        List<GradedExpertManage> newLists = new ArrayList<>();
        allList.parallelStream().forEach(a->{
            List<GradedMergeExpertManage> gradedMergeExpertManages = Lists.newArrayList();
            GradedMergeExpertManage expertManage = new GradedMergeExpertManage();
            expertManage.setId(a.getId());
            expertManage.setPdnx(a.getPdnx());
            expertManage.setExpertName(a.getExpertName());
            expertManage.setExpertTitle(a.getExpertTitle());
            gradedMergeExpertManages.add(expertManage);
            List<String> ids = Lists.newArrayList();
            ids.add(a.getId());
            a.setPdnxs(a.getPdnx());
            a.setExpertTitles(a.getExpertTitle());
            a.setGradedMergeExpertManages(gradedMergeExpertManages);
            a.setIds(ids);
        });
        allList.parallelStream()
                .collect(Collectors.groupingBy(item -> (item.getExpertName()), Collectors.toList()))
                .forEach((id, transfer) -> {
                    transfer.stream().reduce((a, b) ->{
                        if(Integer.valueOf(a.getPdnx())>Integer.valueOf(b.getPdnx())){
                            a.getGradedMergeExpertManages().addAll(b.getGradedMergeExpertManages());
                            a.getIds().addAll(b.getIds());
                            return new GradedExpertManage(
                                    a.getId(), a.getCzbz(),
                                    a.getExpertName(), a.getExpertSex(), a.getExpertSort(), a.getExpertTitle(),
                                    b.getExpertTitle() +","+ a.getExpertTitle(),
                                    a.getGzdw(), a.getKhzh(), a.getPdnx(),
                                    b.getPdnxs() +","+ a.getPdnxs(),
                                    a.getPhoneNum(), a.getSfzh(), a.getYhkh(),
                                    a.getCreateBy(), a.getCreateTime(), a.getUpdateBy(), a.getUpdateTime(),
                                    a.getGradedMergeExpertManages(),
                                    a.getIds()
                            );
                        }else{
                            b.getGradedMergeExpertManages().addAll(a.getGradedMergeExpertManages());
                            b.getIds().addAll(a.getIds());
                            return new GradedExpertManage(
                                    b.getId(), b.getCzbz(),
                                    b.getExpertName(), b.getExpertSex(), b.getExpertSort(), b.getExpertTitle(),
                                    a.getExpertTitle() +","+ b.getExpertTitle(),
                                    b.getGzdw(), b.getKhzh(), a.getPdnx(),
                                    a.getPdnxs() +","+ b.getPdnxs(),
                                    b.getPhoneNum(), b.getSfzh(), b.getYhkh(),
                                    b.getCreateBy(), b.getCreateTime(), b.getUpdateBy(), b.getUpdateTime(),
                                    b.getGradedMergeExpertManages(),
                                    a.getIds()
                            );
                        }
                    }).ifPresent(newLists::add);
                });
        Page<GradedExpertManage> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        page.setRecords(newLists);
        page.setTotal(newLists.size());
        return BeanUtils.copyToPage(page, GradedExpertManageVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GradedExpertManageVo detail(String id) {
        IGradedExpertManageService gradedExpertManageService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(gradedExpertManageService.getById(id), GradedExpertManageVo.class);
    }

    @DataBindService(strategy = DataBindGradedExpertManage.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gradedExpertManageMapper);
    }

    
    @Override
    public String saveData(GradedExpertManageSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedExpertManage gradedExpertManage = BeanUtils.copyByClass(param, GradedExpertManage.class);
        gradedExpertManage.setId(IdUtil.simpleUUID());
        gradedExpertManage.setCreateBy(loginUser.getName());
        gradedExpertManage.setUpdateBy(loginUser.getName());
        gradedExpertManage.setUpdateTime(LocalDateTime.now());
        gradedExpertManage.setCreateTime(LocalDateTime.now());
        gradedExpertManage.setCzbz(CzBzEnum.I.getCzBz());

        GradedExpertManage unx = getByUnx(param.getExpertName(), param.getPdnx());
        Assert.isTrue(ObjectUtils.isEmpty(unx), "已存在姓名和评定年限相同的专家数据");

        super.save(gradedExpertManage);
        param.setId(gradedExpertManage.getId());
        return gradedExpertManage.getId();
    }

    
    @Override
    public String updateData(GradedExpertManageSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedExpertManage gradedExpertManage = BeanUtils.copyByClass(param, GradedExpertManage.class);
        gradedExpertManage.setCzbz(CzBzEnum.U.getCzBz());
        gradedExpertManage.setUpdateBy(loginUser.getName());
        gradedExpertManage.setUpdateTime(LocalDateTime.now());
        GradedExpertManage unx = getByUnx(param.getExpertName(), param.getPdnx());
        if (ObjectUtils.isNotEmpty(unx)) {
            Assert.isTrue(StringUtils.equals(param.getId(), unx.getId()), "已存在姓名和评定年限相同的专家数据");
        }
        super.updateById(gradedExpertManage);
        param.setId(gradedExpertManage.getId());
        return gradedExpertManage.getId();
    }

    
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(GradedExpertManage::getId, ids)
                .set(GradedExpertManage::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedExpertManage::getUpdateBy, loginUser.getName())
                .set(GradedExpertManage::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @DataBindFieldConvert
    @Override
    public void exportData(GradedExpertManagePageParam pageParam) {
        List<GradedExpertManage> list = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedExpertManage::getId, pageParam.getId())
                .like(ObjectUtils.isNotEmpty(pageParam.getExpertName()), GradedExpertManage::getExpertName, pageParam.getExpertName())
                .like(ObjectUtils.isNotEmpty(pageParam.getExpertTitle()), GradedExpertManage::getExpertTitle, pageParam.getExpertTitle())
                .eq(ObjectUtils.isNotEmpty(pageParam.getPdnx()), GradedExpertManage::getPdnx, pageParam.getPdnx())
                .in(CollectionUtils.isNotEmpty(pageParam.getIds()), GradedExpertManage::getId, pageParam.getIds())
                .ne(GradedExpertManage::getCzbz, CzBzEnum.D.getCzBz())
                .orderByAsc(GradedExpertManage::getExpertName)
                .orderByDesc(GradedExpertManage::getPdnx).list();
        List<GradedExpertManageVo> voList = BeanUtils.copyToList(list, GradedExpertManageVo.class);
        DataBindHandleBootstrap.dataHandConvert(voList, 2);
        List<GradedExpertDto> expertDtos = BeanUtils.copyToList(voList, GradedExpertDto.class);
        ZtExcelBuildUtil.buildExcelExport(GradedExpertDto.class, "专家信息").setData(expertDtos).doWebExport();
    }

    @SneakyThrows
    @Override
    public void importData(MultipartFile file) {
        // 只支持xlsx或者xls的文件格式
        String fileType = FileUtil.extName(file.getOriginalFilename());
        boolean state = isState(fileType);
        Assert.state(state, "只支持xlsx或者xls的文件格式,系统检测该文件实际类型是" + fileType);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        List<GradedExpertDto> dtos = ExcelImportUtil.importExcel(file.getInputStream(), GradedExpertDto.class, params);
        Assert.state(CollectionUtils.isNotEmpty(dtos), "导入数据为空，请重新导入！");
        List<GradedExpertManageSaveParam> save = BeanUtils.copyToList(dtos, GradedExpertManageSaveParam.class);
        Assert.state(CollectionUtils.isNotEmpty(save), "导入数据为空，请重新导入！");
        for (int i = 0; i < save.size(); i++) {
            updateOrSaveByUnx(save.get(i));
        }
    }

    private boolean isState(String fileType) {
        Set<String> fileTypes = new HashSet();
        fileTypes.add("xlsx");
        fileTypes.add("xls");
        return fileTypes.contains(fileType);
    }


    @Override
    public GradedExpertManage getByUnx(String expertName, String pdnx) {
        return super.lambdaQuery()
                .select(GradedExpertManage::getId)
                .eq(GradedExpertManage::getExpertName, expertName)
                .eq(GradedExpertManage::getPdnx, pdnx)
                .ne(GradedExpertManage::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedExpertManage::getUpdateTime)
                .oneOpt()
                .map(GradedExpertManage::getId)
                .map(this::getById)
                .orElse(null);
    }

    @Override
    public Boolean updateOrSaveByUnx(GradedExpertManageSaveParam saveParam) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedExpertManage unx = getByUnx(saveParam.getExpertName(), saveParam.getPdnx());
        if (ObjectUtils.isNotEmpty(unx)) {
            return super.lambdaUpdate()
                    .eq(ObjectUtils.isNotEmpty(saveParam.getExpertName()), GradedExpertManage::getExpertName, saveParam.getExpertName())
                    .eq(ObjectUtils.isNotEmpty(saveParam.getPdnx()), GradedExpertManage::getPdnx, saveParam.getPdnx())
                    .set(GradedExpertManage::getExpertName, saveParam.getExpertName())
                    .set(GradedExpertManage::getExpertSex, saveParam.getExpertSex())
                    .set(GradedExpertManage::getPhoneNum, saveParam.getPhoneNum())
                    .set(GradedExpertManage::getExpertTitle, saveParam.getExpertTitle())
                    .set(GradedExpertManage::getSfzh, saveParam.getSfzh())
                    .set(GradedExpertManage::getYhkh, saveParam.getYhkh())
                    .set(GradedExpertManage::getKhzh, saveParam.getKhzh())
                    .set(GradedExpertManage::getGzdw, saveParam.getGzdw())
                    .set(GradedExpertManage::getPdnx, saveParam.getPdnx())
                    .set(GradedExpertManage::getCzbz, CzBzEnum.U.getCzBz())
                    .set(GradedExpertManage::getUpdateBy, loginUser.getName())
                    .set(GradedExpertManage::getUpdateTime, LocalDateTime.now())
                    .update();
        } else {
            GradedExpertManage gradedExpertManage = BeanUtils.copyByClass(saveParam, GradedExpertManage.class);
            gradedExpertManage.setId(IdUtil.simpleUUID());
            gradedExpertManage.setCreateBy(loginUser.getName());
            gradedExpertManage.setUpdateBy(loginUser.getName());
            gradedExpertManage.setUpdateTime(LocalDateTime.now());
            gradedExpertManage.setCreateTime(LocalDateTime.now());
            gradedExpertManage.setCzbz(CzBzEnum.I.getCzBz());
            return super.save(gradedExpertManage);
        }
    }

}