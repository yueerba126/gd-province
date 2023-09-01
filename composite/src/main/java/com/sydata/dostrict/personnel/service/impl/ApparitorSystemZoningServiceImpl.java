package com.sydata.dostrict.personnel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.dostrict.personnel.domain.ApparitorSystemZoning;
import com.sydata.dostrict.personnel.mapper.ApparitorSystemZoningMapper;
import com.sydata.dostrict.personnel.service.IApparitorSystemZoningService;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 行政管理-人员制度管理管理行政区划Service业务层处理
 *
 * @author fuql
 * @date 2023-04-24
 */
@Service("apparitorSystemZoningService")
public class ApparitorSystemZoningServiceImpl
        extends ServiceImpl<ApparitorSystemZoningMapper, ApparitorSystemZoning> implements IApparitorSystemZoningService {

    @Resource
    private ApparitorSystemZoningMapper apparitorSystemZoningMapper;

    @Override
    public void saveData(List<ApparitorSystemZoning> zoningList, String id) {
        LoginUser loginUser = UserSecurity.loginUser();
        LocalDateTime now = LocalDateTime.now();
        if (CollectionUtils.isNotEmpty(zoningList)) {
            zoningList.forEach(sc -> {
                sc.setSystemId(id)
                        .setCreateBy(loginUser.getName())
                        .setUpdateBy(loginUser.getName())
                        .setCreateTime(now)
                        .setUpdateTime(now);
            });
            super.saveBatch(zoningList);
        }
    }
}
