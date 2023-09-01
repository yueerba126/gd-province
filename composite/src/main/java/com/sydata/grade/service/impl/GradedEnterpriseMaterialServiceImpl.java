/**
 * @filename:GradedEnterpriseMaterialServiceImpl 2023年05月22日
 * @project multiple  V1.0
 * Copyright(c) 2018 lzq Co. Ltd.
 * All right reserved.
 */
package com.sydata.grade.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.common.file.service.IFileStorageService;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.databind.annotation.DataBindService;
import com.sydata.framework.databind.domain.DataBindQuery;
import com.sydata.framework.databind.utils.DataBindQueryCache;
import com.sydata.framework.util.BeanUtils;
import com.sydata.grade.annotation.DataBindGradedEnterpriseMaterial;
import com.sydata.grade.domain.GradedEnterpriseMaterial;
import com.sydata.grade.mapper.GradedEnterpriseMaterialMapper;
import com.sydata.grade.param.GradedEnterpriseMaterialPageParam;
import com.sydata.grade.param.GradedEnterpriseMaterialSaveParam;
import com.sydata.grade.service.IGradedEnterpriseMaterialService;
import com.sydata.grade.vo.GradedEnterpriseMaterialVo;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Description:TODO(等级粮库评定管理-企业申报证明材料服务实现)
 *
 * @version: V1.0
 * @author: lzq
 *
 */
@Service("gradedEnterpriseMaterialService")
public class GradedEnterpriseMaterialServiceImpl extends ServiceImpl<GradedEnterpriseMaterialMapper, GradedEnterpriseMaterial> implements IGradedEnterpriseMaterialService {

    final static String CACHE_NAME = "composite:gradedEnterpriseMaterial";

    @Resource
    private GradedEnterpriseMaterialMapper gradedEnterpriseMaterialMapper;

    @Resource
    private IFileStorageService fileStorageService;

    @DataBindFieldConvert
    @Override
    public String uploadUse(MultipartFile file) {
        LoginUser loginUser = UserSecurity.loginUser();
        return fileStorageService.uploadUse(file, loginUser.getOrganizeId(), loginUser.getStockHouseId());
    }

    @DataBindFieldConvert
    @Override
    public void download(String fileStorageId) {
        GradedEnterpriseMaterial gradedEnterpriseMaterial = super.lambdaQuery()
                .eq(GradedEnterpriseMaterial::getFileId, fileStorageId)
                .ne(GradedEnterpriseMaterial::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseMaterial::getUpdateTime).list().stream().findFirst().orElse(null);
        if(ObjectUtils.isNotEmpty(gradedEnterpriseMaterial)){
            fileStorageService.downloadWithFileName(fileStorageId,gradedEnterpriseMaterial.getFileName());
        }
    }

    @DataBindFieldConvert
    @Override
    public void downloadToZip(String fileStorageIds) {
        String[] fileStorageId = fileStorageIds.split(",");
        List<String> fileStorageIdList = Lists.newArrayList();
        List<String> fileNames = Lists.newArrayList();
        Collections.addAll(fileStorageIdList,fileStorageId);
        if(CollectionUtils.isNotEmpty(fileStorageIdList)){
            for (int i = 0; i < fileStorageIdList.size(); i++) {
                GradedEnterpriseMaterial gradedEnterpriseMaterial = super.lambdaQuery()
                        .eq(GradedEnterpriseMaterial::getFileId, fileStorageIdList.get(i))
                        .ne(GradedEnterpriseMaterial::getCzbz, CzBzEnum.D.getCzBz())
                        .orderByDesc(GradedEnterpriseMaterial::getUpdateTime).list().stream().findFirst().orElse(null);
                if(ObjectUtils.isNotEmpty(gradedEnterpriseMaterial)){
                    fileNames.add(gradedEnterpriseMaterial.getFileName());
                }else {
                    fileStorageIdList.remove(i);
                    i--;
                }
            }
            fileStorageService.downloadToZip(fileStorageIdList,fileNames,"证明材料");
        }
    }

    @Override
    public String getFileBytes(String fileStorageId) {
        byte[] bytes = fileStorageService.fileBytes(fileStorageId);
        String str = new String(bytes);
        return str;
    }

    @Override
    public InputStream getInputStream(String fileStorageId) {
        InputStream inputStream = fileStorageService.fileStreamById(fileStorageId);
        return inputStream;
    }


