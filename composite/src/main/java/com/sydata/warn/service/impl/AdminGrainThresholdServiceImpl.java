package com.sydata.warn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import com.sydata.warn.domain.AdminGrainThreshold;
import com.sydata.warn.mapper.AdminGrainThresholdMapper;
import com.sydata.warn.service.IAdminGrainThresholdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 粮情预警阈值Service业务层处理
 *
 * @author fuql
 * @date 2023-05-09
 */
@Service("adminGrainThresholdService")
public class AdminGrainThresholdServiceImpl extends ServiceImpl<AdminGrainThresholdMapper, AdminGrainThreshold> implements IAdminGrainThresholdService {

    @Resource
    private AdminGrainThresholdMapper adminGrainThresholdMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(AdminGrainThreshold param) {
        LoginUser loginUser = UserSecurity.loginUser();
        LocalDateTime now = LocalDateTime.now();
        param.setCzbz(CzBzEnum.I.getCzBz());
        param.setCreateBy(loginUser.getName());
        param.setZhgxsj(now);
        super.save(param);
        return param.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(AdminGrainThreshold param) {
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
                .in(AdminGrainThreshold::getId, ids)
                .set(AdminGrainThreshold::getCzbz, CzBzEnum.D.getCzBz())
                .set(AdminGrainThreshold::getUpdateBy, loginUser.getName())
                .set(AdminGrainThreshold::getUpdateTime, LocalDateTime.now())
                .update();
    }

    @DataBindFieldConvert
    @Override
    public AdminGrainThreshold getDataById(Long id) {
        return getById(id);
    }
}
