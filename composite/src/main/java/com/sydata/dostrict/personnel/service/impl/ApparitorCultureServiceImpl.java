package com.sydata.dostrict.personnel.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.dostrict.personnel.domain.ApparitorCulture;
import com.sydata.dostrict.personnel.enums.CultureBillStatusEnums;
import com.sydata.dostrict.personnel.enums.SystemBillStatusEnums;
import com.sydata.dostrict.personnel.mapper.ApparitorCultureMapper;
import com.sydata.dostrict.personnel.param.ApparitorCulturePageParam;
import com.sydata.dostrict.personnel.service.IApparitorCultureService;
import com.sydata.dostrict.personnel.vo.ApparitorCultureVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 行政管理-人才培养计划Service业务层处理
 *
 * @author fuql
 * @date 2023-04-25
 */
@Service("apparitorCultureService")
public class ApparitorCultureServiceImpl extends ServiceImpl<ApparitorCultureMapper, ApparitorCulture> implements IApparitorCultureService {

    @Resource
    private ApparitorCultureMapper apparitorCultureMapper;

    @Override
    public String saveData(ApparitorCulture param) {
        param.setBillStatus(SystemBillStatusEnums.SAVE.getCode());
        super.save(param);
        return param.getId();
    }

    @DataBindFieldConvert
    @Override
    public Page<ApparitorCultureVo> pageData(ApparitorCulturePageParam param) {
        Page<ApparitorCulture> page = super.lambdaQuery()
                .ge(isNotEmpty(param.getBeginCreateTime()), ApparitorCulture::getCreateTime, param.getBeginCreateTime())
                .le(isNotEmpty(param.getEndCreateTime()), ApparitorCulture::getCreateTime, param.getEndCreateTime())
                .ge(isNotEmpty(param.getBeginApprovedTime()), ApparitorCulture::getApprovedTime, param.getBeginApprovedTime())
                .le(isNotEmpty(param.getEndApprovedTime()), ApparitorCulture::getApprovedTime, param.getEndApprovedTime())
                .eq(isNotEmpty(param.getEnterpriseId()), ApparitorCulture::getEnterpriseId, param.getEnterpriseId())
                .eq(isNotEmpty(param.getDeptId()), ApparitorCulture::getDeptId, param.getDeptId())
                .ne(ApparitorCulture::getBillStatus, CultureBillStatusEnums.ABOLISH.getCode())
                .like(isNotEmpty(param.getFileName()), ApparitorCulture::getFileName, param.getFileName())
                .orderByDesc(ApparitorCulture::getUpdateTime)
                .page(new Page<>(param.getPageNum(), param.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorCultureVo.class);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorCulture param) {
        super.updateById(param);
        return param.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorCulture::getId, ids)
                .set(ApparitorCulture::getBillStatus, CultureBillStatusEnums.ABOLISH.getCode())
                .set(ApparitorCulture::getUpdateBy, loginUser.getName())
                .set(ApparitorCulture::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @DataBindFieldConvert
    @Override
    public ApparitorCultureVo getDataById(Long id) {
        return BeanUtils.copyByClass(getById(id), ApparitorCultureVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean submitDataByParam(ApparitorCulture param) {
        param.setBillStatus(CultureBillStatusEnums.RELEASE.getCode());
        return super.updateById(param);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean revocation(Long id) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorCulture culture = super.getById(id);
        Assert.state(Objects.nonNull(culture), "未查询到相应的数据，请重新选择！");
        Assert.state(loginUser.getRegionId().equals(culture.getRegionId()), "非创建组织不能进行操作！");
        Assert.state(CultureBillStatusEnums.RELEASE.getCode().equals(culture.getBillStatus()), "不是待审核状态不能操作！");
        culture.setBillStatus(CultureBillStatusEnums.SAVE.getCode());
        return super.updateById(culture);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean unAudit(Long id) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorCulture culture = super.getById(id);
        Assert.state(Objects.nonNull(culture), "未查询到相应的数据，请重新选择！");
        Assert.state(loginUser.getRegionId().equals(culture.getRegionId()), "非创建组织不能进行操作！");
        Assert.state(CultureBillStatusEnums.AUDIT.getCode().equals(culture.getBillStatus()), "不是审核状态不能操作！");
        culture.setBillStatus(CultureBillStatusEnums.SAVE.getCode());
        return super.updateById(culture);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean audit(Long id) {
        LoginUser loginUser = UserSecurity.loginUser();
        ApparitorCulture culture = super.getById(id);
        Assert.state(Objects.nonNull(culture), "未查询到相应的数据，请重新选择！");
        Assert.state(loginUser.getRegionId().equals(culture.getRegionId()), "非创建组织不能审核！");
        Assert.state(CultureBillStatusEnums.RELEASE.getCode().equals(culture.getBillStatus()), "不是待审核状态不能操作！");
        culture.setBillStatus(CultureBillStatusEnums.AUDIT.getCode())
                .setApprovedName(loginUser.getName())
                .setApprovedTime(LocalDateTime.now());
        return super.updateById(culture);
    }

}
