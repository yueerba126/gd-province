package com.sydata.dostrict.casetarget.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sydata.dostrict.casetarget.domain.ApparitorCase;
import com.sydata.dostrict.casetarget.mapper.ApparitorCaseMapper;
import com.sydata.dostrict.casetarget.param.ApparitorCasePageParam;
import com.sydata.dostrict.casetarget.param.ApparitorCaseSavaParam;
import com.sydata.dostrict.casetarget.service.IApparitorCaseService;
import com.sydata.dostrict.casetarget.vo.ApparitorCaseVo;
import com.sydata.framework.databind.annotation.DataBindFieldConvert;
import com.sydata.framework.util.BeanUtils;
import com.sydata.organize.security.LoginUser;
import com.sydata.organize.security.UserSecurity;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

/**
 * 行政管理-执法案件Service业务层处理
 *
 * @author fuql
 * @date 2023-04-26
 */
@Service("apparitorCaseService")
public class ApparitorCaseServiceImpl extends ServiceImpl<ApparitorCaseMapper, ApparitorCase> implements IApparitorCaseService {

    @Resource
    private ApparitorCaseMapper apparitorCaseMapper;


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveData(ApparitorCaseSavaParam param) {
        ApparitorCase apparitorCase = BeanUtils.copyByClass(param, ApparitorCase.class);
        super.save(apparitorCase);
        return apparitorCase.getId();
    }

    @DataBindFieldConvert
    @Override
    public Page<ApparitorCaseVo> pageData(ApparitorCasePageParam param) {
        Page<ApparitorCase> page = super.lambdaQuery()
                .eq(isNotEmpty(param.getCaseEnterpriseId()), ApparitorCase::getCaseEnterpriseId, param.getCaseEnterpriseId())
                .ge(isNotEmpty(param.getBeginCaseDate()), ApparitorCase::getCaseDate, param.getBeginCaseDate())
                .le(isNotEmpty(param.getEndCaseDate()), ApparitorCase::getCaseDate, param.getEndCaseDate())
                .eq(isNotEmpty(param.getCaseType()), ApparitorCase::getCaseType, param.getCaseType())
                .eq(isNotEmpty(param.getCaseSource()), ApparitorCase::getCaseSource, param.getCaseSource())
                .like(isNotEmpty(param.getCaseName()), ApparitorCase::getCaseName, param.getCaseName())
                .orderByDesc(ApparitorCase::getUpdateTime)
                .page(new Page<>(param.getPageNum(), param.getPageSize()));
        return BeanUtils.copyToPage(page, ApparitorCaseVo.class);
    }

    @Override
    public String updateData(ApparitorCase param) {
        LoginUser login = UserSecurity.loginUser();
        ApparitorCase apparitorCase = super.getById(param.getId());
        Assert.state(Objects.nonNull(apparitorCase), "未查询到相应的执法案件数据，请重新选择！");
        Assert.state(login.getRegionId().equals(apparitorCase.getRegionId()), "非创建组织不能修改！");
        super.updateById(param);
        return param.getId();
    }

    @Override
    public Boolean removeData(List<String> ids) {
        List<ApparitorCase> list = super.lambdaQuery()
                .in(ApparitorCase::getId, ids)
                .list();
        LoginUser login = UserSecurity.loginUser();
        Assert.state(CollectionUtils.isNotEmpty(list), "未查询到相应的执法案件数据，请重新选择！");
        list.forEach(system -> {
            Assert.state(login.getRegionId().equals(system.getRegionId()), "非创建组织不能删除！");
        });
        return super.lambdaUpdate()
                .in(ApparitorCase::getId, ids)
                .remove();
    }

    @DataBindFieldConvert
    @Override
    public ApparitorCaseVo getDataById(Long id) {
        return BeanUtils.copyByClass(getById(id), ApparitorCaseVo.class);
    }
}