    @DataBindFieldConvert
    @Override
    public Page<GradedEnterpriseMaterialVo> pages(GradedEnterpriseMaterialPageParam pageParam) {
        Page<GradedEnterpriseMaterial> page = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(pageParam.getId()), GradedEnterpriseMaterial::getId, pageParam.getId())
                .eq(ObjectUtils.isNotEmpty(pageParam.getQyid()), GradedEnterpriseMaterial::getQyid, pageParam.getQyid())
                .like(ObjectUtils.isNotEmpty(pageParam.getFileName()), GradedEnterpriseMaterial::getFileName, pageParam.getFileName())
                .ne(GradedEnterpriseMaterial::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseMaterial::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, GradedEnterpriseMaterialVo.class);
    }

    @DataBindFieldConvert
    @Override
    public List<GradedEnterpriseMaterialVo> list(String qyid) {
        List<GradedEnterpriseMaterial> list = super.lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(qyid), GradedEnterpriseMaterial::getQyid, qyid)
                .ne(GradedEnterpriseMaterial::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(GradedEnterpriseMaterial::getUpdateTime).list();
        return BeanUtils.copyToList(list, GradedEnterpriseMaterialVo.class);
    }

    @DataBindFieldConvert
    @Override
    public GradedEnterpriseMaterialVo detail(String id) {
        IGradedEnterpriseMaterialService gradedEnterpriseMaterialService = SpringUtil.getBean(this.getClass());
        return BeanUtils.copyByClass(gradedEnterpriseMaterialService.getById(id), GradedEnterpriseMaterialVo.class);
    }

    @DataBindService(strategy = DataBindGradedEnterpriseMaterial.class)
    @Override
    public void dataBind(Collection<DataBindQuery> dataBindQuerys) {
        DataBindQueryCache.dataBindFieldCache(CACHE_NAME, dataBindQuerys, gradedEnterpriseMaterialMapper);
    }

    
    @Override
    public String saveData(GradedEnterpriseMaterialSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseMaterial gradedEnterpriseMaterial = BeanUtils.copyByClass(param, GradedEnterpriseMaterial.class);
        gradedEnterpriseMaterial.setId(IdUtil.simpleUUID());
        gradedEnterpriseMaterial.setCreateBy(loginUser.getName());
        gradedEnterpriseMaterial.setUpdateBy(loginUser.getName());
        gradedEnterpriseMaterial.setUpdateTime(LocalDateTime.now());
        gradedEnterpriseMaterial.setCreateTime(LocalDateTime.now());
        gradedEnterpriseMaterial.setCzbz(CzBzEnum.I.getCzBz());
        super.save(gradedEnterpriseMaterial);
        param.setId(gradedEnterpriseMaterial.getId());
        return gradedEnterpriseMaterial.getId();
    }

    
    @Override
    public String updateData(GradedEnterpriseMaterialSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        GradedEnterpriseMaterial gradedEnterpriseMaterial = BeanUtils.copyByClass(param, GradedEnterpriseMaterial.class);
        gradedEnterpriseMaterial.setCzbz(CzBzEnum.U.getCzBz());
        gradedEnterpriseMaterial.setUpdateBy(loginUser.getName());
        gradedEnterpriseMaterial.setUpdateTime(LocalDateTime.now());
        super.updateById(gradedEnterpriseMaterial);
        param.setId(gradedEnterpriseMaterial.getId());
        return gradedEnterpriseMaterial.getId();
    }

    
    @Override
    public Boolean saveOrUpdateBatchData(List<GradedEnterpriseMaterialSaveParam> params) {
        Boolean returnStatus = true;
        LoginUser loginUser = UserSecurity.loginUser();
        List<GradedEnterpriseMaterial> gradedEnterpriseMaterials = BeanUtils.copyToList(params, GradedEnterpriseMaterial.class);
        if(CollectionUtil.isNotEmpty(gradedEnterpriseMaterials)){
            for (int i = 0; i < gradedEnterpriseMaterials.size(); i++) {
                GradedEnterpriseMaterial gradedEnterpriseMaterial = gradedEnterpriseMaterials.get(i);
                gradedEnterpriseMaterial.setCreateBy(loginUser.getName());
                gradedEnterpriseMaterial.setUpdateBy(loginUser.getName());
                gradedEnterpriseMaterial.setUpdateTime(LocalDateTime.now());
                gradedEnterpriseMaterial.setCreateTime(LocalDateTime.now());
                gradedEnterpriseMaterial.setCzbz(CzBzEnum.I.getCzBz());
                returnStatus = super.saveOrUpdate(gradedEnterpriseMaterial);
            }
        }
        return returnStatus;
    }

    
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(GradedEnterpriseMaterial::getId, ids)
                .set(GradedEnterpriseMaterial::getCzbz, CzBzEnum.D.getCzBz())
                .set(GradedEnterpriseMaterial::getUpdateBy, loginUser.getName())
                .set(GradedEnterpriseMaterial::getUpdateTime, LocalDateTime.now())
                .update();
    }

}