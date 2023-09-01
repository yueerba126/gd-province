package com.sydata.warn.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.warn.domain.AdminAgeThreshold;
import com.sydata.warn.mapper.AdminAgeThresholdMapper;
import com.sydata.warn.param.AdminAgeThresholdPageParam;
import com.sydata.warn.service.IAdminAgeThresholdService;
import com.sydata.warn.vo.AdminAgeThresholdVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 库存年限告警阈值设置Service业务层处理
 *
 * @author fuql
 * @date 2023-05-09
 */
@Service("adminAgeThresholdService")
public class AdminAgeThresholdServiceImpl extends ServiceImpl<AdminAgeThresholdMapper, AdminAgeThreshold> implements IAdminAgeThresholdService {

    @Resource
    private AdminAgeThresholdMapper adminAgeThresholdMapper;


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(AdminAgeThreshold param) {
        LoginUser loginUser = UserSecurity.loginUser();
        LocalDateTime now = LocalDateTime.now();
        param.setCzbz(CzBzEnum.I.getCzBz())
                .setCreateBy(loginUser.getName())
                .setZhgxsj(now);
        super.save(param);
        return param.getId();
    }

    @DataBindFieldConvert
    @Override
    public Page<AdminAgeThresholdVo> pageData(AdminAgeThresholdPageParam param) {
        Page<AdminAgeThreshold> page = super.lambdaQuery()
                .ge(isNotEmpty(param.getBeginCreateTime()), AdminAgeThreshold::getCreateTime, param.getBeginCreateTime())
                .le(isNotEmpty(param.getEndCreateTime()), AdminAgeThreshold::getCreateTime, param.getEndCreateTime())
                .ne(AdminAgeThreshold::getCzbz, CzBzEnum.D.getCzBz())
                .eq(isNotEmpty(param.getYlpz()), AdminAgeThreshold::getYlpz, param.getYlpz())
                .orderByDesc(AdminAgeThreshold::getCreateTime)
                .page(new Page<>(param.getPageNum(), param.getPageSize()));
        return BeanUtils.copyToPage(page, AdminAgeThresholdVo.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(AdminAgeThreshold param) {
        LoginUser loginUser = UserSecurity.loginUser();
        LocalDateTime now = LocalDateTime.now();
        param.setCzbz(CzBzEnum.U.getCzBz())
                .setCreateBy(loginUser.getName())
                .setZhgxsj(now);
        super.updateById(param);
        return param.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(AdminAgeThreshold::getId, ids)
                .set(AdminAgeThreshold::getCzbz, CzBzEnum.D.getCzBz())
                .set(AdminAgeThreshold::getUpdateBy, loginUser.getName())
                .set(AdminAgeThreshold::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @DataBindFieldConvert
    @Override
    public AdminAgeThresholdVo getDataById(Long id) {
        return BeanUtils.copyByClass(getById(id), AdminAgeThresholdVo.class);
    }
}
