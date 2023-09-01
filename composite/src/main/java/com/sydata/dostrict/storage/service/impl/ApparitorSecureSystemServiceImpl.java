/**
 * @filename:ApparitorSecureSystemBeanServiceImpl 2023年04月27日
 * @project gd-province-platform  V1.0
 * Copyright(c) 2018 lzq Co. Ltd. 
 * All right reserved. 
 */
package com.sydata.dostrict.storage.service.impl;

import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.common.file.config.MinioConfig;
import com.sydata.common.file.domain.FileStorage;
import com.sydata.dostrict.personnel.enums.SystemBillStatusEnums;
import com.sydata.dostrict.storage.domain.ApparitorSecureSystem;
import com.sydata.dostrict.storage.mapper.ApparitorSecureSystemMapper;
import com.sydata.dostrict.storage.param.ApparitorSecureSystemPageParam;
import com.sydata.dostrict.storage.param.ApparitorSecureSystemSaveParam;
import com.sydata.dostrict.storage.service.IApparitorSecureSystemService;
import com.sydata.dostrict.storage.vo.ApparitorSecureSystemVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

/**   
 * @Description:TODO(安全仓储-安全生产-生产制度服务实现)
 *
 * @version: V1.0
 * @author: lzq
 * 
 */
@Service
public class ApparitorSecureSystemServiceImpl
        extends ServiceImpl<ApparitorSecureSystemMapper, ApparitorSecureSystem>
        implements IApparitorSecureSystemService {

    @Resource
    private ApparitorSecureSystemMapper apparitorSecureSystemMapper;

    @Resource
    private MinioConfig minioConfig;

    @Resource
    private MinioClient minioClient;
    
    @Override
    @DataBindFieldConvert
    public Page<ApparitorSecureSystemVo> pages(ApparitorSecureSystemPageParam pageParam) {
        Page<ApparitorSecureSystem> page = super.lambdaQuery()
                .eq(isNotEmpty(pageParam.getDwdm()), ApparitorSecureSystem::getDwdm, pageParam.getDwdm())
                .eq(isNotEmpty(pageParam.getTypeId()), ApparitorSecureSystem::getTypeId, pageParam.getTypeId())
                .like(isNotEmpty(pageParam.getFileName()), ApparitorSecureSystem::getFileName, pageParam.getFileName())
                .ge(isNotEmpty(pageParam.getBeginFbsj()), ApparitorSecureSystem::getReleaseTime, pageParam.getBeginFbsj())
                .le(isNotEmpty(pageParam.getEndFbsj()), ApparitorSecureSystem::getReleaseTime, pageParam.getEndFbsj())
                .ne(ApparitorSecureSystem::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorSecureSystem::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorSecureSystemVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorSecureSystemVo detail(String id) {
        return BeanUtils.copyByClass(getById(id), ApparitorSecureSystemVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorSecureSystemSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorSecureSystem apparitorSecureSystem = BeanUtils.copyByClass(param, ApparitorSecureSystem.class);
        apparitorSecureSystem.setId(IdUtil.simpleUUID());
        apparitorSecureSystem.setCzbz(CzBzEnum.I.getCzBz());
        apparitorSecureSystem.setCreateBy(loginUser.getName());
        apparitorSecureSystem.setUpdateBy(loginUser.getName());
        apparitorSecureSystem.setUpdateTime(LocalDateTime.now());
        apparitorSecureSystem.setCreateTime(LocalDateTime.now());
        // apparitorSecureSystem.setReleaseId(loginUser.getId());
        // apparitorSecureSystem.setReleaseTime(LocalDateTime.now());
        apparitorSecureSystem.setBillStatus(SystemBillStatusEnums.SAVE.getCode());
        super.save(apparitorSecureSystem);
        param.setId(apparitorSecureSystem.getId());
        return apparitorSecureSystem.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorSecureSystemSaveParam param) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorSecureSystem apparitorSecureSystem = BeanUtils.copyByClass(param, ApparitorSecureSystem.class);
        apparitorSecureSystem.setCzbz(CzBzEnum.U.getCzBz());
        apparitorSecureSystem.setUpdateBy(loginUser.getName());
        apparitorSecureSystem.setUpdateTime(LocalDateTime.now());
        // apparitorSecureSystem.setReleaseId(loginUser.getId());
        // apparitorSecureSystem.setReleaseTime(LocalDateTime.now());
        super.updateById(apparitorSecureSystem);
        param.setId(apparitorSecureSystem.getId());
        return apparitorSecureSystem.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorSecureSystem::getId, ids)
                .set(ApparitorSecureSystem::getCzbz , CzBzEnum.D.getCzBz())
                .set(ApparitorSecureSystem::getUpdateBy ,loginUser.getName())
                .set(ApparitorSecureSystem::getUpdateTime ,LocalDateTime.now())
                .update();
    }

    @Override
    public boolean pushDataById(String id) {
        LoginUser login = UserSecurity.loginUser();
        ApparitorSecureSystem system = super.getById(id);
        Assert.state(Objects.nonNull(system), "未查询到相应的生产制度数据，请重新选择！");
        Assert.state(login.getRegionId().equals(system.getRegionId()), "非创建组织不能发布！");
        Assert.state(SystemBillStatusEnums.SAVE.getCode().equals(system.getBillStatus()), "不是拟稿状态不能发布！");
        return super.lambdaUpdate()
                .eq(ApparitorSecureSystem::getId, id)
                .set(ApparitorSecureSystem::getBillStatus, SystemBillStatusEnums.RELEASE.getCode())
                .set(ApparitorSecureSystem::getReleaseId, login.getId())
                .set(ApparitorSecureSystem::getReleaseTime, LocalDateTime.now())
                .update();
    }

    @SneakyThrows(Throwable.class)
    @Override
    public void download(String fileId) {
        FileStorage fileStorage = apparitorSecureSystemMapper.queryFileStorage(fileId);
        // 设置响应contentType
        HttpServletResponse response = SpringMVCUtil.getResponse();
        String contentType = MediaTypeFactory
                .getMediaType(fileStorage.getStorageName())
                .orElse(APPLICATION_OCTET_STREAM)
                .toString();
        response.setContentType(contentType);

        // 写出文件流
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = fileStream(fileStorage.getStorageName());
            outputStream = response.getOutputStream();
            IoUtil.copy(inputStream, outputStream);
        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(inputStream);
        }
    }

    /**
     * 获取文件流
     *
     * @param storageName 文件存储名称
     * @return 文件流
     */
    @SneakyThrows(Throwable.class)
    private InputStream fileStream(String storageName) {
        // 读取文件流
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(storageName)
                .build();
        return minioClient.getObject(args);
    }
}