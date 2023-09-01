package com.sydata.warn.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.warn.domain.AdminPlanThreshold;
import com.sydata.warn.mapper.AdminPlanThresholdMapper;
import com.sydata.warn.param.AdminPlanThresholdPageParam;
import com.sydata.warn.service.IAdminPlanThresholdService;
import com.sydata.warn.vo.AdminPlanThresholdVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 储备计划阈值设置Service业务层处理
 *
 * @author fuql
 * @date 2023-05-09
 */
@Service("adminPlanThresholdService")
public class AdminPlanThresholdServiceImpl extends ServiceImpl<AdminPlanThresholdMapper, AdminPlanThreshold> implements IAdminPlanThresholdService {

    @Resource
    private AdminPlanThresholdMapper adminPlanThresholdMapper;


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(AdminPlanThreshold param) {
        LoginUser loginUser = UserSecurity.loginUser();
        LocalDateTime now = LocalDateTime.now();
        param.setCzbz(CzBzEnum.I.getCzBz());
        param.setCreateBy(loginUser.getName());
        param.setZhgxsj(now);
        super.save(param);
        return param.getId();
    }

    @DataBindFieldConvert
    @Override
    public Page<AdminPlanThresholdVo> pageData(AdminPlanThresholdPageParam param) {
        Page<AdminPlanThreshold> page = super.lambdaQuery()
                .ge(isNotEmpty(param.getBeginCreateTime()), AdminPlanThreshold::getCreateTime, param.getBeginCreateTime())
                .le(isNotEmpty(param.getEndCreateTime()), AdminPlanThreshold::getCreateTime, param.getEndCreateTime())
                .ne(AdminPlanThreshold::getCzbz, CzBzEnum.D.getCzBz())
                .eq(isNotEmpty(param.getXzqhdm()), AdminPlanThreshold::getXzqhdm, param.getXzqhdm())
                .orderByDesc(AdminPlanThreshold::getCreateTime)
                .page(new Page<>(param.getPageNum(), param.getPageSize()));
        return BeanUtils.copyToPage(page, AdminPlanThresholdVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(AdminPlanThreshold param) {
        LoginUser loginUser = UserSecurity.loginUser();
        LocalDateTime now = LocalDateTime.now();
        param.setCzbz(CzBzEnum.U.getCzBz());
        param.setCreateBy(loginUser.getName());
        param.setZhgxsj(now);
        super.updateById(param);
        return param.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(AdminPlanThreshold::getId, ids)
                .set(AdminPlanThreshold::getCzbz, CzBzEnum.D.getCzBz())
                .set(AdminPlanThreshold::getUpdateBy, loginUser.getName())
                .set(AdminPlanThreshold::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @DataBindFieldConvert
    @Override
    public AdminPlanThresholdVo getDataById(Long id) {
        return BeanUtils.copyByClass(getById(id), AdminPlanThresholdVo.class);
    }
}
