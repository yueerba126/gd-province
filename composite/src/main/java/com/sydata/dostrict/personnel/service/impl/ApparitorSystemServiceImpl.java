package com.sydata.dostrict.personnel.service.impl;

import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.file.config.MinioConfig;
import com.sydata.common.file.domain.FileStorage;
import com.sydata.dostrict.personnel.domain.ApparitorSystem;
import com.sydata.dostrict.personnel.domain.ApparitorSystemZoning;
import com.sydata.dostrict.personnel.enums.SystemBillStatusEnums;
import com.sydata.dostrict.personnel.mapper.ApparitorSystemMapper;
import com.sydata.dostrict.personnel.param.ApparitorSystemPageParam;
import com.sydata.dostrict.personnel.param.ApparitorSystemSaveParam;
import com.sydata.dostrict.personnel.service.IApparitorSystemService;
import com.sydata.dostrict.personnel.service.IApparitorSystemZoningService;
import com.sydata.dostrict.personnel.vo.ApparitorSystemVo;
import com.sydata.dostrict.personnel.vo.ApparitorSystemZoningVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import one.util.streamex.StreamEx;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;

/**
 * 行政管理-人员制度管理Service业务层处理
 *
 * @author fuql
 * @date 2023-04-24
 */
@Service("apparitorSystemService")
public class ApparitorSystemServiceImpl extends ServiceImpl<ApparitorSystemMapper, ApparitorSystem>
        implements IApparitorSystemService {

    @Resource
    private ApparitorSystemMapper apparitorSystemMapper;

    @Resource
    private IApparitorSystemZoningService apparitorSystemZoningService;

    @Resource
    private MinioConfig minioConfig;

    @Resource
    private MinioClient minioClient;


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorSystemSaveParam param) {
        ApparitorSystem main = new ApparitorSystem();
        BeanUtils.copyProperties(param, main);
        main.setBillStatus(SystemBillStatusEnums.SAVE.getCode());
        super.save(main);
        apparitorSystemZoningService.saveData(param.getZoningList(), main.getId());
        return main.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    @DataBindFieldConvert
    @Override
    public Page<ApparitorSystemVo> pageData(ApparitorSystemPageParam param) {
        LoginUser login = UserSecurity.loginUser();
        param.setMainRegionId(login.getRegionId());
        Page<ApparitorSystemVo> page = apparitorSystemMapper
                .pageData(new Page<>(param.getPageNum(), param.getPageSize()), param);
        List<ApparitorSystemVo> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return page;
        }
        List<String> systemIds = StreamEx.of(records)
                .map(ApparitorSystemVo::getId)
                .distinct()
                .toList();
        List<ApparitorSystemZoning> zonings = apparitorSystemZoningService.lambdaQuery()
                .in(ApparitorSystemZoning::getSystemId, systemIds)
                .list();
        records.forEach(sc -> {
            //判断是当前登录组织创建的数据就可以查看下发单位
            getSystemDtl(login, sc, zonings);
        });
        page.setRecords(records);
        return page;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorSystemSaveParam param) {
        LoginUser login = UserSecurity.loginUser();
        ApparitorSystem system = super.getById(param.getId());
        Assert.state(Objects.nonNull(system), "未查询到相应的人员制度数据，请重新选择！");
        Assert.state(login.getRegionId().equals(system.getRegionId()), "非创建组织不能修改！");
        Assert.state(SystemBillStatusEnums.SAVE.getCode().equals(system.getBillStatus()), "不是拟稿状态不能修改！");
        ApparitorSystem main = new ApparitorSystem();
        BeanUtils.copyProperties(param, main);
        super.updateById(main);
        apparitorSystemZoningService.lambdaUpdate()
                .eq(ApparitorSystemZoning::getSystemId, param.getId())
                .remove();
        apparitorSystemZoningService.saveData(param.getZoningList(), main.getId());
        return main.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        List<ApparitorSystem> list = super.lambdaQuery()
                .in(ApparitorSystem::getId, ids)
                .list();
        LoginUser login = UserSecurity.loginUser();
        Assert.state(CollectionUtils.isNotEmpty(list), "未查询到相应的人员制度数据，请重新选择！");
        list.forEach(system -> {
            Assert.state(login.getRegionId().equals(system.getRegionId()), "非创建组织不能删除！");
            Assert.state(SystemBillStatusEnums.SAVE.getCode().equals(system.getBillStatus()), "不是拟稿状态不能删除！");
        });
        return super.lambdaUpdate()
                .in(ApparitorSystem::getId, ids)
                .set(ApparitorSystem::getBillStatus, SystemBillStatusEnums.ABOLISH.getCode())
                .update();
    }

    @DataBindFieldConvert
    @Override
    public ApparitorSystemVo getDataById(Long id) {
        LoginUser login = UserSecurity.loginUser();
        ApparitorSystem system = super.getById(id);
        ApparitorSystemVo sc = new ApparitorSystemVo();
        BeanUtils.copyProperties(system, sc);
        List<ApparitorSystemZoning> zonings = apparitorSystemZoningService.lambdaQuery()
                .eq(ApparitorSystemZoning::getSystemId, id)
                .list();
        getSystemDtl(login, sc, zonings);
        return sc;
    }

    @Override
    public boolean pushDataById(Long id) {
        LoginUser login = UserSecurity.loginUser();
        ApparitorSystem system = super.getById(id);
        Assert.state(Objects.nonNull(system), "未查询到相应的人员制度数据，请重新选择！");
        Assert.state(login.getRegionId().equals(system.getRegionId()), "非创建组织不能发布！");
        Assert.state(SystemBillStatusEnums.SAVE.getCode().equals(system.getBillStatus()), "不是拟稿状态不能发布！");
        return super.lambdaUpdate()
                .eq(ApparitorSystem::getId, id)
                .set(ApparitorSystem::getBillStatus, SystemBillStatusEnums.RELEASE.getCode())
                .set(ApparitorSystem::getReleaseId, login.getId())
                .set(ApparitorSystem::getReleaseTime, LocalDateTime.now())
                .update();
    }

    private void getSystemDtl(LoginUser login
            , ApparitorSystemVo sc
            , List<ApparitorSystemZoning> zonings) {
        boolean reStatus = login.getRegionId().equals(sc.getRegionId());
        sc.setZoningList(reStatus ? StreamEx.of(zonings)
                .filter(o -> sc.getId().equals(o.getSystemId()))
                .map(v -> BeanUtils.copyByClass(v, ApparitorSystemZoningVo.class))
                .toList() : new ArrayList<>());
    }


    @SneakyThrows(Throwable.class)
    @Override
    public void download(String fileId) {
        FileStorage fileStorage = apparitorSystemMapper.queryFileStorage(fileId);
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
