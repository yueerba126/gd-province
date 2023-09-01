package com.sydata.dostrict.personnel.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.common.api.enums.CzBzEnum;
import com.sydata.dostrict.personnel.domain.ApparitorTitle;
import com.sydata.dostrict.personnel.mapper.ApparitorTitleMapper;
import com.sydata.dostrict.personnel.param.ApparitorTitlePageParam;
import com.sydata.dostrict.personnel.service.IApparitorTitleService;
import com.sydata.dostrict.personnel.vo.ApparitorTitleVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 行政管理-荣誉称号管理Service业务层处理
 *
 * @author fuql
 * @date 2023-04-25
 */
@Service("apparitorTitleService")
public class ApparitorTitleServiceImpl extends ServiceImpl<ApparitorTitleMapper, ApparitorTitle> implements IApparitorTitleService {

    @Resource
    private ApparitorTitleMapper apparitorTitleMapper;


    @DataBindFieldConvert
    @Override
    public Page<ApparitorTitleVo> pages(ApparitorTitlePageParam pageParam) {
        Page<ApparitorTitle> page = super.lambdaQuery()
                .ge(isNotEmpty(pageParam.getBeginUpdateTime()), ApparitorTitle::getUpdateTime, pageParam.getBeginUpdateTime())
                .le(isNotEmpty(pageParam.getEndUpdateTime()), ApparitorTitle::getUpdateTime, pageParam.getEndUpdateTime())
                .eq(isNotEmpty(pageParam.getEnterpriseId()), ApparitorTitle::getEnterpriseId, pageParam.getEnterpriseId())
                .eq(isNotEmpty(pageParam.getDeptId()), ApparitorTitle::getDeptId, pageParam.getDeptId())
                .eq(isNotEmpty(pageParam.getTitleTypeId()), ApparitorTitle::getTitleTypeId, pageParam.getTitleTypeId())
                .ne(ApparitorTitle::getCzbz, CzBzEnum.D.getCzBz())
                .orderByDesc(ApparitorTitle::getUpdateTime)
                .page(new Page<>(pageParam.getPageNum(), pageParam.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorTitleVo.class);
    }

    @DataBindFieldConvert
    @Override
    public ApparitorTitleVo detail(String id) {
        return BeanUtils.copyByClass(getById(id), ApparitorTitleVo.class);
    }

    @Override
    public String saveData(ApparitorTitle param) {
        param.setCzbz(CzBzEnum.I.getCzBz());
        super.save(param);
        param.setId(param.getId());
        return param.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String updateData(ApparitorTitle param) {
        super.updateById(param);
        return param.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Boolean removeData(List<String> ids) {
        LoginUser loginUser = UserSecurity.loginUser();
        return super.lambdaUpdate()
                .in(ApparitorTitle::getId, ids)
                .set(ApparitorTitle::getCzbz, CzBzEnum.D.getCzBz())
                .set(ApparitorTitle::getUpdateBy, loginUser.getName())
                .set(ApparitorTitle::getUpdateTime, LocalDateTime.now())
                .update();
    }
}
